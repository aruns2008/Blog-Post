package com.mysite.core.services.impl;

import com.mysite.core.config.QuotesConfig;
import com.mysite.core.services.QuotesConfigModule;
import opennlp.tools.util.featuregen.StringPattern;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = QuotesConfigModule.class,immediate = true)
@Designate(ocd = QuotesConfig.class)

public class QuotesConfigModuleImpl implements QuotesConfigModule {
    private String quotesApi;
    private String quotesImageApi;

    private String numberOfQuotes;
    private String pagePath;
    private String nodePath;

    private String serviceUser;
    @Activate
    protected void activate(QuotesConfig quotesConfig){
        quotesApi = quotesConfig.quotesApi();
        quotesImageApi = quotesConfig.quotesImageApi();
        numberOfQuotes = quotesConfig.numberOfQuotes();
        pagePath = quotesConfig.pagePath();
        nodePath = quotesConfig.nodePath();
        serviceUser = quotesConfig.serviceUser();

    }

    @Override
    public String getQuotesApi() {
        return quotesApi;
    }

    @Override
    public String getQuotesImageApi() {
        return quotesImageApi;
    }

    @Override
    public String getNumberOfQuotes() {
        return numberOfQuotes;
    }

    @Override
    public String getQuotesPagePath(){
        return pagePath;
    }
    @Override
    public String getQuotesNodePath(){
        return nodePath;
    }

    @Override
    public  String getServiceUser(){
        return serviceUser;
    }
}
