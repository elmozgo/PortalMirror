package org.portalmirror.twitterfeed.core.logic

import com.google.common.cache.CacheLoader
import org.portalmirror.twitterfeed.core.domain.TwitterFeed

class TwitterFeedSimpleCacheLoader(private val twitterFeedFactory: TwitterFeedFactory ) : CacheLoader<String, TwitterFeed>() {

    override fun load(key: String?): TwitterFeed {
        return twitterFeedFactory.getFeed(key!!)
    }

}