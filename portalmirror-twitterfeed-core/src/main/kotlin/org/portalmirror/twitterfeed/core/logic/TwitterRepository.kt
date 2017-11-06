package org.portalmirror.twitterfeed.core.logic

import com.google.common.cache.CacheLoader
import twitter4j.*
import twitter4j.conf.Configuration
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import com.google.common.cache.CacheBuilder
import mu.KotlinLogging


class TwitterRepository(configuration: Configuration, cacheTimeout : Long) {

    private val logger = KotlinLogging.logger {}


    private val repliesToUserCacheLoader = object : CacheLoader<String, List<Status>>() {

        override fun load(key: String?): List<Status> {
            logger.debug { "repliesToUserCacheLoader called" }
            return getRepliesToUser(key!!)
        }

    }

    private val repliesToUsersCache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(cacheTimeout, TimeUnit.SECONDS)
            .build(repliesToUserCacheLoader)

    private fun getRepliesToUser(screenName : String, since : Date? = null , until : Date? = null) : List<Status> {

        logger.debug { "getRepliesToUser : screenName=[$screenName], since=[${since?.formatToLog()}], until=[${until?.formatToLog()}]" }

        val globalRepliesToUser : MutableList<Status> = mutableListOf()
        val query = Query("to:$screenName")

        since?.let {
            query.since = since.formatToTwitterDate()
        }
        until?.let {
            query.until = until.formatToTwitterDate()
        }
        query.count = 100

        var maxId = Long.MAX_VALUE

        for(i in 1..10)  {
            query.maxId = maxId
            logger.debug { "calling twitter.search(query) : q=[${query.query}] maxId=[$maxId] " }
            val tweets = twitter.search(query).tweets
            logger.debug { "received ${tweets.size} tweets" }

            if(tweets.isEmpty()) {
                break
            }
            globalRepliesToUser.addAll(tweets)
            maxId = tweets.minBy({ it.id })!!.id
        }
        logger.debug { "found ${globalRepliesToUser.size} replies" }
        return globalRepliesToUser
    }


    private val twitter: Twitter = TwitterFactory(configuration).instance

    fun getTop20StatusesFromTimeline (screenName: String): List<Status> {

        logger.debug { "getTop20StatusesFromTimeline() : screenName=$screenName" }

        return twitter.getUserTimeline(screenName)
    }

    fun getAllRepliesToStatus(status : Status) : List<Status>{

        logger.debug { "getAllRepliesToStatus() : statusId=[${status.id}]" }

        val cachedReplies : List<Status> = repliesToUsersCache.get(status.user.screenName)

        if(cachedReplies.isEmpty()) {
            logger.debug { "no replies" }
            return cachedReplies
        }

        val totalReplies : List<Status>

        if(cachedReplies.none { it.createdAt.before(status.createdAt) }) {

            val dateOfOldestReply = cachedReplies.minWith(Comparator { o1, o2 ->
                o1.createdAt.compareTo(o2.createdAt)
            })!!.createdAt

            val olderReplies = getRepliesToUser(status.user.screenName, status.createdAt, dateOfOldestReply)

            totalReplies = cachedReplies + olderReplies

            repliesToUsersCache.put(status.user.screenName, totalReplies)
            logger.debug { "cache updated for screenName=[${status.user.screenName}], repliesCount=[${totalReplies.size}]" }


        } else {

            totalReplies = cachedReplies
        }

        val repliesToStatus = totalReplies.filter { tweet -> tweet.inReplyToStatusId == status.id }
        logger.debug { "number of replies to statusId=[${status.id}] : ${repliesToStatus.size}" }

        return repliesToStatus
    }

}

fun java.util.Date.formatToLog() : String {
    return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this)
}

fun java.util.Date.formatToTwitterDate() : String {
    return SimpleDateFormat("yyyy-MM-dd").format(this)
}