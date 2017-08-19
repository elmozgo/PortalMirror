package org.portalmirror.twitterfeed.core.domain

import com.google.common.collect.TreeTraverser

/**
 * Created by arturro on 13/08/17.
 */
class TwitterFeed(val screenName: String, val feedEntries : List<TwitterFeedEntry>) {

    fun findEntry(statusId : Long) : TwitterFeedEntry?{

        return feedEntries.flatMap { entry -> feedTraverser.preOrderTraversal(entry)}.find { entry -> entry.status.id.equals(statusId) }

    }

    private companion object feedTraverser : TreeTraverser<TwitterFeedEntry>() {
        override fun children(root: TwitterFeedEntry?): Iterable<TwitterFeedEntry> {
            return root?.replies ?: emptyList()
        }

    }
}
