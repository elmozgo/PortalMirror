# 0.3.1

Marc Rohlfs (1):

* Add empty default version configuration. It avoids an error when configuring versions that are not predefined and should have already been added when predefined versions were implemented.

# 0.3

Marc Rohlfs (9):

* Move YAML files with predefined versions to dedicated subfolder.
* Apply configuration that allows overwriting with other versions than the predefined ones.
* Documentation for version vars
* Extract local download tasks to separate file.
* Copy archives to remote data directory before installing them.
* Rename the home directory if the name of the base directory in the Tomcat bundle archive doesn't match the desired name of the home directory.
* The version of the bundled Tomcat depends on the version of the Liferay Portal Tomcat bundle. Hence it should be defined in the version dependant variables.
* There are several vars that MUST be defined in the version dependant vars.
* OS dependant package installation - currently only Debian environments are supported.

# 0.2.1

Marc Rohlfs (10):

* New variable to enable/disable remote debugging for the Liferay Tomcat server. By default, remote debugging is disabled.

# 0.2

Marc Rohlfs (21):

* README file containing the required information on the role and its usage.
* Pattern for the file paths to be ignored by Git
* Role dependencies should be be defined in the role itself. Instead, the playbook using the role should declare the roles to be used.
* Use a variable that defines the Liferay version to be installed and points to a vars file containing all required version-related configurations.
* Applied download of installation archives to current Silpion best practices.
* Updating the APT cache always takes a lot of time and there's no need to do it several times while running the same playbook. Just once in an hour seems reasonable.
* Prevent installation of Liferay bundle that has already been installed.
* Fixed service implementation/integration for Ubuntu systems.
* Fixed variables for MySQL JDBC configuration.
* Added SHA checksum validation and prevented suoer user priviledges for archive downloads.
* New default installation configuration: Liferay 6.2.1 Community Edition.

# 0.2

Marc Rohlfs (22):

* Initial version of Ansible role for Liferay, extracted from GitHub repository 'silpion/liferay-dev-setup-vagrant' (subdirectory 'provisioning/roles/liferay').
