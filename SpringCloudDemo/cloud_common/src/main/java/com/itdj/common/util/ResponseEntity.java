package com.itdj.common.util;


import java.util.HashMap;
import java.util.Map;

public class ResponseEntity extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public ResponseEntity() {
        put("code", 0);
        put("msg", "操作成功");
    }

    public static ResponseEntity error() {
        return error(1, "操作失败");
    }

    public static ResponseEntity error(String msg) {
        return error(500, msg);
    }

    public static ResponseEntity error(int code, String msg) {
        ResponseEntity r = new ResponseEntity();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static ResponseEntity ok(String msg) {
        ResponseEntity r = new ResponseEntity();
        r.put("msg", msg);
        return r;
    }

    public static ResponseEntity ok(Map<String, Object> map) {
        ResponseEntity r = new ResponseEntity();
        r.putAll(map);
        return r;
    }

    public static ResponseEntity ok() {
        return new ResponseEntity();
    }

    @Override
    public ResponseEntity put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
