/**
*author：罗圣盛
*target:
*version:
*time:2014-9-10下午9:01:20
*other:
*/
package com.lss.test;

import java.util.Date;  

public class VEachFile {  
    private int dirId;  
    private String fileName;  
    private int fileSize;  
    private Date fileDate;  
    private String fileAuthor;  
    private String filePath;  
  
    public VEachFile(int dirId, String fileName, int fileSize, Date fileDate,  
            String fileAuthor, String filePath) {  
        super();  
        this.dirId = dirId;  
        this.fileName = fileName;  
        this.fileSize = fileSize;  
        this.fileDate = fileDate;  
        this.fileAuthor = fileAuthor;  
        this.filePath = filePath;  
    }  
  
    public int getDirId() {  
        return dirId;  
    }  
    public void setDirId(int dirId) {  
        this.dirId = dirId;  
    }  
    public String getFileName() {  
        return fileName;  
    }  
    public void setFileName(String fileName) {  
        this.fileName = fileName;  
    }  
  
    public int getFileSize() {  
        return fileSize;  
    }  
  
    public void setFileSize(int fileSize) {  
        this.fileSize = fileSize;  
    }  
  
    public Date getFileDate() {  
        return fileDate;  
    }  
  
    public void setFileDate(Date fileDate) {  
        this.fileDate = fileDate;  
    }  
  
    public String getFileAuthor() {  
        return fileAuthor;  
    }  
  
    public void setFileAuthor(String fileAuthor) {  
        this.fileAuthor = fileAuthor;  
    }  
  
    public String getFilePath() {  
        return filePath;  
    }  
  
    public void setFilePath(String filePath) {  
        this.filePath = filePath;  
    }  
  
}  
