package org.portalmirror.twitterfeed.core

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.portalmirror.twitterfeed.core.domain.TwitterFeedEntry
import org.portalmirror.twitterfeed.core.logic.TwitterFeedFactory
import org.portalmirror.twitterfeed.core.logic.TwitterRepository
import java.util.*

@RunWith(JUnitPlatform::class)
class TwitterFeedFactorySpec : Spek({

    describe("a TwitterFeedFactory") {

        val repository : TwitterRepository = mock(TwitterRepository::class.java)
        val factory =  TwitterFeedFactory(repository, 2)

        context("TwitterRepositoryMock for 'twitterapi' returns a status hierarchy with depth of 2") {

            val screenName = "twitterapi"

            `when`(repository.getTop20StatusesFromTimeline(screenName)).thenReturn(Arrays.asList(rootStatus1, rootStatus2, rootStatus3))
            `when`(repository.getAllRepliesToStatus(rootStatus1)).thenReturn(Arrays.asList(replyTo_rootStatus1))
            `when`(repository.getAllRepliesToStatus(replyTo_rootStatus1)).thenReturn(Arrays.asList(replyTo_replyTo_rootStatus1))
            `when`(repository.getAllRepliesToStatus(replyTo_replyTo_rootStatus1)).thenReturn(Arrays.asList(replyTo_replyTo_replyTo_rootStatus1))

            on("buildFeed()") {
                val feed = factory.buildFeed(screenName)

                val repliesTo_Status1 : List<TwitterFeedEntry> = feed.feedEntries.find { e -> e.status == rootStatus1 }!!.replies
                val repliesTo_repliesTo_Status1 : List<TwitterFeedEntry> = repliesTo_Status1.flatMap { e -> e.replies }
                val repliesTo_repliesTo_repliesTo_Status1 : List<TwitterFeedEntry> = repliesTo_repliesTo_Status1.flatMap { e -> e.replies }

                it("should eager load 2 levels of replies") {

                    assertThat(repliesTo_Status1).hasSize(1)
                    assertThat(repliesTo_Status1[0].status).isEqualTo(replyTo_rootStatus1)

                    assertThat(repliesTo_repliesTo_Status1).hasSize(1)
                    assertThat(repliesTo_repliesTo_Status1[0].status).isEqualTo(replyTo_replyTo_rootStatus1)

                    assertThat(repliesTo_repliesTo_repliesTo_Status1).hasSize(0)
                }

            }

            on("buildFeed() and reloading refreshReplies() on level 2 replies") {
                val feed = factory.buildFeed(screenName)

                val repliesTo_Status1 : List<TwitterFeedEntry> = feed.feedEntries.find { e -> e.status == rootStatus1 }!!.replies
                val repliesTo_repliesTo_Status1 : List<TwitterFeedEntry> = repliesTo_Status1.flatMap { e -> e.replies }

                repliesTo_repliesTo_Status1.forEach { e -> e.refreshReplies() }
                val repliesTo_repliesTo_repliesTo_Status1 : List<TwitterFeedEntry> = repliesTo_repliesTo_Status1.flatMap { e -> e.replies }

                it("should load level 3 entries") {
                    assertThat(repliesTo_repliesTo_repliesTo_Status1).hasSize(1)
                    assertThat(repliesTo_repliesTo_repliesTo_Status1[0].status).isEqualTo(replyTo_replyTo_replyTo_rootStatus1)
                }
            }

        }
    }

})

