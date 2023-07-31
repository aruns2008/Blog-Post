package com.mysite.core.services;

import org.apache.sling.api.resource.ResourceResolver;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.Map;

public interface CreateNodeService {
    Node createNode(ResourceResolver resourceResolver, Map<String, Object> properties, int numberOfQuotes,String currentPagePath);
    public void addQuotesToNode(Node quotesNode, ArrayList<String> quotes, int numberOfQuotes) throws RepositoryException;
}
