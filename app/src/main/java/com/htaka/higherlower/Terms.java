package com.htaka.higherlower;

/**
 * Created by HT2K9 on 8/31/2016.
 */
public class Terms {

    public String title;
    public int average;

    public Terms(String title, int average) {
        this.title = title;
        this.average = average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }


}
