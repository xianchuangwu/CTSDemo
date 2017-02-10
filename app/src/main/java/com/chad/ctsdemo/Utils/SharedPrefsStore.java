package com.chad.ctsdemo.Utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

import com.chad.ctsdemo.bean.VideoInfo;

/**
 * 此处仅为存储示例 您的项目中可能使用其他存储形式
 * 
 * @author baidu
 */
public class SharedPrefsStore {
    private static final String MAIN_VIDEO_LIST_SPNAME = "video-main";
    private static final String CACHE_VIDEO_LIST_SPNAME = "video-cache";
    private static final String SETTINGS_SPNAME = "video-settings";

    private static final String KEY_VIDEOS_ARRAY = "videos";

    public static boolean addToMainVideo(Context context, VideoInfo info) {
        SharedPreferences spList = context.getSharedPreferences(MAIN_VIDEO_LIST_SPNAME, 0);
        String jsonStr = spList.getString(KEY_VIDEOS_ARRAY, "[]");
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            jsonArray.put(info.toJson());
            SharedPreferences.Editor editor = spList.edit();
            editor.putString(KEY_VIDEOS_ARRAY, jsonArray.toString());
            editor.commit();
        } catch (JSONException e1) {
            e1.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean deleteMainVideo(Context context, VideoInfo info) {
        SharedPreferences spList = context.getSharedPreferences(MAIN_VIDEO_LIST_SPNAME, 0);
        String jsonStr = spList.getString(KEY_VIDEOS_ARRAY, "[]");
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            JSONArray newJsonArray = new JSONArray();
            for (int i = 0; i < jsonArray.length(); ++i) {
                try {
                    JSONObject json = (JSONObject) jsonArray.get(i);
                    VideoInfo info2 = VideoInfo.fromJson(json);
                    if (info2.getUrl().equals(info.getUrl()) && info2.getTitle().equals(info.getTitle())) {
                        continue;
                    } else {
                        newJsonArray.put(json);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (newJsonArray.length() == jsonArray.length()) {
                return false;
            } else {
                SharedPreferences.Editor editor = spList.edit();
                editor.putString(KEY_VIDEOS_ARRAY, newJsonArray.toString());
                editor.commit();
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public static boolean addToCacheVideo(Context context, VideoInfo info) {
        SharedPreferences spList = context.getSharedPreferences(CACHE_VIDEO_LIST_SPNAME, 0);
        String jsonStr = spList.getString(KEY_VIDEOS_ARRAY, "[]");
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            jsonArray.put(info.toJson());
            SharedPreferences.Editor editor = spList.edit();
            editor.putString(KEY_VIDEOS_ARRAY, jsonArray.toString());
            editor.commit();
        } catch (JSONException e1) {
            e1.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteCacheVideo(Context context, VideoInfo info) {
        SharedPreferences spList = context.getSharedPreferences(CACHE_VIDEO_LIST_SPNAME, 0);
        String jsonStr = spList.getString(KEY_VIDEOS_ARRAY, "[]");
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            JSONArray newJsonArray = new JSONArray();
            for (int i = 0; i < jsonArray.length(); ++i) {
                try {
                    JSONObject json = (JSONObject) jsonArray.get(i);
                    VideoInfo info2 = VideoInfo.fromJson(json);
                    if (info2.getUrl().equals(info.getUrl()) && info2.getTitle().equals(info.getTitle())) {
                        continue;
                    } else {
                        newJsonArray.put(json);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (newJsonArray.length() == jsonArray.length()) {
                return false;
            } else {
                SharedPreferences.Editor editor = spList.edit();
                editor.putString(KEY_VIDEOS_ARRAY, newJsonArray.toString());
                editor.commit();
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public static ArrayList<VideoInfo> getAllMainVideoFromSP(Context context) {
        ArrayList<VideoInfo> infoList = new ArrayList<VideoInfo>();
        // add sample data first
        infoList.addAll(getMainSampleData(context));
        
        // add user added data
        SharedPreferences spList = context.getSharedPreferences(MAIN_VIDEO_LIST_SPNAME, 0);
        String jsonStr = spList.getString(KEY_VIDEOS_ARRAY, "[]");
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); ++i) {
                try {
                    JSONObject json = (JSONObject) jsonArray.get(i);
                    VideoInfo info = VideoInfo.fromJson(json);
                    infoList.add(info);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        
        return infoList;
    }

    /**
     * 初次进入应用，SP无数据时，准备样例数据
     * 
     * @return
     */
    public static ArrayList<VideoInfo> getMainSampleData(Context context) {
        ArrayList<VideoInfo> sampleList = new ArrayList<VideoInfo>();

        String title1 = "百度云宣传视频";
        String url1 = "http://gkkskijidms30qudc3v.exp.bcevod.com/mda-gkkswvrb2zhp41ez/mda-gkkswvrb2zhp41ez.m3u8";
        VideoInfo info1 = new VideoInfo(title1, url1);
        info1.setCanDelete(false);
        info1.setImageUrl("baidu_cloud_bigger.jpg");
        sampleList.add(info1);

        String title2 = "LSS3.0使用说明";
        String url2 = "http://gkkskijidms30qudc3v.exp.bcevod.com/mda-gkks7fejzyj89qkf/mda-gkks7fejzyj89qkf.m3u8";
        VideoInfo info2 = new VideoInfo(title2, url2);
        info2.setCanDelete(false);
        info2.setImageUrl("baidu_cloud_lss3_release.jpg");
        sampleList.add(info2);

        String title3 = "直播链接(HLS/RTMP/HTTP-FLV均可播放)";
        String url3 = "http://play.e-web.com.cn/live/lss-ghuq0q40xwhvp5pd.flv";
        VideoInfo info3 = new VideoInfo(title3, url3);
        info3.setCanDelete(false);
        sampleList.add(info3);
        
        String title4 = "直播链接是您推流对应的播放链接";
        String url4 = "默认播放列表在demo的SharedPrefsStore类中";
        VideoInfo info4 = new VideoInfo(title4, url4);
        info4.setCanDelete(false);
        sampleList.add(info4);

        return sampleList;
    }

    public static ArrayList<VideoInfo> getAllCacheVideoFromSP(Context context) {
        ArrayList<VideoInfo> infoList = new ArrayList<>();
        SharedPreferences spList = context.getSharedPreferences(CACHE_VIDEO_LIST_SPNAME, 0);
        String jsonStr = spList.getString(KEY_VIDEOS_ARRAY, "[]");
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); ++i) {
                try {
                    JSONObject json = (JSONObject) jsonArray.get(i);
                    VideoInfo info = VideoInfo.fromJson(json);
                    infoList.add(info);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return infoList;
    }

    public static void setDefaultOrientation(Context context, boolean isPortrait) {
        SharedPreferences spList = context.getSharedPreferences(SETTINGS_SPNAME, 0);
        SharedPreferences.Editor editor = spList.edit();
        editor.putBoolean("isPortrait", isPortrait);
        editor.commit();
    }

    public static boolean isDefaultPortrait(Context context) {
        SharedPreferences spList = context.getSharedPreferences(SETTINGS_SPNAME, 0);
        return spList.getBoolean("isPortrait", true);
    }

    public static void setControllBar(Context context, boolean isSimple) {
        SharedPreferences spList = context.getSharedPreferences(SETTINGS_SPNAME, 0);
        SharedPreferences.Editor editor = spList.edit();
        editor.putBoolean("isSimple", isSimple);
        editor.commit();
    }

    public static boolean isControllBarSimple(Context context) {
        SharedPreferences spList = context.getSharedPreferences(SETTINGS_SPNAME, 0);
        return spList.getBoolean("isSimple", false);
    }

    public static void setPlayerFitMode(Context context, boolean isCrapping) {
        SharedPreferences spList = context.getSharedPreferences(SETTINGS_SPNAME, 0);
        SharedPreferences.Editor editor = spList.edit();
        editor.putBoolean("isCrapping", isCrapping);
        editor.commit();
    }

    public static boolean isPlayerFitModeCrapping(Context context) {
        SharedPreferences spList = context.getSharedPreferences(SETTINGS_SPNAME, 0);
        return spList.getBoolean("isCrapping", false);
    }
}
