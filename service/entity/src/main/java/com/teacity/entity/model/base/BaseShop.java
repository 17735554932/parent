package com.teacity.entity.model.base;

import com.jfinal.plugin.activerecord.IBean;
import io.jboot.db.model.JbootModel;

/**
 * Generated by Jboot, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseShop<M extends BaseShop<M>> extends JbootModel<M> implements IBean {

	public void setShopId(java.lang.String shopId) {
		set("shop_id", shopId);
	}
	
	public java.lang.String getShopId() {
		return getStr("shop_id");
	}

	public void setShopName(java.lang.String shopName) {
		set("shop_name", shopName);
	}
	
	public java.lang.String getShopName() {
		return getStr("shop_name");
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

	public void setCity(java.lang.String city) {
		set("city", city);
	}
	
	public java.lang.String getCity() {
		return getStr("city");
	}

}
