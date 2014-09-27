package com.newfeature.msg.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.newfeature.msg.database.SparksContract.PromotionsColumns;

import android.content.ContentProviderOperation.Builder;

public class PendingMsgModel {

	private String couponTitle;
	private String couponLink;
	private String store;
	private Date crawlTime;
	
	public PendingMsgModel() {
	}
	
	public PendingMsgModel(String couponTitle, String couponLink, String store,
			Date crawlTime) {
		super();
		this.couponTitle = couponTitle;
		this.couponLink = couponLink;
		this.store = store;
		this.crawlTime = crawlTime;
	}

	public String getCouponTitle() {
		return couponTitle;
	}
	public void setCouponTitle(String couponTitle) {
		this.couponTitle = couponTitle;
	}
	public String getCouponLink() {
		return couponLink;
	}
	public void setCouponLink(String couponLink) {
		this.couponLink = couponLink;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public Date getCrawlTime() {
		return crawlTime;
	}
	public void setCrawlTime(Date crawlTime) {
		this.crawlTime = crawlTime;
	}

	public static List<PendingMsgModel> getList(JSONArray resultArray) {
		List<PendingMsgModel> list = new ArrayList<PendingMsgModel>();
		
		for(int i = 0; i < resultArray.length(); i++) {
			list.add(PendingMsgModel.get(resultArray.optJSONObject(i)));
		}
		
		return list;
	}

	private static PendingMsgModel get(JSONObject optJSONObject) {
		PendingMsgModel pendingMsgModel = new PendingMsgModel();
		pendingMsgModel.setCouponTitle(optJSONObject.optString("coupon_title"));
		pendingMsgModel.setCouponLink(optJSONObject.optString("coupon_link"));
		pendingMsgModel.setStore(optJSONObject.optString("store"));
		pendingMsgModel.setCrawlTime(optJSONObject.optLong("crawl_time"));
		return pendingMsgModel;
	}

	private void setCrawlTime(long long1) {
		setCrawlTime(new Date(long1));
	}

	public Builder toContentProviderOperation(Builder builder) {
		builder.withValue(PromotionsColumns.COUPON_LINK, couponLink);
		builder.withValue(PromotionsColumns.COUPON_TITLE, couponTitle);
		builder.withValue(PromotionsColumns.STORE, store);
		builder.withValue(PromotionsColumns.crawl_time, crawlTime.getTime());
		return builder;
	}

}
