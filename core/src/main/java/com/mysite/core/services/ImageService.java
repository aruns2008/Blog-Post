package com.mysite.core.services;

import org.apache.sling.api.resource.ResourceResolver;

import java.util.List;

public interface ImageService {
    List<String> fetchImages(int numberOfQuotes);
}
