package com.bank.actions;

import com.bank.dao.AccountDAO;
import com.bank.dao.User;
import com.bank.forms.AdminAccountForm;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Admin action - create account.
 */
public class AdminCreateAccountAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);
        if (user == null || !user.isAdmin()) {
            return mapping.findForward("login");
        }

        AdminAccountForm adminForm = (AdminAccountForm) form;
        String userIdStr = adminForm.getUserId();
        String accountNumber = adminForm.getAccountNumber();
        String initialBalanceStr = adminForm.getInitialBalance();

        if (userIdStr == null || userIdStr.trim().isEmpty() ||
            accountNumber == null || accountNumber.trim().isEmpty()) {
            request.setAttribute("error", "User ID and account number are required.");
            return mapping.findForward("failure");
        }

        int userId = Integer.parseInt(userIdStr.trim());
        BigDecimal balance = BigDecimal.ZERO;
        if (initialBalanceStr != null && !initialBalanceStr.trim().isEmpty()) {
            try {
                balance = new BigDecimal(initialBalanceStr.trim());
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid balance.");
                return mapping.findForward("failure");
            }
        }

        AccountDAO accountDAO = new AccountDAO();
        try {
            accountDAO.create(userId, accountNumber.trim(), balance);
        } catch (SQLException e) {
            request.setAttribute("error", "Failed to create account: " + e.getMessage());
            return mapping.findForward("failure");
        }

        return mapping.findForward("success");
    }
}
