<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String ctx = request.getContextPath();
%>
<html>
<head><title>Admin - Create User - ABC Bank</title><link rel="stylesheet" type="text/css" href="<%= ctx %>/css/style.css"/></head>
<body>
<jsp:include page="/common/header.jsp"/>
<div class="content">
    <h2>Create New User</h2>
    <% String error = (String) request.getAttribute("error"); if (error != null) { %><p class="error-msg"><%= error %></p><% } %>
    <form action="<%= ctx %>/adminCreateUser.do" method="post">
        <table class="form-table" border="0">
            <tr><td>Username:</td><td><input type="text" name="username" size="20" required/></td></tr>
            <tr><td>Password:</td><td><input type="password" name="password" size="20" required/></td></tr>
            <tr><td>Role:</td><td><select name="role"><option value="USER">USER</option><option value="ADMIN">ADMIN</option></select></td></tr>
            <tr><td></td><td><input type="submit" value="Create User" class="btn"/></td></tr>
        </table>
    </form>
</div>
<jsp:include page="/common/footer.jsp"/>
</body></html>
