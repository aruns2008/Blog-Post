package com.mysite.core.services;

import org.apache.sling.api.resource.ResourceResolver;

import javax.jcr.Node;
import java.util.List;

public interface QuoteService {
    List<String> fetchQuotes(ResourceResolver resourceResolver, int numberOfQuotes, String pagePath);
    Node getCreatedNode();
}
