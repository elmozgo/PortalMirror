package org.portalmirror.twitterfeed.core.domain

import org.joda.time.DateTime
import org.portalmirror.twitterfeed.core.logic.TwitterRepository
import twitter4j.Status

class TwitterFeedEntry {

    val screenName : String
    val status : Status
    var replies : List<TwitterFeedEntry>
    private val repository: TwitterRepository
    val depth : Int
    val createdAt : DateTime
    private val repliesMaxDepth: Int

    /***
     * Constructor to create root entries
     */
    constructor(screenName : String, status : Status, repository: TwitterRepository, repliesMaxDepth: Int) {

        this.screenName = screenName
        this.status = status
        this.repository = repository
        this.depth = 0
        this.repliesMaxDepth = repliesMaxDepth

        if(this.repliesMaxDepth > 0 ) {
            this.replies = getReplies(repository, status)
        } else {
            this.replies = emptyList()
        }
        this.createdAt = DateTime.now()

    }

    constructor(status : Status, repository: TwitterRepository, parent : TwitterFeedEntry) {

        this.screenName = status.user.screenName
        this.status = status
        this.repository = repository
        this.depth = parent.depth + 1
        this.repliesMaxDepth = parent.repliesMaxDepth

        if(this.repliesMaxDepth > this.depth) {
            this.replies = getReplies(repository, status)
        } else {
            this.replies = emptyList()
        }
        this.createdAt = DateTime.now()
    }


    private fun getReplies(repository: TwitterRepository, status: Status) = repository.getAllRepliesToStatus(this.status).map { s -> TwitterFeedEntry(s, repository, this) }


    fun isEagerLoaded() : Boolean {
        return this.depth <= this.repliesMaxDepth
    }

    fun refreshReplies() : Unit {
        this.replies = getReplies(this.repository, this.status)
    }

    fun isReplyTo() : Boolean {
        return status.inReplyToStatusId > 0
    }

    fun isRetweeted() : Boolean {
        return status.retweetedStatus != null
    }

    fun hasQuotedStatus() : Boolean {
        return status.quotedStatus != null
    }

    fun hasVideo() : Boolean {
        return hasMediaEntityOfType("video")
    }

    fun hasGif() : Boolean {
        return hasMediaEntityOfType("animated_gif")
    }

    fun hasPhoto() : Boolean {
        return hasMediaEntityOfType("photo")
    }

    private fun hasMediaEntityOfType(type : String) = status.mediaEntities.find { me -> me.type.equals(type) } != null

}
