package com.bsoft.template.service.auth;

import com.bsoft.template.mapper.auth.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 菜单业务类
 */
@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;
}
