/**
 *author：罗圣盛
 *target:
 *version:
 *time:2014-9-10下午5:37:41
 *other:
 */
package com.lss.control;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.lss.global.FileOperation;
import com.lss.model.FileSystem;
import com.lss.model.VirtualFile;
import com.lss.window.MainJFrame;

public class CommandExe {
	MainJFrame mainJFrame = null;
	FileSystem fileSystem = null;
	String[] command = null;
	String userName = null;
	int commandType = 0;
	// 命令集
	public static String[] commandArray = { "mkdir", "rmdir", "touch", "cd",
			"ls", "rm", "pwd", "help", "clear", "edit", "init", "cp", "mv",
			"chomd", "loginout", };
	// 命令集对应参数个数
	public static int[] commandArgArray = {};

	public CommandExe(String[] command, FileSystem fileSystem, int commandType,
			MainJFrame mainJFrame) {
		this.commandType = commandType;
		this.fileSystem = fileSystem;
		this.userName = fileSystem.getCurrentUser();
		this.command = command;
		this.mainJFrame = mainJFrame;
	}

	// 检查是否有重名文件冲突
	public boolean checkConflictName(DefaultMutableTreeNode node,
			String fileName) {
		System.out.println("检查是否有重名文件冲突");
		if (!node.isLeaf()) {
			Enumeration<DefaultMutableTreeNode> childEnumeration = node
					.children();
			while (childEnumeration.hasMoreElements()) {
				DefaultMutableTreeNode defaultMutableTreeNode = childEnumeration
						.nextElement();
				VirtualFile childVirtualFile = (VirtualFile) defaultMutableTreeNode
						.getUserObject();
				// 加入数据
				if (childVirtualFile.getName().equals(fileName)) {
					mainJFrame.addStringToJta2(fileName + "文件已经存在");
					return true;
				}
			}
		}
		return false;
	}

	public boolean exeCheck(int[] checkItem) {
		// 权限检查
		for (int i = 0; i < checkItem.length; i++) {
			if (checkItem[i] == 1) {
				switch (i + 1) {
				case 1: {
					if (!fileSystem.primisionCheck(fileSystem.getCurrentPath(),
							userName, 1)) {
						mainJFrame.addStringToJta2("无读取权限！");
						return false;
					}
				}
					break;
				case 2: {
					if (!fileSystem.primisionCheck(fileSystem.getCurrentPath(),
							userName, 2)) {
						mainJFrame.addStringToJta2("无写权限！");
						return false;
					}
				}
					break;
				case 3: {
					if (!fileSystem.primisionCheck(fileSystem.getCurrentPath(),
							userName, 3)) {
						mainJFrame.addStringToJta2("无执行权限！");
						return false;
					}
					break;
				}
				case 4: {
					if (!fileSystem.primisionCheck(fileSystem.getCurrentPath(),
							userName, 4)) {
						mainJFrame.addStringToJta2("当前文件类型不是目录！");
						return false;
					}
					break;
				}
				default:
					break;
				}
			}
		}
		return true;
	}

	public boolean exeCdCheck(int[] checkItem, String path) {
		// 权限检查
		// 判断是否有这个文件
		if (mainJFrame.getFileTreeView().findTreePath(path) == null) {
			mainJFrame.addStringToJta2("路径有误！");
			return false;
		}
		// 路径转为xpath
		String xPath = virtaulPathToXPath(path);
		for (int i = 0; i < checkItem.length; i++) {
			if (checkItem[i] == 1) {
				switch (i + 1) {
				case 1: {
					if (!fileSystem.primisionCheck(xPath, userName, 1)) {
						mainJFrame.addStringToJta2("无读取权限！");
						return false;
					}
				}
					break;
				case 2: {
					if (!fileSystem.primisionCheck(xPath, userName, 2)) {
						mainJFrame.addStringToJta2("无写权限！");
						return false;
					}
				}
					break;
				case 3: {
					if (!fileSystem.primisionCheck(xPath, userName, 3)) {
						mainJFrame.addStringToJta2("无执行权限！");
						return false;
					}
					break;
				}
				case 4: {
					if (!fileSystem.primisionCheck(xPath, userName, 4)) {
						mainJFrame.addStringToJta2("文件类型不是目录！");
						return false;
					}
					break;
				}
				default:
					break;
				}
			}
		}
		return true;
	}

