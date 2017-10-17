package org.portalmirror.prototypes.twitterui;

import static org.portalmirror.prototypes.twitterui.Fixtures.getMockFeed;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.portalmirror.twitterfeed.core.domain.TwitterFeed;
import org.portalmirror.twitterfeed.core.domain.TwitterFeedEntry;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeedController {

	@GetMapping(value = "/feedentries", produces = { MediaType.APPLICATION_JSON_VALUE })
	List<TwitterFeedEntry> getFeedEntries() {

		return Arrays.asList(getMockFeed(), getMockFeed(), getMockFeed(), getMockFeed(), getMockFeed(), getMockFeed(),
				getMockFeed(), getMockFeed(), getMockFeed()).stream().flatMap(feed -> feed.getFeedEntries().stream()).collect(Collectors.toList());
	}
	
	@GetMapping(value = "/feed", produces = { MediaType.APPLICATION_JSON_VALUE })
	List<TwitterFeed> getFeed() {

		return Arrays.asList(getMockFeed(), getMockFeed(), getMockFeed(), getMockFeed(), getMockFeed(), getMockFeed(),
				getMockFeed(), getMockFeed(), getMockFeed());
	}

}
