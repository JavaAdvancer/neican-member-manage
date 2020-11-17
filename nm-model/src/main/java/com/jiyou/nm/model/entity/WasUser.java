package com.jiyou.nm.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 管理后台用户表
 * </p>
 *
 * @author 
 * @since 2020-09-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("was_user")
public class WasUser extends Model<WasUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别  1-男， 2-女， 9-其他
     */
    private Integer sex;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 头像图片地址
     */
    private String portrait;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 是否超级管理员 1-是 0-否
     */
    private Integer superAdmin;

    /**
     * 用户状态 1-启用 0-禁用
     */
    private Integer state;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除 0-未删除 1-已删除
     */
    private Integer isDel;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
