package com.mysite.core.config;


import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name="Quotes OSGi Configuration",
        description = "Quotes OSGi Configuration For Quotes Component")
public @interface QuotesConfig {
    @AttributeDefinition(
            name = "Quotes Api",
            description = "Add Quotes API.",
            type = AttributeType.STRING
    )
    String quotesApi() default "https://dummyjson.com/docs/quotes";

    @AttributeDefinition(
            name = "Quotes Image Api",
            description = "Add Image API.",
            type = AttributeType.STRING
    )
    String quotesImageApi() default "https://pravatar.cc/, https://picsum.photos";

    @AttributeDefinition(
            name = "Number of Quotes",
            description = "Enter Number of quotes",
            type = AttributeType.STRING
    )
    String numberOfQuotes();

    @AttributeDefinition(
            name = "Node Path",
            description = "Enter the path where the node needs to be created",
            type = AttributeType.STRING
    )
    String nodePath();
    @AttributeDefinition(
            name = "Page Path",
            description = "Enter the path where the component used.",
            type = AttributeType.STRING
    )
    String pagePath();
    @AttributeDefinition(
            name = "Service User",
            description = "Enter the name of the new service user",
            type = AttributeType.STRING
    )
    String serviceUser();
}
