/**
*author：罗圣盛
*target:
*version:
*time:2014-9-10下午8:57:20
*other:
*/
package com.lss.test;

import java.awt.BorderLayout;  
import java.awt.Component;  
import java.awt.EventQueue;  
import java.awt.GridBagConstraints;  
import java.awt.GridBagLayout;  
import java.awt.Insets;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.awt.event.MouseAdapter;  
import java.awt.event.MouseEvent;  
import java.util.List;  
  
import javax.swing.AbstractAction;  
import javax.swing.DefaultCellEditor;  
import javax.swing.JButton;  
import javax.swing.JFrame;  
import javax.swing.JLabel;  
import javax.swing.JPanel;  
import javax.swing.JPopupMenu;  
import javax.swing.JScrollPane;  
import javax.swing.JTabbedPane;  
import javax.swing.JTable;  
import javax.swing.JTextField;  
import javax.swing.JTree;  
import javax.swing.border.EmptyBorder;  
import javax.swing.table.AbstractTableModel;  
import javax.swing.table.TableCellRenderer;  
import javax.swing.tree.DefaultMutableTreeNode;  
  
  
public class InnerDocsMgt extends JFrame {  
  
    private static final long serialVersionUID = 1L;  
    private JPanel contentPane;  
    private JTable table;  
    private JTree tree;  
    private JLabel currentDirLabel;  
    private JLabel totalFilesNumLabel;  
    private List<VEachFile> docFils;  
    private final int maxFilesAPage = 50;  
    private JLabel curPageLabel;  
    private int curPageNum = 0;  
  
