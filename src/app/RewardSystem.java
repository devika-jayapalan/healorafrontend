package app;

public class RewardSystem {
    public static String getReward(int level) {
        switch(level) {
            case 2: return "🏅 Bronze Badge";
            case 5: return "🥈 Silver Badge";
            case 10: return "🥇 Gold Badge";
            default: return "✨ Keep going!";
        }
    }
}
