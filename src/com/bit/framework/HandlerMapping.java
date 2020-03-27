package com.bit.framework;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.bit.framework.annotation.RequestMapping;

// ���� : ��û�� URL�� ���� ������ Controller ������ �Ѱ��ִ� ����
public class HandlerMapping {
	private Map<String, CtrlAndMethod> mappings;
	
	public HandlerMapping(String urlCtrlName) throws Exception {
		mappings = new HashMap<>();
		
		String[] ctrls = urlCtrlName.split("\\|");
		// | (������ ����) �������� ������ �迭�� ��ƶ�.
		
		for(String ctrl : ctrls) {
			ctrl = ctrl.trim(); //�и��� ��Ʈ�ѷ� �̸��� ���� ���� ����
			
			Class<?> clz = Class.forName(ctrl); // Ŭ���� �ε�
			Object target = clz.newInstance(); // �ν��Ͻ� ����
			
			Method[] methods = clz.getMethods();
			
			//�ش� Ŭ������ �޼ҵ带 �����Ͷ�.
			
			for(Method method : methods) {
				RequestMapping reqAnno = method.getAnnotation(RequestMapping.class);
				// RequestMapping�̶�� �ش�Ǵ� Annotation�� �����Ͷ�.
				
				// ����, ��ȸ ���� Method��  @RequstMApping ������ ����
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
