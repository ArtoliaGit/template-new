package com.bsoft.template.web.auth;

import com.bsoft.template.service.auth.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单controller
 */
@RequestMapping("menu")
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;
}
