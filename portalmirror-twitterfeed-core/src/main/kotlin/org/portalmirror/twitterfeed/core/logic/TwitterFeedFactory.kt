package org.portalmirror.twitterfeed.core.logic

import org.portalmirror.twitterfeed.core.domain.TwitterFeed
import org.portalmirror.twitterfeed.core.domain.TwitterFeedEntry

/**
 * Created by arturro on 23/07/17.
 */


class TwitterFeedFactory(val repository: TwitterRepository, val maxFeedDepth : Int) {

    fun buildFeed(screenName: String) : TwitterFeed {
        return TwitterFeed(screenName, repository.getTop20StatusesFromTimeline(screenName)
                .sortedBy { it.createdAt }
                .map { status -> TwitterFeedEntry(screenName, status, repository, maxFeedDepth) }.sortedByDescending { it.status.createdAt })
    }
}