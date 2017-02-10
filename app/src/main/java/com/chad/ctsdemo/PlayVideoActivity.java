package com.chad.ctsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.VideoView;

import com.baidu.cyberplayer.download.AbstractDownloadableVideoItem;
import com.baidu.cyberplayer.download.VideoDownloadManager;
import com.chad.ctsdemo.Utils.DownloadObserverManager;
import com.chad.ctsdemo.Utils.SampleObserver;
import com.chad.ctsdemo.Utils.SharedPrefsStore;
import com.chad.ctsdemo.bean.VideoInfo;

public class PlayVideoActivity extends AppCompatActivity {

    private String url = "http://gkkskijidms30qudc3v.exp.bcevod.com/mda-gkkswvrb2zhp41ez/mda-gkkswvrb2zhp41ez.m3u8";

    VideoDownloadManager downloadManagerInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        downloadManagerInstance = VideoDownloadManager.getInstance(PlayVideoActivity.this, DownloadVideoActivity.SAMPLE_USER_NAME);


        VideoInfo info = new VideoInfo("baidu",url);
        SharedPrefsStore.addToCacheVideo(PlayVideoActivity.this, info);
        SampleObserver sampleObs = new SampleObserver();
        DownloadObserverManager.addNewObserver(info.getUrl(), sampleObs);
        downloadManagerInstance.startOrResumeDownloader(info.getUrl(), sampleObs);
        Toast.makeText(PlayVideoActivity.this, "开始缓存，请点击右上角「本地缓存」查看进度", Toast.LENGTH_SHORT).show();

        VideoView videoView = (VideoView) findViewById(R.id.video);

        final AbstractDownloadableVideoItem downItem = downloadManagerInstance
                .getDownloadableVideoItemByUrl(info.getUrl());

        videoView.setVideoPath("file://" + downItem.getLocalAbsolutePath());
        videoView.start();
        videoView.requestFocus();

    }
}
