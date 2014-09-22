/**
 *author：罗圣盛
 *target:
 *version:
 *time:2014-9-8下午4:27:50
 *other:
 */
package com.lss.window;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.dom4j.Document;
import org.dom4j.Element;

import com.lss.model.FileSystem;
import com.lss.model.VirtualFile;

@SuppressWarnings("serial")
public class FileTreeView implements Observer, TreeSelectionListener,
		MouseListener {
	private JTree jTree = null;
	private FileSystem fileSystem = null;
	private MainJFrame mainJFrame;

	public FileTreeView() {
		initComponent();
	}

	public FileTreeView(FileSystem fileSystem, MainJFrame mainJFrame) {
		this.setFileSystem(fileSystem);
		this.setMainJFrame(mainJFrame);
		initComponent();
	}

	public void initComponent() {
		Document document = FileSystem.document;
		// jTree根节点
		// DefaultMutableTreeNode root = buildTreeAndReturnRoot(document);
		// DefaultTreeModel defaultTreeModel = new DefaultTreeModel(root);
		Element rootElement = document.getRootElement();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(
				new VirtualFile(rootElement.attributeValue("name"),
						rootElement.attributeValue("owner"),
						rootElement.attributeValue("primission"), null,
						rootElement.attributeValue("type"), null,
						rootElement.attributeValue("time")));
		List<Element> list = document.selectNodes("/file/file");
		buildTree(list, root, "/file", "/root");
		setjTree(new JTree(root));
		TreePath treePath = new TreePath(jTree.getModel().getRoot());
		this.jTree.setSelectionPath(treePath);
		this.jTree.addTreeSelectionListener(this);
	}

	// 递归建立文件树
	public void buildTree(List<Element> list,
			DefaultMutableTreeNode parentNode, String path, String virtualPath) {
		Iterator<?> it = list.iterator();
		System.out.println("filetreeview.buildTree");
		DefaultMutableTreeNode child = null;
		while (it.hasNext()) {
			Element element = (Element) it.next();
			String childpath = path + "[@name='"
					+ element.attributeValue("name") + "']";
			String nextPath = path + "/file";
			String childVirtualPath = virtualPath + "/"
					+ element.attributeValue("name");
			child = new DefaultMutableTreeNode(new VirtualFile(
					element.attributeValue("name"),
					element.attributeValue("owner"),
					element.attributeValue("primission"), path,
					element.attributeValue("type"), virtualPath,
					element.attributeValue("time")));
			List<Element> childList = element.selectNodes("./file");
			if (childList != null) {
				parentNode.add(child);
				buildTree(childList, child, nextPath, childVirtualPath);
			} else {
				parentNode.add(child);
			}
		}
		return;
	}

	// 建树并且返回树的根
	public DefaultMutableTreeNode buildTreeAndReturnRoot(Document document) {
		Element rootElement = document.getRootElement();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(
				new VirtualFile(rootElement.attributeValue("name"),
						rootElement.attributeValue("owner"),
						rootElement.attributeValue("primission"), null,
						rootElement.attributeValue("type"), null,
						rootElement.attributeValue("time")));
		List<Element> list = document.selectNodes("/file/file");
		buildTree(list, root, "/file", "/root");
		return root;
	}

	// 遍历树查找node treepath
	public TreePath findTreePath(String virtualPath) {
		// target:
		System.out.println("filetreeview.findtreePath via virtualPath");
		boolean judge = true;
		String[] virtaulPathArray = virtualPath.split("/");
		DefaultMutableTreeNode node = null;
		for (int i = 1; i < virtaulPathArray.length; i++) {
			System.out.println("cd:" + virtaulPathArray[1]);
			if (!virtaulPathArray[1].equals("root")) {
				return null;
			}
			if (i == 1 && virtaulPathArray[1].equals("root")) {
				node = (DefaultMutableTreeNode) jTree.getModel().getRoot();
			} else {
				VirtualFile virtualFile = (VirtualFile) node.getUserObject();
				if (!node.isLeaf()) {
					Enumeration<DefaultMutableTreeNode> childEnumeration = node
							.children();
					while (childEnumeration.hasMoreElements()) {
						DefaultMutableTreeNode defaultMutableTreeNode = childEnumeration
								.nextElement();
						VirtualFile childVirtualFile = (VirtualFile) defaultMutableTreeNode
								.getUserObject();
						// 对比
						if (childVirtualFile.getName().equals(
								virtaulPathArray[i])) {
							node = defaultMutableTreeNode;
							judge = true;
							break;
						}
						judge = false;
					}
				}
			}
		}
		TreePath treePath = null;
		if (judge && node != null) {
			treePath = new TreePath(
					((DefaultTreeModel) jTree.getModel()).getPathToRoot(node));
		}
		return treePath;
	}

	//
	public void visitAllNodes(JTree tree) {
		TreeNode root = (TreeNode) tree.getModel().getRoot();
		visitAllNodes(root);
	}

	public void visitAllNodes(TreeNode node) {

		if (node.getChildCount() >= 0) {
			for (Enumeration e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				visitAllNodes(n);
			}
		}
	}

	//
	public void visitAllExpandedNodes(JTree tree) {
		TreeNode root = (TreeNode) tree.getModel().getRoot();
		visitAllExpandedNodes(tree, new TreePath(root));
	}

	public void visitAllExpandedNodes(JTree tree, TreePath parent) {
		// Return if node is not expanded
		if (!tree.isVisible(parent)) {
			return;
		}

		// node is visible and is visited exactly once
		TreeNode node = (TreeNode) parent.getLastPathComponent();

		// Visit all children
		if (node.getChildCount() >= 0) {
			for (Enumeration e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				visitAllExpandedNodes(tree, path);
			}
		}
	}

	// 节点被选中事件
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// target:
		System.out.println("filetreeView.valueChange");
		DefaultMutableTreeNode node = null;
		node = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();
		if (node == null)
			return;
		VirtualFile virtualFile = (VirtualFile) node.getUserObject();
		mainJFrame.addStringToJta2("你选择了：" + virtualFile.getName());
		if (fileSystem == null) {
			System.out.println("filesystem null");
		}
		fileSystem.setCurrentPath(virtualFile.getPath());
		fileSystem.setCurrentVirtualPath(virtualFile.getVirtualPath());
		System.out.println("filetreeView.valueChange你选择了："
				+ virtualFile.getName());
		// System.out.println("由jTree返回的路径：" + jTree.getSelectionPath());
		mainJFrame.addStringToJta2("当前路径为："
				+ fileSystem.getCurrentVirtualPath());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// target:
		int clickTimes = e.getClickCount();
		// 右击事件
		if (e.getButton() == MouseEvent.BUTTON3) {

		} else if (e.getButton() == MouseEvent.BUTTON1) {
			// 双击事件
			if (clickTimes == 2) {
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		// target:

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		// target:

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		// target:

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		// target:

	}

	public FileSystem getFileSystem() {
		return fileSystem;
	}

	public void setFileSystem(FileSystem fileSystem) {
		this.fileSystem = fileSystem;
	}

	public MainJFrame getMainJFrame() {
		return mainJFrame;
	}

	public void setMainJFrame(MainJFrame mainJFrame) {
		this.mainJFrame = mainJFrame;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		// target:
		this.getjTree().updateUI();
	}

	public JTree getjTree() {
		return jTree;
	}

	public void setjTree(JTree jTree) {
		this.jTree = jTree;
	}

}
