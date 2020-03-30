package com.bit.util;

import java.io.File;
import java.util.UUID;

import com.oreilly.servlet.multipart.FileRenamePolicy;


public class MyFileRenamePolicy implements FileRenamePolicy{
	@Override
	public File rename(File f) {
		String name = f.getName();
		System.out.println(f.getName()+"");
		String ext = "";
		int dot = name.lastIndexOf(".");
		
		if(dot != -1) {
			ext = name.substring(dot);
		}else {
			ext = "";
		}//end else

		String fileName="bit"+UUID.randomUUID(); 
		File renameFile = new File(f.getParentFile(), fileName+ext);

		return renameFile;
		
	}
}
