
package com.itdj.admin.model.dto;

import com.itdj.admin.model.entity.SysDept;
import lombok.Data;

/**
 * @author djj
 * @date 2018/1/20
 *
 */
@Data
public class DeptDTO extends SysDept {
    //父类Id
    private String parentName;
}
