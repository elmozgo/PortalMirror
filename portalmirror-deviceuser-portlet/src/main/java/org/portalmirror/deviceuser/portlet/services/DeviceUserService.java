package org.portalmirror.deviceuser.portlet.services;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.*;
import com.liferay.portal.service.*;
import org.portalmirror.deviceuser.portlet.utils.DeviceUserNamesResolver;
import org.springframework.stereotype.Service;
import sun.util.locale.LocaleUtils;

import java.util.List;

@Service
public class DeviceUserService {

    private static final Log LOG = LogFactoryUtil.getLog(DeviceUserService.class);

    public List<User> getDeviceUsers(User user, ServiceContext serviceContext) throws SystemException, PortalException {

        String devicesOrganisationName = DeviceUserNamesResolver.resolveOrganisationName(user);


        Organization organization = OrganizationLocalServiceUtil.fetchOrganization(
                serviceContext.getCompanyId(), devicesOrganisationName);
        if(organization == null) {
            organization = createDeviceOrganisation(user, serviceContext, devicesOrganisationName);
        }


        return UserLocalServiceUtil.getOrganizationUsers(organization.getOrganizationId());

    }

    public void addDeviceUser(User user, String deviceUserName, ServiceContext serviceContext) throws SystemException, PortalException {


        User deviceUser = UserLocalServiceUtil.addUser(
                user.getUserId(),
                serviceContext.getCompanyId(),
                true,
                null,
                null,
                false,
                deviceUserName,
                user.getEmailAddress(),
                0,
                "",
                LocaleUtil.getDefault(),
                deviceUserName,
                "",
                "",
                1,
                1,
                true,
                1,
                1,
                1970,
                "",
                null,
                null,
                null,
                null,
                false,
                serviceContext
                );

    /*

    * @param creatorUserId the primary key of the creator
	* @param companyId the primary key of the user's company
	* @param autoPassword whether a password should be automatically generated
	for the user
	* @param password1 the user's password
	* @param password2 the user's password confirmation
	* @param autoScreenName whether a screen name should be automatically
	generated for the user
	* @param screenName the user's screen name
	* @param emailAddress the user's email address
	* @param facebookId the user's facebook ID
	* @param openId the user's OpenID
	* @param locale the user's locale
	* @param firstName the user's first name
	* @param middleName the user's middle name
	* @param lastName the user's last name
	* @param prefixId the user's name prefix ID
	* @param suffixId the user's name suffix ID
	* @param male whether the user is male
	* @param birthdayMonth the user's birthday month (0-based, meaning 0 for
	January)
	* @param birthdayDay the user's birthday day
	* @param birthdayYear the user's birthday year
	* @param jobTitle the user's job title
	* @param groupIds the primary keys of the user's groups
	* @param organizationIds the primary keys of the user's organizations
	* @param roleIds the primary keys of the roles this user possesses
	* @param userGroupIds the primary keys of the user's user groups
	* @param sendEmail whether to send the user an email notification about
	their new account
	* @param serviceContext the service context to be applied (optionall

     */
    }


    private Organization createDeviceOrganisation(User user, ServiceContext serviceContext, String devicesOrganisationName) throws PortalException, SystemException {
        return OrganizationLocalServiceUtil.addOrganization(
                user.getUserId(),
                OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
                devicesOrganisationName,
                OrganizationConstants.TYPE_REGULAR_ORGANIZATION,
                RegionConstants.DEFAULT_REGION_ID,
                CountryConstants.DEFAULT_COUNTRY_ID,
                ListTypeConstants.ORGANIZATION_STATUS_DEFAULT,
                DeviceUserNamesResolver.resolveOrganisationComment(user),
                false,
                serviceContext
        );
    }

}
