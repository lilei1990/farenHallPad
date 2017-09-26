package com.shyms.farendating.http;

import org.json.JSONObject;

public interface AsyncHttpCallBack {

	void onSuccess(int what, int statusCode, JSONObject response);
	void onFailure(int what, int statusCode, JSONObject errorResponse);
	
}
