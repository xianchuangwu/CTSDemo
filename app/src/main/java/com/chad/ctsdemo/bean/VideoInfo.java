package com.chad.ctsdemo.bean;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 视频信息类
 * @author baidu
 *
 */
public class VideoInfo implements Parcelable {
    
    private String title = "";
    private String url = "";
    private String imageUrl = "";
    private boolean canDelete = true;
    
    public VideoInfo(String title, String url) {
        super();
        this.title = title;
        this.url = url;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    
    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }
    
    /**
     * 方便持久化
     * @return
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("url", url);
            json.put("title", title);
            json.put("imageUrl", imageUrl);
            json.put("canDelete", canDelete);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
    
    /**
     * 从持久化恢复
     * @param json
     * @return
     */
    public static VideoInfo fromJson(JSONObject json) {
        VideoInfo info = null;
        try {
            info = new VideoInfo(json.getString("title"), json.getString("url"));
            info.setImageUrl(json.optString("imageUrl", ""));
            info.setCanDelete(json.optBoolean("canDelete", true));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    
    /**
     * 以下三个函数或成员，都属于Parcelable接口。是为了方便通过Intent传输准备的。
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Intent putExtra时，会调用的函数
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(imageUrl);
        boolean[] boolArray = new boolean[1];
        boolArray[0] = this.canDelete;
        dest.writeBooleanArray(boolArray);
        
    }
    
    /**
     * Intent getExtra时，
     */
    public static final Creator<VideoInfo> CREATOR = new Creator<VideoInfo>() {

        @Override
        public VideoInfo createFromParcel(Parcel source) {
            String title = source.readString();
            String url = source.readString();
            String imageUrl = source.readString();
            boolean[] boolArray = new boolean[1];
            source.readBooleanArray(boolArray);
            VideoInfo p = new VideoInfo(title, url);
            p.setImageUrl(imageUrl);
            p.setCanDelete(boolArray[0]);
            return p;
        }

        @Override
        public VideoInfo[] newArray(int size) {
            return new VideoInfo[size];
        }
    };
}
