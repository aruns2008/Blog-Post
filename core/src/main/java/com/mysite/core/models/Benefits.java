package com.mysite.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Benefits {
    @ValueMapValue(name = "benefitsHeading")
    private String benefitsHeading;

    @ValueMapValue(name = "benefitsText")
    private String benefitsText;

    @ValueMapValue(name = "benefitsButton")
    private String benefitsButton;

    @ValueMapValue(name = "benefitsIcon")
    private String benefitsIcon;


    public String getBenefitsHeading() {
        return benefitsHeading;
    }

    public String getBenefitsText() {
        return benefitsText;
    }

    public String getBenefitsIcon() {
        return benefitsIcon;
    }

    public String getBenefitsButton() {
        return benefitsButton;
    }
}
