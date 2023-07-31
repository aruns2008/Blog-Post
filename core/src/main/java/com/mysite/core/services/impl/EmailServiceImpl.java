package com.mysite.core.services.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mysite.core.listeners.PageEventHandler;
import com.mysite.core.services.EmailService;
import com.mysite.core.services.OSGiConfigModule;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.Subject;
import java.io.IOException;

@Component(service = EmailService.class)
public class EmailServiceImpl implements EmailService {

    private String subject;
    private String body;
    @Reference
    OSGiConfigModule osGiConfigModule;

    Logger LOG = LoggerFactory.getLogger(EmailServiceImpl.class);
    @Override
    public void sendEmail(String Subject, String Body) {
        subject = Subject;
        body = Body;

        JsonObject emailJson = new JsonObject();
        String endpoint = osGiConfigModule.getEndPoint();
        String API_KEY = osGiConfigModule.getApiKey();
        String toEmail = osGiConfigModule.getReceiverAddress();
        String fromEmail = osGiConfigModule.getSenderEmailAddress();

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
        emailJson.addProperty("subject",subject);
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
                LOG.debug("Email sent successfully.");

            } else {
                LOG.debug("Failed to send email. Status code: " + statusCode);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
