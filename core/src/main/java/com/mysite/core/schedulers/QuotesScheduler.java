package com.mysite.core.schedulers;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.mysite.core.listeners.PageEventHandler;
import com.mysite.core.services.CreateNodeService;
import com.mysite.core.services.QuoteService;
import com.mysite.core.services.QuotesConfigModule;
import org.apache.sling.api.resource.*;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(immediate = true, service = Runnable.class)
public class QuotesScheduler implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(PageEventHandler.class);

    @Reference
    QuotesConfigModule quotesConfigModule;

    @Reference
    CreateNodeService createNodeService;
    @Reference
    private QueryBuilder queryBuilder;
    private static final String SCHEDULER_NAME = "quotesScheduler";
    private int numberOfQuotes;
    private int previousRefreshInterval = 5;

    private Node quotesNode;
    private ArrayList<String> quotes = new ArrayList<>();
    @Reference
    private Scheduler scheduler;

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Reference
    QuoteService quoteService;


    @Activate
    protected void activate() throws LoginException {
        LOG.debug("QuotesScheduler activated");
        LOG.debug("cronExpression");

        String serviceUser = quotesConfigModule.getServiceUser();
        try {
            ScheduleOptions scheduleOptions = scheduler.EXPR("0/"+previousRefreshInterval+" * * * * ?");
            scheduleOptions.name(SCHEDULER_NAME);
            scheduleOptions.canRunConcurrently(false);
            scheduler.schedule(this, scheduleOptions);

        } catch (Exception e) {
            LOG.debug("Error scheduling QuotesScheduler", e);
        }
    }

    @Override
    public void run() {
            String serviceUser = quotesConfigModule.getServiceUser();
            try {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put(ResourceResolverFactory.SUBSERVICE, serviceUser);
                ResourceResolver resolver = resolverFactory.getServiceResourceResolver(param);
                Map<String, String> queryParams = new HashMap<>();
                queryParams.put("type", "nt:unstructured");
                queryParams.put("path", "/content");
                queryParams.put("property", "sling:resourceType");
                queryParams.put("property.value", "blogpost/components/quotes");
                queryParams.put("p.limit", "-1");

// Create the Query object
                Query query = queryBuilder.createQuery(PredicateGroup.create(queryParams), resolver.adaptTo(Session.class));

                SearchResult result = query.getResult();
                List<String> pagePaths = new ArrayList<>();
                for (Hit hit : result.getHits()) {
                    String path = hit.getPath();
                    Resource pageResource = resolver.getResource(path);
                    if (pageResource != null) {
                        int refreshInterval = pageResource.getValueMap().get("numberOfQuotes",Integer.class);
//                        LOG.debug(String.valueOf(refreshInterval));
                    }
//                    LOG.debug(path);
                    pagePaths.add(path);
                }

                 //Getting Component
                Resource pageResource = resolver.getResource(quotesConfigModule.getQuotesPagePath());
                ValueMap properties = pageResource.getValueMap();
                int currentRefreshInterval = Integer.parseInt(properties.get("refreshInterval", String.class));
                numberOfQuotes = Integer.parseInt(properties.get("numberOfQuotes",String.class));
                Resource nodeResource = resolver.getResource(quotesConfigModule.getQuotesNodePath());
//                LOG.debug(nodeResource.getName());
                if (nodeResource != null) {
                    // Adapt the resource to a Node`
                    Node parentNode = nodeResource.adaptTo(Node.class);
//                    LOG.debug(parentNode.getName());
                    if (parentNode.hasNode("quotes_data")) {
                        quotesNode = parentNode.getNode("quotes_data");
//                        LOG.debug(quotesNode.getName());
                    }
                    if (properties.containsKey("quotes")) {
                        quotes = (ArrayList<String>) properties.get("quotes");
//                        LOG.debug(String.valueOf(quotes));
//                       createNodeService.addQuotesToNode(quotesNode, quotes,numberOfQuotes);
                    }}

//                createNodeService.scheduleQuotesUpdate(pageResource,numberOfQuotes);
                if (currentRefreshInterval != previousRefreshInterval) {
                    // Re-run the activate method with the new refreshInterval
                    activate();
                    // Update the previousRefreshInterval with the current value
                    previousRefreshInterval = currentRefreshInterval;
                }
        } catch (Exception e) {
            LOG.debug("Error executing QuotesScheduler", e);
        }
    }
}
