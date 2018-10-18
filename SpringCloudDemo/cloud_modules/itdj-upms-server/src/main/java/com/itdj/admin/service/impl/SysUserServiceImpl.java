
package com.itdj.admin.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.itdj.admin.mapper.SysUserMapper;
import com.itdj.admin.model.entity.SysUser;
import com.itdj.admin.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author djj
 * @since 2018-10-16
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}
