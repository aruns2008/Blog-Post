package com.mysite.core.models;


import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CarouselNewModelMultifield {
    @ValueMapValue(name = "carouselHeading")
    private String carouselHeading;
    @ValueMapValue(name = "carouselMainHeading")
    private String carouselMainHeading;

    @ValueMapValue(name = "carouselText")
    private String carouselText;

    @ValueMapValue(name = "buttonText")
    private String buttonText;

    @ValueMapValue(name = "carouselBackground")
    private String carouselBackground;

    public String getCarouselHeading() {
        return carouselHeading;
    }

    public String getCarouselMainHeading() {
        return carouselMainHeading;
    }

    public String getCarouselText() {
        return carouselText;
    }

    public String getButtonText() {
        return buttonText;
    }

    public String getCarouselBackground() {
        return carouselBackground;
    }
}
