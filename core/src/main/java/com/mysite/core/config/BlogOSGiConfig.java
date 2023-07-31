package com.mysite.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;


@ObjectClassDefinition(name="Email OSGi Configuration",
        description = "Email OSGi Configuration For Event Listeners")
public @interface BlogOSGiConfig {
    @AttributeDefinition(
            name = "Api Key",
            description = "Enter the API Key",
            type = AttributeType.STRING)
        String apiKey();
    @AttributeDefinition(
            name = "Email Receiver",
            description = "Enter Receiver Address",
            type = AttributeType.STRING)
    String receiverAddress();

    @AttributeDefinition(
            name = "Parent Page Path",
            description = "Enter the Parent Page Path",
            type = AttributeType.STRING)
    String parentPagePath();
    @AttributeDefinition(
            name = "Sender Address",
            description = "Enter Sender Address.",
            type = AttributeType.STRING
    )
    String senderEmailAddress() default "aukitestmail@gmail.com";
    @AttributeDefinition(
            name = "Sender EndPoint",
            description = "Add EndPoint.",
            type = AttributeType.STRING
    )
    String endPoint() default "https://api.sendgrid.com/v3/mail/send";
}