/**
*author：罗圣盛
*target:
*version:
*time:2014-9-10下午9:00:37
*other:
*/
package com.lss.test;

import java.net.MalformedURLException;  
import java.util.ArrayList;  
import java.util.Date;  
import java.util.List;  
import javax.swing.tree.DefaultMutableTreeNode;  
import org.apache.log4j.Logger;  
  
public class DocsMgtRmi {  
  
    private final Logger logger = Logger.getLogger(DocsMgtRmi.class);  
    private static DocsMgtRmi rmi = null;  
  
    public static DocsMgtRmi getINS() {  
        if (rmi == null) {  
            synchronized (DocsMgtRmi.class) {  
                if (rmi == null) {  
                    rmi = new DocsMgtRmi();  
                }  
            }  
        }  
        return rmi;  
    }  
  
    private DocsMgtRmi() {  
          
    }  
  
    public List<DefaultMutableTreeNode> getFirstLevelDirs() {  
  
        ArrayList<DefaultMutableTreeNode> nodes = new ArrayList<DefaultMutableTreeNode>();  
  
        DefaultMutableTreeNode fstnode = null;  
        for (int i = 0; i < 10; i++) {  
            VEachDir fstLevelDir = new VEachDir(i, "200" + i + "年以前规章", 0);  
            fstnode = new DefaultMutableTreeNode(fstLevelDir);  
            nodes.add(fstnode);  
        }  
  
        for (int i = 0; i < 10; i++) {  
            VEachDir secLevelDir = new VEachDir(i, "2009年投诉建议-卷" + i, 9);  
            DefaultMutableTreeNode secNode = new DefaultMutableTreeNode(  
                    secLevelDir);  
            fstnode.add(secNode);  
        }  
  
        return nodes;  
    }  
  
    public List<VEachFile> getFilesViaDirid(int dirId) {  
  
        ArrayList<VEachFile> files = new ArrayList<VEachFile>();  
        for (int i = 0; i < 97; i++) {  
            VEachFile one = new VEachFile(dirId, "天上星，亮晶晶，那是" + i + "双双眼睛.txt",  
                    1000, new Date(), "张为山", "one->two-" + i + "-<>");  
            files.add(one);  
        }  
        return files;  
  
    }  
  
    public boolean saveDirChanged(List<VEachDir> dirs) {  
  
        return true;  
    }  
  
}  

