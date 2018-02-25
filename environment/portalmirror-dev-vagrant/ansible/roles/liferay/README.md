ansible-liferay
===============

Installs a Liferay Portal server (Tomcat bundle).

Limitations
-----------

* This role currently only supports a MySQL database.
* This role is currently only tested with Ubuntu. It will probably not run with other remote environments.

Requirements
------------

(none - so far)

Role Variables
--------------

* `liferay_version`: The version of the Liferay Portal server to be installed.
* `liferay_base_dir`: The base directory for the Liferay Portal server installation(s).
* `liferay_home_dir_name`: The name of the home directory of the Liferay Portal server installation (string, default: ``liferay-portal-{{ liferay_version }}``)
* `liferay_home_dir`: The home directory of the Liferay Portal server installation (string, default: ``{{liferay_base_dir}}/{{ liferay_home_dir_name }}``)
* `liferay_tomcat_dir`: The Tomcat home directory of the current Liferay Portal server installation (string, default: ``{{liferay_home_dir}}/tomcat-{{ liferay_bundle_tomcat_version }}``)
* `liferay_autodeploy_dir`: The Liferay auto deploy directory for the extensions.
* `liferay_service_name`: The name of the Liferay service process.
* `liferay_service_user`: The owner of the Liferay service process.
* `liferay_service_group`: The group of the Liferay service process.
* `liferay_system_users_access`: The list of users that should get read access to the Liferay installation.
* `mysql_connector_version`: The version of the MySQL JDBC connector library.
* `mysql_connector_download_base_url`: The base URL for the download of the MySQL JDBC connector library.
* `mysql_connector_archive`: The archive name of the MySQL JDBC connector library.
* `mysql_connector_archive_shasum`: The checksum of the MySQL JDBC connector library archive.
* `liferay_enable_remote_debug`: Enable/disable remote debugging for the Liferay Tomcat server.

For default values see `defaults/main.yml`.

### Variables defined for each version

When using a Liferay Portal version, that is not predefined in this role (so far),
the following variables must also be defined in the playbook:

* `liferay_download_base_url`: The base URL for the download of the Tomcat bundle.
* `liferay_bundle_archive`: The file name of the Tomcat bundle archive.
* `liferay_bundle_archive_shasum`: The checksum of the Tomcat bundle archive.
* `liferay_bundle_base_dir_name`: The name of the base directory in the Tomcat bundle archive - must only be provided if it doesn't equal `{{ liferay_home_dir_name }}`.
* `liferay_bundle_tomcat_version`: The version of the Tomcat that is bundled in the current Liferay Portal server installation.

Dependencies
------------

* [ansible-java](https://github.com/silpion/ansible-java.git)
* [galaxy-mysql](https://github.com/bennojoy/mysql.git)

Example Playbook
----------------

The playbook should look pretty much like this:

    - hosts: all
      sudo: true
      sudo_user: root
      roles:
        - mysql
        - java
        - liferay

License
-------

???

Author Information
------------------

Marc Rohlfs, Silpion IT-Solutions GmbH - rohlfs@silpion.de
