/**
*author：罗圣盛
*target:
*version:
*time:2014-9-10下午8:46:19
*other:
*/
package com.lss.test;

import java.awt.GridBagConstraints; 
import java.awt.GridBagLayout; 
import java.awt.Insets; 
import java.awt.Toolkit; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.MouseAdapter; 
import java.awt.event.MouseEvent; 
import java.util.ArrayList; 
import java.util.List; 
   
import javax.swing.AbstractAction; 
import javax.swing.JButton; 
import javax.swing.JLabel; 
import javax.swing.JOptionPane; 
import javax.swing.JPanel; 
import javax.swing.JPopupMenu; 
import javax.swing.JScrollPane; 
import javax.swing.JTree; 
import javax.swing.SwingUtilities; 
import javax.swing.event.TreeModelEvent; 
import javax.swing.event.TreeModelListener; 
import javax.swing.tree.DefaultMutableTreeNode; 
import javax.swing.tree.DefaultTreeModel; 
import javax.swing.tree.MutableTreeNode; 
import javax.swing.tree.TreeNode; 
import javax.swing.tree.TreePath; 
import javax.swing.tree.TreeSelectionModel; 
   
import org.apache.log4j.Logger; 

import com.lss.test.DocsTreeTransferHanlder;;
   
   
public class TreePanel extends JPanel { 
   
    private final Logger logger = Logger.getLogger(TreePanel.class); 
    private static final long serialVersionUID = 1975901083214104961L; 
    private JTree tree; 
    private InnerDocsMgt mainPanel; 
    private List<VEachDir> dirsList = new ArrayList<VEachDir>(); 
    private DefaultTreeModel treeModel; 
    private DefaultMutableTreeNode rootNode; 
    private Toolkit toolkit = Toolkit.getDefaultToolkit(); 
   
    public TreePanel(InnerDocsMgt mainPanel) { 
        super(); 
        this.mainPanel = mainPanel; 
        init(); 
    } 
   
    public void init() { 
   
        GridBagLayout gbl_panel = new GridBagLayout(); 
        gbl_panel.columnWidths = new int[] { 54, 0, 0, 0, 0, 0, 0, 0 }; 
        gbl_panel.rowHeights = new int[] { 31, 0, 0 }; 
        gbl_panel.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 
                0.0, Double.MIN_VALUE }; 
        gbl_panel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE }; 
        this.setLayout(gbl_panel); 
   
        JLabel lblNewLabel = new JLabel( 
                "\u5185\u90E8\u8D44\u6599\u76EE\u5F55\u5217\u8868"); 
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints(); 
        gbc_lblNewLabel.gridwidth = 5; 
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5); 
        gbc_lblNewLabel.gridx = 0; 
        gbc_lblNewLabel.gridy = 0; 
        add(lblNewLabel, gbc_lblNewLabel); 
   
        JButton saveDirsOprBt = new JButton( 
                "\u4FDD\u5B58\u76EE\u5F55\u64CD\u4F5C"); 
        GridBagConstraints gbc_saveDirsOprBt = new GridBagConstraints(); 
        gbc_saveDirsOprBt.insets = new Insets(0, 0, 5, 5); 
        gbc_saveDirsOprBt.gridx = 5; 
        gbc_saveDirsOprBt.gridy = 0; 
        add(saveDirsOprBt, gbc_saveDirsOprBt); 
        saveDirsOprBt.addActionListener(new ActionListener() { 
   
            @Override 
            public void actionPerformed(ActionEvent e) { 
   
                String title = "注意！"; 
                String message = "是否确定保存对目录的修改！"; 
                int messageType = JOptionPane.INFORMATION_MESSAGE; 
                int result = JOptionPane.showConfirmDialog(mainPanel, message, 
                        title, JOptionPane.YES_NO_OPTION, messageType); 
   
                if (result == JOptionPane.YES_OPTION) { 
                    System.out.println("Yes"); 
                    logger.debug("用户确定了对目录的修改！"); 
                    DocsMgtRmi.getINS().saveDirChanged(dirsList); 
                } 
            } 
        }); 
   
        JScrollPane scrollPane = new JScrollPane(); 
        GridBagConstraints gbc_scrollPane = new GridBagConstraints(); 
        gbc_scrollPane.gridwidth = 7; 
        gbc_scrollPane.fill = GridBagConstraints.BOTH; 
        gbc_scrollPane.gridx = 0; 
        gbc_scrollPane.gridy = 1; 
        add(scrollPane, gbc_scrollPane); 
   
        VEachDir rootDir = new VEachDir(0, "/", 0); 
//        VEachDir rootDir = new VEachDir(0, "/", 0, 123); 
        rootNode = new DefaultMutableTreeNode(rootDir); 
        createNodes(rootNode); 
        treeModel = new DocTreeModel(rootNode); 
        treeModel.addTreeModelListener(new DocTreeModelListener()); 
        tree = new JTree(treeModel); 
        tree.getSelectionModel().setSelectionMode( 
                TreeSelectionModel.SINGLE_TREE_SELECTION); 
   
        scrollPane.setViewportView(tree); 
        tree.setEditable(true); 
        tree.setDragEnabled(true); 
        tree.setTransferHandler(new DocsTreeTransferHanlder()); 
