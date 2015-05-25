package com;

import java.io.File;
import java.io.IOException;

import brut.common.BrutException;

public class ZipApk {

	public void zippo(String dirPath) {
		try {
			if (new File(dirPath).isDirectory()) {
				System.out.println("zip apk begin!");
				brut.apktool.Main.main(new String[] { "b", dirPath });
				System.out.println("apk has built, you need sign it");
			}
		} catch (IOException | InterruptedException | BrutException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

}
