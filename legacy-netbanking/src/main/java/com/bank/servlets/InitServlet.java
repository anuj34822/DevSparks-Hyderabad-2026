package com.bank.servlets;

import com.bank.util.DatabaseInitializer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class InitServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        try {
            DatabaseInitializer.initialize();
            out.println("<html><body><h2>ABC Bank - Database initialized successfully.</h2>");
            out.println("<a href=\"" + req.getContextPath() + "/login.jsp\">Go to Login</a></body></html>");
        } catch (Exception e) {
            out.println("<html><body><h2>Error: " + e.getMessage() + "</h2></body></html>");
            e.printStackTrace();
        }
    }
}
