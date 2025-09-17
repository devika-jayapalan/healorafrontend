package app;

import java.util.ArrayList;
import java.util.List;

public class AchievementSystem {
    private List<Achievement> achievements;
    private int playCount;
    private int feedCount;

    public AchievementSystem() {
        achievements = new ArrayList<>();
        achievements.add(new Achievement("First Play", "You played with your pet for the first time."));
        achievements.add(new Achievement("Food Lover", "You fed your pet 5 times."));
        achievements.add(new Achievement("Happy Pet", "Reach Level 5 with your pet."));
    }

    public void recordPlay() {
        playCount++;
        if (playCount == 1) {
            achievements.get(0).unlock();
        }
    }

    public void recordFeed() {
        feedCount++;
        if (feedCount == 5) {
            achievements.get(1).unlock();
        }
    }

    public void checkLevel(Pet pet) {
        if (pet.getLevel() >= 5) {
            achievements.get(2).unlock();
        }
    }

    public void showAchievements() {
        System.out.println("=== Achievements ===");
        for (Achievement a : achievements) {
            System.out.println(a.getName() + " - " + (a.isUnlocked() ? "✅ Unlocked" : "❌ Locked"));
        }
    }
}
