package com.mysite.core.models;



import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

@Model(adaptables = { SlingHttpServletRequest.class },resourceType = BenefitsModel.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BenefitsModel {

    public static final String RESOURCE_TYPE = "/apps/blogpost/components/benefits";
    @ValueMapValue(name = "sectionHeading")
    private String sectionHeading;
    @ValueMapValue(name = "footerHeading")
    private String footerHeading;

    @ChildResource(name = "benefits")
    private List<Benefits> benefits;
    
    public String getSectionHeading() {
        return sectionHeading;
    }

    public String getFooterHeading() {
        return footerHeading;
    }

    public List<Benefits> getBenefits() {
        return benefits;
    }

}