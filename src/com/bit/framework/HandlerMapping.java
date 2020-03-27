package com.bit.framework;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.bit.framework.annotation.RequestMapping;

// 역할 : 요청한 URL에 대한 정보를 Controller 단으로 넘겨주는 역할
public class HandlerMapping {
	private Map<String, CtrlAndMethod> mappings;
	
	public HandlerMapping(String urlCtrlName) throws Exception {
		mappings = new HashMap<>();
		
		String[] ctrls = urlCtrlName.split("\\|");
		// | (파이프 라인) 기준으로 나누어 배열에 담아라.
		
		for(String ctrl : ctrls) {
			ctrl = ctrl.trim(); //분리한 컨트롤러 이름의 양쪽 공백 제거
			
			Class<?> clz = Class.forName(ctrl); // 클래스 로드
			Object target = clz.newInstance(); // 인스턴스 생성
			
			Method[] methods = clz.getMethods();
			
			//해당 클래스의 메소드를 가져와라.
			
			for(Method method : methods) {
				RequestMapping reqAnno = method.getAnnotation(RequestMapping.class);
				// RequestMapping이라고 해당되는 Annotation을 가져와라.
				
				// 만약, 순회 중인 Method에  @RequstMApping 없으면 순외
				if(reqAnno == null) continue;
				
				String uri = reqAnno.value();
				CtrlAndMethod cam
					= new CtrlAndMethod(target, method);
				
				mappings.put(uri,cam);
			}
		}
	}
	
	public CtrlAndMethod getCtrlAndMethod(String uri) {
		return mappings.get(uri);
		
	}
}
