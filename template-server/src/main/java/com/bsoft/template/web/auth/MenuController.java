package com.bsoft.template.web.auth;

import com.bsoft.template.common.Result;
import com.bsoft.template.entity.auth.Menu;
import com.bsoft.template.service.auth.MenuService;
import com.bsoft.template.util.RequestParamPaser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 菜单controller
 */
@RequestMapping("menu")
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(@RequestBody Menu menu) {
        return menuService.save(menu);
    }

    /**
     * 获取列表
     */
    @GetMapping("/getList")
    public Result getList(HttpServletRequest request) {
        return menuService.getList(RequestParamPaser.getParameters(request));
    }

    /**
     * 删除
     */
    @GetMapping("/remove")
    public Result remove(int id) {
        return menuService.remove(id);
    }
}