	// 执行命令函数
	public boolean executeCommand() {
		switch (commandType) {
		case 1: {
			// mkdir处理命令
			if (command.length != 2) {
				// 参数个数检查
				System.out.println("参数个数检查");
				mainJFrame.addStringToJta2("命令参数个数不对");
				return false;
			} else {
				int[] checkItem = { 0, 1, 0, 1 };
				if (exeCheck(checkItem)) {
					mkdirExe();
				}
			}
			break;
		}
		case 2: {
			// rmdir命令处理
			// 参数检查
			// 如果是-a,-r先cd
			if (command.length == 3) {
				if (command[1].equals("-c") || command[1].equals("-a")
						|| command[1].equals("-r")) {
					if (cdFirst()) {
						if (exeCheck(new int[] { 0, 1, 1, 1 })) {
							rmExe();
						}
					}
				}
			} else if (command.length == 2 && command[1].equals("-c")) {
				rmExe();
			} else {
				System.out.println("参数个数检查");
				mainJFrame.addStringToJta2("命令参数个数不对");
				return false;
			}

			break;
		}
		case 3: {
			// touch命令处理
			if (command.length == 3) {
				if (exeCheck(new int[] { 0, 1, 0, 1 })) {
					touchExe();
				}
			} else {
				System.out.println("参数个数检查");
				mainJFrame.addStringToJta2("命令参数个数不对");
				return false;
			}
			break;
		}
		case 4: {
			// cd 命令处理
			// cd 路径转换
			if (command.length == 3) {
				if (command[1].equals("-a") || command[1].equals("-r")) {
					if (pathChange()) {
						if (exeCdCheck(new int[] { 0, 0, 1, 0 }, command[2])) {
							cdExe();
							command[1] = "-c";
						}
					}
				}
			}
			break;
		}
		case 5: {
			// ls 命令处理
			int[] checkItem = { 0, 0, 1, 1 };
			if (exeCheck(checkItem) && command.length == 1) {
				lsExe();
			} else {
				return false;
			}
			break;
		}
		case 6: {
			// rm 命令处理
			// 如果是-a||-r先cd
			if (command.length == 3) {
				if (command[1].equals("-c") || command[1].equals("-a")
						|| command[1].equals("-r")) {
					if (cdFirst()) {
						if (exeCheck(new int[] { 0, 1, 1, 1 })) {
							rmExe();
						}
					}
				}
			} else if (command.length == 2 && command[1].equals("-c")) {
				rmExe();
			} else {
				System.out.println("参数个数检查");
				mainJFrame.addStringToJta2("命令参数个数不对");
				return false;
			}
			break;
		}
		case 7: {
			// pwd 命令处理
			if (exeCheck(new int[] { 0, 0, 0, 0 }) && command.length == 1) {
				pwdExe();
				return true;
			} else {
				return false;
			}
		}
		case 8: {
			// help 命令处理
			if (command.length == 1) {
				helpExe();
				return true;
			} else {
				return false;
			}
		}
		case 9: {
			// clear 命令处理
			if (command.length == 1) {
				clearExe();
				return true;
			} else {
				return false;
			}
		}
		case 10: {
			// edit file
			System.out.println("commandexecute10");
			if (command.length == 3) {
				if (command[1].equals("-a") || command[1].equals("-r")) {
					if (cdFirst()) {
						// 是文件
						if (!exeCheck(new int[] { 0, 0, 0, 1 })) {
							if (exeCheck(new int[] { 0, 1, 1, 0 })) {
								if (editExe())
									return true;
							}
						} else {
							mainJFrame.addStringToJta2("是目录，不是文件");
							return false;
						}
					}
				}
			} else if (command.length == 2 && command[1].equals("-c")) {
				// 是文件
				if (!exeCheck(new int[] { 0, 0, 0, 1 })) {
					if (exeCheck(new int[] { 0, 1, 1, 0 })) {
						if (editExe())
							return true;
					}
				} else {
					mainJFrame.addStringToJta2("是目录，不是文件");
					return false;
				}
			} else {
				System.out.println("参数个数检查");
				mainJFrame.addStringToJta2("命令参数个数不对");
				return false;
			}

			break;
		}
		default: {
			return false;
		}
		}
		return true;
	}