    /** 
     * Launch the application. 
     */  
    public static void main(String[] args) {  
        EventQueue.invokeLater(new Runnable() {  
            public void run() {  
                try {  
                    InnerDocsMgt frame = new InnerDocsMgt();  
                    frame.setVisible(true);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        });  
    }  
  
    /** 
     * Create the frame. 
     */  
    public InnerDocsMgt() {  
        setTitle("\u5185\u90E8\u8D44\u6599\u7BA1\u7406");  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setBounds(100, 100, 450, 300);  
        this.setSize(800, 600);  
        contentPane = new JPanel();  
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));  
        contentPane.setLayout(new BorderLayout(0, 0));  
        setContentPane(contentPane);  
  
        JPanel panel = new JPanel();  
        contentPane.add(panel, BorderLayout.WEST);  
        GridBagLayout gbl_panel = new GridBagLayout();  
        gbl_panel.columnWidths = new int[] { 54, 0, 0, 0, 0, 0, 0, 0 };  
        gbl_panel.rowHeights = new int[] { 31, 0, 0 };  
        gbl_panel.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 0.0,  
                0.0, Double.MIN_VALUE };  
        gbl_panel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };  
        panel.setLayout(gbl_panel);  
  
        JLabel lblNewLabel = new JLabel(  
                "\u5185\u90E8\u8D44\u6599\u76EE\u5F55\u5217\u8868");  
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();  
        gbc_lblNewLabel.gridwidth = 5;  
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);  
        gbc_lblNewLabel.gridx = 0;  
        gbc_lblNewLabel.gridy = 0;  
        panel.add(lblNewLabel, gbc_lblNewLabel);  
  
        JButton button_4 = new JButton("\u4FDD\u5B58\u76EE\u5F55\u64CD\u4F5C");  
        GridBagConstraints gbc_button_4 = new GridBagConstraints();  
        gbc_button_4.insets = new Insets(0, 0, 5, 5);  
        gbc_button_4.gridx = 5;  
        gbc_button_4.gridy = 0;  
        panel.add(button_4, gbc_button_4);  
  
        JScrollPane scrollPane = new JScrollPane();  
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();  
        gbc_scrollPane.gridwidth = 7;  
        gbc_scrollPane.fill = GridBagConstraints.BOTH;  
        gbc_scrollPane.gridx = 0;  
        gbc_scrollPane.gridy = 1;  
        panel.add(scrollPane, gbc_scrollPane);  
  
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("/");  
        createNodes(top);  
        tree = new JTree(top);  
        scrollPane.setViewportView(tree);  
        tree.setEditable(true);  
        tree.setDragEnabled(true);  
        this.setPopupMenu();  
        try {  
            tree.setTransferHandler(new DocsTreeTransferHanlder());  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        JPanel panel_1 = new JPanel();  
        contentPane.add(panel_1, BorderLayout.CENTER);  
        GridBagLayout gbl_panel_1 = new GridBagLayout();  
        gbl_panel_1.columnWidths = new int[] { 708, 0 };  
        gbl_panel_1.rowHeights = new int[] { 556, 0 };  
        gbl_panel_1.columnWeights = new double[] { 1.0, Double.MIN_VALUE };  
        gbl_panel_1.rowWeights = new double[] { 1.0, Double.MIN_VALUE };  
        panel_1.setLayout(gbl_panel_1);  
  
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);  
        GridBagConstraints gbc_tabbedPane = new GridBagConstraints();  
        gbc_tabbedPane.fill = GridBagConstraints.BOTH;  
        gbc_tabbedPane.gridx = 0;  
        gbc_tabbedPane.gridy = 0;  
        panel_1.add(tabbedPane, gbc_tabbedPane);  
  
        JPanel panel_2 = new JPanel();  
        tabbedPane.addTab("\u6587\u4EF6\u4E0E\u76EE\u5F55\u7BA1\u7406", null,  
                panel_2, null);  
        GridBagLayout gbl_panel_2 = new GridBagLayout();  
        gbl_panel_2.columnWidths = new int[] { 166, 59, 0, 0, 0, 0, 0 };  
        gbl_panel_2.rowHeights = new int[] { 30, 0, 49, 0, 0, 0 };  
        gbl_panel_2.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0,  
                0.0, Double.MIN_VALUE };  
        gbl_panel_2.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0,  
                Double.MIN_VALUE };  
        panel_2.setLayout(gbl_panel_2);  
  
        currentDirLabel = new JLabel(  
                "\u5F53\u524D\u76EE\u5F55\uFF1Aone->two->three");  
        GridBagConstraints gbc_lblonetwothree = new GridBagConstraints();  
        gbc_lblonetwothree.gridwidth = 6;  
        gbc_lblonetwothree.anchor = GridBagConstraints.WEST;  
        gbc_lblonetwothree.insets = new Insets(0, 0, 5, 0);  
        gbc_lblonetwothree.gridx = 0;  
        gbc_lblonetwothree.gridy = 0;  
        panel_2.add(currentDirLabel, gbc_lblonetwothree);  
  
        JButton button_3 = new JButton("\u5168\u9009/\u5168\u4E0D\u9009");  
        GridBagConstraints gbc_button_3 = new GridBagConstraints();  
        gbc_button_3.insets = new Insets(0, 0, 5, 5);  
        gbc_button_3.gridx = 3;  
        gbc_button_3.gridy = 1;  
        panel_2.add(button_3, gbc_button_3);  
  
        JButton button = new JButton("\u5220\u9664");  
        GridBagConstraints gbc_button = new GridBagConstraints();  
        gbc_button.insets = new Insets(0, 0, 5, 5);  
        gbc_button.gridx = 4;  
        gbc_button.gridy = 1;  
        panel_2.add(button, gbc_button);  
  
        JButton button_1 = new JButton("\u79FB\u52A8\u5230");  
        button_1.setToolTipText("\u8BF7\u5148\u9009\u62E9\u8981\u79FB\u52A8\u7684\u6587\u4EF6\uFF0C\u7136\u540E\u518D\u9009\u62E9\u4E00\u4E2A\u5DE6\u4FA7\u76EE\u5F55");  
        GridBagConstraints gbc_button_1 = new GridBagConstraints();  
        gbc_button_1.insets = new Insets(0, 0, 5, 0);  
        gbc_button_1.gridx = 5;  
        gbc_button_1.gridy = 1;  
        panel_2.add(button_1, gbc_button_1);  
  
        JScrollPane scrollPane_1 = new JScrollPane();  
        GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();  
        gbc_scrollPane_1.gridwidth = 6;  
        gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);  
        gbc_scrollPane_1.fill = GridBagConstraints.BOTH;  
        gbc_scrollPane_1.gridx = 0;  
        gbc_scrollPane_1.gridy = 2;  
        panel_2.add(scrollPane_1, gbc_scrollPane_1);  
  
        String[] headNames = new String[] { "\u9009\u62E9",  
                "\u6587\u4EF6\u540D", "\u5927\u5C0F\uFF08KB\uFF09",  
                "\u6587\u4EF6\u65E5\u671F", "\u4F5C\u8005", "", "\u4E0B\u8F7D" };  
  
        Object[][] tableDatas = new Object[50][7];  
        for (int i = 0; i < 50; i++) {  
            for (int j = 0; j < 7; j++) {  
                tableDatas[i][j] = null;  
            }  
        }  
        table = new JTable(new DocsTableModel(headNames, tableDatas));  
        table.getColumnModel().getColumn(0)  
                .setCellEditor(table.getDefaultEditor(Boolean.class));  
        table.getColumnModel().getColumn(0)  
                .setCellRenderer(table.getDefaultRenderer(Boolean.class));  
        table.getColumnModel().getColumn(0).setPreferredWidth(38);  
        table.getColumnModel().getColumn(1).setPreferredWidth(206);  
        table.getColumnModel().getColumn(2).setPreferredWidth(53);  
        table.getColumnModel().getColumn(3).setPreferredWidth(134);  
        table.getColumnModel().getColumn(4).setPreferredWidth(58);  
          
        // hide the 5th column,it contans the filepath  
        table.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(0);// hide  
        table.getTableHeader().getColumnModel().getColumn(5).setMinWidth(0);// hide  
        table.getTableHeader().getColumnModel().getColumn(5)  
                .setPreferredWidth(0);  
        table.getTableHeader().getColumnModel().getColumn(5).setWidth(0);  
        table.getColumnModel().getColumn(6)  
                .setCellRenderer(new ButtonCellRenderer());  
        table.getColumnModel().getColumn(6)  
                .setCellEditor(new ButtonCellEditor(table));  
        scrollPane_1.setViewportView(table);  
        totalFilesNumLabel = new JLabel("\u6587\u4EF6\u603B\u6570\uFF1A0");  
        GridBagConstraints gbc_totalFilesNumLabel = new GridBagConstraints();  
        gbc_totalFilesNumLabel.anchor = GridBagConstraints.WEST;  
        gbc_totalFilesNumLabel.insets = new Insets(0, 0, 0, 5);  
        gbc_totalFilesNumLabel.gridx = 0;  
        gbc_totalFilesNumLabel.gridy = 4;  
        panel_2.add(totalFilesNumLabel, gbc_totalFilesNumLabel);  
        curPageLabel = new JLabel(  
                "\u5F53\u524D\u9875\u6570\uFF1A0| \u603B\u9875\u6570\uFF1A0");  
        GridBagConstraints gbc_curPageLabel = new GridBagConstraints();  
        gbc_curPageLabel.gridwidth = 2;  
        gbc_curPageLabel.insets = new Insets(0, 0, 0, 5);  
        gbc_curPageLabel.gridx = 2;  
        gbc_curPageLabel.gridy = 4;  
        panel_2.add(curPageLabel, gbc_curPageLabel);  
        JButton btnNewButton = new JButton("\u4E0A\u4E00\u9875");  
        btnNewButton.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                prePage();  
            }  
        });  
        GridBagConstraints gbc_btnNewButton = new GridBagConstraints();  
        gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);  
        gbc_btnNewButton.gridx = 4;  
        gbc_btnNewButton.gridy = 4;  
        panel_2.add(btnNewButton, gbc_btnNewButton);  
        JButton button_2 = new JButton("\u4E0B\u4E00\u9875");  
        button_2.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                nextPage();  
            }  
        });  
        GridBagConstraints gbc_button_2 = new GridBagConstraints();  
        gbc_button_2.gridx = 5;  
        gbc_button_2.gridy = 4;  
        panel_2.add(button_2, gbc_button_2);  
        JPanel panel_3 = new JPanel();  
        tabbedPane.addTab("\u7F16\u8F91\u6587\u6863", null, panel_3, null);  
        GridBagLayout gbl_panel_3 = new GridBagLayout();  
        gbl_panel_3.columnWidths = new int[] { 0 };  
        gbl_panel_3.rowHeights = new int[] { 0 };  
        gbl_panel_3.columnWeights = new double[] { Double.MIN_VALUE };  
        gbl_panel_3.rowWeights = new double[] { Double.MIN_VALUE };  
        panel_3.setLayout(gbl_panel_3);  
    }  
  
    private void createNodes(DefaultMutableTreeNode top) {  
        List<DefaultMutableTreeNode> nodes = DocsMgtRmi.getINS()  
                .getFirstLevelDirs();  
        for (DefaultMutableTreeNode one : nodes) {  
            top.add(one);  
        }  
    }  
  
    public void setPopupMenu() {  
        final JPopupMenu pop = new JPopupMenu();  
        pop.add(new AbstractAction("添加子目录") {  
            private static final long serialVersionUID = 1L;  
  
            public void actionPerformed(ActionEvent e) {  
                System.out.println("Tree Add");  
            }  
        });  
        pop.add(new AbstractAction("删除目录") {  
            private static final long serialVersionUID = 1L;  
  
            public void actionPerformed(ActionEvent e) {  
                System.out.println("Delete");  
            }  
        });  
        tree.addMouseListener(new MouseAdapter() {  
            @Override  
            public void mouseReleased(MouseEvent e) {  
                if (e.isMetaDown()) {  
                    pop.show(tree, e.getX(), e.getY());  
                }  
            }  
  
            public void mouseClicked(MouseEvent evt) {  
                if (evt.getClickCount() == 1) {  
                    VEachDir eDir = (VEachDir) getSelectedNode()  
                            .getUserObject();  
                    docFils = DocsMgtRmi.getINS().getFilesViaDirid(  
                            eDir.getDirId());  
                    int filesNum = docFils.size();  
                    currentDirLabel  
                            .setText("\u5F53\u524D\u76EE\u5F55\uFF1Aone->two->three,=>DirID:"  
                                    + eDir.getDirId());  
                    totalFilesNumLabel.setText("\u6587\u4EF6\u603B\u6570\uFF1A"  
                            + filesNum);  
                    int maxNum = Math.min(maxFilesAPage, filesNum);  
                    if (filesNum <= maxFilesAPage) {  
                        curPageLabel  
                                .setText("\u5F53\u524D\u9875\u6570\uFF1A 1 | \u603B\u9875\u6570\uFF1A 1");  
                    } else {  
                        int totalNum = filesNum / maxFilesAPage;  
                        if (totalNum * maxFilesAPage < filesNum) {  
                            totalNum++;  
                        }  
                        curPageLabel  
                                .setText("\u5F53\u524D\u9875\u6570\uFF1A 1 | \u603B\u9875\u6570\uFF1A "  
                                        + totalNum);  
                    }  
                    curPageNum = 0;  
                    setDocTableValues(docFils.subList(0, maxNum));  
                }  
            }  
        });  
    }  
  
    public void prePage() {  
        if (this.curPageNum <= 0) {  
            return;  
        }  
        int filesNum = docFils.size();  
        curPageNum--;  
        int totalPage = filesNum / maxFilesAPage;  
        if (totalPage * maxFilesAPage < filesNum) {  
            totalPage++;  
        }  
        curPageLabel  
                .setText("\u5F53\u524D\u9875\u6570\uFF1A " + (curPageNum + 1)  
                        + " | \u603B\u9875\u6570\uFF1A " + totalPage);  
        setDocTableValues(docFils.subList(curPageNum * maxFilesAPage,  
                Math.min((curPageNum + 1) * maxFilesAPage, filesNum)));  
    }  
  
    public void nextPage() {  
        int filesNum = docFils.size();  
        int totalPage = filesNum / maxFilesAPage;  
        if (totalPage * maxFilesAPage < filesNum) {  
            totalPage++;  
        }  
        if (curPageNum >= totalPage - 1) {  
            return;  
        }  
        curPageNum++;  
        setDocTableValues(docFils.subList(curPageNum * maxFilesAPage,  
                Math.min((curPageNum + 1) * maxFilesAPage, filesNum)));  
        curPageLabel  
                .setText("\u5F53\u524D\u9875\u6570\uFF1A " + (curPageNum + 1)  
                        + " | \u603B\u9875\u6570\uFF1A " + totalPage);  
    }  
  
    public void setDocTableValues(List<VEachFile> subDocs) {  
        int i = 0;  
        for (; i < subDocs.size(); i++) {  
            table.getModel().setValueAt(false, i, 0);  
            table.getModel().setValueAt(subDocs.get(i).getFileName(), i, 1);  
            table.getModel().setValueAt(subDocs.get(i).getFileSize(), i, 2);  
            table.getModel().setValueAt(  
                    subDocs.get(i).getFileDate().toLocaleString(), i, 3);  
            table.getModel().setValueAt(subDocs.get(i).getFileAuthor(), i, 4);  
            table.getModel().setValueAt(subDocs.get(i).getFilePath(), i, 5);  
        }  
        if (subDocs.size() < maxFilesAPage) {  
            for (; i < maxFilesAPage; i++) {  
                table.getModel().setValueAt(false, i, 0);  
                table.getModel().setValueAt(null, i, 1);  
                table.getModel().setValueAt(null, i, 2);  
                table.getModel().setValueAt(null, i, 3);  
                table.getModel().setValueAt(null, i, 4);  
                table.getModel().setValueAt(null, i, 5);  
            }  
        }  
        table.updateUI();  
        table.repaint();  
    }  
  
    public DefaultMutableTreeNode getSelectedNode() {  
        return (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();  
    }  
  
    class DocsTableModel extends AbstractTableModel {  
        private String headName[];  
        private Object obj[][];  
        private Class[] columnTypes = new Class[] { Boolean.class,  
                Object.class, Object.class, Object.class, Object.class,  
                Object.class, Object.class };  
  
        public DocsTableModel() {  
            super();  
        }  
  
        public DocsTableModel(String[] headName, Object[][] obj) {  
            this();  
            this.headName = headName;  
            this.obj = obj;  
        }  
  
        public int getColumnCount() {  
            return headName.length;  
        }  
  
        public int getRowCount() {  
            return obj.length;  
        }  
  
        public Object getValueAt(int r, int c) {  
            return obj[r][c];  
        }  
  
        public String getColumnName(int c) {  
            return headName[c];  
        }  
  
        public Class<?> getColumnClass(int columnIndex) {  
            return columnTypes[columnIndex].getClass();  
        }  
  
        @Override  
        public boolean isCellEditable(int rowIndex, int columnIndex) {  
            return true;  
        }  
  
        public void setValueAt(Object value, int row, int col) {  
            obj[row][col] = value;  
            fireTableCellUpdated(row, col);  
        }  
    }  
}  
  
