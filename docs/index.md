---
layout: default
---
![](http://images.mentalfloss.com/sites/default/files/styles/insert_main_wide_image/public/construction%205.gif)

Inspired by [MagicMirror2](https://magicmirror.builders)


### [](#tech-stack)Technology stack:

*   [**Liferay 6.2**](https://dev.liferay.com/develop/tutorials/-/knowledge_base/6-2/tutorials) - a Java Portal running on Java 7. Refresh-on-form-submit style. The legacy part of this project.   
*   [**Spring Portlet MVC**](https://docs.spring.io/autorepo/docs/spring/4.2.x/spring-framework-reference/html/portlet.html) - to make Portlet (Liferay's plugin) development familiar for Spring users.
*   **Kotlin** - It can be compiled to Java 7, so it also can be used to develop Portlets.
*   **ES6 Javascript** - To code single page app features in vanilla JS.
*   **Node.js** - To bypass Java Portlet spec limitations and add WebSockets.   

# [](#input-dev)Input devices
A mirror should support an optional input device that:
*   **is not a touch screen digitizer** - Fingerprints don't look good on reflective surfaces!
*   **is not a camera** - People cover their laptop cameras for comfort of privacy and a bedroom is a common place to install mirrors. It won't work.
*   **is supported on Linux** - Unfortunately, Kinect requires a Windows client. 
*   **is cheap and easy to fit in the mirror's frame**

I've been looking for a device that match those requirements and I was inspired by the classic Apple iPod - it's big wheel and the central button. I figured out that the **mousewheel** that is in every computer mouse gives the same functionality and similar feeling.

A mousewheel can send 3 evenets, scroll up', 'scroll down' and 'wheel pressed', and they can all be handled by code in the browser.

Here is the Twitter feed reader plugin that make use of the mousewheel. You can scroll though tweets of selected accounts and press to see the replies or play the video that is attached to the tweet.

{::options parse_block_html="true" /}
<video src="twitter.webm" controls loop></video>
{::options parse_block_html="false" /}
* * *

# [](#customization)Customization 
PortalMirror plugins are configurable from admin interface level. You can place adaptive componenets in any position on the mirror.
{::options parse_block_html="true" /}
<video src="new_widgets-s.webm" controls loop></video>
{::options parse_block_html="false" /}
* * *

Changes to the mirror setup are visible immidiately - Single click to deploy to devices. 
{::options parse_block_html="true" /}
<video src="deployment-s.webm" controls loop></video>
{::options parse_block_html="false" /}
* * *

# [](#two-way-communication)Two way communication
With Server to Client communication it is possible to send messages to your mirror, for example, from your phone.  
{::options parse_block_html="true" /}
<video src="flashmessage-s.webm" controls loop></video>
{::options parse_block_html="false" /}
* * *

# [](#cms)CMS
Manage your devices from the control panel

![](access_control.png)