//        tree.setTransferHandler(new DocsTreeTransferHanlder(dirsList)); 
   
        setPopupMenu(); 
   
    } 
   
    private void createNodes(DefaultMutableTreeNode top) { 
   
        List<DefaultMutableTreeNode> nodes = DocsMgtRmi.getINS() 
                .getFirstLevelDirs(); 
   
        for (DefaultMutableTreeNode one : nodes) { 
            top.add(one); 
        } 
   
    } 
   
    public DefaultMutableTreeNode getSelectedNode() { 
        return (DefaultMutableTreeNode) tree.getLastSelectedPathComponent(); 
    } 
   
    public void setPopupMenu() { 
        final JPopupMenu pop = new JPopupMenu(); 
        pop.add(new AbstractAction("添加子目录") { 
            private static final long serialVersionUID = 1L; 
   
            public void actionPerformed(ActionEvent e) { 
                System.out.println("Tree Add"); 
   
                VEachDir newDir = new VEachDir(0, "新的目录", 0); 
//                VEachDir newDir = new VEachDir(0, "新的目录", 0, 123); 
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode( 
                        newDir); 
   
                addObject(newDir); 
            } 
        }); 
        pop.add(new AbstractAction("删除目录") { 
            private static final long serialVersionUID = 1L; 
   
            public void actionPerformed(ActionEvent e) { 
                System.out.println("Delete"); 
                removeCurrentNode(); 
            } 
        }); 
   
        pop.add(new AbstractAction("重命名") { 
            private static final long serialVersionUID = 1L; 
   
            public void actionPerformed(ActionEvent e) { 
                TreePath path = tree.getSelectionPath(); 
                tree.startEditingAtPath(path); 
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
                    SwingUtilities.invokeLater(new Runnable() { 
                        public void run() { 
//                            mainPanel.selectOneDir(); 
                        } 
                    }); 
                } else if (evt.getClickCount() == 2) { 
                    TreePath path = tree.getSelectionPath(); 
                    tree.startEditingAtPath(path); 
                } 
            } 
        }); 
    } 
   
    /** Remove the currently selected node. */ 
    public void removeCurrentNode() { 
        TreePath currentSelection = tree.getSelectionPath(); 
        if (currentSelection != null) { 
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection 
                    .getLastPathComponent()); 
            MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent()); 
            if (parent != null) { 
                treeModel.removeNodeFromParent(currentNode); 
                return; 
            } 
        } // Either there was no selection, or the root was selected. 
        toolkit.beep(); 
    } 
   
    /** Add child to the currently selected node. */ 
    public DefaultMutableTreeNode addObject(Object child) { 
        DefaultMutableTreeNode parentNode = null; 
        TreePath parentPath = tree.getSelectionPath(); 
        if (parentPath == null) { 
            parentNode = rootNode; 
        } else { 
            parentNode = (DefaultMutableTreeNode) (parentPath 
                    .getLastPathComponent()); 
        } 
        return addObject(parentNode, child, true); 
    } 
   
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, 
            Object child) { 
        return addObject(parent, child, false); 
    } 
   
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, 
            Object child, boolean shouldBeVisible) { 
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child); 
        if (parent == null) { 
            parent = rootNode; 
        } 
   
        // It is key to invoke this on the TreeModel, and NOT 
        // DefaultMutableTreeNode 
        treeModel.insertNodeInto(childNode, parent, parent.getChildCount()); 
   
        // Make sure the user can see the lovely new node. 
        if (shouldBeVisible) { 
            tree.scrollPathToVisible(new TreePath(childNode.getPath())); 
        } 
        return childNode; 
    } 
   
    class DocTreeModelListener implements TreeModelListener { 
   
        public void treeNodesChanged(TreeModelEvent e) { 
   
            TreePath tp = e.getTreePath(); 
            DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) (tp 
                    .getLastPathComponent()); 
            /*
             * If the event lists children, then the changed node is the child
             * of the node we've already gotten. Otherwise, the changed node and
             * the specified node are the same.
             */ 
   
            int index = e.getChildIndices()[0]; 
            DefaultMutableTreeNode changedNode = (DefaultMutableTreeNode) (parentNode 
                    .getChildAt(index)); 
   
            VEachDir newdir = (VEachDir) changedNode.getUserObject(); 
            dirsList.add(newdir); 
   
        } 
   
        public void treeNodesInserted(TreeModelEvent e) { 
        } 
   
        public void treeNodesRemoved(TreeModelEvent e) { 
        } 
   
        public void treeStructureChanged(TreeModelEvent e) { 
            System.out.println("treeStructureChanged"); 
        } 
    } 
   
    private class DocTreeModel extends DefaultTreeModel { 
   
        private static final long serialVersionUID = 922481109805944053L; 
   
        public DocTreeModel(TreeNode root) { 
            super(root); 
        } 
   
        @Override 
        public void valueForPathChanged(TreePath path, Object newValue) { 
            Object obj = ((DefaultMutableTreeNode) path.getLastPathComponent()) 
                    .getUserObject(); 
            ((VEachDir) obj).setDirName(newValue.toString()); 
            super.valueForPathChanged(path, obj); 
        } 
    } 
}  