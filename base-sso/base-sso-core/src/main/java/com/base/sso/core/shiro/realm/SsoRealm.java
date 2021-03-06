package com.base.sso.core.shiro.realm;

import com.base.common.util.EhCacheUtil;
import com.base.common.util.MD5Util;
import com.base.common.util.PropertiesFileUtil;
import com.base.sso.core.model.UpmsPermission;
import com.base.sso.core.model.UpmsRole;
import com.base.sso.core.model.UpmsUser;
import com.base.sso.core.service.UpmsApiService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户认证和授权
 * Created by shubase on 2017/1/20.
 */
public class SsoRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(SsoRealm.class);

    private static final String PERMISSIONS_CACHE_NAME = "base-upms-server-permissions-ehcache";
    private static final String ROLES_CACHE_NAME = "base-upms-server-roles-ehcache";
    @Autowired
    private UpmsApiService upmsApiService;

    /**
     * 授权：验证权限时调用
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        //todo loger
        System.out.println("----------------------------------------------------------");
        System.out.println("java.io.tmpdir:" + System.getProperty("java.io.tmpdir"));
        System.out.println("----------------------------------------------------------");
        Set<String> permissions = (Set<String>) EhCacheUtil.get(PERMISSIONS_CACHE_NAME, username);
        Set<String> roles = (Set<String>) EhCacheUtil.get(ROLES_CACHE_NAME, username);
        if (null == permissions || permissions.isEmpty() || null == roles || permissions.isEmpty()) {
            UpmsUser upmsUser = upmsApiService.selectUpmsUserByUsername(username);
            // 当前用户所有角色
            List<UpmsRole> upmsRoles = upmsApiService.selectUpmsRoleByUpmsUserId(upmsUser.getUserId());
            if (roles == null) {
                roles = new HashSet<String>();
            }
            for (UpmsRole upmsRole : upmsRoles) {
                if (StringUtils.isNotBlank(upmsRole.getName())) {
                    roles.add(upmsRole.getName());
                }
            }
            // 当前用户所有权限
            List<UpmsPermission> upmsPermissions = upmsApiService.selectUpmsPermissionByUpmsUserId(upmsUser.getUserId());
            if (permissions == null) {
                permissions = new HashSet<String>();
            }
            for (UpmsPermission upmsPermission : upmsPermissions) {
                if (StringUtils.isNotBlank(upmsPermission.getPermissionValue())) {
                    permissions.add(upmsPermission.getPermissionValue());
                }
            }
            EhCacheUtil.put(ROLES_CACHE_NAME, username, roles);
            EhCacheUtil.put(PERMISSIONS_CACHE_NAME, username, permissions);
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }

    /**
     * 认证：登录时调用
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        // client无密认证
        String upmsType = PropertiesFileUtil.getInstance("base-upms-client").get("base.upms.type");
        if ("client".equals(upmsType)) {
            return new SimpleAuthenticationInfo(username, password, getName());
        }

        // 查询用户信息
        UpmsUser upmsUser = upmsApiService.selectUpmsUserByUsername(username);

        if (null == upmsUser) {
            throw new UnknownAccountException();
        }
        if (!upmsUser.getPassword().equals(MD5Util.md5(password + upmsUser.getSalt()))) {
            throw new IncorrectCredentialsException();
        }
        if (upmsUser.getLocked() == 1) {
            throw new LockedAccountException();
        }

        return new SimpleAuthenticationInfo(username, password, getName());
    }

}
