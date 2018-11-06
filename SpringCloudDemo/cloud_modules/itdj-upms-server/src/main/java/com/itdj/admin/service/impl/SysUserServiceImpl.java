
package com.itdj.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itdj.admin.mapper.SysUserMapper;
import com.itdj.admin.model.dto.UserDTO;
import com.itdj.admin.model.entity.SysUser;
import com.itdj.admin.model.queryPage.UserPage;
import com.itdj.admin.service.SysUserService;
import com.itdj.admin.utils.LayuiReplay;
import com.itdj.common.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Override
    public int slectChount(UserPage userPage) {
        return mapper.slectChount(userPage);
    }

    @Override
    public LayuiReplay userPage(UserPage userPage) {
        int count = slectChount(userPage);
        List<UserVO> userVOs = selectWithRolePage(userPage);
        return new LayuiReplay(0, "获得数据成功", count, userVOs);
    }

    @Override
    public UserVO selectUserVoById(Integer id) {
        return mapper.selectUserVoById(id);
    }

    @Override
    public boolean updateUser(UserDTO userDTO, String username) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDTO, sysUser);
        sysUser.setUpdateTime(new Date());
        return this.updateById(sysUser);
    }

    /**
     * 删除用户
     *
     * @param sysUser 用户
     * @return Boolean
     */
    @Override
    @CacheEvict(value = "user_details", key = "#sysUser.username")
    public Boolean deleteUserById(SysUser sysUser) {
//        sysUserRoleService.deleteByUserId(sysUser.getUserId());
        this.removeById(sysUser.getUserId());
        return Boolean.TRUE;
    }
}
