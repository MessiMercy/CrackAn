package com;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class EditValues {
	public boolean checkValues(String name) {
		boolean flag = true;
		File androidmanifest = new File(name);
		try {
			RandomAccessFile fff = new RandomAccessFile(androidmanifest, "rw");
			String str = "";
			// StringBuilder sbB = new StringBuilder();
			while ((str = fff.readLine()) != null) {
				if (str.contains("app_id") || str.contains("APP_ID")) {
					flag = false;
				}
			}
			fff.close();
		} catch (FileNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return flag;
	}

	public boolean func(String url, String value) {
		System.out.println("editing values!");
		boolean flag = true;
		if (!checkValues(url)) {
			return flag;
		}
		SAXReader sax = new SAXReader();
		try {
			Document doc = sax.read(new File(url));
			doc.setXMLEncoding("utf-8");
			Element element = doc.getRootElement();
			Element eee = element.addElement("string");
			eee.addAttribute("name", "app_id").addText(value);// ������
			System.out.println("aaa");
			XMLWriter xml = new XMLWriter(new FileWriter(new File(url)));
			// do something
			xml.write(doc);// д��ԭxml��
			xml.close();
			// System.out.println();
			// @SuppressWarnings("unchecked");
			// List<Element> list = element.elements("strinig");
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			return flag;
			// TODO: handle exception
		}
		System.out.println("editing values finished!");
		return flag;
	}
}
