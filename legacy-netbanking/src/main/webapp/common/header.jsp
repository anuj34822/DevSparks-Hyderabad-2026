<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.bank.dao.User" %>
<%
    User user = (User) session.getAttribute("user");
    String ctx = request.getContextPath();
%>
<div class="bank-header">
    <h1>ABC Bank</h1>
    <span class="tagline">Internet Banking</span>
</div>
<% if (user != null) { %>
<div class="bank-nav">
    <span class="user-info">Welcome, <%= user.getUsername() %></span>
    <a href="<%= ctx %>/accountSummary.do">Home</a>
    <a href="<%= ctx %>/accountSummary.do">Accounts</a>
    <a href="<%= ctx %>/transactionHistory.do">Transactions</a>
    <a href="<%= ctx %>/transferForm.do">Fund Transfer</a>
    <% if (user.isAdmin()) { %>
    <a href="<%= ctx %>/adminUsers.do">Admin: Users</a>
    <a href="<%= ctx %>/adminAccounts.do">Admin: Accounts</a>
    <a href="<%= ctx %>/adminSeed.do">Admin: Seed</a>
    <% } %>
    <a href="<%= ctx %>/logout">Logout</a>
</div>
<% } %>
