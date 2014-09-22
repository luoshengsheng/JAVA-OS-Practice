/**
 *author：罗圣盛
 *target:
 *version:
 *time:2014-9-7下午7:34:11
 *other:
 */
package com.lss.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTree;

import com.lss.control.CommandExe;
import com.lss.model.FileSystem;
import com.lss.model.TheBanker;

public class MainJFrame extends javax.swing.JFrame implements Observer,
		MouseListener, ActionListener {

	// 当前文件系统
	private FileSystem fileSystem = null;
	private FileTreeView fileTreeView = null;
	private FileJTableView fileJTableView = null;

	// deadlock
	private TheBanker theBanker = null;

	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JButton jButton4;
	private javax.swing.JButton jButton5;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenu jMenu3;
	private javax.swing.JMenu jMenu4;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JMenuItem jMenuItem1;
	private javax.swing.JMenuItem jMenuItem2;
	private javax.swing.JMenuItem jMenuItem3;
	private javax.swing.JMenuItem jMenuItem4;
	private javax.swing.JMenuItem jMenuItem5;
	private javax.swing.JMenuItem jMenuItem6;
	private javax.swing.JMenuItem jMenuItem7;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JPanel jPanel7;
	private javax.swing.JPanel jPanel8;
	private javax.swing.JPanel jPanel9;
	private javax.swing.JPanel jPanel10;
	private javax.swing.JPanel jPanel11;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JScrollPane jScrollPane4;
	private javax.swing.JScrollPane jScrollPane5;
	private javax.swing.JScrollPane jScrollPane6;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JSeparator jSeparator2;
	private javax.swing.JTabbedPane jTabbedPane1;
	private javax.swing.JTable jTable1;
	private javax.swing.JTextArea jTextArea1;
	// 命令输出框
	private javax.swing.JTextArea jTextArea2;
	// 文件内容输入框
	private javax.swing.JTextArea jTextArea3;

	private javax.swing.JTextField jTextField1;
	private javax.swing.JTextField jTextField2;
	private javax.swing.JTextField jTextField3;
	private javax.swing.JTree jTree1;

	/** Creates new form MainJFrame */
	public MainJFrame(FileSystem fileSystem) {
		this.fileSystem = fileSystem;
		initComponents();
		this.addStringToJta2("欢迎来到文件系统！");
		this.addStringToJta2("当前路径为：" + fileSystem.getCurrentVirtualPath());
	}

	// BEGIN:initComponents
	private void initComponents() {
		this.setSize(800, 1000);
		fileTreeView = new FileTreeView(this.fileSystem, this);
		// 新增观察者
		fileSystem.addObserver(fileTreeView);
		fileSystem.addObserver(this);

		// jtreeview
		jTree1 = fileTreeView.getjTree();
		fileJTableView = new FileJTableView(jTree1);
		jTree1.addTreeSelectionListener(fileJTableView);

		// jtreeTale
		// jTable1 = new javax.swing.JTable();
		// jTable1 = fileJTableView.getjTable();
		jTable1 = fileJTableView.getjTable();

		jPanel1 = new javax.swing.JPanel();
		jPanel2 = new javax.swing.JPanel();
		jTabbedPane1 = new javax.swing.JTabbedPane();
		jPanel3 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jPanel6 = new javax.swing.JPanel();
		jScrollPane3 = new javax.swing.JScrollPane();

		jScrollPane5 = new javax.swing.JScrollPane();
		jScrollPane6 = new javax.swing.JScrollPane();

		jTextArea1 = new javax.swing.JTextArea();
		jTextArea2 = new javax.swing.JTextArea();
		jTextArea3 = new javax.swing.JTextArea();
		jPanel8 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel("进程号：");
		jLabel3 = new javax.swing.JLabel("资源数：");
		// 运行button
		jPanel9 = new javax.swing.JPanel();

		jPanel10 = new javax.swing.JPanel();
		jPanel11 = new javax.swing.JPanel();

		jTextField1 = new javax.swing.JTextField();
		jTextField2 = new javax.swing.JTextField();
		jTextField3 = new javax.swing.JTextField();
		jTextField1.addMouseListener(this);
		jTextField2.addMouseListener(this);
		jTextField3.addMouseListener(this);

		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton("写入");
		jButton3 = new javax.swing.JButton("运行");
		jButton4 = new javax.swing.JButton("清除");
		jButton5 = new javax.swing.JButton("分配");

		jButton2.addMouseListener(this);
		jButton3.addMouseListener(this);
		jButton4.addMouseListener(this);
		jButton5.addMouseListener(this);

		jPanel5 = new javax.swing.JPanel();
		jPanel7 = new javax.swing.JPanel();
		jScrollPane4 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMenuItem1 = new javax.swing.JMenuItem();
		jSeparator1 = new javax.swing.JSeparator();
		jMenuItem2 = new javax.swing.JMenuItem();
		jSeparator2 = new javax.swing.JSeparator();
		jMenuItem3 = new javax.swing.JMenuItem();
		jMenu2 = new javax.swing.JMenu();
		jMenuItem5 = new javax.swing.JMenuItem();
		jMenuItem7 = new javax.swing.JMenuItem();
		jMenu3 = new javax.swing.JMenu();
		jMenuItem4 = new javax.swing.JMenuItem();
		jMenu4 = new javax.swing.JMenu();
		jMenuItem6 = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jScrollPane1.setViewportView(jTree1);
		jScrollPane3.setViewportView(jTable1);

		jScrollPane5.setViewportBorder(javax.swing.BorderFactory
				.createLineBorder(new java.awt.Color(0, 0, 0)));

		jTextArea1.setColumns(20);
		jTextArea1.setRows(5);
		jScrollPane4.setViewportView(jTextArea1);

		jTextArea2.setColumns(20);
		jTextArea2.setRows(5);
		jScrollPane5.setViewportView(jTextArea2);

		jTextArea3.setColumns(20);
		jTextArea3.setRows(5);
		jScrollPane6.setViewportView(jTextArea3);

		jPanel8.setBorder(javax.swing.BorderFactory
				.createLineBorder(new java.awt.Color(0, 0, 0)));

		jLabel1.setFont(new java.awt.Font("微软雅黑", 0, 18));
		jLabel1.setText("命令：");

		jButton1.setText("提交");
		jButton1.setActionCommand("jButton1");

		javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(
				jPanel8);
		jPanel8.setLayout(jPanel8Layout);
		jPanel8Layout
				.setHorizontalGroup(jPanel8Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel8Layout
										.createSequentialGroup()
										.addGap(62, 62, 62)
										.addComponent(
												jLabel1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												58,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jTextField1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												370,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jButton1)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jButton2,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												100,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(63, Short.MAX_VALUE)));
		jPanel8Layout
				.setVerticalGroup(jPanel8Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel8Layout
										.createSequentialGroup()
										.addGap(22, 22, 22)
										.addGroup(
												jPanel8Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																jButton1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																37,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jTextField1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																34,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jButton2,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																37,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jLabel1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																33,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(23, Short.MAX_VALUE)));

		javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(
				jPanel6);
		jPanel6.setLayout(jPanel6Layout);
		jPanel6Layout
				.setHorizontalGroup(jPanel6Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel6Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel6Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jScrollPane5,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																624,
																Short.MAX_VALUE)
														.addComponent(
																jScrollPane6,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																624,
																Short.MAX_VALUE)
														.addComponent(
																jScrollPane3,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																624,
																Short.MAX_VALUE)
														.addComponent(
																jPanel8,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap()));
		jPanel6Layout
				.setVerticalGroup(jPanel6Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel6Layout
										.createSequentialGroup()
										.addComponent(
												jPanel8,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(
												jScrollPane5,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												173, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane6,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												173, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane3,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												206,
												javax.swing.GroupLayout.PREFERRED_SIZE)));

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(
				jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout
				.setHorizontalGroup(jPanel3Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel3Layout
										.createSequentialGroup()
										.addComponent(
												jScrollPane1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												149,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jPanel6,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		jPanel3Layout.setVerticalGroup(jPanel3Layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jScrollPane1,
						javax.swing.GroupLayout.PREFERRED_SIZE, 0,
						Short.MAX_VALUE)
				.addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		jTabbedPane1.addTab("文件管理", jPanel3);

		// 死锁------------------------------------------------------
		// BorderLayout jPanel7Layout = new BorderLayout();
		// BorderLayout jPanel10Layout = new BorderLayout();
		// BorderLayout jPanel11Layout = new BorderLayout();
		// jPanel7.setLayout(jPanel7Layout);
		// jPanel10.setLayout(jPanel7Layout);
		// jPanel11.setLayout(jPanel7Layout);
		jLabel2.setSize(50, 30);
		jLabel3.setSize(50, 30);
		jTextField2.setPreferredSize(new Dimension(100, 30));
		jTextField3.setPreferredSize(new Dimension(100, 30));
		jPanel10.setSize(800, 30);
		jPanel11.setSize(800, 30);
		jPanel7.setLayout(new FlowLayout());
		jPanel7.setSize(804, 600);
		jPanel10.setLayout(new FlowLayout());
		jPanel11.setLayout(new FlowLayout());
		jPanel7.add(jScrollPane4);

		jPanel7.add(jPanel9);
		jPanel7.add(jPanel10);
		jPanel7.add(jPanel11);

		jPanel10.add(jLabel2);
		jPanel10.add(jTextField2);
		jPanel11.add(jLabel3);
		jPanel11.add(jTextField3);

		jTextArea1.setColumns(20);
		jTextArea1.setRows(20);
		jScrollPane4.setPreferredSize(new Dimension(804, 600));
		jScrollPane4.setViewportView(jTextArea1);

		BorderLayout jPanel9Layout = new BorderLayout();
		jPanel9.setLayout(jPanel9Layout);
		jPanel9.add(jButton3, BorderLayout.CENTER);
		jPanel9.add(jButton4, BorderLayout.WEST);
		jPanel9.add(jButton5, BorderLayout.EAST);

		javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(
				jPanel5);
		jPanel5.setLayout(jPanel5Layout);
		jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		jTabbedPane1.addTab("死锁避免", jPanel5);

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(
				jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jTabbedPane1));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 511,
				Short.MAX_VALUE));

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		jMenu1.setText("\u6587\u4ef6");

		jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_N,
				java.awt.event.InputEvent.CTRL_MASK));
		jMenuItem1.setText("\u65b0\u5efa\u6587\u4ef6");
		jMenu1.add(jMenuItem1);
		jMenu1.add(jSeparator1);

		jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_D,
				java.awt.event.InputEvent.CTRL_MASK));
		jMenuItem2.setText("\u5220\u9664\u6587\u4ef6");
		jMenu1.add(jMenuItem2);
		jMenu1.add(jSeparator2);

		jMenuItem3.setText("\u5173\u673a");
		jMenu1.add(jMenuItem3);

		jMenuBar1.add(jMenu1);

		jMenu2.setText("\u7528\u6237");

		jMenuItem5.setText("\u5207\u6362\u7528\u6237");
		jMenu2.add(jMenuItem5);

		jMenuItem7.setText("\u6ce8\u9500");
		jMenu2.add(jMenuItem7);

		jMenuBar1.add(jMenu2);

		jMenu3.setText("\u5e2e\u52a9");

		jMenuItem4.setText("help");
		jMenu3.add(jMenuItem4);

		jMenuBar1.add(jMenu3);

		jMenu4.setText("\u5173\u4e8e");

		jMenuItem6.setText("\u7248\u672c\u8bf4\u660e");
		jMenu4.add(jMenuItem6);

		jMenuBar1.add(jMenu4);

		setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		pack();
	}

	// END:initComponents

	// 初始化设计
	public void showView(String userName) {
		System.out.println(userName + "的主界面");
		this.setVisible(true);
		jButton1.addMouseListener(this);
		jTextField1.addActionListener(this);
	}

	// 自定义函数

	// 解析命令
	private void parsingCommand(String commandString) {
		// target:
		System.out.println("mainJFrame解析命令:" + commandString);
		boolean isCommand = false;
		// 获得命令的各个段
		String[] command = jTextField1.getText().split("[\\s]+");
		// 命令类型
		int commandType = 0;
		// 检查是否属于内置命令
		if (commandString == null || commandString.length() == 0) {
			this.addStringToJta2("输入不能为空");
			return;
		} else {
			for (int i = 0; i < CommandExe.commandArray.length; i++) {
				if (command[0].equals(CommandExe.commandArray[i])) {
					commandType = i + 1;
					isCommand = true;
					break;
				}
			}
			if (!isCommand) {
				JOptionPane.showMessageDialog(null, "无此命令！请重新输入");
			} else {
				// 执行命令
				CommandExe commandExe = new CommandExe(command,
						this.fileSystem, commandType, this);
				commandExe.executeCommand();
			}
		}
	}

	// get fileSystem
	public FileSystem getFileSystem() {
		return fileSystem;
	}

	// deadlock
	public void addStringToJta1(String string) {
		jTextArea1.append(string + "\n");
		jTextArea1.repaint();
	}

	// deadlock不换行
	public void addStringToJta1No(String string) {
		jTextArea1.append(string);
		jTextArea1.repaint();
	}

	// 向面板添加信息
	public void addStringToJta2(String string) {
		jTextArea2.append(string + "\n");
		jTextArea2.repaint();
	}

	// 写入文件面板
	public void addStringToJta3(String string) {
		jTextArea3.append(string + "\n");
		jTextArea3.repaint();
	}

	// 清空面板信息
	public void clearJta1() {
		jTextArea1.setText("");
		jTextArea1.repaint();
	}

	// 清空面板信息
	public void clearJta2() {
		jTextArea2.setText("");
		jTextArea2.repaint();
	}

	// 清空面板信息
	public void clearJta3() {
		jTextArea3.setText("");
		jTextArea3.repaint();
	}

	// ------------------------------------------------------------------------------------

	// observer的函数
	@Override
	public void update(Observable o, Object arg) {
		// 更新jtree最好的方法是用vector 构建model新的jtree
		// 当vector发生改变时再调用repaint，updateUI就会更新树，
		// 获取jtree的根节点fileTreeView.getjTree().getModel().getRoot();
		// root.removeAllChildren(); 清除根节点的所有子节点
		// jTree1.updateUI();
		// jPanel1.repaint();
	}

	// 事件函数
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// target:
		// 如果是提交按钮
		int clickTimes = e.getClickCount();
		if (e.getSource() == this.jButton1) {
			System.out.println("点击了提交");
			String commandString = jTextField1.getText();
			// 解析命令
			parsingCommand(commandString);
		}
		if (e.getSource() == this.jButton2) {
			System.out.println("点击了写入");
			String text = jTextArea3.getText();
			fileSystem.changeNodeText(fileSystem.getCurrentPath(), text);
			this.addStringToJta2("写入成功");
		}
		if (e.getSource() == this.jButton3) {
			System.out.println("点击了运行");
			theBanker = new TheBanker(this);
			// 死锁避免
			theBanker.deadlockAvoidance();

			this.addStringToJta2("运行成功");
		}
		if (e.getSource() == this.jButton4) {
			System.out.println("点击了清除");
			this.addStringToJta2("清除成功");
			jTextField2.setText("");
			jTextField3.setText("");
		}
		if (e.getSource() == this.jButton5) {
			System.out.println("点击了分配");
			String processNoString = jTextField2.getText();
			String resourceString = jTextField3.getText();
			int processNo = 0;
			try {
				processNo = Integer.parseInt(processNoString);
			} catch (Exception e2) {
				// TODO: handle exception
				this.addStringToJta1("输入有误");
				return;
			}
			theBanker.deadlockDetection(processNo, resourceString);

		}
		if (e.getSource() == this.jTextField1) {
			if (clickTimes == 2) {
				jTextField1.setText("");
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// target:
		// 如果是命令行文本框
		System.out.println("按了enter");
		if (e.getSource() == this.jTextField1) {
			System.out.println("按了enter");
			JTextField jtf = (JTextField) e.getSource();
			String commandString = jtf.getText();
			parsingCommand(commandString);
		}
	}

	public FileTreeView getFileTreeView() {
		return fileTreeView;
	}

	public void setFileTreeView(FileTreeView fileTreeView) {
		this.fileTreeView = fileTreeView;
	}

	public JTree getJTree() {
		return jTree1;
	}

	public FileJTableView getFileJTableView() {
		return fileJTableView;
	}

	public JTextField getJTextField1() {
		return jTextField1;
	}
}