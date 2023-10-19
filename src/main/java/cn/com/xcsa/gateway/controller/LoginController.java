package cn.com.xcsa.gateway.controller;

import cn.com.xcsa.api.dto.SettingsDto;
import cn.com.xcsa.api.exception.ApiRuntimeException;
import cn.com.xcsa.api.framework.platform.DeployType;
import cn.com.xcsa.api.framework.platform.PlatformApi;
import cn.com.xcsa.api.framework.platform.SettingsApi;
import cn.com.xcsa.api.framework.platform.TenantSettingsApi;
import cn.com.xcsa.api.framework.redis.RedisCache;
import cn.com.xcsa.api.framework.security.SecurityApi;
import cn.com.xcsa.api.util.InfoCode;
import cn.com.xcsa.api.util.SettingsKey;
import cn.com.xcsa.gateway.domain.po.AccessScope;
import cn.com.xcsa.gateway.domain.po.AccessStrategy;
import cn.com.xcsa.gateway.domain.po.LoginRecord;
import cn.com.xcsa.gateway.domain.po.ResultMap;
import cn.com.xcsa.gateway.domain.po.Tenant;
import cn.com.xcsa.gateway.domain.po.User;
import cn.com.xcsa.gateway.domain.vo.UserVo;
import cn.com.xcsa.gateway.service.AccessScopeService;
import cn.com.xcsa.gateway.service.AccessStrategyService;
import cn.com.xcsa.gateway.service.LoginRecordService;
import cn.com.xcsa.gateway.service.TenantService;
import cn.com.xcsa.gateway.service.UserService;
import cn.com.xcsa.gateway.util.Constants;
import cn.com.xcsa.gateway.util.GroupVariable;
import cn.com.xcsa.gateway.util.SourceEnum;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@RestController
@RequestMapping("/api/v1/login")
@Slf4j
public class LoginController {

    @Resource
    private UserService userService;

    @Resource
    private RedisCache redisCache;

    @Resource
    private LoginRecordService loginRecordService;

    @Resource
    private SecurityApi securityApi;
    @Resource
    private PlatformApi platformApi;
    @Resource
    private SettingsApi settingsApi;
    @Resource
    private TenantSettingsApi tenantSettingsApi;
    @Resource
    private AccessStrategyService accessStrategyService;
    @Resource
    private TenantService tenantService;
    @Resource
    private AccessScopeService scopeService;
    @Resource
    private LdapTemplate ldapTemplate;

    private static final String CONFIG_LOGIN_NAME = "loginName"; //登陆配置项：账号密码登陆
    private static final String CONFIG_LOGIN_PHONE = "phone"; //登陆配置项：手机号登陆
    private  static final String CONFIG_LOGIN_EMAIL = "email"; //登陆配置项：邮箱登陆

    private static final Integer OPEN_LOGIN = 1;

    private static final Integer LOGIN_TIME_OUT = 30;

    /**
     * 登陆方法.
     * @param userVo
     * @param request
     * @return 开启双因子 返回下一步认证所需信息，未开启正常返回登录结果.
     */
    @PostMapping("/account")
    public Object account(@RequestBody @Validated(GroupVariable.class) UserVo userVo,
                          HttpServletRequest request) {
        //确认拖拽验证码开关状态
        Boolean flag = BooleanUtil.toBoolean(getConfigValue(SettingsKey.CAPTCHA_CONFIG));
        if (flag) {
            //校验参数
            String key = Constants.genTokenKey(Constants.BUSINESS_AUTH,
                    Constants.CAPTCHA, userVo.getToken());
            if (StrUtil.isBlankIfStr(key) || !redisCache.hasKey(key)) {
                throw new ApiRuntimeException(InfoCode.PARAMS_ERROR);
            }
        }
        //查询账号登陆系统配置
        LambdaQueryWrapper<AccessStrategy> queryWrapper =
                Wrappers.lambdaQuery(AccessStrategy.class);
        Tenant tenant = null;
        DeployType deployInfo = platformApi.deployInfo();
        //saas、pass部署方式需区分域名确定租户
        if (deployInfo.equals(DeployType.SAAS) || deployInfo.equals(DeployType.PASS)) {
            tenant = tenantService.getOne(Wrappers.lambdaQuery(Tenant.class)
                    .eq(Tenant::getDomain, request.getHeader("host")));
            queryWrapper.eq(ObjectUtil.isNotEmpty(tenant),
                    AccessStrategy::getTenantId, tenant.getId());
        }
        //local部署以及本系统不需要租户id查询配置
        AccessStrategy accessStrategy = accessStrategyService.getOne(queryWrapper);
        if (ObjectUtil.isEmpty(accessStrategy)) {
            throw new ApiRuntimeException(InfoCode.SERVICE_UNAVAILABLE, "服务器内部错误");
        }
        //确认当前登录ip是否是合法范围段
        String clientIP = ServletUtil.getClientIP(request);
        log.info("clientIP:" + clientIP);
         AccessScope accessScope = scopeService.getOne(Wrappers.lambdaQuery(AccessScope.class));
        String cidr = accessScope.getVisitRange();
        //不在允许网段范围内
        if (!NetUtil.isInRange(clientIP, cidr)) {
            throw new ApiRuntimeException(InfoCode.FAIL);
        }
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class);
        wrapper.eq(accessStrategy.getLoginName().equals(OPEN_LOGIN),
                        User::getLoginName, userVo.getLoginName()).or()
                .eq(accessStrategy.getEmail().equals(OPEN_LOGIN),
                        User::getEmail, userVo.getLoginName()).or()
                .eq(accessStrategy.getPhone().equals(OPEN_LOGIN),
                        User::getPhone, userVo.getLoginName());
        User user = userService.getOne(wrapper);
        if (BeanUtil.isEmpty(user)) {
            throw new ApiRuntimeException(InfoCode.USER_IS_NULL);
        }
        //确认用户状态是否可登陆
        if (User.ActivateStatus.UNACTIVATED.getValue().equals(user.getActivateStatus())
                || User.LockStatus.LOCKED.getValue().equals(user.getLockStatus())
                || !User.UserStatus.DEFAULT.getValue().equals(user.getUserStatus())) {
            throw new ApiRuntimeException(InfoCode.USER_ERROR);
        }
       /* if (!SourceEnum.LOCAL.getValue().equals(user.getSource())) {
            throw new ApiRuntimeException(InfoCode.FAIL);
        }*/
        //来源是ldap 走ldap认证登录
        if (SourceEnum.LDAP.getValue().equals(user.getSource())) {
            Boolean loginFlag = ldapLogin(userVo);
            if (!loginFlag) {
                throw new ApiRuntimeException(InfoCode.FAIL);
            }
        }
        //验证密码
        if (!userVo.getPassword().equals(user.getPassword())) {
            throw new ApiRuntimeException(InfoCode.PASSWORD_ERROR);
        }

