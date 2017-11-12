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
        <img class="reply-list-avatar" src="${reply.status.user.profileImageURL}"/>
        <span class="reply-screenname">@${reply.status.user.screenName}</span>
        <span class="reply-name">${reply.status.user.name}</span>:
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

let icon = function(iconName) { return `
    <svg class="icon">
        <use xlink:href="font/open-iconic/sprite/open-iconic-sprite.svg#${iconName}"
            class="${iconName}">
        </use>
    </svg>
`;};

let menuItemTemplate = function(entry) { return `
    <li data-pmtf-statusid="${entry.status.id}" data-pmtf-screenname="${entry.screenName}" >
        <img class="status-list-avatar" src="${entry.status.user.profileImageURL}"/>
        <div class="status-metadata">
            <span class="screenname">@${entry.status.user.screenName}</span>
            <span class="name">${entry.status.user.name}</span>
        </div>
        <div class="status-stats">
            <div class="retweeted">
                ${icon('loop-circular')}
                <span>${entry.status.retweetCount}</span>
            </div>
            <div class="liked">
                ${icon('heart')}
                <span>${entry.status.favoriteCount}</span>
            </div>
            <div class="replied">
                ${icon('chat')}
                <span>${entry.replies.length}+</span>
            </div>
            
            ${hasMediaType(entry, 'photo') ? `
                <div class="photo">
                    ${icon('image')}
                </div>
            ` : ``}
            ${hasMediaType(entry, 'animated_gif') ? `
                <div class="animated-gif">
                    ${icon('video')}
                </div>
            ` : ``}
            ${hasMediaType(entry, 'video') ? `
                <div class="video">
                    ${icon('video')}
                </div>
            ` : ``}

        </div>
        <div class="status-header">
            <span class="tweeted-on-date">${formatCreatedAtDate(entry.status.createdAt)}</span>
            
            ${entry.status.inReplyToStatusId > -1 ? `
                <span class="replied-to">in reply to: <span class="screenname">@${entry.status.inReplyToScreenName}</span></span>
            ` : ``}
            ${entry.status.retweeted === true ? `
                <span class="retweeted-by">retweeted by: <span class="screenname">@${entry.screenName}</span></span>
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

    listenToActiveChanges: function () {

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

                if(active.classList.contains('pressed')) {
                    self.onPressed(active);
                } else {
                    self.onUnpressed(active);
                }


            }
        };
        document.onauxclick = document.onclick; //for chrome
    },

    onPressed: function (pressedElement) {
        let activeStatusId = pressedElement.getAttribute('data-pmtf-statusid');

        let activeEntry = this.feedJsonArray.filter(entry => entry.status.id == activeStatusId)[0];
        if(activeEntry && hasMediaType(activeEntry, 'video')) {
            readyOnce('.status-video', function(){
                this.play();
            });
        }
        
    },

    onUnpressed: function (pressedElement) {
        let activeStatusId = pressedElement.getAttribute('data-pmtf-statusid');

        let activeEntry = this.feedJsonArray.filter(entry => entry.status.id == activeStatusId)[0];
        if(activeEntry && hasMediaType(activeEntry, 'video')) {
            document.querySelectorAll('.status-video')[0].pause();
        }
        
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
            this.listenToActiveChanges();
            this.statusList.children[0].classList.add('active');
            this.containerElement.classList.remove('loading-mode');
        });
    }
};


//js util functions to be placed in the hook :


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
  

//on document ready with MutationObserver (http://ryanmorr.com/using-mutation-observers-to-watch-for-element-availability/)
//with some small changes by me:
// - function get called only once


(function(win) {
    'use strict';
    
    var listeners = [], 
    doc = win.document, 
    MutationObserver = win.MutationObserver || win.WebKitMutationObserver,
    observer;

    function readyOnce(selector, fn) {
        // Store the selector and callback to be monitored
        listeners.push({
            selector: selector,
            fn: fn,
            fnCalled: false
        });
        if (!observer) {
            // Watch for changes in the document
            observer = new MutationObserver(checkOnce);
            observer.observe(doc.documentElement, {
                childList: true,
                subtree: true
            });
        }
        // Check if the element is currently in the DOM
        checkOnce();
    }
        
    function checkOnce() {
        // Check the DOM for elements matching a stored selector
        for (var i = 0, len = listeners.length, listener, element; i < len; i++) {
            listener = listeners[i];
            if(listener.fnCalled === false) {
                // Query for elements matching the specified selector
                element = doc.querySelectorAll(listener.selector)[0];
                if(element) {
                    listener.fn.call(element, element);
                    listener.fnCalled = true;
                }
            }
        }

        listeners = listeners.filter(l => l.fnCalled === false); // clearing listeners with invoked functions 

    }

    // Expose `ready`
    win.readyOnce = readyOnce;
            
})(this);