package com.bank.actions;

import com.bank.dao.User;
import com.bank.util.DatabaseInitializer;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Admin action - re-run seed data.
 */
public class AdminSeedAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);
        if (user == null || !user.isAdmin()) {
            return mapping.findForward("login");
        }

        String message = "Seed data already exists. Re-run init to reset.";
        try {
            DatabaseInitializer.initialize();
            message = "Database schema and seed data initialized successfully.";
        } catch (Exception e) {
            message = "Error: " + e.getMessage();
        }
        request.setAttribute("seedMessage", message);
        request.setAttribute("user", user);
        return mapping.findForward("success");
    }
}
