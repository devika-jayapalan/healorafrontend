// package app;


// public class Pet {
//     private String name;
//     private int level;
//     private int happiness;
//     private int health;
//     private int energy;

//     private AchievementSystem achievementSystem;

//     // Constructor for new pet
//     public Pet(String name) {
//         this.name = name;
//         this.level = 1;
//         this.happiness = 50;
//         this.health = 50;
//         this.energy = 50;
//         this.achievementSystem = new AchievementSystem();
//     }

//     // Constructor for loading pet from database
//     public Pet(String name, int level, int happiness, int health, int energy) {
//         this.name = name;
//         this.level = level;
//         this.happiness = happiness;
//         this.health = health;
//         this.energy = energy;
//         this.achievementSystem = new AchievementSystem();
//     }

//     // === Core Pet Actions ===
//     public void feed() {
//         health += 10;
//         happiness += 5;
//         energy += 5;

//         System.out.println(name + " enjoyed the food!");
//         achievementSystem.recordFeed();
//         levelUp();
//     }

//     public void play() {
//         happiness += 15;
//         energy -= 10;

//         System.out.println(name + " had fun playing!");
//         achievementSystem.recordPlay();
//         levelUp();
//     }

//     public void rest() {
//         energy += 20;
//         happiness += 5;
//         System.out.println(name + " is resting...");
//     }

//     // === Mood Integration (Member 3) ===
//     public void applyMood(String mood) {
//         switch(mood.toLowerCase()) {
//             case "happy": 
//                 happiness += 10; 
//                 System.out.println(name + " feels happy!");
//                 break;
//             case "sad": 
//                 happiness -= 10; 
//                 health -= 5;
//                 System.out.println(name + " feels sad...");
//                 break;
//             case "angry": 
//                 health -= 5; 
//                 happiness -= 5;
//                 System.out.println(name + " senses anger!");
//                 break;
//             case "calm": 
//                 energy += 10;
//                 System.out.println(name + " feels calm.");
//                 break;
//             default: 
//                 System.out.println("Mood not recognized.");
//         }
//         levelUp();
//     }

//     // === Level System ===
//     private void levelUp() {
//         if (happiness >= 100 || health >= 100) {
//             level++;
//             happiness = Math.min(happiness, 100);
//             health = Math.min(health, 100);
//             energy = Math.min(energy, 100);

//             System.out.println(name + " leveled up! 🎉 Now Level " + level);

//             // Check achievements
//             achievementSystem.checkLevel(this);
//         }
//     }

//     // === Achievements ===
//     public void showAchievements() {
//         achievementSystem.showAchievements();
//     }

//     // === Getters ===
//     public String getName() { return name; }
//     public int getLevel() { return level; }
//     public int getHappiness() { return happiness; }
//     public int getHealth() { return health; }
//     public int getEnergy() { return energy; }

//     // === Setters (for database integration) ===
//     public void setLevel(int level) { this.level = level; }
//     public void setHappiness(int happiness) { this.happiness = happiness; }
//     public void setHealth(int health) { this.health = health; }
//     public void setEnergy(int energy) { this.energy = energy; }

//     public void setAchievementSystem(AchievementSystem system) {
//         this.achievementSystem = system;
//     }

//     public AchievementSystem getAchievementSystem() {
//         return achievementSystem;
//     }
// }
package app;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Pet {
    private final String name;
    private final IntegerProperty happiness = new SimpleIntegerProperty();
    private final IntegerProperty energy = new SimpleIntegerProperty();
    private final IntegerProperty level = new SimpleIntegerProperty();
    private final IntegerProperty xp = new SimpleIntegerProperty(); // optional for leveling

    public Pet(String name) {
        this.name = name;
        happiness.set(50); // 0-100
        energy.set(50);    // 0-100
        level.set(1);
        xp.set(0);
    }

    // Properties for binding
    public IntegerProperty happinessProperty() { return happiness; }
    public IntegerProperty energyProperty() { return energy; }
    public IntegerProperty levelProperty() { return level; }
    public IntegerProperty xpProperty() { return xp; }

    // Value getters
    public int getHappiness(){ return happiness.get(); }
    public int getEnergy(){ return energy.get(); }
    public int getLevel(){ return level.get(); }
    public int getXp(){ return xp.get(); }
    public String getName(){ return name; }

    // Behavior methods called by UI
    public void feed() {
        energy.set(Math.min(100, energy.get() + 20));
        happiness.set(Math.min(100, happiness.get() + 5));
        addXp(8);
    }

    public void play() {
        // playing raises happiness but reduces energy
        happiness.set(Math.min(100, happiness.get() + 15));
        energy.set(Math.max(0, energy.get() - 12));
        addXp(12);
    }

    public void heal() {
        // heal increases happiness and energy a bit
        energy.set(Math.min(100, energy.get() + 12));
        happiness.set(Math.min(100, happiness.get() + 10));
        addXp(6);
    }

    public void rest() {
        energy.set(Math.min(100, energy.get() + 30));
        // resting slightly reduces happiness if very bored (optional)
        addXp(4);
    }

    // XP & simple leveling logic
    private void addXp(int amount) {
        xp.set(Math.max(0, xp.get() + amount));
        checkLevelUp();
    }

    private void checkLevelUp() {
        // simple: require 100 * level xp to go to next level
        int threshold = level.get() * 100;
        while (xp.get() >= threshold) {
            xp.set(xp.get() - threshold);
            level.set(level.get() + 1);
            threshold = level.get() * 100;
        }
    }
}
