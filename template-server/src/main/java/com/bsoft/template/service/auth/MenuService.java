package com.bsoft.template.service.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bsoft.template.common.Result;
import com.bsoft.template.common.ResultCodeEnum;
import com.bsoft.template.entity.auth.Menu;
import com.bsoft.template.mapper.auth.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 菜单业务类
 */
@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 保存
     */
    public Result save(Menu menu) {
        Result result = new Result();
        int num;
        if (menu.getId() == null) {
            num = menuMapper.insert(menu);
        } else {
            num = menuMapper.updateById(menu);
        }
        if (num > 0) {
            result.ok().message("保存成功").data(menu);
        } else {
            result.code(ResultCodeEnum.SAVE_OR_UPDATE_FAIL.getCode())
                .message(ResultCodeEnum.SAVE_OR_UPDATE_FAIL.getMessage());
        }
        return result;
    }

    /**
     * 获取列表
     */
    public Result getList(Map<String, String> params) {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.allEq(params);
        List<Menu> menuList = menuMapper.selectList(wrapper);
        return new Result().ok().data(menuList);
    }

    /**
     * 删除
     */
    public Result remove(int id) {
        int num = menuMapper.deleteById(id);
        if (num > 0) {
            return new Result().ok();
        } else {
            return new Result().fail();
        }
    }
}
