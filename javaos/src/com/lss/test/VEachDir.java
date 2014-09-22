/**
*author：罗圣盛
*target:
*version:
*time:2014-9-10下午9:02:00
*other:
*/
package com.lss.test;

public class VEachDir {  
	  
    private int dirId;  
    private String dirName;  
    private int parentDirId;  
  
    public VEachDir(int dirId, String dirName, int parentDirId) {  
        super();  
        this.dirId = dirId;  
        this.dirName = dirName;  
        this.parentDirId = parentDirId;  
    }  
  
    public int getDirId() {  
        return dirId;  
    }  
  
    public void setDirId(int dirId) {  
        this.dirId = dirId;  
    }  
  
    public String getDirName() {  
        return dirName;  
    }  
  
    public void setDirName(String dirName) {  
        this.dirName = dirName;  
    }  
  
    public int getParentDirId() {  
        return parentDirId;  
    }  
  
    public void setParentDirId(int parentDirId) {  
        this.parentDirId = parentDirId;  
    }  
  
    public String toString() {  
  
        return this.dirName;  
    }  
  
} 

