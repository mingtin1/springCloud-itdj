package com.itdj.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itdj.admin.model.entity.SysUser;
import com.itdj.admin.model.queryPage.UserPage;
import com.itdj.common.vo.UserVO;

import java.util.List;


/**
 * @author djj
 * @since 2018-10-16
 */
public interface SysUserService extends IService<SysUser> {


    /**
     * 分页查询
     * @param userPage
     * @return
     */
    List<UserVO> selectWithRolePage(UserPage userPage);
}
