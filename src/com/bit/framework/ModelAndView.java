package com.bit.framework;

import java.util.HashMap;
import java.util.Map;

/*
 * Model	: 유저에게 보여줄 Data
 * View		: 유저에게 보여줄 Page 정보를 저장.
 */
public class ModelAndView {
	private Map<String, Object> model;
	private String view;
	
	public ModelAndView() {
		model = new HashMap<>();
	}

	public Map<String, Object> getModel() {
		return model;
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}
	
	public void addAtrribute(String key, Object value) {
		model.put(key, value);
	}
}
