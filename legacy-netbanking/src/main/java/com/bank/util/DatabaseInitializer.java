package com.bank.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseInitializer {

    public static void initialize() throws Exception {
        Connection conn = DBUtil.getConnection();
        try {
            runScript(conn, "/sql/schema.sql");
            runScript(conn, "/sql/seed.sql");
        } finally {
            DBUtil.closeQuietly(conn);
        }
    }

    private static void runScript(Connection conn, String resourcePath) throws Exception {
        InputStream is = DatabaseInitializer.class.getResourceAsStream(resourcePath);
        if (is == null) return;
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        List<String> statements = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("--")) continue;
            sb.append(line).append(" ");
            if (line.endsWith(";")) {
                String stmt = sb.toString().trim();
                if (stmt.length() > 1) statements.add(stmt.substring(0, stmt.length() - 1));
                sb = new StringBuilder();
            }
        }
        reader.close();
        Statement stmt = conn.createStatement();
        try { for (String sql : statements) if (sql.length() > 0) stmt.execute(sql); }
        finally { stmt.close(); }
    }
}
