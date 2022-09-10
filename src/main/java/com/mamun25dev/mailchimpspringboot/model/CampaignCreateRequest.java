package com.mamun25dev.mailchimpspringboot.model;

import java.util.ArrayList;

public class CampaignCreateRequest {

    private String subjectLine;
    private String previewText;
    private String title;
    private String campaignTitle;
    private ArrayList<String> recipients;
    private String selectedProducts;
    private String selectedProductsImg;

    private String segmentId;

    public String getSubjectLine() {
        return subjectLine;
    }

    public void setSubjectLine(String subjectLine) {
        this.subjectLine = subjectLine;
    }

    public String getPreviewText() {
        return previewText;
    }

    public void setPreviewText(String previewText) {
        this.previewText = previewText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCampaignTitle() {
        return campaignTitle;
    }

    public void setCampaignTitle(String campaignTitle) {
        this.campaignTitle = campaignTitle;
    }

    public ArrayList<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(ArrayList<String> recipients) {
        this.recipients = recipients;
    }

    public String getSelectedProducts() {
        return selectedProducts;
    }

    public void setSelectedProducts(String selectedProducts) {
        this.selectedProducts = selectedProducts;
    }

    public String getSelectedProductsImg() {
        return selectedProductsImg;
    }

    public void setSelectedProductsImg(String selectedProductsImg) {
        this.selectedProductsImg = selectedProductsImg;
    }

    public String getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }

}
