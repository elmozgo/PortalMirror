package org.portalmirror.clock.timezone.api.geonames;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Properties;

import org.geonames.WebService;
import org.junit.Before;
import org.junit.Test;
import org.portalmirror.clock.timezone.api.TimezoneApiLogic;
import org.portalmirror.clock.timezone.api.TimezoneQueryResponse;


public class GeonamesApiTest {

	private static final String GEONAMES_API_USERNAME = "geonames.api.username";
	
	private Properties properties;
	
	@Before
	public void setup() throws IOException {

		properties = new Properties();
		properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
		
		WebService.setUserName(properties.getProperty(GEONAMES_API_USERNAME));
		
	}
	
	@Test
	public void geonamesLondonQueryTest() throws IOException{
		
		//given
		String servername;
		TimezoneApiLogic apiLogic = new GeonamesApiLogic(); 
		
		//when
		servername = WebService.getGeoNamesServer();
		TimezoneQueryResponse response = apiLogic.getTimezoneResponseQuery("London,UK");
		
		//then
		assertThat("server name", servername, not(isEmptyOrNullString()));
		assertThat("error flag", response.isError() , is(false));
		
		assertThat("London timezone", response.getTimezoneId() , equalToIgnoringCase("Europe/London"));
		
	}
	
}
