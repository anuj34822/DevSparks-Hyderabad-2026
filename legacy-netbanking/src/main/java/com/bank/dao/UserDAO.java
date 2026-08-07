package com.bank.dao;

import com.bank.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Legacy DAO - inline SQL, no ORM.
 */
public class UserDAO {

    public User findByNameAndPassword(String username, String password) throws SQLException {
        Connection conn = null; PreparedStatement stmt = null; ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement("SELECT id, username, password, role FROM USERS WHERE username = ? AND password = ?");
            stmt.setString(1, username); stmt.setString(2, password);
            rs = stmt.executeQuery();
            return rs.next() ? mapRow(rs) : null;
        } finally { close(rs, stmt, conn); }
    }

    public User findById(int id) throws SQLException {
        Connection conn = null; PreparedStatement stmt = null; ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement("SELECT id, username, password, role FROM USERS WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            return rs.next() ? mapRow(rs) : null;
        } finally { close(rs, stmt, conn); }
    }

    public List<User> findAll() throws SQLException {
        Connection conn = null; PreparedStatement stmt = null; ResultSet rs = null;
        List<User> list = new ArrayList<User>();
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement("SELECT id, username, password, role FROM USERS ORDER BY id");
            rs = stmt.executeQuery();
            while (rs.next()) { list.add(mapRow(rs)); }
            return list;
        } finally { close(rs, stmt, conn); }
    }

    public void create(String username, String password, String role) throws SQLException {
        Connection conn = null; PreparedStatement stmt = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement("INSERT INTO USERS (username, password, role) VALUES (?, ?, ?)");
            stmt.setString(1, username); stmt.setString(2, password); stmt.setString(3, role != null ? role : "USER");
            stmt.executeUpdate();
        } finally {
            if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
            DBUtil.closeQuietly(conn);
        }
    }

    private User mapRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id")); user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password")); user.setRole(rs.getString("role"));
        return user;
    }

    private void close(ResultSet rs, PreparedStatement stmt, Connection conn) {
        if (rs != null) try { rs.close(); } catch (SQLException e) {}
        if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
        DBUtil.closeQuietly(conn);
    }
}
