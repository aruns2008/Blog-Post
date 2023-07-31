package com.mysite.core.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysite.core.listeners.PageEventHandler;
import com.mysite.core.services.ImageService;
import com.mysite.core.services.QuotesConfigModule;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component(service = ImageService.class)

public class ImageServiceImpl implements ImageService {

    private static final Logger LOG = LoggerFactory.getLogger(PageEventHandler.class);
    @Reference
    QuotesConfigModule quotesConfigModule;

    @Override
    public List<String> fetchImages(int numberOfQuotes) {
        List<String> imageList = new ArrayList<>();
        try {
            String apiUrl = quotesConfigModule.getQuotesImageApi();
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

                if (jsonNode.isArray()) {
                    int count = 0;
                    for (JsonNode imageNode : jsonNode) {
                        if (count >= numberOfQuotes) {
                            break; // Exit the loop if we have enough images
                        }
                        String imageUrl = imageNode.get("download_url").asText();
                        imageList.add(imageUrl);
                        count++;
                    }
                }
            } else {
                LOG.debug("Request failed. Response Code: " + responseCode);
            }
            connection.disconnect();
        } catch (IOException e) {
            LOG.debug("Error occurred during API call", e);
            e.printStackTrace();
        }

        return imageList;
    }

}
