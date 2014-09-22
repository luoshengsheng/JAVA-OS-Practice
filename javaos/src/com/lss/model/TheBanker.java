/**
 *author：罗圣盛
 *target:
 *version:
 *time:2014-9-15下午8:47:45
 *other:
 */
package com.lss.model;

import com.lss.window.MainJFrame;

public class TheBanker {
	int m;
	int n;
	int[][] max;
	// 备份用
	int[][] maxbak;
	int[][] allocation;
	// 备份用
	int[][] allocationbak;
	int[][] need;
	// 备份用
	int[][] needbak;
	int[] available;
	// 备份用
	int[] availablebak;

	private MainJFrame mainJFrame = null;

	public TheBanker(MainJFrame mainJFrame) {
		this.mainJFrame = mainJFrame;
		mainJFrame
				.addStringToJta1("初始化-----------------------------------------------------------------------");
		FileSystem fileSystem = mainJFrame.getFileSystem();
		String processNumString = fileSystem.readFile(
				"/file/file/file[@name='processNum']").trim();
		String resourceNumString = fileSystem.readFile(
				"/file/file/file[@name='resourceNum']").trim();
		m = Integer.parseInt(processNumString);
		mainJFrame.addStringToJta1("读入进程数：" + m);
		n = Integer.parseInt(resourceNumString);
		mainJFrame.addStringToJta1("读入资源数：" + n);

		max = new int[m][n];
		maxbak = new int[m][n];
		allocation = new int[m][n];
		allocationbak = new int[m][n];
		need = new int[m][n];
		needbak = new int[m][n];
		available = new int[n];
		availablebak = new int[n];

		// 读入各个进程的已分配各资源数
		String allocationString = fileSystem
				.readFile("/file/file/file[@name='allocation']");
		String maxString = fileSystem.readFile("/file/file/file[@name='max']");
		String availdableResourceNumString = fileSystem
				.readFile("/file/file/file[@name='availdableResourceNum']");

		String[] allocationLine = allocationString.trim().split("[\\n]+");
		String[] maxLine = maxString.trim().split("[\\n]+");
		String[] availdableResourceNumLine = availdableResourceNumString.trim()
				.split("[\\n]+");

		// 初始化向量MAX、ALLOCATION、NEED、AVAILABLE
		// Max
		for (int i = 0; i < maxLine.length; i++) {
			if (maxLine[i].length() == 0) {
				continue;
			}
			String[] v = maxLine[i].trim().split("[,]+");
			for (int j = 0; j < v.length; j++) {
				max[i][j] = Integer.parseInt(v[j]);
				maxbak[i][j] = max[i][j];
			}
		}
		// allocation
		for (int i = 0; i < allocationLine.length; i++) {
			if (allocationLine[i].length() == 0) {
				continue;
			}
			String[] v = allocationLine[i].trim().split("[,]+");
			for (int j = 0; j < v.length; j++) {
				allocation[i][j] = Integer.parseInt(v[j]);
				allocationbak[i][j] = allocation[i][j];
			}
		}
		// need
		for (int i = 0; i < need.length; i++) {
			for (int j = 0; j < need[i].length; j++) {
				need[i][j] = max[i][j] - allocation[i][j];
				needbak[i][j] = need[i][j];
			}
		}
		// available
		for (int i = 0; i < availdableResourceNumLine.length; i++) {
			if (availdableResourceNumLine[i].length() == 0) {
				continue;
			}
			String[] v = availdableResourceNumLine[i].trim().split("[,]+");
			for (int j = 0; j < v.length; j++) {
				available[j] = Integer.parseInt(v[j]);
				System.out.println(available[j]);
				availablebak[j] = available[j];
			}
		}
		for (int j = 0; j < available.length; j++) {
			System.out.println(available[j]);
		}

		mainJFrame
				.addStringToJta1("初始化结果-----------------------------------------------------------------");
		mainJFrame
				.addStringToJta1("         MAX          ALLOCATION          NEED              AVAILABLE");

		for (int i = 0; i < m; i++) {
			System.out.print("P" + i + ": ");
			for (int j = 0; j < n; j++) {
				if (max[i][j] > 9) {
					// 如果是两位数，控制格式，在数字前少输出一个" "。
					mainJFrame.addStringToJta1No(max[i][j] + " ");
				} else {
					mainJFrame.addStringToJta1No(" " + max[i][j] + " ");
				}
			}
			mainJFrame.addStringToJta1No("     ");
			for (int j = 0; j < n; j++) {
				if (allocation[i][j] > 9) {
					mainJFrame.addStringToJta1No(allocation[i][j] + " ");
				} else {
					mainJFrame.addStringToJta1No(" " + allocation[i][j] + " ");
				}
			}
			mainJFrame.addStringToJta1No("     ");
			for (int j = 0; j < n; j++) {
				if (need[i][j] > 9) {
					mainJFrame.addStringToJta1No(need[i][j] + " ");
				} else {
					mainJFrame.addStringToJta1No(" " + need[i][j] + " ");
				}
			}
			if (i == 0) {
				mainJFrame.addStringToJta1No("     ");
				for (int j = 0; j < n; j++) {
					if (available[j] > 9) {
						mainJFrame.addStringToJta1No(available[j] + " ");
					} else {
						mainJFrame.addStringToJta1No(" " + available[j] + " ");
					}
				}
			}
			mainJFrame.addStringToJta1("");
		}
		mainJFrame
				.addStringToJta1("完成初始化-----------------------------------------------------------------");
		mainJFrame.addStringToJta1("");
	}

