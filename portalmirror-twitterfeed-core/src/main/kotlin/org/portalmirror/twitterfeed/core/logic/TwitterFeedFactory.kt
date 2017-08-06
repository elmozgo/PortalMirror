package org.portalmirror.twitterfeed.core.logic

import org.portalmirror.twitterfeed.core.domain.TwitterFeedEntry

/**
 * Created by arturro on 23/07/17.
 */

val MAX_EAGER_LOADING_FEED_DEPTH : Int = 2

class TwitterFeedFactory(val repository: TwitterRepository) {

    fun getFeed(screenNames : List<String>) : List<TwitterFeedEntry> {
        return screenNames
                .flatMap { name -> repository.getTop20StatusesFromTimeline(name)}
                .map { status -> TwitterFeedEntry(status, repository) }
    }
}