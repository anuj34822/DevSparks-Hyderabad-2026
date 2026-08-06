package com.bank.dao;

import com.bank.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Legacy DAO - inline SQL for transactions.
 */
public class TransactionDAO {

    public void create(int fromAccountId, int toAccountId, java.math.BigDecimal amount, String description)
            throws SQLException {
        Connection conn = null; PreparedStatement stmt = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement("INSERT INTO TRANSACTIONS (from_account, to_account, amount, description) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, fromAccountId); stmt.setInt(2, toAccountId);
            stmt.setBigDecimal(3, amount); stmt.setString(4, description);
            stmt.executeUpdate();
        } finally {
            if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
            DBUtil.closeQuietly(conn);
        }
    }

    public List<Transaction> findByAccountId(int accountId) throws SQLException {
        Connection conn = null; PreparedStatement stmt = null; ResultSet rs = null;
        List<Transaction> list = new ArrayList<Transaction>();
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT t.id, t.from_account, t.to_account, t.amount, t.description, t.timestamp, " +
                    "fa.account_number as from_acct_num, ta.account_number as to_acct_num " +
                    "FROM TRANSACTIONS t " +
                    "LEFT JOIN ACCOUNTS fa ON t.from_account = fa.id " +
                    "LEFT JOIN ACCOUNTS ta ON t.to_account = ta.id " +
                    "WHERE t.from_account = ? OR t.to_account = ? " +
                    "ORDER BY t.timestamp DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accountId); stmt.setInt(2, accountId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Transaction tx = new Transaction();
                tx.setId(rs.getInt("id")); tx.setFromAccountId(rs.getInt("from_account"));
                tx.setToAccountId(rs.getInt("to_account")); tx.setAmount(rs.getBigDecimal("amount"));
                tx.setDescription(rs.getString("description")); tx.setTimestamp(rs.getTimestamp("timestamp"));
                tx.setFromAccountNumber(rs.getString("from_acct_num")); tx.setToAccountNumber(rs.getString("to_acct_num"));
                list.add(tx);
            }
            return list;
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
            DBUtil.closeQuietly(conn);
        }
    }
}
