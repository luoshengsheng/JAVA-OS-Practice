/**
*author：罗圣盛
*target:
*version:
*time:2014-9-7下午7:34:11
*other:
*/
package com.lss.model;

import java.io.BufferedWriter;
import java.io.FileWriter;

import com.lss.global.FileOperation;



public class User {
	private String name=null;
	
	public void addUser(String userName) {
		try {  
			FileWriter fileWriter = new FileWriter("Root/etc/user.txt",true);
		    if(userName!=null&&userName.length()!=0){  
		        fileWriter.write(userName);
		        fileWriter.write("\n");
		        addUserNode(userName);
		    }  
		    fileWriter.close();  
		    System.out.println("写入user.txt成功");
		} catch (Exception e) {  
		    e.printStackTrace();  
		}   
	}
	//新增一个用户节点，既userhome
	public boolean addUserNode(String userName) {
		FileSystem fileSystem = new FileSystem(userName);
		fileSystem.addFileNode(userName,userName, "directory", "/file/file[@name='home'][1]");
		System.out.println("创建"+userName+"home节点成功");
		return true;
	}
	//删除用户
	public void deleteUser() {
		
	}
	public static void main(String[] args) {
		User user=new User();
	}
}
