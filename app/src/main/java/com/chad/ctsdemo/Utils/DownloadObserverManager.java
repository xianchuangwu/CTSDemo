package com.chad.ctsdemo.Utils;

import java.util.concurrent.ConcurrentHashMap;

public class DownloadObserverManager {
    /**
     * 此处对Observer进行统一管理
     */
    private static ConcurrentHashMap<String, SampleObserver> observers =
            new ConcurrentHashMap<String, SampleObserver>();

    public static void addNewObserver(String url, SampleObserver observer) {
        observers.put(url, observer);
    }

    public static SampleObserver getExistObserver(String url) {
        return observers.get(url);
    }
}
