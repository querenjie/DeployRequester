package com.myself.deployrequester.util.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JsonResult implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final Integer JSON_RESULT_SUCCESS = Integer.valueOf(1);

	public static final Integer JSON_RESULT_FAILED = Integer.valueOf(0);

	public static final Integer JSON_RESULT_SUCCESS_PART = Integer.valueOf(2);

	private final List<Object> data = new ArrayList();
	private Integer returnCode;
	private String msg;
	private String html;
	private String totalCount;
	private String pageCount;


	protected JsonResult() {
	}

	private JsonResult(Integer returnCode, String msg) {
		this.returnCode = returnCode;
		this.msg = msg;
	}

	public static JsonResult createSuccess() {
		return new JsonResult(JSON_RESULT_SUCCESS, null);
	}

	public static JsonResult createSuccess(String msg) {
		return new JsonResult(JSON_RESULT_SUCCESS, msg);
	}

	public static JsonResult createSuccessPart() {
		JsonResult jsonResult = new JsonResult(JSON_RESULT_SUCCESS_PART, null);
		return jsonResult;
	}

	public static JsonResult createSuccessPart(String msg) {
		JsonResult jsonResult = new JsonResult(JSON_RESULT_SUCCESS_PART, msg);
		return jsonResult;
	}

	public static JsonResult createFailed(String msg) {
		JsonResult jsonResult = new JsonResult(JSON_RESULT_FAILED, msg);
		return jsonResult;
	}

	public Integer getReturnCode() {
		return this.returnCode;
	}

	public void setReturnCode(Integer returnCode) {
		this.returnCode = returnCode;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<Object> getData() {
		return this.data;
	}

	public void addData(Object obj) {
		this.data.add(obj);
	}

	public String getHtml() {
		return this.html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public void removeDataAll() {
		if (this.data != null)
			this.data.clear();
	}

	public void addDataAll(List list) {
		if (null != list)
			this.data.addAll(list);
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getPageCount() {
		return pageCount;
	}

	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}

	@Override
	public String toString() {
		return "JsonResult{" +
				"data=" + data +
				", returnCode=" + returnCode +
				", msg='" + msg + '\'' +
				", html='" + html + '\'' +
				", totalCount='" + totalCount + '\'' +
				", pageCount='" + pageCount + '\'' +
				'}';
	}
}