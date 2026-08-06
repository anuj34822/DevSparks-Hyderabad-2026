<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.bank.dao.Account,java.util.List" %>
<%
    String ctx = request.getContextPath();
%>
<html>
<head>
    <title>Fund Transfer - ABC Bank</title>
    <link rel="stylesheet" type="text/css" href="<%= ctx %>/css/style.css"/>
</head>
<body>
<jsp:include page="/common/header.jsp"/>
<div class="content">
    <h2>Fund Transfer</h2>
    <%
        String error = (String) request.getAttribute("error");
        if (error != null) { %><p class="error-msg"><%= error %></p><% }
        List accounts = (List) request.getAttribute("accounts");
        if (accounts == null || accounts.isEmpty()) { %><p>No accounts available for transfer.</p>
    <% } else { %>
    <form action="<%= ctx %>/transfer.do" method="post">
        <table class="form-table" border="0">
            <tr><td>From Account:</td><td><select name="fromAccountId" required>
                <option value="">-- Select --</option>
                <% for (Object o : accounts) { Account acc = (Account) o; %>
                <option value="<%= acc.getId() %>"><%= acc.getAccountNumber() %> (Balance: <%= acc.getBalance() %>)</option>
                <% } %>
            </select></td></tr>
            <tr><td>To Account Number:</td><td><input type="text" name="toAccountNumber" size="20" required/></td></tr>
            <tr><td>Amount:</td><td><input type="text" name="amount" size="15" required/></td></tr>
            <tr><td>Description:</td><td><input type="text" name="description" size="40"/></td></tr>
            <tr><td></td><td><input type="submit" value="Transfer" class="btn"/></td></tr>
        </table>
    </form>
    <% } %>
</div>
<jsp:include page="/common/footer.jsp"/>
</body>
</html>
