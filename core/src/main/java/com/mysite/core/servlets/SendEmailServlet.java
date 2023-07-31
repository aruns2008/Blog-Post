package com.mysite.core.servlets;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mysite.core.services.OSGiConfigModule;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;


@Component(
        service = Servlet.class,immediate = true,
        property = {
                "sling.servlet.methods=" + HttpConstants.METHOD_POST,
                "sling.servlet.paths="+"/bin/training/testservlet"
        }
)

public class SendEmailServlet extends SlingAllMethodsServlet{
    public static final long serialVersionUID = 1L;
    @Reference
    OSGiConfigModule osGiConfigModule;


    private static final Logger logger = LoggerFactory.getLogger(SendEmailServlet.class);

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)throws ServletException, IOException {
        logger.debug("inside the get");
        final Resource resource = req.getResource();
        resp.setContentType("text/plain");
        resp.getWriter().write("success");
    }
    @Override
    protected void doPost(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)throws ServletException, IOException {
        String endpoint = osGiConfigModule.getEndPoint();
        String API_KEY = osGiConfigModule.getApiKey();
        String toEmail = req.getParameter("email");
        String emailSubject = req.getParameter("subject");
        String body = req.getParameter("body");
        String fromEmail =osGiConfigModule.getSenderEmailAddress();
        JsonObject emailJson = new JsonObject();
        JsonObject from = new JsonObject();
        JsonArray content = new JsonArray();
        JsonArray to = new JsonArray();

        //sub objects
        JsonObject toObj = new JsonObject();
        JsonObject toEmailList = new JsonObject();
        toEmailList.addProperty("email",toEmail);
        JsonArray toArr = new JsonArray();
        toArr.add(toEmailList);
        toObj.add("to",toArr);
        to.add(toObj);
        from.addProperty("email",fromEmail);
        JsonObject contentObj = new JsonObject();
        contentObj.addProperty("type","text/html");
        contentObj.addProperty("value",body);
        content.add(contentObj);
        emailJson.add("personalizations",to);
        emailJson.add("from",from);
        emailJson.addProperty("subject",emailSubject);
        emailJson.add("content",content);

        try {
            HttpResponse emailResponse = Request.Post(endpoint)
                    .connectTimeout(5000)
                    .socketTimeout(5000)
                    .addHeader("Authorization","Bearer "+API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .bodyString(emailJson.toString(), ContentType.APPLICATION_JSON)
                    .execute().returnResponse();
            int statusCode = emailResponse.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode < 300) {
                logger.debug("Email sent successfully.");
                req.setAttribute("emailSent", "true");
                String encodedValue = URLEncoder.encode("true", "UTF-8");
                Cookie cookie = new Cookie("message", encodedValue);
                cookie.setMaxAge(86400);
                resp.addCookie(cookie);
                resp.sendRedirect("/content/blogpost/us/en.html?emailSent=true");
                resp.setContentType("text/html");
                RequestDispatcher view = req.getRequestDispatcher("/content/blogpost/us/en.html");
                view.forward(req, resp);
            } else {
                logger.debug("Failed to send email. Status code: " + statusCode);
                req.setAttribute("emailSent", "false");
                resp.sendRedirect("/content/blogpost/us/en.html?emailSent=false");
                RequestDispatcher view = req.getRequestDispatcher("/content/blogpost/us/en.html");
                view.forward(req, resp);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


