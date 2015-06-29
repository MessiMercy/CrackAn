package com;

import java.io.File;

public class Mian {
	
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		UnZipApk unz = new UnZipApk();
		EditXml edi = new EditXml();
		EditValues val = new EditValues();
		EditSmali eds = new EditSmali();
		ZipApk zip = new ZipApk();
		unz.AntiApk();// 解压APK
		String fileName = unz.fileName;
		String fileDir = fileName.substring(0, fileName.length() - 4);
		edi.dom4J(fileDir + "\\AndroidManifest.xml");
		edi.EditXmlPlus(fileDir + "\\Androidmanifest.xml");
		String returnValue = edi.getApp_id();
		if (returnValue != null
				&& (!returnValue.contains("app_id") || !returnValue
						.contains("APP_ID"))) {// 如果发现有app_id就修改value
			val.func(fileDir + "\\res\\values\\strings.xml", returnValue);
			// 第一个值为value下string的目录，第二个值为appid
			// 编辑app_id的值+/
		}
		// dom4j出现问题，待改
		//已解决
		// 如果没有，直接编辑smali
		String PackageName = edi.getPackageName();
		String MainActivity = edi.getMainActivity(fileDir
				+ "\\Androidmanifest.xml");
		if (MainActivity.contains(".")) {
			if (MainActivity.split("\\.")[0].equals("")) {
				MainActivity = PackageName + MainActivity;
			}
		} else {
			MainActivity = PackageName + "." + MainActivity;
		}
		String activityDir = eds.dotToDir(MainActivity);// 将程序入口改成目录
		eds.smaliEdit(fileDir + "\\smali\\" + activityDir + ".smali");// 编辑smali
		zip.zippo(fileDir);// 打包
		File apkNeedSign = new File(fileDir + "\\dist\\" + fileName);
		apkNeedSign.renameTo(new File(".", "unsigned_" + fileName));
		apkNeedSign.delete();
	}

}
