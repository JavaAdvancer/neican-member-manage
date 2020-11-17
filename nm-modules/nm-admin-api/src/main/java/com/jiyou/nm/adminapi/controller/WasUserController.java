package com.jiyou.nm.adminapi.controller;


import com.jiyou.nm.adminapi.service.IWasUserService;
import com.jiyou.nm.adminapi.util.ShiroUtil;
import com.jiyou.nm.common.entity.PageReq;
import com.jiyou.nm.common.exception.RestResponse;
import com.jiyou.nm.model.vo.UpdateWasUserReq;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 管理后台用户表 前端控制器
 * </p>
 *
 * @author 
 * @since 2020-09-25
 */
@RestController
@RequestMapping("/user")
public class WasUserController {

    private IWasUserService wasUserService;

    public WasUserController(IWasUserService wasUserService) {
        this.wasUserService = wasUserService;
    }

    @PostMapping
    public RestResponse addUser(@RequestBody @Validated UpdateWasUserReq req) {
        return wasUserService.saveOrUpdateUser(req)
                ? RestResponse.success("保存成功") : RestResponse.error("保存失败");
    }

    @PutMapping("/{id}")
    public RestResponse updateUser(@RequestBody @Validated UpdateWasUserReq req, @PathVariable("id") Integer id) {
        req.setId(id);
        return wasUserService.saveOrUpdateUser(req)
                ? RestResponse.success("修改成功") : RestResponse.error("修改失败");
    }

    @GetMapping("/page")
    public RestResponse pageQuery(String keyword, PageReq pageReq) {
        return RestResponse.success(wasUserService.pageQuery(keyword, pageReq));
    }

    @PutMapping("/{id}/{state}")
    public RestResponse updateState(@PathVariable("id") Integer id, @PathVariable("state") Integer state) {
        return wasUserService.updateState(id, state)
                ? RestResponse.success("修改成功") : RestResponse.error("修改失败");
    }

    @DeleteMapping("/{id}")
    public RestResponse updateState(@PathVariable("id") Integer id) {
        return wasUserService.delete(id)
                ? RestResponse.success("删除成功") : RestResponse.error("删除失败");
    }

    @GetMapping("/menu")
    public RestResponse getOwnedMenu() {
        return RestResponse.success(wasUserService.selectOwnedMenuTree(ShiroUtil.getCurrentUser().getId()));
    }

}
