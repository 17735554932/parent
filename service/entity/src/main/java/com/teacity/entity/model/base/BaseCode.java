package com.teacity.entity.model.base;

import com.jfinal.plugin.activerecord.IBean;
import io.jboot.db.model.JbootModel;

/**
 * Generated by Jboot, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseCode<M extends BaseCode<M>> extends JbootModel<M> implements IBean {

	public void setCodeId(java.lang.Integer codeId) {
		set("code_id", codeId);
	}
	
	public java.lang.Integer getCodeId() {
		return getInt("code_id");
	}

	public void setCode(java.lang.String code) {
		set("code", code);
	}
	
	public java.lang.String getCode() {
		return getStr("code");
	}

	public void setState(java.lang.Integer state) {
		set("state", state);
	}
	
	public java.lang.Integer getState() {
		return getInt("state");
	}

	public void setCreated(java.util.Date created) {
		set("created", created);
	}
	
	public java.util.Date getCreated() {
		return get("created");
	}

	public void setUpdated(java.util.Date updated) {
		set("updated", updated);
	}
	
	public java.util.Date getUpdated() {
		return get("updated");
	}

	public void setUserId(java.lang.String userId) {
		set("user_id", userId);
	}
	
	public java.lang.String getUserId() {
		return getStr("user_id");
	}

}
