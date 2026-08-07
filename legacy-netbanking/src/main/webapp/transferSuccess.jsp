<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String ctx = request.getContextPath();
%>
<html>
<head>
    <title>Transfer Success - ABC Bank</title>
    <link rel="stylesheet" type="text/css" href="<%= ctx %>/css/style.css"/>
</head>
<body>
<jsp:include page="/common/header.jsp"/>
<div class="content">
    <h2>Transfer Successful</h2>
    <%
        Object amt = request.getAttribute("amount");
        Object toAcct = request.getAttribute("toAccountNumber");
    %>
    <p>Amount <b><%= amt != null ? amt : "" %></b> transferred to account <b><%= toAcct != null ? toAcct : "" %></b>.</p>
    <div class="back-links">
        <p><a href="<%= ctx %>/accountSummary.do">View Account Summary</a></p>
        <p><a href="<%= ctx %>/transferForm.do">Make Another Transfer</a></p>
    </div>
</div>
<jsp:include page="/common/footer.jsp"/>
</body>
</html>
