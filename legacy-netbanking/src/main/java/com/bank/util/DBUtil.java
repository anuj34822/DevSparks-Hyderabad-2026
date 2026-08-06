package com.bank.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static String getDbPath() {
        String custom = System.getProperty("netbanking.db.path");
        if (custom != null) return custom;
        String base = System.getProperty("user.dir");
        File dataDir = new File(base, "data");
        dataDir.mkdirs();
        return new File(dataDir, "netbanking.db").getAbsolutePath();
    }

    static {
        try { Class.forName("org.sqlite.JDBC"); }
        catch (ClassNotFoundException e) { throw new RuntimeException("SQLite JDBC driver not found", e); }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + getDbPath());
    }

    public static void closeQuietly(Connection conn) {
        if (conn != null) try { conn.close(); } catch (SQLException e) {}
    }
}
