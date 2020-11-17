package com.jiyou.nm.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 老酒后台-角色对应的菜单
 * </p>
 *
 * @author 
 * @since 2020-09-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("was_role_menu")
public class WasRoleMenu extends Model<WasRoleMenu> {

    private static final long serialVersionUID = 1L;

    private Integer roleId;

    /**
     * 菜单id
     */
    private Integer menuId;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
