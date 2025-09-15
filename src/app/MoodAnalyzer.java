package app;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MoodAnalyzer {
    private List<mood> moods;

    public MoodAnalyzer(List<mood> moods) {
        this.moods = moods;
    }

    // 1. Find most frequent mood
    public String mostFrequentMood() {
        if (moods.isEmpty()) return "No moods logged yet.";

        Map<String, Integer> countMap = new HashMap<>();
        for (mood m : moods) {
            String moodType = m.getMoodType().trim().toLowerCase();  
            countMap.put(moodType, countMap.getOrDefault(moodType, 0) + 1);
        }

        String mostFrequent = "";
        int max = 0;
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                mostFrequent = entry.getKey();
            }
        }
        return mostFrequent;
    }

    // 2. Calculate average intensity
    public double calculateAverageIntensity() {
        if (moods.isEmpty()) return 0.0;

        int sum = 0;
        for (mood m : moods) {
            sum += m.getIntensity();
        }
        return (double) sum / moods.size();
    }

    // 3. Weekly Report (last 7 days summary)
    public String getWeeklyReport() {
        if (moods.isEmpty()) return "No moods logged yet.";

        // sort moods by date
        List<mood> sorted = new ArrayList<>(moods);
        sorted.sort(Comparator.comparing(mood::getDate));

        LocalDate latestDate = sorted.get(sorted.size() - 1).getDate();
        LocalDate fromDate = latestDate.minusDays(7);

        // filter last 7 days
        List<mood> last7 = sorted.stream()
                .filter(m -> !m.getDate().isBefore(fromDate))
                .collect(Collectors.toList());

        if (last7.isEmpty()) return "No moods recorded in the last 7 days.";

        // average intensity
        double avg = last7.stream().mapToInt(mood::getIntensity).average().orElse(0.0);

        // most frequent mood in last 7 days
        Map<String, Long> freqMap = last7.stream()
                .collect(Collectors.groupingBy(
                        m -> m.getMoodType().trim().toLowerCase(),
                        Collectors.counting()
                ));

        String mostFrequent = Collections.max(freqMap.entrySet(), Map.Entry.comparingByValue()).getKey();

        // most recent mood
        mood recent = last7.get(last7.size() - 1);

        return "Weekly Mood Report\n" +
                "- Average Intensity: " + String.format("%.2f", avg) + "\n" +
                "- Most Frequent Mood: " + mostFrequent + "\n" +
                "- Most Recent Mood: " + recent.getMoodType() +
                " (Intensity: " + recent.getIntensity() + ")";
    }

    // 4. Get frequency count for a given mood
    public int getMoodFrequency(String moodType) {
        int count = 0;
        for (mood m : moods) {
            if (m.getMoodType().equalsIgnoreCase(moodType)) {
                count++;
            }
        }
        return count;
    }
}
