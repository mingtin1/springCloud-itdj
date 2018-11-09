package com.itdj.admin.utils;

import com.itdj.admin.model.entity.SysDept;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class LayuiTreeReplay extends LayuiReplay {

    private boolean is;
    private String tip;

    public LayuiTreeReplay() {

    }

    public LayuiTreeReplay(int code, String msg, int count, Object data,boolean is,String tip) {
        super();
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
        this.is = is;
        this.tip = tip;
    }
}