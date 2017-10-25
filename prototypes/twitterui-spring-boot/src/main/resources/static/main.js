/*jshint esversion: 6 */

(function () {
    'use strict';
}());

function hasMediaType (entry, type) {

    return entry.status.mediaEntities.filter(e => e.type == type).length > 0;    
}
function getPoster (entry, type) {
    return entry.status.mediaEntities.filter(e => e.type == type)[0].mediaURL;
}

function getHighestBitrateVideoVariant(entry, type) {
    let videoMediaEntity = entry.status.mediaEntities.filter(e => e.type == type)[0];
    
    return videoMediaEntity.videoVariants.sort(function(a, b) {
        return b.bitrate - a.bitrate; //desc
    })[0];
}

function formatCreatedAtDate (createdAtMilis) {
    let createdAtDate = moment(createdAtMilis);
    let age = moment.duration(moment().diff(createdAtDate));
    if(age.asHours() > 24) {
        return createdAtDate.format("D MMM YYYY, HH:mm"); 
    } else {
        return createdAtDate.fromNow();
    }
}

let statusRepliesTemplate = function (reply) { return `
    
    <li>
        <span class="reply-screenname">@${reply.status.user.screenName}</span>
        <span class="reply-name">${reply.status.user.name}</span>
        <span class="reply-text">${reply.status.text}</span>
        ${reply.replies.length > 0 ? `
            <ol>
                ${reply.replies.map(
                    reply => statusRepliesTemplate(reply)).join('').trim()}
            </ol>
        ` :``}
    </li>

`;};

let statusContentTemplate = function(entry) { return `
    <div class="feed-details">
        <p class="text">${entry.status.text}</p>
        ${hasMediaType(entry, 'photo') ? `
            <img class="status-img" src="${getPoster(entry, 'photo')}"/>
        ` : ``}
        ${hasMediaType(entry, 'animated_gif') ? `
            <video
                class="status-gif"
                preload="none"
                autoplay="true"
                poster="${getPoster(entry, 'animated_gif')}" 
                playsinline="true"
                loop="true"
                src="${getHighestBitrateVideoVariant(entry, 'animated_gif').url}"
                type="${getHighestBitrateVideoVariant(entry, 'animated_gif').contentType}" >
            </video>
        ` : ``}
        ${hasMediaType(entry, 'video') ? `
            <video
                class="status-video"
                preload="none"
                poster="${getPoster(entry, 'video')}" 
                playsinline="true"
                loop="true"
                src="${getHighestBitrateVideoVariant(entry, 'video').url}"
                type="${getHighestBitrateVideoVariant(entry, 'video').contentType}" >
            </video>
        ` : ``}
    </div>
    <div class="feed-replies">
        <ol>
            ${entry.replies.map(
                reply => statusRepliesTemplate(reply)).join('').trim()}
        </ol>
    </div>
`;};

let menuItemTemplate = function(entry) { return `
    <li data-pmtf-statusid="${entry.status.id}" data-pmtf-screenname="${entry.screenName}" >
        <img class="status-list-avatar" src="${entry.status.user.profileImageURL}"/>
        <div class="status-metadata">
            <span class="screenname">@${entry.status.user.screenName}</span>
            <span class="name">${entry.status.user.name}</span>
        </div>
        <div class-"status-stats">
            <span class="retweeted">${entry.status.retweetCount}</span>
            <span class="liked">${entry.status.favoriteCount}</span>
            <span class="replied">${entry.replies.length}+</span>
            
            ${hasMediaType(entry, 'photo') ? `
                <span class="photo">p</span>
            ` : ``}
            ${hasMediaType(entry, 'animated_gif') ? `
                <span class="animated-gif">g</span>
            ` : ``}
            ${hasMediaType(entry, 'video') ? `
                <span class="video">v</span>
            ` : ``}

        </div>
        <div class="status-header">
            ${entry.status.inReplyToStatusId > -1 ? `
                <span class="replied-to">in reply to: @${entry.status.inReplyToScreenName}</span>
            ` : ``}
            ${entry.status.retweeted === true ? `
                <span class="retweeted-by">retweeted by: @${entry.screenName}</span>
            ` : ``}
            <span class="tweeted-on-date">${formatCreatedAtDate(entry.status.createdAt)}</span>
        </div>
        <span class="text">${entry.status.text}</span>
    </li>
`;};

function PortalMirrorTwitterFeedUi(containerElement, feedUrl) {
    this.feedUrl = feedUrl;
    this.containerElement = containerElement;
    this.statusList = containerElement.getElementsByClassName('status-list')[0];
    this.feedDetails = containerElement.getElementsByClassName('feed-details')[0];
    this.feedReplies = containerElement.getElementsByClassName('feed-replies')[0];
    this.statusContent = containerElement.getElementsByClassName('status-content')[0];
    this.feedJsonArray = [];
}

