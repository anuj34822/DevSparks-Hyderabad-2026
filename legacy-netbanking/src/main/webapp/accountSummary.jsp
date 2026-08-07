<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.bank.dao.Account" %>
<%@ page import="java.util.List" %>
<%
    String ctx = request.getContextPath();
%>
<html>
<head>
    <title>Account Summary - ABC Bank</title>
    <link rel="stylesheet" type="text/css" href="<%= ctx %>/css/style.css"/>
</head>
<body>
<jsp:include page="/common/header.jsp"/>
<div class="content">
    <h2>Account Summary</h2>
    <%
        String error = (String) request.getAttribute("error");
        if (error != null) { %><p class="error-msg"><%= error %></p><% }
        List accounts = (List) request.getAttribute("accounts");
        if (accounts != null && !accounts.isEmpty()) {
    %>
    <table class="data-table">
        <tr><th>Account Number</th><th>Balance</th></tr>
    <%
        java.math.BigDecimal total = java.math.BigDecimal.ZERO;
        for (Object o : accounts) {
            Account acc = (Account) o;
            total = total.add(acc.getBalance());
    %>
        <tr><td><%= acc.getAccountNumber() %></td><td><%= acc.getBalance() %></td></tr>
    <%  } %>
        <tr><td><b>Total</b></td><td><b><%= total %></b></td></tr>
    </table>
    <% } else { %><p>No accounts found.</p><% } %>
</div>
<jsp:include page="/common/footer.jsp"/>
</body>
</html>
