package com.bank.actions;

import com.bank.dao.User;
import com.bank.dao.UserDAO;
import com.bank.forms.AdminUserForm;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 * Admin action - create user.
 */
public class AdminCreateUserAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);
        if (user == null || !user.isAdmin()) {
            return mapping.findForward("login");
        }

        AdminUserForm adminForm = (AdminUserForm) form;
        String username = adminForm.getUsername();
        String password = adminForm.getPassword();
        String role = adminForm.getRole();

        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Username and password are required.");
            return mapping.findForward("failure");
        }

        UserDAO userDAO = new UserDAO();
        try {
            userDAO.create(username.trim(), password, role != null ? role : "USER");
        } catch (SQLException e) {
            request.setAttribute("error", "Failed to create user: " + e.getMessage());
            return mapping.findForward("failure");
        }

        return mapping.findForward("success");
    }
}
