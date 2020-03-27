package com.bit.util;

import java.io.File;
import java.util.UUID;

import com.oreilly.servlet.multipart.FileRenamePolicy;


public class MyFileRenamePolicy implements FileRenamePolicy{
	@Override
	public File rename(File f) {
		// �솗�옣�옄 �뜲�씠�꽣 異붿텧
		String name = f.getName();
		System.out.println(f.getName()+"파일");
		String ext = "";
		int dot = name.lastIndexOf(".");
		
		if(dot != -1) { // �솗�옣�옄媛� 議댁옱�븳�떎硫�,
			ext = name.substring(dot);
		}else { // �뾾�떎硫�, 鍮� 臾몄옄�뿴!
			ext = "";
		}//end else
		
		/*
		 * 	UID (User Identifier)
		 * 		- �궗�슜�옄 �떇蹂꾩옄 (OS�뿉 �뵲�씪 32鍮꾪듃, 64鍮꾪듃 �벑...)
		 * 	UUID (Universally Unique Identifier)
		 * 		- 踰붿슜 怨좎쑀 �떇蹂꾩옄 (32媛쒖쓽 16吏꾩닔 �몴�쁽, 128鍮꾪듃, ...)
		 */
		String fileName="bit"+UUID.randomUUID(); // �쑀�땲�겕�븳 �옖�뜡 �떇蹂꾩옄 �깮�꽦
		File renameFile = new File(f.getParentFile(), fileName+ext);
		// �깉濡쒖슫 �뙆�씪 �씠由꾧낵 �솗�옣�옄瑜� 遺숈씤 �깉 �뙆�씪 �깮�꽦!
		
		return renameFile; // �씠由꾩쓣 諛붽씔 �뙆�씪 諛섑솚!
		
	}
}
