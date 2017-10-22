package org.portalmirror.prototypes.twitterui.fixtures


import twitter4j.Status
import twitter4j.TwitterObjectFactory

val rootStatus1: Status = TwitterObjectFactory.createStatus(
        """

        {
    "created_at": "Fri Oct 20 15:00:00 +0000 2017",
    "id": 850007368138018001,
    "id_str": "850007368138018001",
    "text": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam vitae ligula aliquam lectus lobortis porta non a diam. Proin fringilla augue.",
    "truncated": false,
    "entities": {
      "hashtags": [],
      "symbols": [],
      "user_mentions": [],
      "urls": []
    },
    "source": "<a href=\"http://twitter.com\" rel=\"nofollow\">Twitter Web Client</a>",
    "in_reply_to_status_id": null,
    "in_reply_to_status_id_str": null,
    "in_reply_to_user_id": null,
    "in_reply_to_user_id_str": null,
    "in_reply_to_screen_name": null,
    "user": {
      "id": 6253282,
      "id_str": "6253282",
      "name": "Artur Karwowski",
      "screen_name": "elmozgo",
      "location": "London, UK",
      "description": "test",
      "url": "http://t.co/78pYTvWfJd",
      "entities": {
        "url": {
          "urls": [
            {
              "url": "http://t.co/78pYTvWfJd",
              "expanded_url": "https://dev.twitter.com",
              "display_url": "dev.twitter.com",
              "indices": [
                0,
                22
              ]
            }
          ]
        },
        "description": {
          "urls": []
        }
      },
      "protected": false,
      "followers_count": 6172353,
      "friends_count": 46,
      "listed_count": 13091,
      "created_at": "Wed May 23 06:01:13 +0000 2007",
      "favourites_count": 26,
      "utc_offset": -25200,
      "time_zone": "Pacific Time (US & Canada)",
      "geo_enabled": true,
      "verified": true,
      "statuses_count": 3583,
      "lang": "en",
      "contributors_enabled": false,
      "is_translator": false,
      "is_translation_enabled": false,
      "profile_background_color": "C0DEED",
      "profile_background_image_url": "http://pbs.twimg.com/profile_background_images/656927849/miyt9dpjz77sc0w3d4vj.png",
      "profile_background_image_url_https": "https://pbs.twimg.com/profile_background_images/656927849/miyt9dpjz77sc0w3d4vj.png",
      "profile_background_tile": true,
      "profile_image_url": "http://pbs.twimg.com/profile_images/899321502218190853/4accIQWu_normal.jpg",
      "profile_image_url_https": "https://pbs.twimg.com/profile_images/899321502218190853/4accIQWu_normal.jpg",
      "profile_banner_url": "https://pbs.twimg.com/profile_banners/6253282/1431474710",
      "profile_link_color": "0084B4",
      "profile_sidebar_border_color": "C0DEED",
      "profile_sidebar_fill_color": "DDEEF6",
      "profile_text_color": "333333",
      "profile_use_background_image": true,
      "has_extended_profile": false,
      "default_profile": false,
      "default_profile_image": false,
      "following": true,
      "follow_request_sent": false,
      "notifications": false,
      "translator_type": "regular"
    },
    "geo": null,
    "coordinates": null,
    "place": null,
    "contributors": null,
    "retweeted_status": null,
    "is_quote_status": false,
    "retweet_count": 0,
    "favorite_count": 0,
    "favorited": false,
    "retweeted": false,
    "possibly_sensitive": false,
    "lang": "en"
  }

""")
val rootStatus2: Status = TwitterObjectFactory.createStatus(
        """

        {
    "created_at": "Thu Apr 06 15:28:43 +0000 2017",
    "id": 850007368138018002,
    "id_str": "850007368138018002",
    "text": "rootStatus2",
    "truncated": false,
    "entities": {
      "hashtags": [],
      "symbols": [],
      "user_mentions": [],
      "urls": []
    },
    "source": "<a href=\"http://twitter.com\" rel=\"nofollow\">Twitter Web Client</a>",
    "in_reply_to_status_id": null,
    "in_reply_to_status_id_str": null,
    "in_reply_to_user_id": null,
    "in_reply_to_user_id_str": null,
    "in_reply_to_screen_name": null,
    "user": {
      "id": 6253282,
      "id_str": "6253282",
      "name": "Twitter API",
      "screen_name": "twitterapi",
      "location": "San Francisco, CA",
      "description": "The Real Twitter API. I tweet about API changes, service issues and happily answer questions about Twitter and our API. Don't get an answer? It's on my website.",
      "url": "http://t.co/78pYTvWfJd",
      "entities": {
        "url": {
          "urls": [
            {
              "url": "http://t.co/78pYTvWfJd",
              "expanded_url": "https://dev.twitter.com",
              "display_url": "dev.twitter.com",
              "indices": [
                0,
                22
              ]
            }
          ]
        },
        "description": {
          "urls": []
        }
      },
      "protected": false,
      "followers_count": 6172353,
      "friends_count": 46,
      "listed_count": 13091,
      "created_at": "Wed May 23 06:01:13 +0000 2007",
      "favourites_count": 26,
      "utc_offset": -25200,
      "time_zone": "Pacific Time (US & Canada)",
      "geo_enabled": true,
      "verified": true,
      "statuses_count": 3583,
      "lang": "en",
      "contributors_enabled": false,
      "is_translator": false,
      "is_translation_enabled": false,
      "profile_background_color": "C0DEED",
      "profile_background_image_url": "http://pbs.twimg.com/profile_background_images/656927849/miyt9dpjz77sc0w3d4vj.png",
      "profile_background_image_url_https": "https://pbs.twimg.com/profile_background_images/656927849/miyt9dpjz77sc0w3d4vj.png",
      "profile_background_tile": true,
      "profile_image_url": "http://pbs.twimg.com/profile_images/2284174872/7df3h38zabcvjylnyfe3_normal.png",
      "profile_image_url_https": "https://pbs.twimg.com/profile_images/2284174872/7df3h38zabcvjylnyfe3_normal.png",
      "profile_banner_url": "https://pbs.twimg.com/profile_banners/6253282/1431474710",
      "profile_link_color": "0084B4",
      "profile_sidebar_border_color": "C0DEED",
      "profile_sidebar_fill_color": "DDEEF6",
      "profile_text_color": "333333",
      "profile_use_background_image": true,
      "has_extended_profile": false,
      "default_profile": false,
      "default_profile_image": false,
      "following": true,
      "follow_request_sent": false,
      "notifications": false,
      "translator_type": "regular"
    },
    "geo": null,
    "coordinates": null,
    "place": null,
    "contributors": null,
    "retweeted_status": null,
    "is_quote_status": false,
    "retweet_count": 0,
    "favorite_count": 0,
    "favorited": false,
    "retweeted": false,
    "possibly_sensitive": false,
    "lang": "en"
  }

""")
val rootStatus3: Status = TwitterObjectFactory.createStatus(
        """

        {
    "created_at": "Thu Apr 06 15:28:43 +0000 2017",
    "id": 850007368138018003,
    "id_str": "850007368138018003",
    "text": "rootStatus3",
    "truncated": false,
    "entities": {
      "hashtags": [],
      "symbols": [],
      "user_mentions": [],
      "urls": []
    },
    "source": "<a href=\"http://twitter.com\" rel=\"nofollow\">Twitter Web Client</a>",
    "in_reply_to_status_id": null,
    "in_reply_to_status_id_str": null,
    "in_reply_to_user_id": null,
    "in_reply_to_user_id_str": null,
    "in_reply_to_screen_name": null,
    "user": {
      "id": 6253282,
      "id_str": "6253282",
      "name": "Twitter API",
      "screen_name": "twitterapi",
      "location": "San Francisco, CA",
      "description": "The Real Twitter API. I tweet about API changes, service issues and happily answer questions about Twitter and our API. Don't get an answer? It's on my website.",
      "url": "http://t.co/78pYTvWfJd",
      "entities": {
        "url": {
          "urls": [
            {
              "url": "http://t.co/78pYTvWfJd",
              "expanded_url": "https://dev.twitter.com",
              "display_url": "dev.twitter.com",
              "indices": [
                0,
                22
              ]
            }
          ]
        },
        "description": {
          "urls": []
        }
      },
      "protected": false,
      "followers_count": 6172353,
      "friends_count": 46,
      "listed_count": 13091,
      "created_at": "Wed May 23 06:01:13 +0000 2007",
      "favourites_count": 26,
      "utc_offset": -25200,
      "time_zone": "Pacific Time (US & Canada)",
      "geo_enabled": true,
      "verified": true,
      "statuses_count": 3583,
      "lang": "en",
      "contributors_enabled": false,
      "is_translator": false,
      "is_translation_enabled": false,
      "profile_background_color": "C0DEED",
      "profile_background_image_url": "http://pbs.twimg.com/profile_background_images/656927849/miyt9dpjz77sc0w3d4vj.png",
      "profile_background_image_url_https": "https://pbs.twimg.com/profile_background_images/656927849/miyt9dpjz77sc0w3d4vj.png",
      "profile_background_tile": true,
      "profile_image_url": "http://pbs.twimg.com/profile_images/2284174872/7df3h38zabcvjylnyfe3_normal.png",
      "profile_image_url_https": "https://pbs.twimg.com/profile_images/2284174872/7df3h38zabcvjylnyfe3_normal.png",
      "profile_banner_url": "https://pbs.twimg.com/profile_banners/6253282/1431474710",
      "profile_link_color": "0084B4",
      "profile_sidebar_border_color": "C0DEED",
      "profile_sidebar_fill_color": "DDEEF6",
      "profile_text_color": "333333",
      "profile_use_background_image": true,
      "has_extended_profile": false,
      "default_profile": false,
      "default_profile_image": false,
      "following": true,
      "follow_request_sent": false,
      "notifications": false,
      "translator_type": "regular"
    },
    "geo": null,
    "coordinates": null,
    "place": null,
    "contributors": null,
    "retweeted_status": null,
    "is_quote_status": false,
    "retweet_count": 0,
    "favorite_count": 0,
    "favorited": false,
    "retweeted": false,
    "possibly_sensitive": false,
    "lang": "en"
  }

""")

