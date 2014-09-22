/**
*author：罗圣盛
*target:
*version:
*time:2014-9-10下午8:59:49
*other:
*/
package com.lss.test;

import java.awt.datatransfer.DataFlavor;  
import java.awt.datatransfer.Transferable;  
import java.awt.datatransfer.UnsupportedFlavorException;  
import java.io.IOException;  
import java.util.ArrayList;  
import java.util.List;  
  
import javax.swing.JComponent;  
import javax.swing.JTree;  
import javax.swing.TransferHandler;  
import javax.swing.tree.DefaultMutableTreeNode;  
import javax.swing.tree.DefaultTreeModel;  
import javax.swing.tree.TreePath;  
  
import com.lss.test.VEachDir;  
  
public class DocsTreeTransferHanlder extends TransferHandler {  
  
    private static final long serialVersionUID = 1910170308494195795L;  
  
    public int getSourceActions(JComponent c) {  
        return MOVE;  
    }  
  
    @Override  
    protected Transferable createTransferable(JComponent c) {  
        JTree tree = (JTree) c;  
        TreePath[] paths = tree.getSelectionPaths();  
        ArrayList nodes = new ArrayList();  
        for (TreePath path : paths) {  
            nodes.add(path.getLastPathComponent());  
        }  
        return new JTreeTransferable(nodes);  
    }  
  
    @Override  
    protected void exportDone(JComponent source, Transferable data, int action) {  
    }  
  
    @Override  
    public boolean canImport(TransferSupport support) {  
        if (support.isDataFlavorSupported(JTreeTransferable.FLAVOR)) {  
            if (support.getDropAction() == MOVE)  
                return true;  
        }  
        return false;  
    }  
  
    @Override  
    public boolean importData(TransferSupport support) {  
        System.out.println("ImportData");  
        JTree tree = (JTree) support.getComponent();  
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();  
        Transferable transfer = support.getTransferable();  
        try {  
            List<DefaultMutableTreeNode> nodes = (List<DefaultMutableTreeNode>) transfer  
                    .getTransferData(JTreeTransferable.FLAVOR);  
            JTree.DropLocation location = (JTree.DropLocation) support  
                    .getDropLocation();  
            DefaultMutableTreeNode newParent = (DefaultMutableTreeNode) location  
                    .getPath().getLastPathComponent();  
  
            VEachDir eDir = (VEachDir) newParent.getUserObject();  
            System.out.println("The drage after NewParent DirName:"  
                    + eDir.getDirName() + ",DirID:" + eDir.getDirId());  
  
            for (DefaultMutableTreeNode node : nodes) {  
                eDir = (VEachDir) node.getUserObject();  
                System.out.println("Before Drag the Node DirName:"  
                        + eDir.getDirName());  
                model.removeNodeFromParent(node);  
                model.insertNodeInto(node, newParent, newParent.getChildCount());  
            }  
        } catch (UnsupportedFlavorException e) {  
            e.printStackTrace();  
            return false;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
        return true;  
    }  
}  
  
class JTreeTransferable implements Transferable {  
    public static DataFlavor FLAVOR = null;  
    private List<DefaultMutableTreeNode> nodes;  
  
    public JTreeTransferable(ArrayList<DefaultMutableTreeNode> nodes) {  
        try {  
            FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType  
                    + ";class=\"" + ArrayList.class.getName() + "\"");  
            this.nodes = nodes;  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
    }  
  
    public Object getTransferData(DataFlavor flavor)  
            throws UnsupportedFlavorException, IOException {  
        return nodes;  
    }  
  
    public DataFlavor[] getTransferDataFlavors() {  
        return new DataFlavor[] { FLAVOR };  
    }  
  
    public boolean isDataFlavorSupported(DataFlavor flv) {  
        return FLAVOR.equals(flv);  
    }  
}  

