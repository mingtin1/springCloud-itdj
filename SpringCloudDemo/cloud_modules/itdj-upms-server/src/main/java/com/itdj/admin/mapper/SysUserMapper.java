/*
 *    Copyright (c) 2018-2025, djj All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: djj (wangiegie@gmail.com)
 */

package com.itdj.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itdj.admin.model.entity.SysUser;
import com.itdj.admin.model.queryPage.UserPage;
import com.itdj.common.vo.UserVO;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author djj
 * @since 2018-10-16
 */

public interface SysUserMapper extends BaseMapper<SysUser> {

    List<UserVO> selectWithRolePage(UserPage userPage);

    int slectChount(UserPage userPage);

    UserVO selectUserVoById(Integer id);
}