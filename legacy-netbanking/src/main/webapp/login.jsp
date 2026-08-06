<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String ctx = request.getContextPath();
%>
<html>
<head>
    <title>Login - ABC Bank</title>
    <link rel="stylesheet" type="text/css" href="<%= ctx %>/css/style.css"/>
</head>
<body>
<div class="login-page">
    <h1>ABC Bank</h1>
    <h2>Please sign in to Internet Banking</h2>
    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
    <p class="error-msg"><%= error %></p>
    <%
        }
    %>
    <form action="<%= ctx %>/login.do" method="post">
        <table class="form-table" border="0">
            <tr><td>Username:</td><td><input type="text" name="username" size="20"/></td></tr>
            <tr><td>Password:</td><td><input type="password" name="password" size="20"/></td></tr>
            <tr><td></td><td><input type="submit" value="Login" class="btn"/></td></tr>
        </table>
    </form>
    <p class="init-link"><a href="<%= ctx %>/init">Initialize Database</a> (run once on first setup)</p>
</div>
</body>
</html>
