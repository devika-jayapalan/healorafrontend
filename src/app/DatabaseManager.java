package app;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private Connection conn;

    public DatabaseManager() throws SQLException {
        try {
            // Load SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            // Connect to database file
            conn = DriverManager.getConnection("jdbc:sqlite:moodtracker.db");
            System.out.println("Database connected successfully!");
            initializeTables();
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
            throw e;
        }
    }

    public Connection getConnection() {
        return conn;
    }

    // Create table if it doesn't exist
    private void initializeTables() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS moods (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "moodType TEXT NOT NULL," +
                "intensity INTEGER NOT NULL," +
                "date TEXT NOT NULL)";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }

    // ✅ Save a mood
    public void saveMood(mood m) throws SQLException {
        String sql = "INSERT INTO moods(moodType, intensity, date) VALUES (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, m.getMoodType());
        pstmt.setInt(2, m.getIntensity());
        pstmt.setString(3, m.getDate().toString());  // LocalDate → String
        pstmt.executeUpdate();
        pstmt.close();
    }

    // ✅ Fetch all moods from DB
    public List<mood> getAllMoods() throws SQLException {
        List<mood> moods = new ArrayList<>();
        String sql = "SELECT * FROM moods";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            String moodType = rs.getString("moodType");
            int intensity = rs.getInt("intensity");
            String dateStr = rs.getString("date");
            LocalDate date = LocalDate.parse(dateStr);

            moods.add(new mood(moodType, intensity, date));//this line showing error
        }

        rs.close();
        stmt.close();
        return moods;
    }

    public void close() throws SQLException {
        if (conn != null) conn.close();
    }
}
