package com.itdj;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itdj.admin.mapper.SysDeptRelationMapper;
import com.itdj.admin.model.entity.SysDept;
import com.itdj.admin.model.entity.SysDeptRelation;
import com.itdj.admin.service.SysDeptRelationService;
import com.itdj.admin.service.SysDeptService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItdjUpmsServerApplicationTests {


    @Autowired
    private SysDeptService mapper;
    @Autowired
    private SysDeptRelationService sysDeptRelationService;
    @Autowired
    private SysDeptRelationMapper sysDeptRelationMapper;

    /**
     * <p> * 根据 entity 条件，查询一条记录, * 这里和上方删除构造条件一样，只是seletOne返回的是一条实体记录，当出现多条时会报错 * </p>
     */
    @Test
    public void selectOne() {
        QueryWrapper<SysDeptRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("descendant", 1);
        List<SysDeptRelation> list = sysDeptRelationMapper.selectList(queryWrapper);
        System.out.println(list);
    }


}
