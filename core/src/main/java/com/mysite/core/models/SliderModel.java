package com.mysite.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

@Model( adaptables = {SlingHttpServletRequest.class},resourceType = SliderModel.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SliderModel {
    public static final String RESOURCE_TYPE = "/apps/blogpost/components/slider";

    @ValueMapValue ( name = "sliderHeading" )
    private String sliderHeading;

    @ValueMapValue ( name = "sliderMainHeading" )
    private String sliderMainHeading;

    @ValueMapValue ( name = "sliderText" )
    private String sliderText;

    @ValueMapValue ( name = "footerHeading" )
    private String footerHeading;

    @ValueMapValue ( name = "footerButtonText" )
    private String footerButtonText;

    @ValueMapValue ( name = "sliderBackground" )
    private String sliderBackground;

    @ChildResource ( name = "slider" )
    private List<Slider> slider;

    public String getSliderMainHeading(){
        return sliderMainHeading;
    }

    public String getSliderHeading(){
        return sliderHeading;
    }

    public String getSliderText(){
        return sliderText;
    }

    public String getFooterHeading(){
        return footerHeading;
    }

    public String getFooterButtonText(){
        return footerButtonText;
    }

    public String getSliderBackground(){
        return sliderBackground;
    }

    public List<Slider> getSlider() {
        return slider;
    }
}
