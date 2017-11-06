package org.portalmirror.prototypes.twitterui;

import static java.util.Arrays.asList;
import static org.portalmirror.prototypes.twitterui.Fixtures.getMockFeed;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.portalmirror.twitterfeed.core.domain.TwitterFeed;
import org.portalmirror.twitterfeed.core.domain.TwitterFeedEntry;
import org.portalmirror.twitterfeed.core.logic.TwitterFeedFactory;
import org.portalmirror.twitterfeed.core.logic.TwitterFeedService;
import org.portalmirror.twitterfeed.core.logic.TwitterFeedSimpleCacheLoader;
import org.portalmirror.twitterfeed.core.logic.TwitterRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;

import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

@RestController
public class FeedController {
	
	@Value("${oauth.consumer.key}")
	private String oAuthConsumerKey;
	@Value("${oauth.consumer.secret}")
	private String oAuthConsumerSecret;
	@Value("${oauth.access.token}")
	private String oAuthAccessToken;
	@Value("${oauth.access.token.secret}")
	private String oAuthAccessTokenSecret;
	
	private TwitterFeedService service;
	
	@PostConstruct
	public void setup() throws TwitterException {
		
		Configuration initConfig = new ConfigurationBuilder()
				.setDebugEnabled(true)
				.setOAuthConsumerKey(oAuthConsumerKey)
				.setOAuthConsumerSecret(oAuthConsumerSecret)
				//.setOAuthAccessToken(oAuthAccessToken)
				//.setOAuthAccessTokenSecret(oAuthAccessTokenSecret)
				.setApplicationOnlyAuthEnabled(true)
				.build();
		OAuth2Token token = new TwitterFactory(initConfig).getInstance().getOAuth2Token();
		
		Configuration config = new ConfigurationBuilder()
				.setDebugEnabled(true)
				.setOAuthConsumerKey(oAuthConsumerKey)
				.setOAuthConsumerSecret(oAuthConsumerSecret)
				.setOAuth2TokenType(token.getTokenType())
				.setOAuth2AccessToken(token.getAccessToken())
				.setApplicationOnlyAuthEnabled(true)
				.build();
		
		TwitterRepository repo = new TwitterRepository(config, 60 * 10);
		TwitterFeedFactory factory = new TwitterFeedFactory(repo, 1);
		
		TwitterFeedSimpleCacheLoader cacheLoader = new TwitterFeedSimpleCacheLoader(factory);
		
        LoadingCache<String, TwitterFeed> cache = CacheBuilder.newBuilder().maximumSize(3).expireAfterWrite(30, TimeUnit.MINUTES).build(cacheLoader);

		
		service = new TwitterFeedService(cache);
	
	}
	
	@GetMapping(value = "/feedentries", produces = { MediaType.APPLICATION_JSON_VALUE })
	List<TwitterFeedEntry> getFeedEntries() {
		
		return service.getFeed(new HashSet<String>(asList("elmozgo", "BBCBreaking"))).stream().flatMap(feed -> feed.getFeedEntries().stream()).collect(Collectors.toList());
	}

	@GetMapping(value = "/mocked/feedentries", produces = { MediaType.APPLICATION_JSON_VALUE })
	List<TwitterFeedEntry> getMockedFeedEntries() {
		
		return Stream.generate(() -> getMockFeed()).limit(10).flatMap(feed -> feed.getFeedEntries().stream()).collect(Collectors.toList());
	}
	
	@GetMapping(value = "/mocked/feed", produces = { MediaType.APPLICATION_JSON_VALUE })
	List<TwitterFeed> getMockedFeed() {

		return Arrays.asList(getMockFeed(), getMockFeed(), getMockFeed(), getMockFeed(), getMockFeed(), getMockFeed(),
				getMockFeed(), getMockFeed(), getMockFeed());
	}

}
