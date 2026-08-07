<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.bank.dao.Account,com.bank.dao.Transaction,com.bank.dao.User,java.util.List" %>
<%
    String ctx = request.getContextPath();
%>
<html>
<head>
    <title>Transaction History - ABC Bank</title>
    <link rel="stylesheet" type="text/css" href="<%= ctx %>/css/style.css"/>
</head>
<body>
<jsp:include page="/common/header.jsp"/>
<div class="content">
    <h2>Transaction History</h2>
    <%
        String error = (String) request.getAttribute("error");
        if (error != null) { %><p class="error-msg"><%= error %></p><% }
        List accounts = (List) request.getAttribute("accounts");
        Integer selectedAccountId = (Integer) request.getAttribute("selectedAccountId");
        if (accounts != null && !accounts.isEmpty()) {
    %>
    <form action="<%= ctx %>/transactionHistory.do" method="get">
        <table class="form-table" border="0"><tr><td>Select Account:</td><td>
            <select name="accountId" onchange="this.form.submit()">
                <option value="">-- Select --</option>
                <% for (Object o : accounts) { Account acc = (Account) o;
                   String sel = (selectedAccountId != null && selectedAccountId.intValue() == acc.getId()) ? " selected" : ""; %>
                <option value="<%= acc.getId() %>"<%= sel %>><%= acc.getAccountNumber() %> (<%= acc.getBalance() %>)</option>
                <% } %>
            </select>
        </td></tr></table>
    </form>
    <% }
        List transactions = (List) request.getAttribute("transactions");
        if (transactions != null && !transactions.isEmpty()) {
    %>
    <table class="data-table">
        <tr><th>Date</th><th>From</th><th>To</th><th>Amount</th><th>Description</th></tr>
    <% for (Object o : transactions) {
            Transaction tx = (Transaction) o;
            String amtDisplay = tx.getAmount().toString();
            if (selectedAccountId != null && tx.getFromAccountId() == selectedAccountId.intValue()) amtDisplay = "-" + amtDisplay;
    %>
        <tr>
            <td><%= tx.getTimestamp() != null ? tx.getTimestamp() : "" %></td>
            <td><%= tx.getFromAccountNumber() != null ? tx.getFromAccountNumber() : "" %></td>
            <td><%= tx.getToAccountNumber() != null ? tx.getToAccountNumber() : "" %></td>
            <td><%= amtDisplay %></td>
            <td><%= tx.getDescription() != null ? tx.getDescription() : "" %></td>
        </tr>
    <% } %>
    </table>
    <% } else if (selectedAccountId != null) { %><p>No transactions for this account.</p><% } %>
</div>
<jsp:include page="/common/footer.jsp"/>
</body>
</html>
