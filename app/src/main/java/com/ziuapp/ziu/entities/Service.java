package com.ziuapp.ziu.entities;

import java.util.ArrayList;

/**
 * Created by ABHIJEET on 25-05-2015.
 */
public class Service {

    private String imageUrl;
    private String title;
    private String price;
    private ArrayList<String> screenshots;
    private ArrayList<String> deals;
    private int recommendations;


    public Service(String title,String imageUrl) {
        this.imageUrl = imageUrl;
        this.title = title;
    }

    public Service(String imageUrl, String title, String price, ArrayList<String> screenshots, ArrayList<String> deals, int recommendations) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.price = price;
        this.screenshots = screenshots;
        this.deals = deals;
        this.recommendations = recommendations;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ArrayList<String> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(ArrayList<String> screenshots) {
        this.screenshots = screenshots;
    }

    public ArrayList<String> getDeals() {
        return deals;
    }

    public void setDeals(ArrayList<String> deals) {
        this.deals = deals;
    }

    public int getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(int recommendations) {
        this.recommendations = recommendations;
    }
}
