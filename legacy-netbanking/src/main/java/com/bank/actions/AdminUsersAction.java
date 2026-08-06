package com.bank.actions;

import com.bank.dao.User;
import com.bank.dao.UserDAO;
import com.bank.util.DBUtil;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Admin action - inline SQL for listing users (demonstrates mixed DAO + inline).
 */
public class AdminUsersAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);
        if (user == null || !user.isAdmin()) {
            return mapping.findForward("login");
        }

        List<User> users = new ArrayList<User>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT id, username, password, role FROM USERS ORDER BY id";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("role"));
                users.add(u);
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Database error.");
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
            DBUtil.closeQuietly(conn);
        }

        request.setAttribute("users", users);
        request.setAttribute("user", user);
        return mapping.findForward("success");
    }
}
