package org.portalmirror.twitterfeed.core.logic

import twitter4j.*
import twitter4j.conf.Configuration
import java.text.SimpleDateFormat


fun java.util.Date.formatToTwitterDate() : String {
    val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    return sdf.format(this)
}

class TwitterRepository(configuration: Configuration) {

    private val twitter: Twitter = TwitterFactory(configuration).instance

    fun getTop20StatusesFromTimeline (screenName: String): ResponseList<Status>? {

        return twitter.getUserTimeline(screenName)
    }

    fun getAllRepliesToStatus(status : Status) : List<Status>{

        val allRepliesToUser = twitter.search(Query("to:${status.user.screenName} since:${status.createdAt.formatToTwitterDate()}"))
        return allRepliesToUser.tweets.filter { tweet -> tweet.inReplyToStatusId == status.id }
    }

}