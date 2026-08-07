package com.bank.actions;

import com.bank.dao.Account;
import com.bank.dao.AccountDAO;
import com.bank.dao.Transaction;
import com.bank.dao.TransactionDAO;
import com.bank.dao.User;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Struts Action - fetches transactions for selected account.
 */
public class TransactionHistoryAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);
        if (user == null) {
            return mapping.findForward("login");
        }

        AccountDAO accountDAO = new AccountDAO();
        TransactionDAO transactionDAO = new TransactionDAO();
        List<Account> accounts = null;
        List<Transaction> transactions = new ArrayList<Transaction>();

        try {
            accounts = accountDAO.findByUserId(user.getId());
            String accountIdStr = request.getParameter("accountId");
            if (accountIdStr != null && !accountIdStr.isEmpty()) {
                int accountId = Integer.parseInt(accountIdStr);
                transactions = transactionDAO.findByAccountId(accountId);
                request.setAttribute("selectedAccountId", accountId);
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Database error.");
        }

        request.setAttribute("accounts", accounts);
        request.setAttribute("transactions", transactions);
        request.setAttribute("user", user);
        return mapping.findForward("success");
    }
}
