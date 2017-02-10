package com.chad.ctsdemo.Utils;

import java.lang.ref.WeakReference;

import com.baidu.cyberplayer.download.AbstractDownloadableVideoItem;
import com.baidu.cyberplayer.download.AbstractDownloadableVideoItem.DownloadStatus;
import com.baidu.cyberplayer.download.DownloadObserver;
import com.chad.ctsdemo.interfaces.ListViewItemProgressListener;

import android.util.Log;

/**
 * 
 * 这是下载回调的实时回调接口，继承自 下载管理SDK的DownloadObserver类
 * 其具有应用全局性，不局限在某个activity或者某个ListviewItem
 * 
 * 另外，本demo中有ProgressListener是为了Listview更新时使用
 * @author baidu
 *
 */
public class SampleObserver extends DownloadObserver {
    public static final String TAG = "SampleObserver";
    
    volatile WeakReference<ListViewItemProgressListener> weakListener;
    
    public WeakReference<ListViewItemProgressListener> getListener() {
        return weakListener;
    }
    
    public void setListener(WeakReference<ListViewItemProgressListener> listener) {
        this.weakListener = listener;
    }

    public SampleObserver() {
    }

    @Override
    public void update(AbstractDownloadableVideoItem downloader) {
        if (weakListener == null) {
            Log.d(TAG, "weakListener is null, url =" + downloader.getUrl());
            return;
        }
        if (weakListener.get() == null) {
            Log.d(TAG, "weakListener.get() is null, url =" + downloader.getUrl());
            return;
        }
        if (weakListener.get() != null && weakListener.get().getUrl().equals(downloader.getUrl())) {
            weakListener.get().onStatusUpdate();
        }
        
    }
    
    /**
     * DownloadStatus状态比较详尽，但界面显示时仅需要几类状态即可(下载中、已暂停、完成)
     * @param status
     * @return
     */
    public static String getStatusForUI(DownloadStatus status) {
        String statusForUI = null;
        
        if (status == DownloadStatus.PENDING || status == DownloadStatus.DOWNLOADING) {
            statusForUI = "下载中, 点击可暂停";
        } else if (status == DownloadStatus.COMPLETED) {
            statusForUI = "下载完成，点击可播放";
        } else if (status == DownloadStatus.ERROR) {
            statusForUI = "下载出错，点击可重试";
        } else {
            statusForUI = "下载暂停，点击可继续";
        }
        
        return statusForUI;
    }
}
