package com.mamun25dev.mailchimpspringboot.service;

import com.mamun25dev.mailchimpspringboot.model.CampaignCreateRequest;
import com.mamun25dev.mailchimpspringboot.model.CampaignCreateResponse;
import com.mamun25dev.mailchimpspringboot.model.generic.ResponseErrorModel;
import com.mamun25dev.mailchimpspringboot.templates.EmailTemplatesRepo;
import com.mamun25dev.mailchimpspringboot.utils.CommonHttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PriceDropMarketingService extends MailChimpService {

    private static final Logger logger = LoggerFactory.getLogger(PriceDropMarketingService.class);


    private final RestTemplate restTemplate;

    @Autowired
    public PriceDropMarketingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public Boolean validateCreateCampaign(CampaignCreateRequest createRequestDTO){
        String segmentId = createRequestDTO.getSegmentId();
        if(segmentId == null || segmentId.equals("")){
            throw new RuntimeException("SegmentId is mandatory!");
        }
        return true;
    }

    public Map<String, Object> setCampaignTitleAndData(CampaignCreateRequest createRequestDTO){
        ( (HashMap<String, Object>)this.campaignCreatePayloadTemplate.get("settings") ).put("subject_line", createRequestDTO.getSubjectLine());
        ( (HashMap<String, Object>)this.campaignCreatePayloadTemplate.get("settings") ).put("preview_text", createRequestDTO.getPreviewText());
        ( (HashMap<String, Object>)this.campaignCreatePayloadTemplate.get("settings") ).put("title",  createRequestDTO.getTitle());
        // recipients
        String segmentId = createRequestDTO.getSegmentId();
        if(segmentId != null){
            ( (HashMap<String, Object>)this.campaignCreatePayloadTemplate.get("recipients") ).put("segment_opts", new HashMap<>(){{
                put("saved_segment_id", Integer.parseInt(segmentId));
                put("match", "any");
                put("conditions", List.of(
                        new HashMap<>(){{
                            put("condition_type", "StaticSegment");
                            put("field", "static_segment");
                            put("op", "static_is");
                            put("value", Integer.parseInt(segmentId));
                        }}
                ));
            }});
        }
        return this.campaignCreatePayloadTemplate;
    }


    public ResponseEntity<?> sendCampaignMailToCustomer(CampaignCreateResponse cmpCreateResponse, Object updateResponseObj, CampaignCreateRequest createRequestDTO){
        String campaignId = cmpCreateResponse.getId();
        String apiURL = this.mailchimpApiUrl + "campaigns/"+campaignId+"/actions/send";

        HashMap<String, Object> payloadData = new HashMap<>();
//        payloadData.put("test_emails", List.of("mamun7025@gmail.com", "rita@gmail.com"));
//        payloadData.put("send_type", "html");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set("Authorization", "apikey " + this.mailchimpApiKey);

        HttpEntity<?> requestBody = new HttpEntity<>(payloadData, httpHeaders);
        ResponseEntity<Object> responseObj;
        try {
            logger.info("@apiURL {}", apiURL);
            responseObj = restTemplate.exchange(apiURL, HttpMethod.POST, requestBody, Object.class);
        } catch(HttpStatusCodeException e) {
            return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
        }

        if (responseObj.getStatusCode().equals(HttpStatus.OK)) {
            Object responseBody = responseObj.getBody();
            System.out.println(responseBody);
            if(responseBody != null){
                return ResponseEntity.status(HttpStatus.OK).body(responseBody);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(cmpCreateResponse);
    }

    public String getMailTemplateString(CampaignCreateRequest createRequestDTO){
        String tmpBody = EmailTemplatesRepo.demoTemp1;
        // process your HTML data
        // tmpBody = tmpBody.replaceAll("##CampaignTiyle##", "Discount on Lunch Meal 25th September only!");
        tmpBody = tmpBody.replaceAll("##CampaignTitle##", createRequestDTO.getCampaignTitle());
        // append wishList product for this customer
        // apply replace
        System.out.println(tmpBody);
        return tmpBody;
    }
    public ResponseEntity<?> updateCampaignContent_addingHTML(CampaignCreateResponse cmpCreateResponse, CampaignCreateRequest createRequestDTO){

        String campaignId = cmpCreateResponse.getId();
        String apiURL = this.mailchimpApiUrl + "campaigns/"+campaignId+"/content";
        String mailHTML_content = this.getMailTemplateString(createRequestDTO);

        HashMap<String, String> payloadData = new HashMap<>();
        payloadData.put("html", mailHTML_content);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set("Authorization", "apikey " + this.mailchimpApiKey);

        HttpEntity<?> requestBody = new HttpEntity<>(payloadData, httpHeaders);
        ResponseEntity<Object> responseObj;
        try {
            logger.info("@apiURL {}", apiURL);
            responseObj = restTemplate.exchange(apiURL, HttpMethod.PUT, requestBody, Object.class);
        } catch(HttpStatusCodeException e) {
            return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
        }

        if (responseObj.getStatusCode().equals(HttpStatus.OK)) {
            Object responseBody = responseObj.getBody();
            System.out.println(responseBody);
            if(responseBody != null){
                return this.sendCampaignMailToCustomer(cmpCreateResponse, responseBody, createRequestDTO);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(cmpCreateResponse);
    }


    public ResponseEntity<?> createCampaign(CampaignCreateRequest createRequestDTO, HttpServletRequest servletRequest) {
        // create segment first
        String segmentId = this.createNewSegmentsV2(createRequestDTO);
        createRequestDTO.setSegmentId(segmentId);
        // validate
        this.validateCreateCampaign(createRequestDTO);

        // process
        String apiURL = this.mailchimpApiUrl + "campaigns";

        Map<String, Object> payloadData = this.setCampaignTitleAndData(createRequestDTO);
        payloadData.put("content_type", "template");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set("Authorization", "apikey " + this.mailchimpApiKey);

        HttpEntity<?> requestBody = new HttpEntity<>(payloadData, httpHeaders);
        // ResponseEntity<Object> responseObj = restTemplate.exchange(apiURL, HttpMethod.POST, requestBody, Object.class);
        ResponseEntity<Object> responseObj;
        try {
            logger.info("@apiURL {}", apiURL);
            responseObj = restTemplate.exchange(apiURL, HttpMethod.POST, requestBody, Object.class);
        } catch(HttpStatusCodeException e) {
            return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
        }

        if (responseObj.getStatusCode().equals(HttpStatus.OK)) {
            Object responseBody = responseObj.getBody();
            System.out.println(responseBody);
            if(responseBody != null){
                CampaignCreateResponse cmpCreateResponse = CommonHttpUtil.convertToOriginalObject(responseBody, CampaignCreateResponse.class);
                 return updateCampaignContent_addingHTML(cmpCreateResponse, createRequestDTO);
                // return ResponseEntity.status(HttpStatus.OK).body(cmpResponse);
            }
        }
        logger.error("return error response...");
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseErrorModel(false, responseObj.getStatusCodeValue(), "Request fail", "", responseObj.getBody()));

    }



}
