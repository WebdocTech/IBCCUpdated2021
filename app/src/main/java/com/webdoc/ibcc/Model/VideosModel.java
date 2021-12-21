package com.webdoc.ibcc.Model;

public class VideosModel {
    public String Url;
    public String datetime;
    public String VideoDuration ;
    public String VideoId;
    public String VideoTitle;

    public VideosModel(String url, String datetime, String videoDuration, String videoTitle) {
        Url = url;
        this.datetime = datetime;
        VideoDuration = videoDuration;
        VideoTitle = videoTitle;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getVideoDuration() {
        return VideoDuration;
    }

    public void setVideoDuration(String videoDuration) {
        VideoDuration = videoDuration;
    }

    public String getVideoId() {
        return VideoId;
    }

    public void setVideoId(String videoId) {
        VideoId = videoId;
    }

    public String getVideoTitle() {
        return VideoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        VideoTitle = videoTitle;
    }
}
