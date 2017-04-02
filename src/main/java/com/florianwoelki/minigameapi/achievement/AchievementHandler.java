package com.florianwoelki.minigameapi.achievement;

import java.util.ArrayList;
import java.util.List;

public class AchievementHandler {

	private List<Achievement> achievementList;

	public AchievementHandler() {
		this.achievementList = new ArrayList<>();
		for(AchievementType achievementType : AchievementType.values()) {
			achievementList.add(new Achievement(this, achievementType));
		}
		initInventory();
	}

	public void register(Achievement... achievements) {
		for(Achievement achievement : achievements) {
			achievementList.add(achievement);
		}
	}

	public void initInventory() {

	}

	public List<Achievement> getAchievementList() {
		return achievementList;
	}

}
