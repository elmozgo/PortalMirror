package org.portalmirror.twitterfeed.core.logic
import com.google.common.cache.LoadingCache
import org.portalmirror.twitterfeed.core.domain.TwitterFeed
import org.portalmirror.twitterfeed.core.domain.TwitterFeedEntry


class TwitterFeedService(val twitterFeedCache: LoadingCache<String, TwitterFeed>) {


    fun getFeed(screenNames : Set<String>): List<TwitterFeed> {

        return screenNames.map { name -> twitterFeedCache.get(name) }

                //flatMap { name -> twitterFeedCache.get(name) ?: TwitterFeed(emptyList()) }
    }

    fun loadReplies(screenName: String, statusId : Long): List<TwitterFeedEntry>{

        val feed = twitterFeedCache.get(screenName)
        val entry = feed.findEntry(statusId) ?: throw TwitterFeedException("no status id: ${statusId} in feed of screenname: ${screenName}")

        entry.refreshReplies()

        //update the cached value
        twitterFeedCache.put(screenName, feed)

        return entry.replies

    }

}

class TwitterFeedException(message : String) : IllegalArgumentException(message)

