package org.portalmirror.clock.timezone.api.geonames;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.geonames.InsufficientStyleException;
import org.geonames.Style;
import org.geonames.Timezone;
import org.geonames.Toponym;
import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;
import org.portalmirror.clock.common.portlet.CustomPropsUtil;
import org.portalmirror.clock.timezone.api.TimezoneApiLogic;
import org.portalmirror.clock.timezone.api.TimezoneQueryResponse;
import org.springframework.stereotype.Component;

import com.liferay.portal.kernel.exception.SystemException;

@Component("geonamesApiLogic")
public class GeonamesApiLogic implements TimezoneApiLogic {

	private static String USERNAME = "geonames.api.username";

	@PostConstruct
	private void setup() throws SystemException {
		WebService.setUserName(CustomPropsUtil.getProp(USERNAME));
	}

	@Override
	public TimezoneQueryResponse getTimezoneResponseQuery(String locationQuery) {

		ToponymSearchCriteria criteria = new ToponymSearchCriteria();
		criteria.setQ(locationQuery);
		criteria.setStyle(Style.LONG);

		ToponymSearchResult result;
		try {
			result = WebService.search(criteria);
		} catch (Exception e) {
			
			return new TimezoneQueryResponse(TimezoneQueryResponse.ErrorType.GENERAL_ERROR, e);
		}

		if (result.getTotalResultsCount() > 0) {
			Toponym toponym = result.getToponyms().get(0);
			try {
				
				return figureOutTimezoneResponse(toponym);
			} catch (Exception e) {
				
				return new TimezoneQueryResponse(TimezoneQueryResponse.ErrorType.GENERAL_ERROR, e);
			}
		} else {
			return new TimezoneQueryResponse(TimezoneQueryResponse.ErrorType.NO_RESULTS);
		}

	}

	private TimezoneQueryResponse figureOutTimezoneResponse(Toponym toponym) throws IOException, Exception {
		
		if(toponym.getTimezone() != null) {
			return new TimezoneQueryResponse(toponym.getTimezone().getTimezoneId());	
		}
		
		Timezone timezone = WebService.timezone(toponym.getLatitude(), toponym.getLongitude());
		return new TimezoneQueryResponse(timezone.getTimezoneId());
		
		
	}

}
