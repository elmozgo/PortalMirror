See: [PortalMirror GitHub pages](https://elmozgo.github.io/PortalMirror/)

### Instructions

*   Requires Java 8, Maven, Vagrant and Ansible
*   `git clone https://github.com/elmozgo/PortalMirror.git`
*   `cd PortalMirror`
*   `mvn package liferay:deploy`
*   `cd environment/portalmirror-dev-vagrant`
*   `vagrant up`
*   `cp -R ../../portalmirror-notification-service/. notification-serv-deploy/`
*   `vagrant ssh`
*   `cd /vagrant/notification-serv-deploy/ && npm install && pm2 start index.js --name "notification-serv"`
*   `exit`
*   http://localhost:9000
