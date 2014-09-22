/**
 *author：罗圣盛
 *target:
 *version:
 *time:2014-9-7下午7:34:11
 *other:
 */
package com.lss.global;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class FileOperation {
	// 读取一个文件的文本信息到List
	public static List<String> readFile(String path) {
		System.out.println("fileOperation.readFile");
		List<String> userNameList = new ArrayList<String>();
		try {
			// FileReader fileReader=new FileReader("");
			// System.out.println(new File(".").getAbsolutePath());
			System.out.println("读取文本信息" + path);
			BufferedReader bufferedReader = new BufferedReader(new FileReader(
					path));
			String data = bufferedReader.readLine();
			while (data != null) {
				userNameList.add(data);
				// System.out.println(data);
				data = bufferedReader.readLine();
			}
			bufferedReader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userNameList;
	}

	// 按行读取user文件
	public static List<String> readUserName() {
		return readFile("Root/etc/user.txt");
	}

	// 按行读取help文件
	public static List<String> readHelpTxt() {
		return readFile("Root/etc/help.txt");
	}

	// No Use
	public static List<String> readxmlFile() {
		return readFile("Root/file/file.xml");
	}

	// 测试xpath
	public static void testXpath() {
		Document document = FileOperation.readFileXml();
		// List<Element> elements =
		// document.selectNodes("/file/file[@name='home']/file[@name='lsshome']/file/content");
		// String realPath = path+"[@name='"+nameString+"'][1]";
		// List<Element> elements =
		// document.selectNodes("/file/file[@name='home']/file[@name='lsshome']/file/content");
		List<Element> elements = document
				.selectNodes("/file/file[@name='home']/file[@name='lsshome']");
		for (Element element : elements) {
			element.setAttributeValue("type", "txt");
		}
		// List<Element> elements = document.selectNodes("/file/file");
		// for(Element element:elements){
		// System.out.println(element.attributeValue("name"));
		// if (element.attributeValue("type").trim().equals("directory")) {
		// List<Element> childList = element.selectNodes("./file");
		// for(Element childnode:childList){
		// System.out.println(childnode.attributeValue("name"));
		// }
		// }
		// }
		FileOperation.testWriteFileXml(document);
		return;
	}

	// 写进filexml 只是这个文件测试用
	public static boolean testWriteFileXml(Document document) {
		File xmlFile = new File("Root/file/file.xml");
		try {
			XMLWriter xmlWriter = new XMLWriter(new FileWriter(xmlFile));
			xmlWriter.write(document);
			System.out.println("写入file.xml成功");
			xmlWriter.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	// 读取一个xml文件到document
	public static Document readFileXml() {
		SAXReader saxReader = new SAXReader();
		File xmlFile = new File("Root/file/file.xml");
		Document document = null;
		try {
			document = saxReader.read(xmlFile);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			System.out.println("fail to read file.xml");
			e1.printStackTrace();
		}
		return document;
	}

	// 创建一个xml文件：：no use
	public static int createXMLFile(String filename) {
		return 1;
	}

	public static void main(String[] args) {

		// List<String> list=FileOperation.readFile("Root/file/file.xml");
		// createXMLFile("Root/file/test.xml");
		// FileOperation.readxmlFile();
		// FileOperation.readFileXml();
		FileOperation.testXpath();
	}
}
