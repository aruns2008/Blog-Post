package com.mysite.core.models;

import com.day.cq.wcm.api.Page;
import com.mysite.core.listeners.PageEventHandler;
import com.mysite.core.services.ImageService;
import com.mysite.core.services.QuoteService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Model(adaptables = { SlingHttpServletRequest.class },resourceType = QuotesModel.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class QuotesModel {

    @Inject
    QuoteService quoteService;

    @Inject
    ImageService imageService;

    @Inject
    private Page currentPage;
    public static final String RESOURCE_TYPE = "/apps/blogpost/components/quotes";

    private static final Logger LOG = LoggerFactory.getLogger(PageEventHandler.class);

    private String currentPagePath;
    Node createdNode;
    List<Map<String, String>> quoteList = new ArrayList<>();

    List<String> imageList;
    @Inject
    @Self
    private SlingHttpServletRequest request;
    @ValueMapValue(name = "numberOfQuotes")
    private int numberOfQuotes;

    @ValueMapValue(name = "nextIcon")
    private String nextIcon;

    @ValueMapValue(name = "prevIcon")
    private String prevIcon;

    @ValueMapValue(name = "quoteColor")
    private String quoteColor;

    @ValueMapValue(name = "topBarColor")
    private String topBarColor;

    @ValueMapValue(name = "refreshInterval")

    private int refreshInterval;

    // Getters and setters for dialog fields

    public int getNumberOfQuotes() throws RepositoryException {
        Resource currentPagePath = request.getResource();
        ResourceResolver resourceResolver = request.getResourceResolver();
        List<String> quotes = quoteService.fetchQuotes(resourceResolver, numberOfQuotes,currentPagePath.getPath());
        List<String> images = imageService.fetchImages(numberOfQuotes); // Assuming you have a method to fetch the images

        createdNode = quoteService.getCreatedNode();
        if (createdNode != null) {
            NodeIterator nodeIterator = createdNode.getNodes();
            int index = 0; // Index for iterating through the images list
            while (nodeIterator.hasNext()) {
                Node quoteNode = nodeIterator.nextNode();
                String quoteText = quoteNode.getProperty("quote").getString();
                String author = quoteNode.getProperty("author").getString();
                String image = images.get(index); // Get the corresponding image for the current quote
                index++; // Increment the index for the next iteration
                // Replace the problematic characters with their correct counterparts
                quoteText = quoteText.replace("â€™", "'");
                author = author.replace("â€™", "'");
                // Encode the quote text and author using HTML entities
                Map<String, String> quoteMap = new HashMap<>();
                quoteMap.put("quote", quoteText);
                quoteMap.put("author", author);
                quoteMap.put("image", image); // Add the image to the quoteMap
                quoteList.add(quoteMap);
            }
        }
        request.setAttribute("quoteList", quoteList);
        return numberOfQuotes;
    }
    public String getNextIcon() {
        return nextIcon;
    }

    public String getPrevIcon() {
        return prevIcon;
    }

    public String getQuoteColor() {
        return quoteColor;
    }

    public String getTopBarColor() {
        return topBarColor;
    }

    public int getRefreshInterval() {
        ResourceResolver resourceResolver = request.getResourceResolver();
        return refreshInterval;
    }

    public List<Map<String, String>> getQuotes(){
        LOG.debug("Final Log");
        LOG.debug(String.valueOf(quoteList));
        return quoteList;
    }


}

