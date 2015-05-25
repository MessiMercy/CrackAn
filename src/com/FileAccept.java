package com;

import java.io.File;
import java.io.FilenameFilter;

public class FileAccept implements FilenameFilter {

	// @Override
	String extendsName;

	public void setName(String s) {
		extendsName = "." + s;
	}

	public boolean accept(File dir, String name) {
		// TODO 自动生成的方法存根
		return name.endsWith(extendsName);
	}

}
