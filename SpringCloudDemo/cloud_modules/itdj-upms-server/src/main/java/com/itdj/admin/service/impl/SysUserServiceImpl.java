
package com.itdj.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itdj.admin.mapper.SysUserMapper;
import com.itdj.admin.model.entity.SysUser;
import com.itdj.admin.model.queryPage.UserPage;
import com.itdj.admin.service.SysUserService;
import com.itdj.common.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author djj
 * @since 2018-10-16
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper mapper;

    @Override
    public List<UserVO> selectWithRolePage(UserPage userPage) {
        return mapper.selectWithRolePage(userPage);

    }
}
