package org.portalmirror.twitterfeed.core.domain

import org.joda.time.DateTime
import org.portalmirror.twitterfeed.core.logic.MAX_EAGER_LOADING_FEED_DEPTH
import org.portalmirror.twitterfeed.core.logic.TwitterRepository
import twitter4j.Status

class TwitterFeedEntry {

    val status : Status
    var replies : List<TwitterFeedEntry>
    val repository: TwitterRepository

    val depth : Int
    val createdAt : DateTime

    /***
     * Constructor to create root entries
     */
    constructor(status : Status, repository: TwitterRepository) {

        this.status = status
        this.repository = repository
        this.depth = 0
        this.replies = getReplies(repository, status)
        this.createdAt = DateTime.now()
    }

    constructor(status : Status, repository: TwitterRepository, parent : TwitterFeedEntry) {

        this.status = status
        this.repository = repository
        this.depth = parent.depth + 1
        if(MAX_EAGER_LOADING_FEED_DEPTH < this.depth) {
            this.replies = getReplies(repository, status)
        } else {
            this.replies = emptyList()
        }
        this.createdAt = DateTime.now()
    }


    private fun getReplies(repository: TwitterRepository, status: Status) = repository.getAllRepliesToStatus(this.status).map { s -> TwitterFeedEntry(status, repository, this) }


    fun isEagerLoaded() : Boolean {
        return this.depth <= MAX_EAGER_LOADING_FEED_DEPTH
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
