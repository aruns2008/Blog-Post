package com.mysite.core.services.impl;


import com.mysite.core.listeners.PageEventHandler;
import com.mysite.core.services.CreateNodeService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component(service = CreateNodeService.class)
public class CreateNodeImpl implements CreateNodeService {

    private static final Logger LOG = LoggerFactory.getLogger(PageEventHandler.class);
    private ResourceResolver createNodeResourceResolver;

    private ArrayList<String> quotes;
    private Node quotesNode;

    private int numberOfQuotesFromProperties;
    @Override
    public Node createNode(ResourceResolver resourceResolver, Map<String, Object> properties,int numberOfQuotes, String PagePath) {
        LOG.debug(PagePath);
        createNodeResourceResolver = resourceResolver;
        Node createdNode = null;
        numberOfQuotesFromProperties = numberOfQuotes;
        try {
            // Get the resource at the path "/content/blogpost/"
            Resource resource = resourceResolver.getResource(PagePath);

            if (resource != null) {
                // Adapt the resource to a Node`
                Node parentNode = resource.adaptTo(Node.class);

                    if (!parentNode.hasNode("quotes_content")) {
                        quotesNode = parentNode.addNode("quotes_content", "nt:unstructured");
                    } else {
                        quotesNode = parentNode.getNode("quotes_content");
                    }
                    if (properties.containsKey("quotes")) {
                        quotes = (ArrayList<String>) properties.get("quotes");
                        addQuotesToNode(quotesNode, quotes,numberOfQuotes);
                    }
                    // Save the changes to the repository
                    Session session = resourceResolver.adaptTo(Session.class);
                    session.save();

                    createdNode = quotesNode;
                }
        } catch (RepositoryException e) {
            // Handle the exception
            LOG.debug(String.valueOf(e));
            e.printStackTrace();
        }

        return createdNode;
    }

    public void addQuotesToNode(Node quotesNode, ArrayList<String> quotes, int numberOfQuotes) throws RepositoryException {
        Map<String, Object> result = new HashMap<>();
        int existingNumberOfQuotes = getNumberOfQuotesFromParentNode(quotesNode);

        if (numberOfQuotes != existingNumberOfQuotes) {

            quotesNode.setProperty("quotesList", quotes.toArray(new String[0]));
            // Clear existing quotes if the user-entered value has changed
            clearExistingQuotes(quotesNode);

            // Update the number of quotes in the parent node
            updateNumberOfQuotesInParentNode(quotesNode, numberOfQuotes);

            int totalQuotes = quotes.size();

            for (int i = 0; i < numberOfQuotes; i++) {
                int quoteIndex = i % totalQuotes;
                String quote = quotes.get(quoteIndex);

                // Split the quote into the quote and author
                String[] parts = quote.split(" - ", 2);
                String quoteText = parts[0];
                String author = parts[1];

                // Create a child node for the quote
                Node quoteNode = quotesNode.addNode("quote_" + (i + 1), "nt:unstructured");

                // Set the quote and author as properties
                quoteNode.setProperty("quote", quoteText);
                quoteNode.setProperty("author", author);
            }
            // Store the values in the result map
            result.put("existingNumberOfQuotes", existingNumberOfQuotes);
            result.put("quotes", quotes);
        }
    }

    private int getNumberOfQuotesFromParentNode(Node quotesNode) throws RepositoryException {
        if (quotesNode.hasProperty("numberOfQuotes")) {
            return Math.toIntExact(quotesNode.getProperty("numberOfQuotes").getLong());
        } else {
            return 0; // Default value if the property is not found
        }
    }

    private void updateNumberOfQuotesInParentNode(Node quotesNode, int numberOfQuotes) throws RepositoryException {
        quotesNode.setProperty("numberOfQuotes", numberOfQuotes);
    }

    private void clearExistingQuotes(Node quotesNode) throws RepositoryException {
        NodeIterator nodeIterator = quotesNode.getNodes();

        while (nodeIterator.hasNext()) {
            Node quoteNode = nodeIterator.nextNode();
            if (quoteNode.getName().startsWith("quote_")) {
                quoteNode.remove();
            }
        }
    }

//    public void scheduleQuotesUpdate(){
//        LOG.debug("Start Exicuting");
//        try {
//            LOG.debug("inside try");
//            Resource resource = createNodeResourceResolver.getResource("/content/blogpost/");
//            LOG.debug(resource.getName());
//            if (resource != null) {
//                // Adapt the resource to a Node
//                Node parentNode = resource.adaptTo(Node.class);
//                if (parentNode.hasNode("quotes")) {
//                    quotesNode = parentNode.getNode("quotes");
//                } else {
//                    LOG.debug("Node is not available now !");
//                }
//                LOG.debug(String.valueOf(quotesNode));
//                LOG.debug(String.valueOf(quotes));
//                LOG.debug(String.valueOf(numberOfQuotesFromProperties));
//                addQuotesToNode(quotesNode, quotes,numberOfQuotesFromProperties);
//                // Save the changes to the repository
//                Session session = createNodeResourceResolver.adaptTo(Session.class);
//                session.save();
//            }
//        } catch (RepositoryException e) {
//            // Handle the exception
//            LOG.debug(String.valueOf(e));
//            e.printStackTrace();
//        }
//    }

}
