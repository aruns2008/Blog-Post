package com.mysite.core.models;


import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model( adaptables= Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Slider {

    @ValueMapValue ( name = "sliderCardHeading" )
    private String sliderCardHeading;

    @ValueMapValue ( name = "sliderCardText" )
    private String sliderCardText;

    @ValueMapValue ( name = "sliderCardButtonText" )
    private String sliderCardButtonText;

    @ValueMapValue ( name = "sliderCardBackground" )
    private String sliderCardBackground;


    public String getSliderCardHeading () {
        return sliderCardHeading;
    }
    public String getSliderCardText() {
        return sliderCardText;
    }
    public String getSliderCardButtonText() {
        return sliderCardButtonText;
    }
    public String getSliderCardBackground() {
        return sliderCardBackground;
    }
}
