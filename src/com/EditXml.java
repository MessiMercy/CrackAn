package com;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class EditXml {

	String app_id = null;// app_id的值
	String str = null;// 指app_id的名字
	String PackageName;

	public String getPackageName() {
		return PackageName;
	}

	public void setPackageName(String packageName) {
		PackageName = packageName;
	}

	public String getApp_id() {
		return app_id;
	}

	// 保存app_id 的值，方便修改value值
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	// public void getPackage() {
	//
	// return;
	// }
	// 找到程序入口
	public String getMainActivity(String fileName) {
		System.out.println("i can get the name of mainactivity!");
		SAXReader sar = new SAXReader();
		try {
			org.dom4j.Document coc = sar.read(new File(fileName));
			org.dom4j.Element element = coc.getRootElement();
			String pac = element.attributeValue("package");
			setPackageName(pac);
			org.dom4j.Element eve = element.element("application");
			eve.addNamespace("android", "");
			@SuppressWarnings("unchecked")
			List<Element> node = eve.elements("activity");
			for (Iterator<Element> iterator = node.iterator(); iterator
					.hasNext();) {
				Element element2 = (Element) iterator.next();
				// Element e =
				// element2.element("intent-filter").element("action");
				Element e2 = element2.element("intent-filter").element(
						"category");
				// Attribute attr = e.attribute("name");
				Attribute attr2 = e2.attribute("name");
				if (attr2.getText()
						.contains("android.intent.category.LAUNCHER")) {
					return element2.attributeValue("name");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		System.out.println("get mainactivity success!");
		return "no value";

	}

	// 得到并修改app_id，返回appid的名字。
	public String dom4J(String name) {
		System.out.println("editing xml!");
		SAXReader sar = new SAXReader();
		try {
			org.dom4j.Document coc = sar.read(new File(name));
			org.dom4j.Element element = coc.getRootElement();
			// 顺便获取包名
			Attribute attrPackage = element.attribute("package");
			setPackageName(attrPackage.getText());
			org.dom4j.Element eve = element.element("application");
			// eve.addNamespace("android", "");
			@SuppressWarnings("unchecked")
			List<org.dom4j.Element> nodes = eve.elements("meta-data");
			for (Iterator<org.dom4j.Element> it = nodes.iterator(); it
					.hasNext();) {
				org.dom4j.Element elm = it.next();
				Attribute attr = elm.attribute("name");
				Attribute attrvalue = elm.attribute("value");
				String str1 = attr.getText();
				if (str1.contains("app_id") || str1.contains("APP_ID")) {
					setApp_id(attrvalue.getText());
					System.out.println(app_id);
					attrvalue.setValue("@string/app_id");
					str = str1;
				}
			}
			XMLWriter xml = new XMLWriter(new FileWriter(new File(name)));
			// do something
			xml.write(coc);// 写入原xml中
			xml.close();

		} catch (DocumentException | IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("get app_id error.");
			return "error";
		}
		System.out.println("check and edit success!");
		return str;
	}

	// dom4j并不支持添加standalone属性。手动添加
	public void EditXmlPlus(String name) {
		File androidmanifest = new File(name);
		try {
			RandomAccessFile fff = new RandomAccessFile(androidmanifest, "rw");
			String str = "";
			fff.readLine();
			StringBuilder sbB = new StringBuilder();
			sbB.append("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?>"
					+ "\r\n");
			while ((str = fff.readLine()) != null) {
				sbB.append(str + "\r\n");
			}
			fff.seek(0);
			fff.write(sbB.toString().getBytes("utf-8"));
			fff.close();
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
