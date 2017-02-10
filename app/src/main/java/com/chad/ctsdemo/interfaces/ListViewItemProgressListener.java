package com.chad.ctsdemo.interfaces;

/**
 * 跟ListView中的单个item绑定
 * 
 * @author baidu
 *
 */
public abstract class ListViewItemProgressListener {
    
    private String url;
    
    public abstract void onStatusUpdate();
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
}
