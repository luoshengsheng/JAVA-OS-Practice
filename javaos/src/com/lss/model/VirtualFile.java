package com.lss.model;

import org.dom4j.Element;

public class VirtualFile {
	private String name;
	private String owner;
	private String primission;
	private String path;
	private String time;
	private String virtualPath;
	private String parentPath;
	private String parentVirtualPath;
	private String type;
	public VirtualFile(String name,String ownerString,String primission,String parentPath,String type,String parentVirtualPath,String time){
		this.name = name;
		this.owner = ownerString;
		this.primission = primission;
		this.time = time;
		this.type = type;
		if (name.equals("root")) {
			this.parentPath = null;
			this.parentVirtualPath = null;
			this.path = "/file[@name='"+name+"']";
			this.virtualPath = "/"+name;
		}else{
			this.parentPath = parentPath;
			this.parentVirtualPath = parentVirtualPath;
			this.path = parentPath+"/file[@name='"+name+"']";
			this.virtualPath = parentVirtualPath+"/"+name;
		}
		System.out.println("new virtualFile nextRealPath:"+path);
		System.out.println("new virtualFile nextVirtualPath:"+virtualPath);
	}
	public VirtualFile(String name){
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getPrimission() {
		return primission;
	}
	public void setPrimission(String primission) {
		this.primission = primission;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getVirtualPath() {
		return virtualPath;
	}
	public void setVirtualPath(String virtualPath) {
		this.virtualPath = virtualPath;
	}
	//重点在这个函数，在界面显示 的文件名由这个函数返回，否则显示包名加地址
	public String toString() {
		return name;
		
	}
	public String getParentPath() {
		return parentPath;
	}
	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}
	public String getParentVirtualPath() {
		return parentVirtualPath;
	}
	public void setParentVirtualPath(String parentVirtualPath) {
		this.parentVirtualPath = parentVirtualPath;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
