package com.joker.allenmp3.util;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class StringPostRequest extends StringRequest {
	private Map<String, String> params;
	private Map<String, String> header;
	public StringPostRequest(String url, Response.Listener<String> listener,
							 Response.ErrorListener errorListener) {
		super(Method.POST, url, listener, errorListener);
		params = new HashMap<String, String>();
		header = new HashMap<String, String>();
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return header;
	}
	public void putHeaders(String key, String value){
		this.header.put(key, value);
	}
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return params;
	}
	public void putParams(String key, String value){
		this.params.put(key, value);
	}


}