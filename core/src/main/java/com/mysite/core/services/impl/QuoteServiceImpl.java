package com.mysite.core.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysite.core.listeners.PageEventHandler;
import com.mysite.core.services.CreateNodeService;
import com.mysite.core.services.QuoteService;
import com.mysite.core.services.QuotesConfigModule;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component(service = QuoteService.class)

public class QuoteServiceImpl implements QuoteService {

    private static final Logger LOG = LoggerFactory.getLogger(PageEventHandler.class);
    @Reference
    private CreateNodeService createNodeService;
    @Reference
    QuotesConfigModule quotesConfigModule;
    Node createdNode;
    @Override
    public List<String> fetchQuotes(ResourceResolver resourceResolver, int numberOfQuotes, String pagePath) {
        String currentPagePath = pagePath;
        LOG.debug(currentPagePath);
        List<String> quotes = new ArrayList<>();
        try {
            String apiUrl = quotesConfigModule.getQuotesApi();
            // Create the URL object
            URL url = new URL(apiUrl);

            // Create the HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {

                // Create a BufferedReader to read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                // Parse the JSON response
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.toString());

                if (jsonNode.isObject()) {
                    JsonNode quotesNode = jsonNode.get("quotes");
                    if (quotesNode != null && quotesNode.isArray()) {


                        for (JsonNode quoteNode : quotesNode) {

                            String quote = quoteNode.get("quote").asText();
                            String author = quoteNode.get("author").asText();
                            String quoteWithAuthor = quote + " - " + author;
                            quotes.add(quoteWithAuthor);
                        }
                    }
                }
                createdNode = createNodeService.createNode(resourceResolver, Collections.singletonMap("quotes", quotes),numberOfQuotes,currentPagePath);
            } else {
                LOG.debug("Request failed. Response Code: " + responseCode);
            }
            connection.disconnect();
        } catch (IOException e) {
            LOG.debug("Error occurred during API call", e);
            e.printStackTrace();
        }

        return quotes;
    }
    @Override
    public Node getCreatedNode() {
        return createdNode;
    }
}