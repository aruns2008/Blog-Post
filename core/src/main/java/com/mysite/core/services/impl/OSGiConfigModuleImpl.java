package com.mysite.core.services.impl;


import com.mysite.core.config.BlogOSGiConfig;
import com.mysite.core.services.OSGiConfigModule;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = OSGiConfigModule.class,immediate = true)
@Designate(ocd = BlogOSGiConfig.class)
public class OSGiConfigModuleImpl implements OSGiConfigModule{
    private String apiKey;
    private String senderEmailAddress;
    private String endPoint;
    private String receiverAddress;
    private String parentPagePath;


    @Activate
    protected void activate(BlogOSGiConfig blogOSGiConfig){
        apiKey=blogOSGiConfig.apiKey();
        senderEmailAddress=blogOSGiConfig.senderEmailAddress();
        endPoint=blogOSGiConfig.endPoint();
        receiverAddress=blogOSGiConfig.receiverAddress();
        parentPagePath=blogOSGiConfig.parentPagePath();
    }

    @Override
    public String getApiKey() {
        return apiKey;
    }
    @Override
    public String getSenderEmailAddress() {
        return senderEmailAddress;
    }
    @Override
    public String getEndPoint() {
        return endPoint;
    }
    @Override
    public String getReceiverAddress() {
        return receiverAddress;
    }
    @Override
    public String getParentPagePath() {
        return parentPagePath;
    }
}