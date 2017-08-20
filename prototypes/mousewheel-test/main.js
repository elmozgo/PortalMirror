/*jshint esversion: 6 */

(function () {
    'use strict';
}());

function PortalMirrorTwitterFeedUi(containerElement) {
    this.containerElement = containerElement;
    this.statusList = containerElement.getElementsByClassName('status-list')[0];
    this.feedDetails = containerElement.getElementsByClassName('feed-details')[0];
    this.feedReplies = containerElement.getElementsByClassName('feed-replies')[0];
    this.statusContent = containerElement.getElementsByClassName('status-content')[0];
}

PortalMirrorTwitterFeedUi.prototype = {

    menuItemDown: function () {
        console.log('mousewheel down');
        let oldActive = this.statusList.getElementsByClassName('active')[0];
        if (oldActive.nextElementSibling) {
            oldActive.classList.remove('active');
            oldActive.nextElementSibling.classList.add('active');
        } else {
            //actionOnLastElement();
        }
    },

    menuItemUp: function () {

        console.log('mousewheel up');
        let oldActive = this.statusList.getElementsByClassName('active')[0];
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
        let statusList = this.statusList;
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

        let that = this;

        document.onclick = function (event) {
            if (event.button === 1) { //wheel button

                console.log('mousewheel button pressed');
                let active = that.statusList.getElementsByClassName('active')[0];
                that.containerElement.classList.toggle('pressed-mode');
                active.classList.toggle('pressed');

            }
        };
    },

    onPressedChange: function (pressedElement) {
        this.feedReplies.innerHTML = pressedElement.getAttribute('data-pmtf-statusid') + " is pressed.";
    },

    init: function () {

        this.setupWheelHandling();
        this.setupButtonPressedHandling();
        this.initOnUiEvent();

        this.statusList.children[0].classList.add('active');

    }

};


