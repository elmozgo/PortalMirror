/*jshint esversion: 6 */

(function () {
    'use strict';
}());

function PortalMirrorTwitterFeedUi(containerElement) {
    this.containerElement = containerElement;
    this.feedList = containerElement.getElementsByClassName('feed-list')[0];
    this.feedDetails = containerElement.getElementsByClassName('feed-details')[0];
    this.feedReplies = containerElement.getElementsByClassName('feed-replies')[0];
    this.rightBox = containerElement.getElementsByClassName('right-box')[0];
}

PortalMirrorTwitterFeedUi.prototype = {

    menuItemDown: function () {
        console.log('mousewheel down');
        let oldActive = this.feedList.getElementsByClassName('active')[0];
        if (oldActive.nextElementSibling) {
            oldActive.classList.remove('active');
            oldActive.nextElementSibling.classList.add('active');
        } else {
            //actionOnLastElement();
        }
    },

    menuItemUp: function () {

        console.log('mousewheel up');
        let oldActive = this.feedList.getElementsByClassName('active')[0];
        if (oldActive.previousElementSibling) {
            oldActive.classList.remove('active');
            oldActive.previousElementSibling.classList.add('active');
        } else {
            //actionOnFirstElement();
        }

    },

    setupWheelHandling: function () {

        let that = this;
        Hamster(document).wheel(function (event, delta, deltaX, deltaY) {
            if (!that.containerElement.classList.contains('pressed-mode')) {
                if (delta > 0) {
                    that.menuItemUp();
                } else {
                    that.menuItemDown();
                }

                event.preventDefault();
            }
        });
    },

    initOnUiEvent: function () {

        let that = this;
        let feedList = this.feedList;
        let observer = new MutationObserver(function (mutations) {

            let setToActiveMutation = mutations.filter(
                (mutation) =>
                    mutation.target.classList.contains('active') && !mutation.target.classList.contains('pressed')
            ).pop();
            if (setToActiveMutation) {
                that.onActiveChange(setToActiveMutation.target);
            }

            let setToPressedMutation = mutations.filter(
                (mutation) =>
                    mutation.target.classList.contains('pressed')
            ).pop();
            if (setToPressedMutation) {
                that.onPressedChange(setToPressedMutation.target);
            }

        });

        observer.observe(feedList, {
            attributes: true,
            childList: false,
            characterData: false,
            subtree: true
        });
    },

    onActiveChange: function (activeElement) {
        this.feedDetails.innerHTML = activeElement.getAttribute('data-pmtf-details');
    },

    setupButtonPressedHandling: function () {

        let that = this;

        document.onclick = function (event) {
            if (event.button === 1) { //wheel button

                console.log('mousewheel button pressed');
                let active = that.feedList.getElementsByClassName('active')[0];
                that.containerElement.classList.toggle('pressed-mode');
                active.classList.toggle('pressed');

            }
        };
    },

    onPressedChange: function (pressedElement) {
        this.feedReplies.innerHTML = pressedElement.getAttribute('data-pmtf-details') + " is pressed.";
    },

    init: function () {

        this.setupWheelHandling();
        this.setupButtonPressedHandling();
        this.initOnUiEvent();

        this.feedList.children[0].classList.add('active');

    }

};


