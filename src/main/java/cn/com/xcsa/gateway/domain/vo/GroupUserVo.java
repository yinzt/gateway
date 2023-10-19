package cn.com.xcsa.gateway.domain.vo;

import cn.com.xcsa.gateway.util.GroupAdd;
import cn.com.xcsa.gateway.util.GroupDelete;
import cn.com.xcsa.gateway.util.GroupList;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
* <p> 用户组关联用户表</p>.
*
* @author huyu
* @since 2023-09-24
*/
@Getter
@Setter
public class GroupUserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键.
    */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
    * 关联sys_group表的id.
    */
    @NotNull(message = "用户组主键信息必须填写", groups = GroupList.class)
    @NotNull(message = "用户所属分组信息必须填写", groups = GroupDelete.class)
    @NotNull(message = "用户所属分组必须填写", groups = GroupAdd.class)
    private Long groupId;

    /**
    * 关联sys_user表的id.
    */
    @NotNull(message = "用户信息必须填写", groups = GroupDelete.class)
    private Long userId;

    /**
     * 关联租户表id.
     */
    @NotNull(message = "租户主键必须填写", groups = GroupAdd.class)
    private Long tenantId;

    /**
     * 创建时间.
     */
    private LocalDateTime createTime;

    /**
     * 用户昵称.模糊搜索用.
     */
    private String name;

    /**
     * 是否删除 0：否 1：是.
     */
    private Integer deleted;

    @NotNull(message = "分页参数必须传递", groups = GroupList.class)
    private Integer pageNo;

    @NotNull(message = "分页参数必须传递", groups = GroupList.class)
    private Integer pageSize;

    /**
     * 添加用户所属对应分组的用户id数组.
     */
    @NotEmpty(message = "用户id不能为空", groups = GroupAdd.class)
    private List<Long> userIds;

}
