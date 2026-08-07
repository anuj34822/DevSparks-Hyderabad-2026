package com.bank.actions;

import com.bank.dao.Account;
import com.bank.dao.AccountDAO;
import com.bank.dao.TransactionDAO;
import com.bank.dao.User;
import com.bank.forms.TransferForm;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * Struts Action - business logic for fund transfer inline.
 */
public class TransferAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);
        if (user == null) {
            return mapping.findForward("login");
        }

        TransferForm transferForm = (TransferForm) form;
        String fromAccountIdStr = transferForm.getFromAccountId();
        String toAccountNumber = transferForm.getToAccountNumber();
        String amountStr = transferForm.getAmount();
        String description = transferForm.getDescription();

        if (fromAccountIdStr == null || fromAccountIdStr.trim().isEmpty() ||
            toAccountNumber == null || toAccountNumber.trim().isEmpty() ||
            amountStr == null || amountStr.trim().isEmpty()) {
            request.setAttribute("error", "All fields are required.");
            return loadFormAndForward(mapping, request, user, "failure");
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal(amountStr.trim());
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                request.setAttribute("error", "Amount must be positive.");
                return loadFormAndForward(mapping, request, user, "failure");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid amount.");
            return loadFormAndForward(mapping, request, user, "failure");
        }

        int fromAccountId = Integer.parseInt(fromAccountIdStr.trim());
        AccountDAO accountDAO = new AccountDAO();
        TransactionDAO transactionDAO = new TransactionDAO();

        Account fromAccount = null;
        Account toAccount = null;
        try {
            fromAccount = accountDAO.findById(fromAccountId);
            toAccount = accountDAO.findByAccountNumber(toAccountNumber.trim());
        } catch (SQLException e) {
            request.setAttribute("error", "Database error.");
            return loadFormAndForward(mapping, request, user, "failure");
        }

        if (fromAccount == null || fromAccount.getUserId() != user.getId()) {
            request.setAttribute("error", "Invalid source account.");
            return loadFormAndForward(mapping, request, user, "failure");
        }
        if (toAccount == null) {
            request.setAttribute("error", "Destination account not found.");
            return loadFormAndForward(mapping, request, user, "failure");
        }
        if (fromAccount.getId() == toAccount.getId()) {
            request.setAttribute("error", "Cannot transfer to same account.");
            return loadFormAndForward(mapping, request, user, "failure");
        }
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            request.setAttribute("error", "Insufficient balance.");
            return loadFormAndForward(mapping, request, user, "failure");
        }

        try {
            BigDecimal newFromBalance = fromAccount.getBalance().subtract(amount);
            BigDecimal newToBalance = toAccount.getBalance().add(amount);
            accountDAO.updateBalance(fromAccount.getId(), newFromBalance);
            accountDAO.updateBalance(toAccount.getId(), newToBalance);
            transactionDAO.create(fromAccount.getId(), toAccount.getId(), amount,
                    description != null ? description : "Fund transfer");
        } catch (SQLException e) {
            request.setAttribute("error", "Transfer failed. Please try again.");
            return loadFormAndForward(mapping, request, user, "failure");
        }

        request.setAttribute("amount", amount);
        request.setAttribute("toAccountNumber", toAccountNumber);
        return mapping.findForward("success");
    }

    private ActionForward loadFormAndForward(ActionMapping mapping, HttpServletRequest request,
                                            User user, String forwardName) throws SQLException {
        AccountDAO accountDAO = new AccountDAO();
        List<Account> accounts = accountDAO.findByUserId(user.getId());
        request.setAttribute("accounts", accounts);
        request.setAttribute("user", user);
        return mapping.findForward(forwardName);
    }
}