class ButtonCellEditor extends DefaultCellEditor {  
    private static final long serialVersionUID = -6546334664166791132L;  
    private JButton button;  
    private JTable table;  
  
    public ButtonCellEditor(JTable table) {  
        super(new JTextField());  
        this.setClickCountToStart(1);  
        this.initButton();  
        this.table = table;  
    }  
  
    private void initButton() {  
        this.button = new JButton();  
        this.button.setSize(50, 15);  
        this.button.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                ButtonCellEditor.this.fireEditingCanceled();  
                System.out.println("Selected Column:"  
                        + table.getSelectedColumn()  
                        + ",row:"  
                        + table.getSelectedRow()  
                        + ",and filePath:"  
                        + table.getModel()  
                                .getValueAt(table.getSelectedRow(), 5));  
            }  
        });  
    }  
  
    @Override  
    public Component getTableCellEditorComponent(JTable table, Object value,  
            boolean isSelected, int row, int column) {  
        this.button.setText("下载");  
        return this.button;  
    }  
  
    @Override  
    public Object getCellEditorValue() {  
        return this.button.getText();  
    }  
}  
  
class ButtonCellRenderer implements TableCellRenderer {  
    private JButton button;  
  
    public ButtonCellRenderer() {  
        this.button = new JButton();  
    }  
  
    public Component getTableCellRendererComponent(JTable table, Object value,  
            boolean isSelected, boolean hasFocus, int row, int column) {  
        this.button.setText("下载");  
        return this.button;  
    }  
}  
