package com.lss.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Observable;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import com.lss.global.FileOperation;

public class FileSystem extends Observable {
	public static Document document = FileOperation.readFileXml();
	private String currentPath = "/file";
	private String currentVirtualPath = "/root";
	private String currentUser = null;

	// 构造
	public FileSystem(String userName) {
		this.setCurrentUser(userName);
	}

	// 新增一个文件节点,用文件的各个参数创建
	public boolean addFileNode(String ownerName, String fileName,
			String fileType, String parentPath) {
		List<Element> elements = document.selectNodes(parentPath);
		for (Element element : elements) {
			Element fileNode = element.addElement("file");
			fileNode.addAttribute("name", fileName);
			fileNode.addAttribute("type", fileType);
			fileNode.addAttribute("owner", ownerName);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = df.format(new Date());
			fileNode.addAttribute("time", time);
			fileNode.addAttribute("primission", "rwxr-x");
		}
		writeFileXml(document);
		return true;
	}

	// 新增一个文件节点,用virtualFile类参数创建
	public boolean addFileNode(VirtualFile virtualFile) {
		List<Element> elements = document.selectNodes(virtualFile
				.getParentPath());
		for (Element element : elements) {
			Element fileNode = element.addElement("file");
			fileNode.addAttribute("name", virtualFile.getName());
			fileNode.addAttribute("type", virtualFile.getType());
			fileNode.addAttribute("owner", virtualFile.getOwner());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = df.format(new Date());
			fileNode.addAttribute("time", time);
			fileNode.addAttribute("primission", virtualFile.getPrimission());
		}
		writeFileXml(document);
		return true;
	}

	// 改动一个节点的内容，type的值是1-2,1是表示改属性，2是表示改内容
	@SuppressWarnings("deprecation")
	public boolean changeNodeAttribute(String path, String attribute,
			String changeValue, int type) {
		String realPath = null;
		if (type == 1) {
			realPath = path;
			List<Element> elements = document.selectNodes(realPath);
			for (Element element : elements) {
				element.setAttributeValue(attribute, changeValue);
			}
		} else {
			realPath = path;
			List<Element> elements = document.selectNodes(realPath);
			for (Element element : elements) {
				element.setText(changeValue);
			}
		}
		writeFileXml(document);
		return true;
	}

	// 改动一个节点的内容
	@SuppressWarnings("deprecation")
	public boolean changeNodeText(String path, String changeValue) {
		String realPath = null;
		realPath = path;
		List<Element> elements = document.selectNodes(realPath);
		for (Element element : elements) {
			element.setText(changeValue);
		}
		writeFileXml(document);
		return true;
	}

	// 读
	@SuppressWarnings("deprecation")
	public String readFile(String path) {
		List<Element> elements = document.selectNodes(path);
		String string = null;
		for (Element element : elements) {
			string = element.getText();
		}
		return string;
	}

	// 删除一个节点
	@SuppressWarnings("deprecation")
	public boolean deleteFileNode(String path, String userName) {
		Element root = document.getRootElement();
		List<Element> elements = document.selectNodes(path);
		for (Element element : elements) {
			element.getParent().remove(element);
		}
		writeFileXml(document);
		return true;
	}

	// 检查是否有权限,type的值为1,2,3,4。1表示检查读，2表示检查写，3表示检查执行，4表示检查是否是目录
	@SuppressWarnings("deprecation")
	public boolean primisionCheck(String path, String userName, int type) {
		boolean primission = false;
		System.out.println("filesystem权限检查，-type:" + type + "-path:" + path);
		if (userName.equals("root")) {
			return true;
		} else {
			Element root = document.getRootElement();
			List<Element> elements = document.selectNodes(path);
			String pString = null;
			String fileType = null;
			String owner = null;
			for (Element element : elements) {
				fileType = element.attributeValue("type");
				pString = element.attributeValue("primission");
				owner = element.attributeValue("owner");
			}
			if (type == 4) {
				// 是否是目录
				if (fileType.trim().equals("directory")) {
					return true;
				}
				return false;
			} else if (!owner.equals(userName)) {
				type = type + 2;
			} else {
				type--;
			}

			switch (pString.charAt(type)) {
			case 'r':
				return true;

			case 'w':
				return true;

			case 'x':
				return true;

			default:
				return false;
			}
		}
	}

	// 写进filexml
	public boolean writeFileXml(Document document) {
		File xmlFile = new File("Root/file/file.xml");
		try {
			XMLWriter xmlWriter = new XMLWriter(new FileWriter(xmlFile));
			xmlWriter.write(document);
			System.out.println("写入file.xml成功");
			xmlWriter.close();
			setChanged();
			notifyObservers();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public String getCurrentPath() {
		return currentPath;
	}

	public void setCurrentPath(String currentPath) {
		this.currentPath = currentPath;
	}

	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

	public String getCurrentVirtualPath() {
		return currentVirtualPath;
	}

	public void setCurrentVirtualPath(String currentVirtualPath) {
		this.currentVirtualPath = currentVirtualPath;
	}

}
