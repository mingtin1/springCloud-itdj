package com.itdj.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itdj.admin.model.dto.UserDTO;
import com.itdj.admin.model.entity.SysUser;
import com.itdj.admin.model.queryPage.UserPage;
import com.itdj.admin.utils.LayuiReplay;
import com.itdj.common.vo.UserVO;

import java.util.List;


/**
 * @author djj
 * @since 2018-10-16
 */
public interface SysUserService extends IService<SysUser> {


    /**
     * 分页查询
     *
     * @param userPage
     * @return
     */
    List<UserVO> selectWithRolePage(UserPage userPage);

    /**
     * 查询条数
     */
    int slectChount(UserPage userPage);


    /**
     * 分页查询
     *
     * @param userPage
     * @return
     */
    LayuiReplay userPage(UserPage userPage);

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    UserVO selectUserVoById(Integer id);
    /**
     * 更新指定用户信息
     * @param userDTO 用户信息
     * @param username 用户信息
     * @return
     */
    boolean updateUser(UserDTO userDTO, String username);
    /**
     * 删除用户
     * @param sysUser 用户
     * @return boolean
     */
    Boolean deleteUserById(SysUser sysUser);

}
