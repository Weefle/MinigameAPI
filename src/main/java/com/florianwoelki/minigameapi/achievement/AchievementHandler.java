package com.florianwoelki.minigameapi.achievement;

import java.util.ArrayList;
import java.util.List;

public class AchievementHandler {

	private List<Achievement> achievementList;

	public AchievementHandler(Achievement... achievements) {
		this.achievementList = new ArrayList<>();
		if(achievements != null) {
			register(achievements);
			initInventory();
		}
	}

	public void register(Achievement... achievements) {
		for(Achievement achievement : achievements) {
			achievement.register(this);
		}
	}

	public void initInventory() {

	}

	public List<Achievement> getAchievementList() {
		return achievementList;
	}

}
