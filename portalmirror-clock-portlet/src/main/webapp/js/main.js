

var PortletFrontConfiguration = function(aClockPortletDiv, aTimezoneId) {
	
	var timezone = aTimezoneId;
	var clockPortletDiv = aClockPortletDiv;
	var timeParentSpan = clockPortletDiv.querySelector(".time>span");
	var topBarSpan = clockPortletDiv.querySelector(".top-bar>span"); 
	var bottomBarSpan = clockPortletDiv.querySelector(".bottom-bar>span");
	var secondsSpan = clockPortletDiv.querySelector(".seconds");
	
	var isPortletInSmallMode = function () {
		
		return clockPortletDiv.classList.contains("small-portlet-mode");
	};
	
	var setPortletAdaptiveMode = function() {
		
		var clockPortletWidth = window.getComputedStyle(clockPortletDiv, null).width.replace("px", "");
		var clockPortletHeight = window.getComputedStyle(clockPortletDiv, null).width.replace("px", "");
		
		var clockPortletDivClasses = clockPortletDiv.classList;
		var smallPortletMode = isPortletInSmallMode();

		// @media
	    // (max-width: 573px) and (max-height: 333px),
	    // not all and (max-aspect-ratio: 917/341)
		if((clockPortletWidth < 573 && clockPortletHeight< 333 ) || clockPortletWidth/clockPortletHeight > 917/341) {
			if(!smallPortletMode) {
				clockPortletDivClasses.add("small-portlet-mode");
			}
		} else {
			if(smallPortletMode) {
				clockPortletDivClasses.remove("small-portlet-mode");
			}
		}
	};
		
	var adjustTextSize = function() {
		
	    $(timeParentSpan).bigText({
	        horizontalAlign: "left",
	        verticalAlign: "top",
	        fontSizeFactor: 1
	    });
	    
	    if(!isPortletInSmallMode()) {
	        
	        var secondsLineHeight = (window.getComputedStyle(timeParentSpan, null).getPropertyValue("line-height").replace("px", "") * 0.6 )+ "px";
	        secondsSpan.style.lineHeight = secondsLineHeight;

	        timeParentSpan.style.removeProperty("margin");
	        timeParentSpan.style.marginTop = "-5%";
	        
	        $(topBarSpan).bigText({
		        horizontalAlign: "left"
	        });
	        
	    } else {
	        timeParentSpan.style.marginTop = "1%";
	        topBarSpan.style.marginTop = "-5%";
	        
	        $(bottomBarSpan).bigText({
		        horizontalAlign: "center",
		        fontSizeFactor: 1.2
		    });
	        
	        bottomBarSpan.style.removeProperty("top");
	        
	    }
		
	};
	
	var time = {
			timeFormat: 24,
			dateLocation: clockPortletDiv.querySelector('.date'),
			hoursAndMinutesLocation: clockPortletDiv.querySelector('.hours-minutes'),
			secondsLocation: clockPortletDiv.querySelector('.seconds'),
			updateInterval: 1000,
			updateTime: function() {
				var _timeFormat = null;
				
				if (parseInt(this.timeFormat) === 12) {
					_timeFormat = 'hh';
				} else {
					_timeFormat = 'HH';
				}
				
				var _now = moment().tz(timezone);
				var _date = _now.format('MMMM D');

				$(this.dateLocation).html(_date);
				$(this.hoursAndMinutesLocation).html(_now.format(_timeFormat+':mm'));
				$(this.secondsLocation).html(_now.format('ss'));
			},
			setupRefreshingInterval: function() {
				
				var _updateInterval = this.updateInterval;
				
				window.portalMirrorClockUpdateTrigger = window.portalMirrorClockUpdateTrigger || {functions: []};
				
				if(window.portalMirrorClockUpdateTrigger.refreshInterval === undefined){
					window.portalMirrorClockUpdateTrigger.refreshInterval = setInterval(function() {
						
						window.portalMirrorClockUpdateTrigger.functions.forEach(function(entry) {
							entry();
						});
						
					}, _updateInterval);
				}
				
				window.portalMirrorClockUpdateTrigger.functions.push(this.updateTime.bind(this));
				
			}
			
		};
	
	this.init = function(){
		
		time.setupRefreshingInterval();
		
		setPortletAdaptiveMode();
		adjustTextSize();
		
		this.observer = new MutationObserver(function(mutations) {
			mutations.forEach(function(mutation) {
				setPortletAdaptiveMode();
				adjustTextSize();
				
		  });    
		});
			 
		var config = { attributes: true, childList: false, characterData: false };
			 
		this.observer.observe($(clockPortletDiv).closest(".portlet-content-container")[0], config);
		
	};
   
};