package com;

import java.io.File;
import java.io.IOException;

import brut.common.BrutException;

public class UnZipApk {
	String fileName = null;

	public void AntiApk() {

		FileAccept fa = new FileAccept();
		fa.setName("apk");
		File fff = new File(".");
		File srcImage[] = fff.listFiles(fa);
		for (int i = 0; i < srcImage.length; i++) {
			try {
				setFileName(srcImage[i].getName());
				System.out.println("unzip " + fileName + " beging!");
				brut.apktool.Main.main(new String[] { "d", getFileName() });
				System.out.println("unzip finished!");
			} catch (IOException | InterruptedException | BrutException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