	// 死锁避免
	public void deadlockAvoidance() {
		int[] security = new int[m];
		boolean[] param = new boolean[m];
		int[] tar = new int[n];
		int count = 0;
		// 计数器,每循环一遍所有进程就自减1
		int num1 = m + 1;
		// 计数器，每遇到一个被满足的进程就自减1
		int num2 = m;

		while (num1 > 0) {
			// 如果num1==0，则说明依次循环下来没有能够满足的进程，因此中止
			for (int i = 0; i < m; i++) {
				if (param[i] == false) {
					// 只有没有被满足的进程才可以进入内层循环
					param[i] = true;
					for (int j = 0; j < n; j++) {
						tar[j] = available[j] - need[i][j];
						if (tar[j] < 0) {
							param[i] = false;
						}
					}
					if (param[i] == true) {
						for (int k = 0; k < n; k++) {
							available[k] = available[k] + allocation[i][k];
						}
						// 记录以满足的进程号
						security[count] = i;
						count++;
						num2--;
					}
				}
			}
			num1--;
			while ((num2 == 0) && (num1 > 0)) {
				mainJFrame.addStringToJta1("[安全序列]为：");
				for (int i = 0; i < m; i++) {
					if (i == (m - 1)) {
						mainJFrame.addStringToJta1No("P" + security[i]);
					} else {
						mainJFrame.addStringToJta1No("P" + security[i] + "-->");
					}
				}
				mainJFrame.addStringToJta1("");
				mainJFrame.addStringToJta1("[死锁避免]结束");
				mainJFrame.addStringToJta1("");
				return;
			}
			while ((num1 == 0) && (num2 > 0)) {
				mainJFrame.addStringToJta1("抱歉！没有[安全序列]！");
				mainJFrame.addStringToJta1("[死锁避免]结束");
				mainJFrame.addStringToJta1("");
				return;
			}
		}
	}

