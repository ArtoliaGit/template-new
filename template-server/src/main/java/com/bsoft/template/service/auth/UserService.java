package com.bsoft.template.service.auth;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bsoft.template.common.Result;
import com.bsoft.template.common.ResultCodeEnum;
import com.bsoft.template.common.auth.UserInfo;
import com.bsoft.template.entity.auth.Person;
import com.bsoft.template.entity.auth.User;
import com.bsoft.template.mapper.auth.PersonMapper;
import com.bsoft.template.mapper.auth.RoleMapper;
import com.bsoft.template.mapper.auth.UserMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * 用户业务类
 */
@Service("userService")
public class UserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserInfo userInfo;

    @Value("${custom.default-password}")
    private String defaultPassword;

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户实体
     * @throws UsernameNotFoundException 用户没有获取到异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return user;
    }

    /**
     * 获取用户列表
     */
    public Result getUserList(Map<String, String> params) {
        Result result = new Result();

        Page<User> page = new Page<>(
                Long.parseLong(params.getOrDefault("page", "1")),
                Long.parseLong(params.getOrDefault("pageSize", "10"))
        );

        IPage<User> iPage = userMapper.getUserList(page, params);

        result.code(ResultCodeEnum.OK.getCode())
                .message("查询成功")
                .data(iPage.getRecords())
                .total(iPage.getTotal());
        return result;
    }

    /**
     * 根据用户id获取用户
     */
    public Result getUserById(int userId) {
        Result result = new Result();
        User user = userMapper.getUserById(userId);
        result.code(ResultCodeEnum.OK.getCode())
                .message("查询成功")
                .data(user);
        return result;
    }

    /**
     * 根据用户名获取用户
     */
    public Result getUserByUsername(String username) {
        Result result = new Result();
        User user = userMapper.findByUsername(username);
        result.code(ResultCodeEnum.OK.getCode())
                .message("查询成功")
                .data(user);
        return result;
    }

    /**
     * 获取当前登录用户信息
     */
    public Result getUserInfo() {
        return getUserByUsername(userInfo.getUsername());
    }

    /**
     * 保存用户
     */
    @Transactional
    public Result save(User user) {
        Result result = new Result();
        int num;
        if (user.getUserId() != null) {
            num = userMapper.updateById(user);
            if (user.getPerson().getEmpId() == null) {
                user.getPerson().setUserId(user.getUserId());
                personMapper.insert(user.getPerson());
            } else {
                personMapper.updateById(user.getPerson());
            }
        } else {
            if (userMapper.findByUsername(user.getUsername()) != null) {
                result.setCode(ResultCodeEnum.SAVE_OR_UPDATE_FAIL.getCode());
                result.setMessage("用户名已存在");
                return result;
            }
            Date now = new Date();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            final String password = DigestUtils.md5Hex(defaultPassword);
            user.setPassword(encoder.encode(password));
            user.setCreateTime(now);
            num = userMapper.insert(user);
            user.getPerson().setUserId(user.getUserId());
            user.getPerson().setCreateTime(now);
            personMapper.insert(user.getPerson());
        }
        roleMapper.deleteRoles(user);
        roleMapper.saveRoles(user);
        if (num > 0) {
            result.code(ResultCodeEnum.OK.getCode()).message("保存成功");
        } else {
            result.code(ResultCodeEnum.SAVE_OR_UPDATE_FAIL.getCode())
                    .message(ResultCodeEnum.SAVE_OR_UPDATE_FAIL.getMessage());
        }
        return result;
    }

    /**
     * 保存用户基本信息
     * @param person 用户基本信息
     * @return result
     */
    public Result savePerson(Person person) {
        Result result = new Result();
        if (person.getEmpId() == null) {
            personMapper.insert(person);
        } else {
            personMapper.updateById(person);
        }
        return result.ok().message("保存成功");
    }

    /**
     * 删除用户
     */
    public Result remove(int userId) {
        Result result = new Result();
        int num = userMapper.deleteById(userId);
        if (num > 0) {
            result.code(ResultCodeEnum.OK.getCode()).message("删除成功");
        } else {
            result.error().message("删除失败");
        }

        return result;
    }

    /**
     * 禁用用户
     */
    public Result disableUser(int userId) {
        Result result = new Result();
        User user = new User();
        user.setUserId(userId);
        user.setStatus(2);
        user.setDisableTime(new Date());
        return result.ok();
    }

    /**
     * 重置密码
     */
    public Result resetPassword(int userId) {
        Result result = new Result();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String password = DigestUtils.md5Hex(defaultPassword);
        User user = new User();
        user.setUserId(userId);
        user.setPassword(encoder.encode(password));
        userMapper.updateById(user);
        return result.ok();
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(DigestUtils.md5Hex("123"));
    }
}
