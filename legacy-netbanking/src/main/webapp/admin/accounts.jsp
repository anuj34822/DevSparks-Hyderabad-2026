<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.bank.dao.Account,java.util.List" %>
<%
    String ctx = request.getContextPath();
%>
<html>
<head><title>Admin - Accounts - ABC Bank</title><link rel="stylesheet" type="text/css" href="<%= ctx %>/css/style.css"/></head>
<body>
<jsp:include page="/common/header.jsp"/>
<div class="content">
    <h2>Admin - Manage Accounts</h2>
    <% String error = (String) request.getAttribute("error"); if (error != null) { %><p class="error-msg"><%= error %></p><% } %>
    <p><a href="<%= ctx %>/admin/createAccount.jsp">Create New Account</a></p>
    <% List accounts = (List) request.getAttribute("accounts"); if (accounts != null && !accounts.isEmpty()) { %>
    <table class="data-table">
        <tr><th>ID</th><th>User ID</th><th>Account Number</th><th>Balance</th></tr>
        <% for (Object o : accounts) { Account acc = (Account) o; %>
        <tr><td><%= acc.getId() %></td><td><%= acc.getUserId() %></td><td><%= acc.getAccountNumber() %></td><td><%= acc.getBalance() %></td></tr>
        <% } %>
    </table>
    <% } else { %><p>No accounts found.</p><% } %>
</div>
<jsp:include page="/common/footer.jsp"/>
</body></html>
