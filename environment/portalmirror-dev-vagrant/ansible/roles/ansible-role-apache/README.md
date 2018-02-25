# apache

[![Build Status](https://travis-ci.org/infOpen/ansible-role-apache.svg?branch=master)](https://travis-ci.org/infOpen/ansible-role-apache)

Install apache package.

By default, this role disable default site, and it's normal to have :
- a 403 by default for apache 2.2
- a 404 by default for apache 2.4

## Requirements

This role requires Ansible 1.4 or higher, and platform requirements are listed
in the metadata file.

## Testing

This role contains two tests methods :
- locally using Vagrant
- automatically with Travis

## Role Variables

### Default role variables

    # Package variables
    #------------------
    apache_package_state   : present

    # Service variables
    #------------------
    apache_service_state   : started
    apache_service_enabled : True

    # Directories management
    apache_datadir : /srv/data/www
    apache_logdir  : /var/log/apache2
    apache_ssldir  : /srv/data/www-ssl

    # Modules management
    apache_modules_disabled :
      - autoindex
      - info
      - status

    apache_modules_enabled  :
      - rewrite
      - ssl

    # Site management
    apache_sites_disabled :
      - 000-default
      - default-ssl
    apache_sites_enabled  : []

    # VHOST management
    # Format :
    #   - server_name      : my_site.com
    #     server_admin     : admin@my_site.com
    #     server_file_name : my_site.com
    #     document_root    : /srv/data/www/my_site/web
    #     directory_extra  :
    #       - AllowOverride All
    #       - Require all granted
    #     directory_options :
    #       - Indexes
    #       - FollowSymLinks
    #       - MultiViews
    #     virtual_hosts :
    #       - has_ssl      : false
    #         port         : 80
    #         log_level    : warn
    #         server_alias :
    #           - www.my_site.com
    #         virtualhost_extra :
    #           - "Redirect / https://www.my_site.com/"
    #       - has_ssl      : true
    #         port         : 443
    #         log_level    : warn
    #         key_file     : "test-ssl.key"
    #         cert_file    : "test-ssl.pem"
    #         chain_file   : "foo"
    #         directory_extra :
    #           - "DirectoryIndex app_dev.php"
    #         server_alias :
    #           - www.my_site.com
    apache_vhosts : []



    #=============================== Main configuration ============================
    apache_timeout : 300

    apache_keep_alive_active       : True
    apache_keep_alive_max_requests : 100
    apache_keep_alive_timeout      : 5

    apache_hostname_lookup : False

    apache_log_level :
      - warn


    #============================= Envvars configuration ===========================
    apache_ulimit_max_files : 8192
    apache_server_arguments : []


    #============================ Security configuration ===========================
    apache_security_disable_access_entire_fs : False
    apache_security_server_token     : "Prod"
    apache_security_server_signature : "Off"
    apache_security_trace_enabled    : "Off"
    apache_security_ssl_protocol :
      - "-ALL"
      - "+TLSv1"
    apache_security_ssl_cipher_suite :
      - "ALL"
      - "!aNULL"
      - "!ADH"
      - "!eNULL"
      - "!LOW"
      - "!EXP"
      - "RC4+RSA"
      - "+HIGH"
      - "+MEDIUM"

### Debian specific vars

    apache_packages :
      - apache2

    apache_service_name: apache2

## Dependencies

None

## Example Playbook

    - hosts: servers
      roles:
         - { role: achaussier.apache }

## License

MIT

## Author Information

Alexandre Chaussier (for Infopen company)
- http://www.infopen.pro
- a.chaussier [at] infopen.pro
