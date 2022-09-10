package com.mamun25dev.mailchimpspringboot.service;


import com.mamun25dev.mailchimpspringboot.model.CampaignCreateRequest;
import com.mamun25dev.mailchimpspringboot.model.generic.ResponseErrorModel;
import com.mamun25dev.mailchimpspringboot.utils.CommonHttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class MailChimpService {

    private static final Logger logger = LoggerFactory.getLogger(MailChimpService.class);

    @Value("${cms.mailchimp.apiurl}")
    protected String mailchimpApiUrl;
    @Value("${cms.mailchimp.apikey}")
    protected String mailchimpApiKey;
    @Value("${cms.mailchimp.audienceId}")
    protected String mailchimpAudienceId;

    @Autowired
    private RestTemplate restTemplate;


    protected Map<String, Object> campaignCreatePayloadTemplate = new HashMap<>(){{
        put("type", "regular");
        put("recipients", new HashMap<>(){{
            put("list_id", "958fe69af6");
        }});
        put("settings", new HashMap<>(){{
            put("subject_line", "Summer Sale");
            put("preview_text", "Summer Sale");
            put("title", "Test Title");
            put("from_name", "EKFC FoodCraft");
            put("reply_to", "mamun@theanalystbd.net");
            put("use_conversation", false);
            put("to_name", "");
            put("folder_id", "");
            put("authenticate", false);
            put("auto_footer", false);
            put("inline_css", false);
            put("auto_tweet", false);
            put("auto_fb_post", new ArrayList<>());
            put("fb_comments", false);
            put("template_id", 0);
        }});
        put("tracking", new HashMap<>(){{
            put("opens", true);
            put("html_clicks", true);
            put("text_clicks", true);
            put("goal_tracking", true);
            put("ecomm360", false);
            put("google_analytics", "");
            put("clicktale", "");
            put("salesforce", new HashMap<>(){{
                put("campaign", false);
                put("notes", false);
            }});
            put("capsule", new HashMap<>(){{
                put("notes", false);
            }});
        }});
        put("social_card", new HashMap<>(){{
            put("image_url", "");
            put("description", "");
            put("title", "");
        }});
        put("content_type", "template");
    }};


    public ResponseEntity<?> getSubscriberMembers() {

        String apiURL = this.mailchimpApiUrl + "lists/"+this.mailchimpAudienceId+"/members";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set("Authorization", "apikey " + this.mailchimpApiKey);

        HttpEntity<?> requestBody = new HttpEntity<>(httpHeaders);
        ResponseEntity<Object> responseObj = restTemplate.exchange(apiURL, HttpMethod.GET, requestBody, Object.class);
        logger.info("@apiURL {}", apiURL);

        if (responseObj.getStatusCode().equals(HttpStatus.OK)) {
            Object responseBody = responseObj.getBody();
            System.out.println(responseBody);
            if(responseBody != null){
                return ResponseEntity.status(HttpStatus.OK).body(responseBody);
            }
        }
        logger.error("return error response...");
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseErrorModel(false, responseObj.getStatusCodeValue(), "Request fail", "", responseObj.getBody()));

    }


    public String getUniqueGroupIdForSegments(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public ResponseEntity<?> createNewSegments(Map<String, ArrayList<String>> reqBody, HttpServletRequest servletRequest){
        // listId/audienceId
        String apiURL = this.mailchimpApiUrl + "lists/"+this.mailchimpAudienceId+"/segments";
        List<String> recipients = reqBody.get("recipients");

        HashMap<String, Object> payloadData = new HashMap<>();
        payloadData.put("name", "GroupUser_"+this.getUniqueGroupIdForSegments());
//        payloadData.put("static_segment", List.of("mamun7025@gmail.com", "rita@gmail.com"));
        payloadData.put("static_segment", recipients);

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
        logger.error("return error response...");
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseErrorModel(false, responseObj.getStatusCodeValue(), "Request fail", "", responseObj.getBody()));

    }

    public String createNewSegmentsV2(CampaignCreateRequest createRequestDTO){
        // listId/audienceId
        String apiURL = this.mailchimpApiUrl + "lists/"+this.mailchimpAudienceId+"/segments";
        List<String> recipients = createRequestDTO.getRecipients();

        HashMap<String, Object> payloadData = new HashMap<>();
        payloadData.put("name", "GroupUser_"+this.getUniqueGroupIdForSegments());
        payloadData.put("static_segment", recipients);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set("Authorization", "apikey " + this.mailchimpApiKey);

        HttpEntity<?> requestBody = new HttpEntity<>(payloadData, httpHeaders);
        ResponseEntity<Object> responseObj;
        try {
            logger.info("@apiURL {}", apiURL);
            responseObj = restTemplate.exchange(apiURL, HttpMethod.POST, requestBody, Object.class);
        } catch(HttpStatusCodeException e) {
            throw new RuntimeException("Fail to create new segments");
        }

        if (responseObj.getStatusCode().equals(HttpStatus.OK)) {
            Object responseBody = responseObj.getBody();
            if(responseBody != null){
                CommonHttpUtil.printPrettyJson(responseBody);
                if (responseBody instanceof LinkedHashMap) {
                    LinkedHashMap dataBean = (LinkedHashMap) responseBody;
                    return ((Integer) dataBean.get("id")).toString();
                }
            }
        }
        return null;
    }


}