PortalMirrorTwitterFeedUi.prototype = {

    scrollToActive: function (newActive) {
        newActive.classList.add('active');

       //if(newActive.INDEX > MAX_HEIGHT / ITEM_HEIGHT)
        jQuery(this.statusList).scrollTo(newActive, 500, {over:0});
    },

    menuItemDown: function () {
        console.log('mousewheel down');
        let oldActive = this.statusList.getElementsByClassName('active')[0];
        if (oldActive.nextElementSibling) {
            oldActive.classList.remove('active');
            var newActive = oldActive.nextElementSibling;
            this.scrollToActive(newActive);
        } else {
            //actionOnLastElement();
        }
    },

    menuItemUp: function () {

        console.log('mousewheel up');
        let oldActive = this.statusList.getElementsByClassName('active')[0];
        if (oldActive.previousElementSibling) {
            oldActive.classList.remove('active');
            var newActive = oldActive.previousElementSibling;
            this.scrollToActive(newActive);
        } else {
            //actionOnFirstElement();
        }

    },
    
    wheelHandlingInProgress: false,

    setupWheelHandling: function () {

        let self = this;

        Hamster(document).wheel(function (event, delta, deltaX, deltaY) {
            if (!self.containerElement.classList.contains('pressed-mode')) {
                if(!self.wheelHandlingInProgress) {
                    self.wheelHandlingInProgress = true;

                    if (delta > 0) {
                        self.menuItemUp();
                    } else {
                        self.menuItemDown();
                    }
                    setTimeout(function() {
                        self.wheelHandlingInProgress = false;
                    }, 500);
                }
                event.preventDefault();
            }
        });
    },

    initOnUiEvent: function () {

        let self = this;
        let statusList = this.statusList;
        let observer = new MutationObserver(function (mutations) {

            let setToActiveMutation = mutations.filter(
                (mutation) =>
                    mutation.target.classList.contains('active') && !mutation.target.classList.contains('pressed')
            ).pop();
            if (setToActiveMutation) {
                self.onActiveChange(setToActiveMutation.target);
            }

            let setToPressedMutation = mutations.filter(
                (mutation) =>
                    mutation.target.classList.contains('pressed')
            ).pop();
            if (setToPressedMutation) {
                self.onPressedChange(setToPressedMutation.target);
            }

        });

        observer.observe(statusList, {
            attributes: true,
            childList: false,
            characterData: false,
            subtree: true
        });
    },

    onActiveChange: function (activeElement) {
        
        this.statusContent.classList.add('loading-mode');

        let activeStatusId = activeElement.getAttribute('data-pmtf-statusid');

        let activeEntry = this.feedJsonArray.filter(
            entry => entry.status.id == activeStatusId)[0];

        setTimeout(() => {
            this.statusContent.innerHTML = statusContentTemplate(activeEntry);
            this.statusContent.classList.remove('loading-mode');
        }, 500);
        
    },

    setupButtonPressedHandling: function () {

        let self = this;

        document.onclick = function (event) {
            if (event.button === 1) { //wheel button

                console.log('mousewheel button pressed');
                let active = self.statusList.getElementsByClassName('active')[0];
                self.containerElement.classList.toggle('pressed-mode');
                active.classList.toggle('pressed');

            }
        };
        document.onauxclick = document.onclick; //for chrome
    },

    onPressedChange: function (pressedElement) {
        let activeStatusId = pressedElement.getAttribute('data-pmtf-statusid');

        this.feedReplies.innerHTML = 
            pressedElement.getAttribute('data-pmtf-statusid') + " is pressed.";
    },

    loadContent: function () {
        let self = this;
        let getJsonPromise = new Promise(function(resolve, reject) {
            getJson(self.feedUrl).then(function (feedJsonArray) {
                
                    self.feedJsonArray = feedJsonArray;
                    let statusListHtml = feedJsonArray.map(entry => menuItemTemplate(entry)).join('').trim();
                    self.statusList.innerHTML = statusListHtml;
                    resolve();
            });
        });
        
        return getJsonPromise;
    },

    init: function () {

        this.containerElement.classList.add('loading-mode');

        this.loadContent().then(() => {
            
            this.setupWheelHandling();
            this.setupButtonPressedHandling();
            this.initOnUiEvent();
            this.statusList.children[0].classList.add('active');
            this.containerElement.classList.remove('loading-mode');
        });

        

        

    }

};



//https://github.com/googlesamples/web-fundamental
function get(url) {
      
    var requestPromise = new Promise(function(resolve, reject) {
      // Do the usual XHR stuff
      var req = new XMLHttpRequest();
      req.open('get', url);
  
      req.onload = function() {
        // 'load' triggers for 404s etc
        // so check the status
        if (req.status == 200) {
          // Resolve the promise with the response text
          resolve(req.response);
        }
        else {
          // Otherwise reject with the status text
          reject(Error(req.statusText));
        }
      };
  
      // Handle network errors
      req.onerror = function() {
        reject(Error("Network Error"));
      };
  
      // Make the request
      req.send();
    });
  
    return requestPromise.then(function(result) {
      return result;
    });
  }
  
  function getJson(url) {
    return get(url).then(JSON.parse);
  }
  
