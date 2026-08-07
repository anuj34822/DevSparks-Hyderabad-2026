<%@ page contentType="text/html;charset=UTF-8" %>
<%
    if (session != null && session.getAttribute("user") != null) {
        response.sendRedirect(request.getContextPath() + "/accountSummary.do");
    } else {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
%>
