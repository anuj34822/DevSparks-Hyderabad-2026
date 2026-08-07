package com.bank.actions;

import com.bank.dao.User;
import com.bank.dao.UserDAO;
import com.bank.forms.LoginForm;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 * Struts Action - business logic embedded, no service layer.
 */
public class LoginAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        LoginForm loginForm = (LoginForm) form;
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Username and password are required.");
            return mapping.findForward("failure");
        }

        UserDAO userDAO = new UserDAO();
        User user = null;
        try {
            user = userDAO.findByNameAndPassword(username.trim(), password);
        } catch (SQLException e) {
            request.setAttribute("error", "Database error. Please try again.");
            return mapping.findForward("failure");
        }

        if (user == null) {
            request.setAttribute("error", "Invalid username or password.");
            return mapping.findForward("failure");
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
        return mapping.findForward("success");
    }
}
