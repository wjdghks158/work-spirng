package com.bit.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 *	Java Annotation
 *		�Ϲ����� �ּ� : Ư�� �ܾ� �� ����(������)�� �����ϱ� ���� ����(������)
 *		-> �����Ϳ� ���� ������, ��Ÿ ������
 *		�ڹ� �ּ� : �ҽ� �ڵ忡 �����ϴ� ��Ÿ ������
 *
 * 		Built-in Annotation (����, import ���� �ʾƵ� ���)
 * 		@Override
 * 		: ������ �ƴ��� ����
 * 		@SuppressWarnings
 * 		: �����Ϸ��� ��� ����
 * 
 * 		Meta Annotation
 * 		@Target
 * 			- ������̼��� ���� ��ġ
 * 				ElementType.METHOD	// �޼ҵ�
 * 				ElementType.TYPE	// �޼ҵ� �� Ŭ����, �������̽� ���� ����
 * 				ElementType.FILED	// ��� ����(�ʵ�)
 * 					-> �ΰ� �̻� �� { } �׷� ����!
 * 		@Retention
 * 			- ������̼��� ���� �ֱ� (���� �ֱ�)
 * 				RetentionPolicy.RUNTIME ���� ��
 * 					-> ���� ó�� �ÿ��� RUNTIME
 * 					-> ���α׷� ���� �� ���� ��� ����
 */

// ����� ���� ������̼�
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
	String value() default "";
	// ���� �� ��, �츮�� �ش� ������̼��� �پ��ִ����� Ȯ��!

}
