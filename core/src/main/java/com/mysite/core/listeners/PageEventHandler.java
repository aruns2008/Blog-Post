package com.mysite.core.listeners;

import com.day.cq.wcm.api.PageEvent;
import com.day.cq.wcm.api.PageModification;
import com.mysite.core.services.EmailService;
import com.mysite.core.services.OSGiConfigModule;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

@Component(service = EventHandler.class,
        immediate = true,
        property = {
                EventConstants.EVENT_TOPIC + "=" + PageEvent.EVENT_TOPIC,

        })
public class PageEventHandler implements EventHandler {
    @Reference
    OSGiConfigModule osGiConfigModule;


    private static final Logger LOG = LoggerFactory.getLogger(PageEventHandler.class);
    public void handleEvent(final Event event)  {
//        LOG.debug(osGiConfigModule.getApiKey());
            try {
                Iterator<PageModification> pageInfo=PageEvent.fromEvent(event).getModifications();
                while (pageInfo.hasNext()){
                    PageModification pageModification=pageInfo.next();

                    if (pageModification.getPath().startsWith(osGiConfigModule.getParentPagePath())) {
                        // The current page is under "/content/blogpost/us/en/blog-page"
//                        LOG.debug("The current page is under"+ osGiConfigModule.getParentPagePath());
                        String modificationType = pageModification.getType().toString();
                        if (modificationType.equals("PageCreated")) {
//                            LOG.debug("inside the PAGE_CREATED");
                            handlePageCreation(pageModification);
                        } else if (modificationType.equals("PageDeleted")) {
//                            LOG.debug("inside the PAGE_DELETED");
                            handlePageDeletion(pageModification);
                        } else if (modificationType.equals("PageModified")) {
//                            LOG.debug("inside the PAGE_MODIFIED");
                            handlePageModification(pageModification);
                        } else {
//                            LOG.debug("Unknown page modification type: " + pageModification.getType());
                        }

                    } else {
                        // The current page is not under "/content/blogpost/us/en/blog-page"
//                        LOG.debug("The current page is not under "+ osGiConfigModule.getParentPagePath());
                    }

//                    LOG.debug("\n Type :  {},  Page : {}",pageModification.getType(),pageModification.getPath());
                    pageModification.getEventProperties().forEach((k,v)->LOG.debug("\n key : {}, Value : {} " , k , v));
                }
            }catch (Exception e){
//                LOG.debug("\n Error while Activating/Deactivating - {} " , e.getMessage());
            }

    }

    private void handlePageModification(PageModification pageModification, String emailSubject) {
        String body = "<html><body>";
        body += "<p>A page has been " + emailSubject.toLowerCase() + ":</p>";
        body += "<p><strong>Type:</strong> " + pageModification.getType() + "</p>";
        body += "<p><strong>Path:</strong> " + pageModification.getPath() + "</p>";
        body += "<p><strong>Modified Time:</strong> " + pageModification.getEventProperties().get("modifiedDate") + "</p>";
        body += "<p><strong>User Name:</strong> " + pageModification.getEventProperties().get("userId") + "</p>";
        body += "</body></html>";
        // emailService.sendEmail(emailSubject, body);
    }

    private void handlePageCreation(PageModification pageModification) {
        handlePageModification(pageModification, "Page Created");
    }

    private void handlePageDeletion(PageModification pageModification) {
        handlePageModification(pageModification, "Page Deleted");
    }

    private void handlePageModification(PageModification pageModification) {
        handlePageModification(pageModification, "Page Modified");
    }

}