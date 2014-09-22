/**
 *author：罗圣盛
 *target:
 *version:
 *time:2014-9-16下午4:29:57
 *other:
 */
package com.lss.control;

import com.lss.model.TheBanker;
import com.lss.window.MainJFrame;

public class DeadLock {

	public void initBanker(MainJFrame mainJFrame) {
		TheBanker theBanker = new TheBanker(mainJFrame);
		theBanker.deadlockAvoidance();
	}
}
