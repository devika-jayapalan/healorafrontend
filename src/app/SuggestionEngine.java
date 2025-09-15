package app;
public class SuggestionEngine {

    // Preferred: pass the whole mood object
    public static String generateSuggestion(mood m) {
        if (m == null) return "Take a pause and check in with yourself.";
        return generateSuggestion(m.getMoodType(), m.getIntensity());
    }

    // Backward-compatible: pass only mood type
    public static String getSuggestion(String moodType) {
        return generateSuggestion(moodType, -1); 
    }

    // Core logic 
    private static String generateSuggestion(String moodType, int intensity) {
        if (moodType == null) return "Take a pause and check in with yourself.";

        String type = moodType.toLowerCase();

        switch (type) {
            case "happy":
            case "joy":
            case "excited":
                if (intensity >= 4) {
                    return "You're glowing with positivity! Celebrate with a fun game or share the joy with friends.";
                } else {
                    return "Enjoy the moment maybe a relaxing activity or light pet interaction.";
                }

            case "bored":
            case "neutral":
                if (intensity >= 3) {
                    return "Shake things up! Try a short, engaging game or a creative hobby.";
                } else {
                    return "Maybe take a 5 minute walk, play with pet or listen to music to refresh your mind.";
                }

            case "sad":
                if (intensity >= 4) {
                    return "Be gentle with yourself. Try meditation, journaling, or reaching out to someone you trust.";
                } else {
                    return "Lighten your mood with calming music or writing down three things you're grateful for.";
                }

            case "stressed":
            case "anxious":
                if (intensity >= 4) {
                    return "Pause for deep breathing (4-7-8 technique) or try a short grounding exercise.";
                } else {
                    return "Take a break with a walk, light stretching, or a mindful activity.";
                }

            case "angry":
                if (intensity >= 4) {
                    return "Step away for a bit. Try physical release like exercise or squeezing a stress ball.";
                } else {
                    return "Cool down with journaling or listening to calming sounds.";
                }

            case "confused":
                if (intensity >= 3) {
                    return "Write your thoughts for 5 minutes to clear your head, then revisit the problem.";
                } else {
                    return "Take a pause sometimes clarity comes after a short break.";
                }

            default:
                return "Try a mindful minute slow breathing and gentle movement.";
        }
    }
}
