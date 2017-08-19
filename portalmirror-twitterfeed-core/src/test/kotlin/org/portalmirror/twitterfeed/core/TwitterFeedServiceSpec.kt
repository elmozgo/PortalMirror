package org.portalmirror.twitterfeed.core

import com.google.common.base.Ticker
import com.google.common.cache.CacheBuilder
import com.google.common.cache.LoadingCache
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.portalmirror.twitterfeed.core.domain.TwitterFeed
import org.portalmirror.twitterfeed.core.domain.TwitterFeedEntry
import org.portalmirror.twitterfeed.core.logic.TwitterFeedFactory
import org.portalmirror.twitterfeed.core.logic.TwitterFeedService
import org.portalmirror.twitterfeed.core.logic.TwitterFeedSimpleCacheLoader
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong

@RunWith(JUnitPlatform::class)
class TwitterFeedServiceSpec  : Spek({
    describe("a TwitterFeedServiceSpec") {

        val factory = mock(TwitterFeedFactory::class.java)
        val loader = TwitterFeedSimpleCacheLoader(factory)

        context("with cache of size 3 that expires after 1 minute") {

            val ticker = object : Ticker(){

                val nanos : AtomicLong = AtomicLong()

                override fun read(): Long {
                    return nanos.get()
                }

            }

            val cache : LoadingCache<String, TwitterFeed> = CacheBuilder.newBuilder().maximumSize(3).expireAfterWrite(1, TimeUnit.MINUTES).ticker(ticker).build(loader)

            context("with TwitterFeedFactory building mocked feeds") {

                val name1Feed = mock(TwitterFeed::class.java)
                val name2Feed = mock(TwitterFeed::class.java)
                val name3Feed = mock(TwitterFeed::class.java)
                val name3Feed2 = mock(TwitterFeed::class.java)
                val name4Feed = mock(TwitterFeed::class.java)
                val name4Feed2 = mock(TwitterFeed::class.java)

                val name5Feed = mock(TwitterFeed::class.java)
                val name5Entry = mock(TwitterFeedEntry::class.java)

                `when`(factory.getFeed("name1")).thenReturn(name1Feed)
                `when`(factory.getFeed("name2")).thenReturn(name2Feed)
                `when`(factory.getFeed("name3")).thenReturn(name3Feed, name3Feed2)
                `when`(factory.getFeed("name4")).thenReturn(name4Feed, name4Feed2)

                `when`(factory.getFeed("name5")).thenReturn(name5Feed)
                `when`(name5Feed.findEntry(1)).thenReturn(name5Entry)

                val service = TwitterFeedService(cache)

                on("get feed for name1 for the first time") {
                    val feed = service.getFeed(Arrays.asList("name1").toSet())

                    it("should load the feed for name1") {
                        assertThat(feed).containsOnly(name1Feed)
                        verify(factory, times(1)).getFeed("name1")
                    }

                }

                on("get feed for name1 for the second time") {
                    val feed = service.getFeed(Arrays.asList("name1").toSet())

                    it("should return the cached feed for name1") {
                        assertThat(feed).containsOnly(name1Feed)
                        verify(factory, times(1)).getFeed("name1")
                    }

                }

                on("get feed for name2 for the first time") {
                    val feed = service.getFeed(Arrays.asList("name2").toSet())

                    it("should load the feed for name2") {
                        assertThat(feed).containsOnly(name2Feed)
                        verify(factory, times(1)).getFeed("name2")
                    }

                }

                on("get feed for name2 for the second time") {
                    val feed = service.getFeed(Arrays.asList("name2").toSet())

                    it("should return the cached feed for name2") {
                        assertThat(feed).containsOnly(name2Feed)
                        verify(factory, times(1)).getFeed("name2")
                    }

                }

                on("get feed for name3 and name4 for the first time") {
                    val feed = service.getFeed(Arrays.asList("name3", "name4").toSet())

                    it("should load the feed for name3 and name4") {
                        assertThat(feed).containsOnly(name3Feed, name4Feed)
                        verify(factory, times(1)).getFeed("name3")
                        verify(factory, times(1)).getFeed("name4")
                    }

                }

                on("get feed for name3 and name4 for the second time") {
                    val feed = service.getFeed(Arrays.asList("name3", "name4").toSet())

                    it("should return the cached feed for name3 and name4") {
                        assertThat(feed).containsOnly(name3Feed, name4Feed)
                        verify(factory, times(1)).getFeed("name3")
                        verify(factory, times(1)).getFeed("name4")
                    }

                }

                on("get feed for name1 for the third time after overflowing the cache") {
                    val feed = service.getFeed(Arrays.asList("name1").toSet())

                    it("should load the feed for name1") {
                        assertThat(feed).containsOnly(name1Feed)
                        verify(factory, times(2)).getFeed("name1")
                    }

                }

                on("get feed for name3 and name4 after cache expiry") {

                    ticker.nanos.addAndGet(TimeUnit.MINUTES.toNanos(1))

                    val feed = service.getFeed(Arrays.asList("name3", "name4").toSet())

                    it("should return load the feed for name3 and name4") {
                        assertThat(feed).containsOnly(name3Feed2, name4Feed2)
                        verify(factory, times(2)).getFeed("name3")
                        verify(factory, times(2)).getFeed("name4")
                    }
                }

                on("load replies an id for not cached name5") {

                    service.loadReplies("name5", 1)

                    it("should load replies") {
                        verify(factory, times(1)).getFeed("name5")
                        verify(name5Feed, times(1)).findEntry(1)
                        verify(name5Entry, times(1)).refreshReplies()
                    }

                }

                on("load replies an id for cached name5") {

                    service.loadReplies("name5", 1)

                    it("should load replies") {
                        verify(factory, times(1)).getFeed("name5")
                        verify(name5Feed, times(2)).findEntry(1)
                        verify(name5Entry, times(2)).refreshReplies()
                    }

                }



            }

        }
    }
})