	// 死锁避免结束
	//
	public void deadlockDetection(int processNum, String resourceString) {
		int key;
		int[] security = new int[m];
		boolean[] param = new boolean[m];
		int[] temp = new int[n];
		int[] tar = new int[n];
		int count = 0;
		// 计数器,每循环一遍所有进程就自减1
		int num1 = m + 1;
		// 计数器，每遇到一个被满足的进程就自减1
		int num2 = m;

		for (int i = 0; i < m; i++) {
			// 回复死锁检测前的状态
			for (int j = 0; j < n; j++) {
				max[i][j] = maxbak[i][j];
				allocation[i][j] = allocationbak[i][j];
				need[i][j] = needbak[i][j];
				available[j] = availablebak[j];
			}
		}
		mainJFrame.addStringToJta1("");
		mainJFrame
				.addStringToJta1("死锁检测------------------------------------------------------------------------------------------------");
		mainJFrame.addStringToJta1("");
		key = processNum;
		String[] resoucreNum = resourceString.split("[\\s]+");
		for (int i = 0; i < resoucreNum.length; i++) {
			try {
				temp[i] = Integer.parseInt(resoucreNum[i]);
			} catch (Exception e) {
				// TODO: handle exception
				mainJFrame.addStringToJta1("输入有误");
				return;
			}
		}
		for (int i = 0; i < n; i++) {
			allocation[key][i] = allocation[key][i] + temp[i];
			need[key][i] = need[key][i] - temp[i];
			if (need[key][i] < 0) {
				mainJFrame.addStringToJta1("申请资源大于所需资源，系统[不能]分配资源！");
				for (int k = 0; k < m; k++) {
					// 回复死锁检测前的状态
					for (int j = 0; j < n; j++) {
						if (k == 0) {
							available[j] = availablebak[j];
						}
						max[k][j] = maxbak[k][j];
						allocation[k][j] = allocationbak[k][j];
						need[k][j] = needbak[k][j];
					}
				}
				return;
			}
			available[i] = available[i] - temp[i];
			if (available[i] < 0) {
				mainJFrame.addStringToJta1("申请资源大于所需资源，系统[不能]分配资源！");
				for (int k = 0; k < m; k++) {
					// 回复死锁检测前的状态
					for (int j = 0; j < n; j++) {
						if (k == 0) {
							available[j] = availablebak[j];
						}
						max[k][j] = maxbak[k][j];
						allocation[k][j] = allocationbak[k][j];
						need[k][j] = needbak[k][j];
					}
				}
				return;
			}
		}
		mainJFrame
				.addStringToJta1("申请资源时各进程的状态-------------------------------------------------------------------------------------");
		mainJFrame
				.addStringToJta1("         MAX             ALLOCATION          NEED           AVAILABLE");
		for (int i = 0; i < m; i++) {
			mainJFrame.addStringToJta1No("P" + i + ": ");
			for (int j = 0; j < n; j++) {
				if (max[i][j] > 9) {
					mainJFrame.addStringToJta1No(max[i][j] + " ");
				} else {
					mainJFrame.addStringToJta1No(" " + max[i][j] + " ");
				}
			}
			mainJFrame.addStringToJta1No("     ");
			for (int j = 0; j < n; j++) {
				if (allocation[i][j] > 9) {
					mainJFrame.addStringToJta1No(allocation[i][j] + " ");
				} else {
					mainJFrame.addStringToJta1No(" " + allocation[i][j] + " ");
				}
			}
			mainJFrame.addStringToJta1No("     ");
			for (int j = 0; j < n; j++) {
				if (need[i][j] > 9) {
					mainJFrame.addStringToJta1No(need[i][j] + " ");
				} else {
					mainJFrame.addStringToJta1No(" " + need[i][j] + " ");
				}
			}
			if (i == 0) {
				mainJFrame.addStringToJta1No("     ");
				for (int j = 0; j < n; j++) {
					if (available[j] > 9) {
						mainJFrame.addStringToJta1No(available[j] + " ");
					} else {
						mainJFrame.addStringToJta1No(" " + available[j] + " ");
					}
				}
			}
			mainJFrame.addStringToJta1("");
		}
		mainJFrame
				.addStringToJta1("----------------------------完成状态展示--------------------------------");
		mainJFrame.addStringToJta1("");
		while (num1 > 0) {
			// 如果num1==0，则说明依次循环下来没有能够满足的进程，因此中止
			for (int i = 0; i < m; i++) {
				if (param[i] == false) {
					// 只有没有被满足的进程才可以进入内层循环
					param[i] = true;
					for (int j = 0; j < n; j++) {
						tar[j] = available[j] - need[i][j];
						if (tar[j] < 0) {
							param[i] = false;
						}
					}
					if (param[i] == true) {
						for (int k = 0; k < n; k++) {
							available[k] = available[k] + allocation[i][k];
						}
						security[count] = i;// 记录以满足的进程号
						count++;
						num2--;
					}
				}
			}
			num1--;
			while ((num2 == 0) && (num1 > 0)) {
				mainJFrame.addStringToJta1("[安全序列]为：");
				for (int i = 0; i < m; i++) {
					if (i == (m - 1)) {
						mainJFrame.addStringToJta1No("P" + security[i]);
					} else {
						mainJFrame.addStringToJta1No("P" + security[i] + "-->");
					}
				}
				mainJFrame.addStringToJta1("");
				mainJFrame.addStringToJta1("可以产生新的安全序列！系统[能]将申请的资源分配给P" + key
						+ "！");
				mainJFrame
						.addStringToJta1("-----------------------死锁检测结束------------------------------");
				mainJFrame.addStringToJta1("");
				return;
			}
			while ((num1 == 0) && (num2 > 0)) {
				mainJFrame.addStringToJta1("抱歉！[没有]安全序列！");
				mainJFrame.addStringToJta1("系统[不能]将申请的资源分配给P" + key + "！");
				mainJFrame
						.addStringToJta1("--------------------------死锁检测结束----------------");
				mainJFrame.addStringToJta1("");
				return;
			}
		}
	}
}
