package me.promenade.pandora.bean;

import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

public class HttpBean {
	private String url = null;
	private List<NameValuePair> param= null;
	private JSONObject json = null;
	private HttpMethod method = null;
	public String getUrl() {
		return url;
	}
	public void setUrl(
			String url) {
		this.url = url;
	}
	public List<NameValuePair> getParam() {
		return param;
	}
	public void setParam(
			List<NameValuePair> param) {
		this.param = param;
	}
	public JSONObject getJson() {
		return json;
	}
	public void setJson(
			JSONObject json) {
		this.json = json;
	}
	public HttpMethod getMethod() {
		return method;
	}
	public void setMethod(
			HttpMethod method) {
		this.method = method;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if( this.getUrl() != null ){
			sb.append("url->");
			sb.append(this.getUrl());
			sb.append("\n");
		}
		
		if( this.getParam() != null ){
			sb.append("param->");
			sb.append(this.getParam().toString());
			sb.append("\n");
		}
		
		if( this.getJson() != null ){
			sb.append("json->");
			sb.append(this.getJson().toString());
			sb.append("\n");
		}
		
		if( this.getMethod() != null ){
			sb.append("method->");
			sb.append(this.getMethod().toString());
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
