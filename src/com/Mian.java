package com;

import java.io.File;

public class Mian {
	// Ŀǰ�ڽ���xml���滹������
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		UnZipApk unz = new UnZipApk();
		EditXml edi = new EditXml();
		EditValues val = new EditValues();
		EditSmali eds = new EditSmali();
		ZipApk zip = new ZipApk();
		unz.AntiApk();// ��ѹAPK
		String fileName = unz.fileName;
		String fileDir = fileName.substring(0, fileName.length() - 4);
		edi.dom4J(fileDir + "\\AndroidManifest.xml");
		edi.EditXmlPlus(fileDir + "\\Androidmanifest.xml");
		String returnValue = edi.getApp_id();
		if (returnValue != null
				&& (!returnValue.contains("app_id") || !returnValue
						.contains("APP_ID"))) {// ���������app_id���޸�value
			val.func(fileDir + "\\res\\values\\strings.xml", returnValue);
			// ��һ��ֵΪvalue��string��Ŀ¼���ڶ���ֵΪappid
			// �༭app_id��ֵ+/
		}
		// dom4j�������⣬����
		// ���û�У�ֱ�ӱ༭smali
		String MainActivity = edi.getMainActivity(fileDir
				+ "\\Androidmanifest.xml");
		String activityDir = eds.dotToDir(MainActivity);// ��������ڸĳ�Ŀ¼
		eds.smaliEdit(fileDir + "\\smali\\" + activityDir + ".smali");// �༭smali
		zip.zippo(fileDir);// ���
		File apkNeedSign = new File(fileDir + "\\dist\\" + fileName);
		apkNeedSign.renameTo(new File(".", "unsigned_" + fileName));
		apkNeedSign.delete();
	}

}
