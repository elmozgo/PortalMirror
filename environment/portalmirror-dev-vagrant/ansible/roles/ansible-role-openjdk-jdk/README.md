# openjdk-jdk

[![Build Status](https://img.shields.io/travis/infOpen/ansible-role-openjdk-jdk/master.svg?label=travis_master)](https://travis-ci.org/infOpen/ansible-role-openjdk-jdk)
[![Build Status](https://img.shields.io/travis/infOpen/ansible-role-openjdk-jdk/develop.svg?label=travis_develop)](https://travis-ci.org/infOpen/ansible-role-openjdk-jdk)
[![Updates](https://pyup.io/repos/github/infOpen/ansible-role-openjdk-jdk/shield.svg)](https://pyup.io/repos/github/infOpen/ansible-role-openjdk-jdk/)
[![Python 3](https://pyup.io/repos/github/infOpen/ansible-role-openjdk-jdk/python-3-shield.svg)](https://pyup.io/repos/github/infOpen/ansible-role-openjdk-jdk/)
[![Ansible Role](https://img.shields.io/ansible/role/15858.svg)](https://galaxy.ansible.com/infOpen/openjdk-jdk/)

Install openjdk-jdk package.

## Requirements

This role requires Ansible 2.2 or higher,
and platform requirements are listed in the metadata file.

## Testing

This role use [Molecule](https://github.com/metacloud/molecule/) to run tests.

Local and Travis tests run tests on Docker by default.
See molecule documentation to use other backend.

Currently, tests are done on:
- Debian Jessie
- Ubuntu Trusty
- Ubuntu Xenial

and use:
- Ansible 2.2.x
- Ansible 2.3.x
- Ansible 2.4.x

### Running tests

#### Using Docker driver

```
$ tox
```

## Role Variables

### Default role variables

``` yaml
# General packages variables
openjdk_jdk_packages: "{{ _openjdk_jdk_packages }}"
openjdk_jdk_version: "{{ _openjdk_jdk_version }}"

# APT specific variables
openjdk_jdk_apt_update_cache: True
openjdk_jdk_apt_cache_valid_time: 3600
```

### Default Debian OS family variables

``` yaml
_openjdk_jdk_packages:
  - name: "openjdk-{{ openjdk_jdk_version }}-jdk"
```

### Default Debian Jessie variables

``` yaml
_openjdk_jdk_version: 7
```

### Default Ubuntu Trusty variables

``` yaml
_openjdk_jdk_version: 7
```

### Default Ubuntu Xenial variables

``` yaml
_openjdk_jdk_version: 8
```

## Dependencies

None

## Example Playbook

``` yaml
- hosts: servers
  roles:
    - { role: infOpen.openjdk-jdk }
```

## License

MIT

## Author Information

Alexandre Chaussier (for Infopen company)
- http://www.infopen.pro
- a.chaussier [at] infopen.pro
