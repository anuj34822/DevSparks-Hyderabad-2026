package com.bank.actions;

import com.bank.dao.Account;
import com.bank.dao.AccountDAO;
import com.bank.dao.User;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Admin action - list all accounts.
 */
public class AdminAccountsAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);
        if (user == null || !user.isAdmin()) {
            return mapping.findForward("login");
        }

        AccountDAO accountDAO = new AccountDAO();
        List<Account> accounts = null;
        try {
            accounts = accountDAO.findAll();
        } catch (SQLException e) {
            request.setAttribute("error", "Database error.");
        }

        request.setAttribute("accounts", accounts);
        request.setAttribute("user", user);
        return mapping.findForward("success");
    }
}
