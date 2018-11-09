package com.itdj.admin.utils;

public class LayuiReplay  {
	/**响应编码*/
	protected int code;
	/**响应消息*/
	protected String msg;
	/**数据总量*/
	protected int count;
	/**数据*/
	protected Object data;

	public LayuiReplay() {
	}

	public LayuiReplay(int code, String msg, int count, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.count = count;
		this.data = data;
	}

	@Override
	public String toString() {
		return "R [code=" + code + ", msg=" + msg + ", count=" + count + ", data=" + data + "]";
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}