	private boolean cdFirst() {
		// TODO Auto-generated method stub
		// target:
		System.out.println("cdFirst");
		if (pathChange()) {
			if (exeCdCheck(new int[] { 0, 0, 1, 0 }, command[2])) {
				if (command[1].equals("-a") || command[1].equals("-r")) {
					cdExe();
					command[1] = "-c";
					return true;
				}
			}
		}
		return false;
	}

	private boolean pathChange() {
		// TODO Auto-generated method stub
		// target:
		if (command[1].equals("-r")) {
			// 相对路径
			// 路径转为绝对路径
			System.out.println("PathChange");
			String dianDian = "";
			if (command[2].indexOf(".") == -1 || command[2].indexOf(".") > 1) {
				String header = fileSystem.getCurrentVirtualPath() + "/";
				command[2] = header + command[2];
			} else {
				dianDian = command[2].substring(0, command[2].indexOf("/"));
				if (dianDian.equals(".")) {
					command[2] = fileSystem.getCurrentVirtualPath()
							+ command[2].substring(command[2].indexOf("/"));
					System.out.println("");
				} else if (dianDian.equals("..")) {
					String header = fileSystem.getCurrentVirtualPath()
							.substring(
									0,
									fileSystem.getCurrentVirtualPath()
											.lastIndexOf("/"));
					command[2] = header
							+ command[2].substring(command[2].indexOf("/"));
				} else {
					mainJFrame.addStringToJta2("输入路径有误！");
					return false;
				}
			}
			System.out.println("cd:" + command[2]);
			command[1] = "-a";
		}
		return true;
	}

	public boolean editExe() {
		// TODO Auto-generated method stub
		// target:]
		System.out.println("editExe");
		mainJFrame.clearJta3();
		String path = fileSystem.getCurrentPath();
		String string = fileSystem.readFile(path);
		mainJFrame.addStringToJta3(string);
		return true;
	}

