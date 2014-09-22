/**
 *author：罗圣盛
 *target:
 *version:
 *time:2014-9-16下午6:48:10
 *other:
 */
package com.lss.test;

import com.lss.model.FileSystem;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// target:
		FileSystem fileSystem = new FileSystem("lss");
		String allResourceAllocation = fileSystem
				.readFile("/file/file/file[@name='resourceAllocation']");
		int a[][] = new int[2][3];
		String[] s = allResourceAllocation.trim().split("[\\n]+");
		for (int i = 0; i < s.length; i++) {
			if (s[i].length() == 0) {
				continue;
			}
			String[] v = s[i].trim().split("[,]+");
			for (int j = 0; j < v.length; j++) {
				a[i][j] = Integer.parseInt(v[j]);
			}
		}
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(a[i][j] + "  ");
			}
		}

	}

}
