package org.portalmirror.twitterfeed.core

import org.assertj.core.api.Assertions.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.portalmirror.twitterfeed.core.domain.TwitterFeed
import org.portalmirror.twitterfeed.core.domain.TwitterFeedEntry
import org.portalmirror.twitterfeed.core.logic.TwitterRepository
import java.util.*

@RunWith(JUnitPlatform::class)
class TwitterFeedSpec : Spek({

    describe("a TwitterFeed"){

        val repository : TwitterRepository = Mockito.mock(TwitterRepository::class.java)

        context("containing 2 level of replies ") {

            Mockito.`when`(repository.getAllRepliesToStatus(rootStatus1)).thenReturn(Arrays.asList(replyTo_rootStatus1))
            Mockito.`when`(repository.getAllRepliesToStatus(replyTo_rootStatus1)).thenReturn(Arrays.asList(replyTo_replyTo_rootStatus1))
            val root = TwitterFeedEntry(rootStatus1, repository)
            val feed = TwitterFeed("root", Arrays.asList(root))

            on("findEntry(id)") {
                val theEntry = feed.findEntry(replyTo_replyTo_rootStatus1.id)

                it("should return the correct entry") {

                    assertThat(theEntry!!.status.id).isEqualTo(replyTo_replyTo_rootStatus1.id)
                    assertThat(theEntry!!.status.user.screenName).isEqualTo(replyTo_replyTo_rootStatus1.user.screenName)
                }
            }
        }

    }

})
