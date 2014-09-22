/**
 *author：罗圣盛
 *target:
 *version:
 *time:2014-9-11下午2:52:09
 *other:
 */
package com.lss.window;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import com.lss.model.TableModelByArray;
import com.lss.model.VirtualFile;

public class FileJTableView implements TableModelListener,
		TreeSelectionListener {

	private JTable jTable = null;
	private JTree jTree = null;
	private String[] columnNames = { "名称", "类型", "用户", "时间", "权限", "路径" };
	private Vector<String> columnNamesVector = new Vector<String>();
	private Object[][] data = { { "Kathy", "Smith", "Snowboarding",
			new Integer(5), new Boolean(false) } };
	private Vector<Vector<String>> dataVector = new Vector<Vector<String>>();

	public FileJTableView(JTree jTree) {
		// Object [][] data = createTableData();
		// FileTableModel fileTableModel = new FileTableModel(data,columnNames)
		// this.jTable = new JTable(fileTableModel) ;
		columnNamesVector.add("名称");
		columnNamesVector.add("类型");
		columnNamesVector.add("用户");
		columnNamesVector.add("时间");
		columnNamesVector.add("权限");
		columnNamesVector.add("路径");
		TableModelByArray fileTableModel = new TableModelByArray(data,
				columnNames);
		DefaultTableModel tableModelByVector = new DefaultTableModel(
				dataVector, columnNamesVector);
		// DefaultTableModel tableModel = new DefaultTableModel(dataVector,
		// columnNamesVector);
		// this.jTable = new JTable(tableModel);
		// this.jTable = new JTable(fileTableModel);
		this.jTable = new JTable(tableModelByVector);
		this.jTree = jTree;
		initColumnSizes();
		jTable.getModel().addTableModelListener(this);
	}

	// no use
	private Object[][] createTableData() {
		// target:
		return null;
	}

	// 设置表格的列宽
	public void initColumnSizes() {
		TableColumn column = null;
		for (int i = 0; i < 6; i++) {
			column = jTable.getColumnModel().getColumn(i);
			if (i == 3 || i == 5) {
				column.setPreferredWidth(90);
			} else {
				column.setPreferredWidth(30);
			}
		}
	}

	// no use表格数据改变的时候触发
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int column = e.getColumn();
		TableModel model = (TableModel) e.getSource();
		String columnName = model.getColumnName(column);
		Object data = model.getValueAt(row, column);

		// 处理数据
	}

	public JTable getjTable() {
		return jTable;
	}

	public void setjTable(JTable jTable) {
		this.jTable = jTable;
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// target:
		System.out.println("fileJtableView.valueChanged");
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) (DefaultMutableTreeNode) jTree
				.getLastSelectedPathComponent();
		if (node == null)
			return;
		dataVector.clear();
		VirtualFile virtualFile = (VirtualFile) node.getUserObject();
		Vector<String> vectorData = new Vector<String>();
		// 加入数据
		vectorData.add(virtualFile.getName());
		vectorData.add(virtualFile.getType());
		vectorData.add(virtualFile.getOwner());
		vectorData.add(virtualFile.getTime());
		vectorData.add(virtualFile.getPrimission());
		vectorData.add(virtualFile.getVirtualPath());
		dataVector.add(vectorData);
		jTable.repaint();
		jTable.updateUI();
	}

	public void lsCommandExe() {
		// target:
		System.out.println("fileJtableView.IsCommandExe");
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree
				.getLastSelectedPathComponent();
		if (node == null)
			return;
		data = getChildData(node);
		dataVector.clear();
		VirtualFile virtualFile = (VirtualFile) node.getUserObject();
		if (!node.isLeaf()) {
			Enumeration<DefaultMutableTreeNode> childEnumeration = node
					.children();
			while (childEnumeration.hasMoreElements()) {
				Vector<String> childVectorData = new Vector<String>();
				DefaultMutableTreeNode defaultMutableTreeNode = childEnumeration
						.nextElement();
				VirtualFile childVirtualFile = (VirtualFile) defaultMutableTreeNode
						.getUserObject();
				// 加入数据
				childVectorData.add(childVirtualFile.getName());
				childVectorData.add(childVirtualFile.getType());
				childVectorData.add(childVirtualFile.getOwner());
				childVectorData.add(childVirtualFile.getTime());
				childVectorData.add(childVirtualFile.getPrimission());
				childVectorData.add(childVirtualFile.getVirtualPath());
				dataVector.add(childVectorData);
			}
		}
		jTable.repaint();
		jTable.updateUI();
	}

	// no use
	private Object[][] getChildData(DefaultMutableTreeNode node) {
		// TODO Auto-generated method stub
		// target:根据父节点返回字节点的所有数据
		VirtualFile virtualFile = (VirtualFile) node.getUserObject();
		if (!node.isLeaf()) {
			int countChild = node.getChildCount();
			this.data = new String[countChild][5];
			int count = 0;
			Enumeration<DefaultMutableTreeNode> childEnumeration = node
					.children();
			while (childEnumeration.hasMoreElements()) {
				DefaultMutableTreeNode defaultMutableTreeNode = childEnumeration
						.nextElement();
				VirtualFile childVirtualFile = (VirtualFile) defaultMutableTreeNode
						.getUserObject();
				// 加入数据
				data[count][0] = childVirtualFile.getName();
				data[count][1] = childVirtualFile.getOwner();
				data[count][2] = childVirtualFile.getTime();
				data[count][3] = childVirtualFile.getPrimission();
				data[count][4] = childVirtualFile.getVirtualPath();
				count++;
				System.out.println(count);
			}
		}
		return data;
	}

	// no use
	private Vector<Vector<String>> getChildVectorData(
			DefaultMutableTreeNode node) {
		// TODO Auto-generated method stub
		// target:根据父节点返回字节点的所有数据
		Vector<Vector<String>> dataVector = new Vector<Vector<String>>();
		VirtualFile virtualFile = (VirtualFile) node.getUserObject();
		if (!node.isLeaf()) {
			Enumeration<DefaultMutableTreeNode> childEnumeration = node
					.children();
			while (childEnumeration.hasMoreElements()) {
				Vector<String> childVectorData = new Vector<String>();
				DefaultMutableTreeNode defaultMutableTreeNode = childEnumeration
						.nextElement();
				VirtualFile childVirtualFile = (VirtualFile) defaultMutableTreeNode
						.getUserObject();
				// 加入数据
				childVectorData.add(childVirtualFile.getName());
				childVectorData.add(childVirtualFile.getOwner());
				childVectorData.add(childVirtualFile.getTime());
				childVectorData.add(childVirtualFile.getPrimission());
				childVectorData.add(childVirtualFile.getVirtualPath());
				dataVector.add(childVectorData);
			}
		}
		return dataVector;
	}
}
