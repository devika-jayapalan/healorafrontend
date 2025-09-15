package app;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class mood {
    private int id;              // <-- DB row id
    private String moodType;     // e.g. Happy, Sad, Angry
    private int intensity;       // scale 1–5
    private LocalDate date;      // keep LocalDate internally

    // Constructor with id (used when loading from DB, date as String)
    public mood(int id, String moodType, int intensity, String dateLogged) {
        this.id = id;
        this.moodType = moodType;
        this.intensity = intensity;
        this.date = parseDate(dateLogged);
    }

    // Constructor without id (when adding new mood, date as String)
    public mood(String moodType, int intensity, String date) {
        this(0, moodType, intensity, date);
    }

    // ✅ NEW: Constructor without id (date as LocalDate)
    public mood(String moodType, int intensity, LocalDate date) {
        this.id = 0;
        this.moodType = moodType;
        this.intensity = intensity;
        this.date = date;
    }

    // ✅ NEW: Constructor with id (date as LocalDate) – useful for DB
    public mood(int id, String moodType, int intensity, LocalDate date) {
        this.id = id;
        this.moodType = moodType;
        this.intensity = intensity;
        this.date = date;
    }

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) {
            return LocalDate.now();
        }
        try {
            return LocalDate.parse(dateStr); // expects YYYY-MM-DD
        } catch (DateTimeParseException e) {
            return LocalDate.now();
        }
    }

    // Getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMoodType() { return moodType; }
    public int getIntensity() { return intensity; }
    public LocalDate getDate() { return date; }

    public void setMoodType(String moodType) { this.moodType = moodType; }
    public void setIntensity(int intensity) { this.intensity = intensity; }
    public void setDate(String date) { this.date = parseDate(date); }
    public void setDate(LocalDate date) { this.date = date; } // ✅ Overloaded setter

    @Override
    public String toString() {
        return date.toString() + " - " + moodType + " (Intensity: " + intensity + ")";
    }
}
