package org.portalmirror.twitterfeed.core.logic

import org.portalmirror.twitterfeed.core.domain.TwitterFeed
import org.portalmirror.twitterfeed.core.domain.TwitterFeedEntry

/**
 * Created by arturro on 23/07/17.
 */

val MAX_EAGER_LOADING_FEED_DEPTH : Int = 2

class TwitterFeedFactory(val repository: TwitterRepository) {

    fun getFeed(screenName: String) : TwitterFeed {
        return TwitterFeed(screenName, repository.getTop20StatusesFromTimeline(screenName)
                .map { status -> TwitterFeedEntry(screenName, status, repository) })
    }
}