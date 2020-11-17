package com.jiyou.nm.adminapi.controller;


import com.jiyou.nm.adminapi.service.IWasRoleMenuService;
import com.jiyou.nm.adminapi.service.IWasRoleService;
import com.jiyou.nm.common.entity.PageReq;
import com.jiyou.nm.common.exception.RestResponse;
import com.jiyou.nm.model.vo.UpdateRoleMenuReq;
import com.jiyou.nm.model.vo.UpdateWasRoleReq;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 老酒后台-角色 前端控制器
 * </p>
 *
 * @author 
 * @since 2020-09-25
 */
@RestController
@RequestMapping("/role")
public class WasRoleController {

    private IWasRoleService wasRoleService;

    private IWasRoleMenuService wasRoleMenuService;

    public WasRoleController(IWasRoleService wasRoleService, IWasRoleMenuService wasRoleMenuService) {
        this.wasRoleService = wasRoleService;
        this.wasRoleMenuService = wasRoleMenuService;
    }

    @PostMapping
    public RestResponse addRole(@RequestBody @Validated UpdateWasRoleReq req) {
        return wasRoleService.saveOrUpdateUser(req)
                ? RestResponse.success("保存成功") : RestResponse.error("保存失败");
    }

    @PutMapping("/{id}")
    public RestResponse updateUser(@RequestBody @Validated UpdateWasRoleReq req, @PathVariable("id") Integer id) {
        req.setId(id);
        return wasRoleService.saveOrUpdateUser(req)
                ? RestResponse.success("修改成功") : RestResponse.error("修改失败");
    }

    @GetMapping("/page")
    public RestResponse pageQuery(String keyword, PageReq pageReq) {
        return RestResponse.success(wasRoleService.pageQuery(keyword, pageReq));
    }

    @PutMapping("/{id}/{state}")
    public RestResponse updateState(@PathVariable("id") Integer id, @PathVariable("state") Integer state) {
        return wasRoleService.updateState(id, state)
                ? RestResponse.success("修改成功") : RestResponse.error("修改失败");
    }

    @DeleteMapping("/{id}")
    public RestResponse updateState(@PathVariable("id") Integer id) {
        return wasRoleService.delete(id)
                ? RestResponse.success("删除成功") : RestResponse.error("删除失败");
    }

    @GetMapping("/{id}/menu")
    public RestResponse selectRoleMenuTree(@PathVariable("id") Integer id) {
        return RestResponse.success(wasRoleMenuService.selectRoleMenuTree(id));
    }

    @PutMapping("/{id}/menu")
    public RestResponse updateRoleMenu(@PathVariable("id") Integer id, @RequestBody @Validated UpdateRoleMenuReq req) {
        req.setRoleId(id);
        return wasRoleMenuService.updateRoleMenu(req)
                ? RestResponse.success("更新成功") : RestResponse.error("更新失败");
    }

}
