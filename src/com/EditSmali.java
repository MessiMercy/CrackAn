package com;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class EditSmali {
	public String dotToDir(String str) {
		if (str.contains(".")) {
			System.out.println("��Ҫ�������������");
			// Scanner sc = new Scanner(arg0)
			return str.replace(".", "\\");
		}
		return null;
	}

	public boolean smaliEdit(String url) {
		boolean flag = true;
		System.out.println("Editing smali!");
		try {
			RandomAccessFile raf = new RandomAccessFile(new File(url), "rw");
			String str = "";
			StringBuilder sbb = new StringBuilder();
			while ((str = raf.readLine()) != null) {
				if (str.contains("iLLLLiiIIiIILLii")) {
					sbb.append("#" + str + "\r\n");
				} else {
					sbb.append(str + "\r\n");
				}

			}
			raf.seek(0);
			String res = new String(sbb.toString().getBytes("iso-8859-1"),
					"utf-8");
			raf.write(res.getBytes("utf-8"));
			// randomAccessFile.close();
			raf.close();
		} catch (FileNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			return false;
		}
		System.out.println("edit smali finished!");
		return flag;
	}
}
