package cn.com.xcsa.gateway.service.impl;

import cn.com.xcsa.gateway.domain.po.License;
import cn.com.xcsa.gateway.mapper.LicenseMapper;
import cn.com.xcsa.gateway.service.LicenseService;
import cn.com.xcsa.framework.core.service.impl.BaseServiceImpl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* <p> 服务实现类</p>.
*
* @author wuhui
* @since 2023-08-11
*/
@Service
public class LicenseServiceImpl extends BaseServiceImpl<LicenseMapper, License>
        implements LicenseService {


    @Resource
    private LicenseMapper licenseMapper;

    /**
     * 根据租户ID查询租户license.
     * @param tenantId
     * @return 租户license
     */
    @Override
    public String findLicenseByTenantId(Long tenantId) {
        License license = licenseMapper.selectOne(Wrappers.lambdaQuery(License.class)
                .eq(License::getTenantId, tenantId));
        if (license == null) {
            return null;
        }
        return license.getContent();
    }
}
