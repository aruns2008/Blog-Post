package com.mysite.core.models.impl;

import com.mysite.core.models.Header;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;

@Model(adaptables = Resource.class,
        adapters = Header.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class HeaderImpl implements Header {
     @Inject
     @Default(values = "Blogs")
     String headerTitle;

    @Inject
    String icon1Text;
    @Inject
    String icon1Link;
    @Inject
    String icon2Text;
    @Inject
    String icon2Link;
    @Inject
    String headerBackgroundImage;


    @Override
    public String getTitle() {
        return headerTitle;
    }

    @Override
    public String getHeaderBackgroundImage() {
        String image = headerBackgroundImage;
        return image;
    }

    @Override
    public String getIcon1Text() {
        return icon1Text;
    }
    @Override
    public String getIcon1Link() {
        return icon1Link;
    }
    @Override
    public String getIcon2Text() {
        return icon2Text;
    }
    @Override
    public String getIcon2Link() {
        return icon2Link;
    }
}
