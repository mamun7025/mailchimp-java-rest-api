package com.mamun25dev.mailchimpspringboot.controller;


import com.mamun25dev.mailchimpspringboot.model.CampaignCreateRequest;
import com.mamun25dev.mailchimpspringboot.service.MailChimpService;
import com.mamun25dev.mailchimpspringboot.service.PriceDropMarketingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/mailchimp/api/v1")
public class MailChimpApiController {

    private static final Logger logger = LoggerFactory.getLogger(MailChimpApiController.class);

    @Autowired
    MailChimpService mailChimpService;
    @Autowired
    PriceDropMarketingService priceDropMarketingService;

    @PostMapping("/get-subscribe-members")
    public ResponseEntity<?> getSubscribeMembers(){
        return this.mailChimpService.getSubscriberMembers();
    }

    @PostMapping("/create-audience-list")
    public ResponseEntity<?> createAudienceList(@RequestBody Map<String, String> reqBody, HttpServletRequest servletRequest){
        //return this.priceDropMarketingService.createAudienceList(reqBody, servletRequest);
        return null;
    }

    @PostMapping("/create-segment")
    public ResponseEntity<?> createNewSegments(@RequestBody Map<String, ArrayList<String>> reqBody, HttpServletRequest servletRequest){
        return this.priceDropMarketingService.createNewSegments(reqBody, servletRequest);
    }

    @PostMapping("/create-campaign")
    public ResponseEntity<?> createCampaign(@RequestBody CampaignCreateRequest createRequestDTO, HttpServletRequest servletRequest){
        return this.priceDropMarketingService.createCampaign(createRequestDTO, servletRequest);
    }

    @PostMapping("/create-campaign-wishlist")
    public ResponseEntity<?> createCampaignWishList(@RequestBody Map<String, String> reqBody, HttpServletRequest servletRequest){
        return null;
    }


}
