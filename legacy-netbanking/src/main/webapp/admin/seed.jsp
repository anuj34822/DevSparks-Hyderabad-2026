<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String ctx = request.getContextPath();
%>
<html>
<head><title>Admin - Seed Data - ABC Bank</title><link rel="stylesheet" type="text/css" href="<%= ctx %>/css/style.css"/></head>
<body>
<jsp:include page="/common/header.jsp"/>
<div class="content">
    <h2>Database Seed</h2>
    <% String msg = (String) request.getAttribute("seedMessage"); if (msg != null) { %><p><%= msg %></p><% } %>
    <form action="<%= ctx %>/adminSeed.do" method="post">
        <input type="submit" value="Run Seed / Initialize" class="btn"/>
    </form>
    <p>This will create tables if they do not exist and insert sample data.</p>
</div>
<jsp:include page="/common/footer.jsp"/>
</body></html>
