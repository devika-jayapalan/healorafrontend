package app;

public class Achievement {
    private String name;
    private String description;
    private boolean unlocked;

    public Achievement(String name, String description) {
        this.name = name;
        this.description = description;
        this.unlocked = false; // locked by default
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public boolean isUnlocked() { return unlocked; }

    public void unlock() {
        if (!unlocked) {
            unlocked = true;
            System.out.println("🏆 Achievement unlocked: " + name + " - " + description);
        }
    }
}
