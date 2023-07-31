package com.mysite.core.models;



import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

@Model(adaptables = { SlingHttpServletRequest.class },resourceType = CarouselNewModel.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CarouselNewModel {

    public static final String RESOURCE_TYPE = "/apps/blogpost/components/carousel1";
    @ValueMapValue(name = "sectionHeading")
    private String sectionHeading;

    @ChildResource(name = "carousel")
    private List<CarouselNewModelMultifield> carousel;
    
    public String getSectionHeading() {
        return sectionHeading;
    }

    public List<CarouselNewModelMultifield> getCarousel() {
        return carousel;
    }

}