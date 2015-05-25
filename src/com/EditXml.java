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

	String app_id = null;// app_id��ֵ
	String str = null;// ָapp_id������
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

	// ����app_id ��ֵ�������޸�valueֵ
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	// public void getPackage() {
	//
	// return;
	// }
	// �ҵ��������
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

	// �õ����޸�app_id������appid�����֡�
	public String dom4J(String name) {
		System.out.println("editing xml!");
		SAXReader sar = new SAXReader();
		try {
			org.dom4j.Document coc = sar.read(new File(name));
			org.dom4j.Element element = coc.getRootElement();
			// ˳���ȡ����
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
			xml.write(coc);// д��ԭxml��
			xml.close();

		} catch (DocumentException | IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			System.out.println("get app_id error.");
			return "error";
		}
		System.out.println("check and edit success!");
		return str;
	}

	// dom4j����֧�����standalone���ԡ��ֶ����
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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
}