	public boolean mkdirExe() {
		// 获得tree model
		DefaultTreeModel defaultTreeModel = (DefaultTreeModel) mainJFrame
				.getJTree().getModel();
		System.out.println("mainJFrame新建文件getSelectionPath："
				+ mainJFrame.getJTree().getSelectionPath());
		// 获取父节点路径
		TreePath parentPath = mainJFrame.getJTree().getSelectionPath();
		// 获得父节点
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) parentPath
				.getLastPathComponent();
		// 检查是否有重名文件
		if (checkConflictName(parentNode, command[1])) {
			return false;
		}
		// 创建新节点
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());
		VirtualFile newVirtualFile = new VirtualFile(command[1], userName,
				"rwxr-x", fileSystem.getCurrentPath(), "directory",
				fileSystem.getCurrentVirtualPath(), time);
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(
				newVirtualFile);
		// 插入到树中
		defaultTreeModel.insertNodeInto(newNode, parentNode,
				parentNode.getChildCount());
		// tree的scrollPathToVisible()方法在使Tree会自动展开文件夹以便显示所加入的新节点。若没加这行则加入的新节点
		// 会被 包在文件夹中，你必须自行展开文件夹才看得到。
		mainJFrame.getJTree().scrollPathToVisible(
				new TreePath(newNode.getPath()));
		// fileSystem.addFileNode(userName, command[1], command[2],
		// fileSystem.getCurrentPath());
		// 写入xml
		fileSystem.addFileNode(newVirtualFile);
		mainJFrame.addStringToJta2("新建目录成功");
		return true;
	}

	public boolean rmdirExe() {
		// 获得tree model
		DefaultTreeModel defaultTreeModel = (DefaultTreeModel) mainJFrame
				.getJTree().getModel();
		// 获取当前所在节点路径
		TreePath selectPath = mainJFrame.getJTree().getSelectionPath();
		// 获得选择节点
		DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode) selectPath
				.getLastPathComponent();
		if (command[1].equals("-c")) {
			// 删除当前所选节点

			// 获得选择节点的父节点
			TreeNode parentNode = selectNode.getParent();
			// 删除节点
			if (parentNode != null) {
				defaultTreeModel.removeNodeFromParent(selectNode);
			}
			// 将treePath转为xml路径
			String deleteNodexmlPath = treePathToXPath(selectPath);
		} else if (command[1].equals("-rp")) {
			// 删除相对路径节点
		} else if (command[1].equals("-ap")) {
			// 删除绝对路径节点
		} else {
			mainJFrame.addStringToJta2("命令参数有误！");
		}
		// 写入xml
		// fileSystem.deleteFileNode(path, userName);
		mainJFrame.addStringToJta2("删除目录成功");
		return true;
	}

	public boolean touchExe() {
		// 获得tree model
		DefaultTreeModel defaultTreeModel = (DefaultTreeModel) mainJFrame
				.getJTree().getModel();
		System.out.println("mainJFrame新建文件getSelectionPath："
				+ mainJFrame.getJTree().getSelectionPath());
		// 获取父节点路径
		TreePath parentPath = mainJFrame.getJTree().getSelectionPath();
		// 获得父节点
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) parentPath
				.getLastPathComponent();
		// 检查是否有重名文件
		if (checkConflictName(parentNode, command[1])) {
			return false;
		}
		// 创建新节点
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());
		VirtualFile newVirtualFile = new VirtualFile(command[1], userName,
				"rwxr-x", fileSystem.getCurrentPath(), command[2],
				fileSystem.getCurrentVirtualPath(), time);
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(
				newVirtualFile);
		// 插入到树中
		defaultTreeModel.insertNodeInto(newNode, parentNode,
				parentNode.getChildCount());
		// tree的scrollPathToVisible()方法在使Tree会自动展开文件夹以便显示所加入的新节点。若没加这行则加入的新节点
		// 会被 包在文件夹中，你必须自行展开文件夹才看得到。
		mainJFrame.getJTree().scrollPathToVisible(
				new TreePath(newNode.getPath()));
		// 写入xml
		fileSystem.addFileNode(newVirtualFile);
		mainJFrame.addStringToJta2("新建文件成功");
		return true;
	}

	public boolean cdExe() {
		// 获得tree model
		DefaultTreeModel defaultTreeModel = (DefaultTreeModel) mainJFrame
				.getJTree().getModel();
		// 获取当前所在节点路径
		TreePath selectPath = mainJFrame.getJTree().getSelectionPath();

		if (command[1].equals("-a")) {
			// 绝对路径
			TreePath treePath = null;
			if (mainJFrame.getFileTreeView().findTreePath(command[2]) == null) {
				mainJFrame.addStringToJta2("输入路径路径有误！");
				return false;
			} else {
				treePath = mainJFrame.getFileTreeView()
						.findTreePath(command[2]);
			}
			mainJFrame.getJTree().setSelectionPath(treePath);
			System.out
					.println("cd:" + mainJFrame.getJTree().getSelectionPath());
			String currentVirtualPath = fileSystem.getCurrentVirtualPath();
			String currentRealPath = virtaulPathToXPath(currentVirtualPath);
			fileSystem.setCurrentVirtualPath(currentVirtualPath);
			fileSystem.setCurrentPath(currentRealPath);
			System.out.println("变更当前路径为：" + fileSystem.getCurrentVirtualPath());

		} else {
			mainJFrame.addStringToJta2("命令参数有误！");
		}
		// 更改当前路径
		// fileSystem.deleteFileNode(path, userName);
		// mainJFrame
		// .addStringToJta2("转换到了：" + fileSystem.getCurrentVirtualPath());
		return true;
	}

	public boolean lsExe() {
		mainJFrame.getFileJTableView().lsCommandExe();
		return true;
	}

	public boolean rmExe() {
		System.out.println("commandExe.rmExe");
		// 获得tree model
		DefaultTreeModel defaultTreeModel = (DefaultTreeModel) mainJFrame
				.getJTree().getModel();
		// 获取当前所在节点路径
		TreePath selectPath = mainJFrame.getJTree().getSelectionPath();
		// 获得选择节点
		DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode) selectPath
				.getLastPathComponent();
		String deleteNodexmlPath = null;
		TreeNode parentNode = null;
		if (command[1].equals("-c")) {
			// 删除当前所选节点
			// 获得选择节点的父节点
			parentNode = selectNode.getParent();
			// 删除节点
			if (parentNode != null) {
				defaultTreeModel.removeNodeFromParent(selectNode);
			} else {
				return false;
			}
			// 更改为父路径
			TreePath parentTreePath = new TreePath(parentNode);
			mainJFrame.getJTree().setSelectionPath(parentTreePath);
			String currentVirtualPath = fileSystem.getCurrentVirtualPath();
			String currentRealPath = virtaulPathToXPath(currentVirtualPath);
			fileSystem.setCurrentVirtualPath(currentVirtualPath);
			fileSystem.setCurrentPath(currentRealPath);
			System.out.println("变更当前路径为：" + fileSystem.getCurrentVirtualPath());

		} else {
			mainJFrame.addStringToJta2("命令参数有误！");
			return false;
		}
		// 写入xml
		// 将treePath转为xml路径
		deleteNodexmlPath = treePathToXPath(selectPath);
		System.out.println("删除" + deleteNodexmlPath);
		fileSystem.deleteFileNode(deleteNodexmlPath, userName);
		mainJFrame.addStringToJta2("删除文件成功");
		return true;
	}

	public boolean pwdExe() {
		mainJFrame.addStringToJta2("当前路径是："
				+ fileSystem.getCurrentVirtualPath());
		return true;
	}

	public boolean helpExe() {
		List<String> help = FileOperation.readHelpTxt();
		for (String string : help) {
			mainJFrame.addStringToJta2(string);
		}
		return true;
	}

	public boolean clearExe() {
		mainJFrame.clearJta2();
		return true;
	}

	// 将treePath 转为XPath
	private String treePathToXPath(TreePath treePath) {
		// TODO Auto-generated method stub
		// target:
		String treePathString = treePath.toString();
		// treePathString分隔放到list里
		// List<String> treePathList = new ArrayList<String>();
		// Xpath
		String xPath = null;
		// xPath stringBuilder
		StringBuilder xPathBuilder = new StringBuilder();
		// 正则表达式
		String regex = "([0-9]|[a-z]|[A-Z]|[.])+";
		Matcher matcher = Pattern.compile(regex).matcher(treePathString);
		String tempString = null;
		while (matcher.find()) {
			tempString = matcher.group();
			int first = treePathString.indexOf(tempString);
			treePathString = treePathString.substring(
					first + tempString.length(), treePathString.length());
			// treePathList.add(tempString);
			xPathBuilder.append("/file");
		}
		tempString = "[@name='" + tempString + "']";
		xPathBuilder.append(tempString);
		xPath = xPathBuilder.toString();
		return xPath;
	}

	// 将virtaulPath 转为 treePath
	private TreePath virtaulPathTotreePath(String virtaulPath) {
		// TODO Auto-generated method stub
		// target:
		// virtaulPath分隔放到list里
		String[] virtaulPathArray = virtaulPath.split("/");
		//

		// treePath
		TreePath treePath = new TreePath(virtaulPathArray);
		return treePath;
	}

	// no use将virtaulPath 转为 realpath
	private String virtaulPathToRealPath(String virtaulPath) {
		// TODO Auto-generated method stub
		// target:
		// virtaulPath分隔放到list里
		String[] virtaulPathArray = virtaulPath.split("/");
		// realPath
		String realPath = null;
		for (int i = 0; i < virtaulPathArray.length; i++) {
			if (i == virtaulPathArray.length) {
				realPath = realPath + "/file[@name='" + virtaulPathArray[i]
						+ "']";
			} else {
				realPath = realPath + "/file";
			}
		}
		return realPath;
	}

	// 将virtaulPath 转为 xpath
	private String virtaulPathToXPath(String virtaulPath) {
		// TODO Auto-generated method stub
		// target:
		// virtaulPath分隔放到list里
		String[] virtaulPathArray = virtaulPath.split("/");
		// xPath
		String xPath = null;
		// xPath stringBuilder
		StringBuilder xPathBuilder = new StringBuilder();
		String tempString = null;
		// 正则表达式
		String regex = "([0-9]|[a-z]|[A-Z]|[.])+";
		Matcher matcher = Pattern.compile(regex).matcher(virtaulPath);
		while (matcher.find()) {
			tempString = matcher.group();
			int first = virtaulPath.indexOf(tempString);
			virtaulPath = virtaulPath.substring(first + tempString.length(),
					virtaulPath.length());
			// treePathList.add(tempString);
			xPathBuilder.append("/file");
		}
		tempString = "[@name='" + tempString + "']";
		xPathBuilder.append(tempString);
		xPath = xPathBuilder.toString();
		return xPath;
	}

	public static void main(String[] args) {
		int[] checkItem = { 0, 1, 0, 1 };
		for (int i = 0; i < checkItem.length; i++) {
			System.out.println(checkItem[i]);
		}
	}
}