val replyTo_rootStatus1: Status = TwitterObjectFactory.createStatus(
        """
{
    "created_at": "Thu Apr 06 10:01:00 +0000 2017",
    "id": 850007368138018011,
    "id_str": "850007368138018011",
    "text": "reply to rootStatus1",
    "truncated": false,
    "entities": {
      "hashtags": [],
      "symbols": [],
      "user_mentions": [

      ],
      "urls": [

      ]
    },
    "source": "<a href=\"http://twitter.com\" rel=\"nofollow\">Twitter Web Client</a>",
    "in_reply_to_status_id": 850007368138018001,
    "in_reply_to_status_id_str": "850007368138018001",
    "in_reply_to_user_id": 6253282,
    "in_reply_to_user_id_str": "6253282",
    "in_reply_to_screen_name": "twitterapi",
    "user": {
      "id": 6253001,
      "id_str": "6253001",
      "name": "replier 1",
      "screen_name": "replier1",
      "location": "San Francisco, CA",
      "description": "replier1",
      "url": "http://t.co/78pYTvWfJd",
      "entities": {
        "url": {
          "urls": [
            {
              "url": "http://t.co/78pYTvWfJd",
              "expanded_url": "https://dev.twitter.com",
              "display_url": "dev.twitter.com",
              "indices": [
                0,
                22
              ]
            }
          ]
        },
        "description": {
          "urls": []
        }
      },
      "protected": false,
      "followers_count": 6172353,
      "friends_count": 46,
      "listed_count": 13091,
      "created_at": "Wed May 23 06:01:13 +0000 2007",
      "favourites_count": 26,
      "utc_offset": -25200,
      "time_zone": "Pacific Time (US & Canada)",
      "geo_enabled": true,
      "verified": true,
      "statuses_count": 3583,
      "lang": "en",
      "contributors_enabled": false,
      "is_translator": false,
      "is_translation_enabled": false,
      "profile_background_color": "C0DEED",
      "profile_background_image_url": "http://pbs.twimg.com/profile_background_images/656927849/miyt9dpjz77sc0w3d4vj.png",
      "profile_background_image_url_https": "https://pbs.twimg.com/profile_background_images/656927849/miyt9dpjz77sc0w3d4vj.png",
      "profile_background_tile": true,
      "profile_image_url": "http://pbs.twimg.com/profile_images/2284174872/7df3h38zabcvjylnyfe3_normal.png",
      "profile_image_url_https": "https://pbs.twimg.com/profile_images/2284174872/7df3h38zabcvjylnyfe3_normal.png",
      "profile_banner_url": "https://pbs.twimg.com/profile_banners/6253282/1431474710",
      "profile_link_color": "0084B4",
      "profile_sidebar_border_color": "C0DEED",
      "profile_sidebar_fill_color": "DDEEF6",
      "profile_text_color": "333333",
      "profile_use_background_image": true,
      "has_extended_profile": false,
      "default_profile": false,
      "default_profile_image": false,
      "following": true,
      "follow_request_sent": false,
      "notifications": false,
      "translator_type": "regular"
    },
    "geo": null,
    "coordinates": null,
    "place": null,
    "contributors": null,
    "retweeted_status": null,
    "is_quote_status": false,
    "retweet_count": 284,
    "favorite_count": 0,
    "favorited": false,
    "retweeted": false,
    "possibly_sensitive": false,
    "lang": "en"
  }

""")
val replyTo_replyTo_rootStatus1: Status = TwitterObjectFactory.createStatus(
        """

{
    "created_at": "Thu Apr 06 10:02:00 +0000 2017",
    "id": 850007368138018021,
    "id_str": "850007368138018021",
    "text": "reply to reply to rootStatus1",
    "truncated": false,
    "entities": {
      "hashtags": [],
      "symbols": [],
      "user_mentions": [

      ],
      "urls": [

      ]
    },
    "source": "<a href=\"http://twitter.com\" rel=\"nofollow\">Twitter Web Client</a>",
    "in_reply_to_status_id": 850007368138018011,
    "in_reply_to_status_id_str": "850007368138018011",
    "in_reply_to_user_id": 6253001,
    "in_reply_to_user_id_str": "6253001",
    "in_reply_to_screen_name": "replier1",
    "user": {
      "id": 6253282,
      "id_str": "6253282",
      "name": "Twitter",
      "screen_name": "twitterapi",
      "location": "San Francisco, CA",
      "description": "replier1",
      "url": "http://t.co/78pYTvWfJd",
      "entities": {
        "url": {
          "urls": [
            {
              "url": "http://t.co/78pYTvWfJd",
              "expanded_url": "https://dev.twitter.com",
              "display_url": "dev.twitter.com",
              "indices": [
                0,
                22
              ]
            }
          ]
        },
        "description": {
          "urls": []
        }
      },
      "protected": false,
      "followers_count": 6172353,
      "friends_count": 46,
      "listed_count": 13091,
      "created_at": "Wed May 23 06:01:13 +0000 2007",
      "favourites_count": 26,
      "utc_offset": -25200,
      "time_zone": "Pacific Time (US & Canada)",
      "geo_enabled": true,
      "verified": true,
      "statuses_count": 3583,
      "lang": "en",
      "contributors_enabled": false,
      "is_translator": false,
      "is_translation_enabled": false,
      "profile_background_color": "C0DEED",
      "profile_background_image_url": "http://pbs.twimg.com/profile_background_images/656927849/miyt9dpjz77sc0w3d4vj.png",
      "profile_background_image_url_https": "https://pbs.twimg.com/profile_background_images/656927849/miyt9dpjz77sc0w3d4vj.png",
      "profile_background_tile": true,
      "profile_image_url": "http://pbs.twimg.com/profile_images/2284174872/7df3h38zabcvjylnyfe3_normal.png",
      "profile_image_url_https": "https://pbs.twimg.com/profile_images/2284174872/7df3h38zabcvjylnyfe3_normal.png",
      "profile_banner_url": "https://pbs.twimg.com/profile_banners/6253282/1431474710",
      "profile_link_color": "0084B4",
      "profile_sidebar_border_color": "C0DEED",
      "profile_sidebar_fill_color": "DDEEF6",
      "profile_text_color": "333333",
      "profile_use_background_image": true,
      "has_extended_profile": false,
      "default_profile": false,
      "default_profile_image": false,
      "following": true,
      "follow_request_sent": false,
      "notifications": false,
      "translator_type": "regular"
    },
    "geo": null,
    "coordinates": null,
    "place": null,
    "contributors": null,
    "retweeted_status": null,
    "is_quote_status": false,
    "retweet_count": 284,
    "favorite_count": 0,
    "favorited": false,
    "retweeted": false,
    "possibly_sensitive": false,
    "lang": "en"
  }

""")
val replyTo_replyTo_replyTo_rootStatus1: Status = TwitterObjectFactory.createStatus(
        """
{
    "created_at": "Thu Apr 06 10:03:00 +0000 2017",
    "id": 850007368138018031,
    "id_str": "850007368138018031",
    "text": "reply to reply to reply to rootStatus1",
    "truncated": false,
    "entities": {
      "hashtags": [],
      "symbols": [],
      "user_mentions": [

      ],
      "urls": [

      ]
    },
    "source": "<a href=\"http://twitter.com\" rel=\"nofollow\">Twitter Web Client</a>",
    "in_reply_to_status_id": 850007368138018021,
    "in_reply_to_status_id_str": "850007368138018021",
    "in_reply_to_user_id": 6253282,
    "in_reply_to_user_id_str": "6253282",
    "in_reply_to_screen_name": "twitterapi",
    "user": {
      "id": 6253001,
      "id_str": "6253001",
      "name": "replier 1",
      "screen_name": "replier1",
      "location": "San Francisco, CA",
      "description": "replier1",
      "url": "http://t.co/78pYTvWfJd",
      "entities": {
        "url": {
          "urls": [
            {
              "url": "http://t.co/78pYTvWfJd",
              "expanded_url": "https://dev.twitter.com",
              "display_url": "dev.twitter.com",
              "indices": [
                0,
                22
              ]
            }
          ]
        },
        "description": {
          "urls": []
        }
      },
      "protected": false,
      "followers_count": 6172353,
      "friends_count": 46,
      "listed_count": 13091,
      "created_at": "Wed May 23 06:01:13 +0000 2007",
      "favourites_count": 26,
      "utc_offset": -25200,
      "time_zone": "Pacific Time (US & Canada)",
      "geo_enabled": true,
      "verified": true,
      "statuses_count": 3583,
      "lang": "en",
      "contributors_enabled": false,
      "is_translator": false,
      "is_translation_enabled": false,
      "profile_background_color": "C0DEED",
      "profile_background_image_url": "http://pbs.twimg.com/profile_background_images/656927849/miyt9dpjz77sc0w3d4vj.png",
      "profile_background_image_url_https": "https://pbs.twimg.com/profile_background_images/656927849/miyt9dpjz77sc0w3d4vj.png",
      "profile_background_tile": true,
      "profile_image_url": "http://pbs.twimg.com/profile_images/2284174872/7df3h38zabcvjylnyfe3_normal.png",
      "profile_image_url_https": "https://pbs.twimg.com/profile_images/2284174872/7df3h38zabcvjylnyfe3_normal.png",
      "profile_banner_url": "https://pbs.twimg.com/profile_banners/6253282/1431474710",
      "profile_link_color": "0084B4",
      "profile_sidebar_border_color": "C0DEED",
      "profile_sidebar_fill_color": "DDEEF6",
      "profile_text_color": "333333",
      "profile_use_background_image": true,
      "has_extended_profile": false,
      "default_profile": false,
      "default_profile_image": false,
      "following": true,
      "follow_request_sent": false,
      "notifications": false,
      "translator_type": "regular"
    },
    "geo": null,
    "coordinates": null,
    "place": null,
    "contributors": null,
    "retweeted_status": null,
    "is_quote_status": false,
    "retweet_count": 284,
    "favorite_count": 0,
    "favorited": false,
    "retweeted": false,
    "possibly_sensitive": false,
    "lang": "en"
  }
""")

