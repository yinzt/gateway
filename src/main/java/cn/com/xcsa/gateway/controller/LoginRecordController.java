package cn.com.xcsa.gateway.controller;

import cn.com.xcsa.gateway.domain.vo.LoginRecordVo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.servlet.ServletUtil;
import org.springframework.validation.annotation.Validated;
import cn.com.xcsa.gateway.domain.po.LoginRecord;
import cn.com.xcsa.gateway.service.LoginRecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * <p> 前端控制器</p>.
 *
 * @author huyu
 * @since 2023-09-13
 */
@RestController
@RequestMapping("/api/v1/loginRecord")
public class LoginRecordController {

    @Resource
    private LoginRecordService loginRecordService;

    /**
     * 保存.
     * @param recordVo
     * @param request
     * @return 保存结果.
     */
    @PostMapping("/save")
    public Object save(@RequestBody LoginRecordVo recordVo, HttpServletRequest request) {
        LoginRecord loginRecord = new LoginRecord();
        BeanUtil.copyProperties(recordVo, loginRecord);
        String clientIP = ServletUtil.getClientIP(request);
        loginRecord.setIpAddr(clientIP);
        return loginRecordService.save(loginRecord);
    }

    /**
     * 修改.
     * @param recordVo
     * @return 修改结果.
     */
    @PostMapping("/update")
    public Object update(@RequestBody @Validated LoginRecordVo recordVo) {
        LoginRecord loginRecord = new LoginRecord();
        BeanUtil.copyProperties(recordVo, loginRecord);
        return loginRecordService.updateById(loginRecord);
    }

    /**
     * 删除.
     * @param id
     * @return 删除结果.
     */
    @PostMapping("/delete")
    public Object delete(@RequestParam Integer id) {
        return loginRecordService.removeById(id);
    }

    /**
     * 列表.
     *
     * @return 列表数据.
     */
    @GetMapping("/list")
    public Object list() {
        return loginRecordService.list();
    }
}
