package org.portalmirror.prototypes.twitterui;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.portalmirror.prototypes.twitterui.fixtures.StatusesKt;
import org.portalmirror.twitterfeed.core.domain.TwitterFeed;
import org.portalmirror.twitterfeed.core.domain.TwitterFeedEntry;
import org.portalmirror.twitterfeed.core.logic.TwitterRepository;

public class Fixtures {

	public static TwitterFeed getMockFeed() {
		
		TwitterRepository repo = mock(TwitterRepository.class);
        when(repo.getAllRepliesToStatus(StatusesKt.getRootStatus1())).thenReturn(Arrays.asList(StatusesKt.getReplyTo_rootStatus1()));
        when(repo.getAllRepliesToStatus(StatusesKt.getReplyTo_rootStatus1())).thenReturn(Arrays.asList(StatusesKt.getReplyTo_replyTo_rootStatus1()));
        TwitterFeedEntry root = new TwitterFeedEntry("root", StatusesKt.getRootStatus1(), repo);
        TwitterFeed feed = new TwitterFeed("root", Arrays.asList(root));
        
        return feed;
		
	}
	
}