        //生成token
        String token = securityApi.login(user.getTenantId(), user.getId(),
                userVo.getLoginName(), user.getSource());
        JSONArray list = new JSONArray();
        if (OPEN_LOGIN.equals(accessStrategy.getToken())) {
            list.add("code");
        }
        if (OPEN_LOGIN.equals(accessStrategy.getSms())) {
            list.add("sms");
        }
        //未开启双因子认证
        if (list.size() < 1) {
            user.setToken(token);
            try {
                LoginRecord record = new LoginRecord();
                BeanUtil.copyProperties(user, record, "id");
                record.setStatus(1);
                record.setUserId(user.getId());
                record.setIpAddr(clientIP);
                record.setSystemName(request.getHeader("sys"));
                record.setSystemVersion(request.getHeader("sysv"));
                record.setClientVersion(request.getHeader("version"));
                record.setTerminalType(request.getHeader("plat"));
                record.setDeviceId(request.getHeader("did"));
                record.setCreateTime(DateTime.now().toLocalDateTime());
                loginRecordService.save(record);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            return user;
        }
        String uuid = UUID.randomUUID().toString();
        String uuidKey = Constants.genTokenKey(
                Constants.BUSINESS_LOGIN, Constants.TYPE_ACCOUNT, uuid);
        redisCache.setCacheObject(uuidKey, true, LOGIN_TIME_OUT, TimeUnit.MINUTES);
        ResultMap resultMap = new ResultMap();
        resultMap.put("secondCheck", list);
        resultMap.put("accessToken", uuid);
        return resultMap;
    }


    private Boolean ldapLogin(UserVo userVo) {
        ldapTemplate.setIgnorePartialResultException(true);
        ldapTemplate.setDefaultCountLimit(0);
        //获取认证源配置信息
//        JSONObject jsonObject = UserAuthService.getOne(Wrappers.lambdaQuery(UserAuth.class););
        JSONObject jsonObject = null;
        List<String> cn = ldapTemplate.search(query().base(jsonObject.getString("baseDn"))
                        .where("objectclass").is("person")
                        .and(jsonObject.getString("配置的账号字段名")).is(userVo.getLoginName())
                        .and("userPassword").is(userVo.getPassword()),
                (AttributesMapper<String>) attrs -> (String) attrs.get("cn").get());
        if (cn.size() > 0 && ObjectUtil.isNotEmpty(cn.get(0))) {
            return true;
        }
        return false;
    }

    /**
     * 根据定义的key获取系统配置.
     * @param key
     * @return 具体配置项的信息.
     */
    private String getConfigValue(String key) {
        //此配置需登录前查询 因为租户id是登录后获取的 所以租户的配置不查询
       /*   DeployType deployInfo = platformApi.deployInfo();
        if (!DeployType.LOCAL.equals(deployInfo)) {
            TenantSettingsDto tenantSettingsDto = tenantSettingsApi.settingsInfo(null, key);
            return tenantSettingsDto.getParamValue();
        }*/
        SettingsDto info = settingsApi.settingsInfo(key);
        if (BeanUtil.isEmpty(info)) {
            throw new ApiRuntimeException(InfoCode.USER_IS_NULL);
        }
        return info.getParamValue();
    }


}
