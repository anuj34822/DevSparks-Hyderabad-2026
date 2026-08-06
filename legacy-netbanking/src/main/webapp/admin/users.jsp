<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.bank.dao.User,java.util.List" %>
<%
    String ctx = request.getContextPath();
%>
<html>
<head><title>Admin - Users - ABC Bank</title><link rel="stylesheet" type="text/css" href="<%= ctx %>/css/style.css"/></head>
<body>
<jsp:include page="/common/header.jsp"/>
<div class="content">
    <h2>Admin - Manage Users</h2>
    <% String error = (String) request.getAttribute("error"); if (error != null) { %><p class="error-msg"><%= error %></p><% } %>
    <p><a href="<%= ctx %>/admin/createUser.jsp">Create New User</a></p>
    <% List users = (List) request.getAttribute("users"); if (users != null && !users.isEmpty()) { %>
    <table class="data-table">
        <tr><th>ID</th><th>Username</th><th>Role</th></tr>
        <% for (Object o : users) { User u = (User) o; %>
        <tr><td><%= u.getId() %></td><td><%= u.getUsername() %></td><td><%= u.getRole() != null ? u.getRole() : "" %></td></tr>
        <% } %>
    </table>
    <% } else { %><p>No users found.</p><% } %>
</div>
<jsp:include page="/common/footer.jsp"/>
</body></html>
