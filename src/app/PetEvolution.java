package app;

public class PetEvolution {
    public static String getAppearance(int level) {
        if (level < 3) return "🐣 Baby Pet";
        else if (level < 5) return "🐥 Teen Pet";
        else return "🦄 Adult Pet";
    }
}
