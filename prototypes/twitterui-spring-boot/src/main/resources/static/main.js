/*jshint esversion: 6 */

(function () {
    'use strict';
}());

function hasMediaType(mediaEntities, type) {

    return mediaEntities.filter(e => e.type == type).length > 0;    
}

let menuItemTemplate = function(entry) { return `
                    <li data-pmtf-statusid="${entry.status.id}" data-pmtf-screenname="${entry.screenName}" >
                        <img class="status-list-avatar" src="${entry.status.user.profileImageURL}}"/>
                        <div class="status-metadata">
                            <span class="screenname">${entry.status.user.screenName}</span>
                            <span class="name">${entry.status.user.name}</span>
                        </div>
                        <div class-"status-stats">
                            <span class="retweeted">${entry.status.retweetCount}</span>
                            <span class="liked">${entry.status.favoriteCount}</span>
                            <span class="replied">${entry.replies.length}+</span>
                            
                            ${hasMediaType(entry.status.mediaEntities, 'photo') ? `
                                <span class="photo">p</span>
                            ` : ``}
                            ${hasMediaType(entry.status.mediaEntities, 'animated_gif') ? `
                                <span class="animated-gif">g</span>
                            ` : ``}
                            ${hasMediaType(entry.status.mediaEntities, 'video') ? `
                                <span class="video">v</span>
                            ` : ``}

                        </div>
                        <div class="status-header">
                            ${entry.status.inReplyToStatusId != -1 ? `
                                <span class="replied-to">Replied to: @${entry.status.inReplyToScreenName}</span>
                            ` : ``}
                            ${entry.status.isRetweeted != -1 ? `
                                <span class="retweeted-by">Retweeted by: @${entry.screenName}</span>
                            ` : ``}
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
        this.feedDetails.innerHTML = activeElement.getAttribute('data-pmtf-statusid');
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
    },

    onPressedChange: function (pressedElement) {
        this.feedReplies.innerHTML = pressedElement.getAttribute('data-pmtf-statusid') + " is pressed.";
    },

    loadContent: function () {
        let self = this;
        let getJsonPromise = new Promise(function(resolve, reject) {
            getJson(self.feedUrl).then(function (feedJsonArray) {
                
                    self.feedJsonArray = feedJsonArray;
                    let statusListHtml = feedJsonArray.map(entry => menuItemTemplate(entry)).join('');
                    self.statusList.innerHTML = statusListHtml;
                    resolve();
            });
        });
        
        return getJsonPromise;
    },

    init: function () {

        this.statusList.classList.add('loading-mode');

        this.loadContent().then(() => {
            
            this.setupWheelHandling();
            this.setupButtonPressedHandling();
            this.initOnUiEvent();
            this.statusList.children[0].classList.add('active');
            this.statusList.classList.remove('loading-mode');
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
  
