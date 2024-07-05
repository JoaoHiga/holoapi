package com.takomuraragi.holoapi.holoapi.models;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public class Vtuber {
    private String channelId;
    private String channelName;
    private String vtuberName;
    private String group;
    private Integer videoCount;
    private Integer subscriberCount;
    private Integer clipCount;
    private List<String> topTopics;
    private Boolean inactive;

    public Vtuber(ChannelInformation channelInformation){
        this.channelId = channelInformation.channelId();
        this.channelName = channelInformation.channelName();
        this.vtuberName = channelInformation.vtuberName();
        this.group = channelInformation.group();
        this.videoCount = channelInformation.videoCount();
        this.subscriberCount = channelInformation.subscriberCount();
        this.clipCount = channelInformation.clipCount();
        this.topTopics = channelInformation.topTopics();
        this.inactive = channelInformation.inactive();
    }

    public String getChannelId() {
        return channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getVtuberName() {
        return vtuberName;
    }

    public String getGroup() {
        return group;
    }

    public Integer getVideoCount() {
        return videoCount;
    }

    public Integer getSubscriberCount() {
        return subscriberCount;
    }

    public Integer getClipCount() {
        return clipCount;
    }

    public List<String> getTopTopics() {
        return topTopics;
    }

    public Boolean getInactive() {
        return inactive;
    }

    @Override
    public String toString() {
        return "\n***************************************************************\n" +
                "Información de " + vtuberName + "\n" +
                "***************************************************************\n" +
                "\tchannelId = " + channelId + "\n" +
                "\tchannelName = " + channelName + "\n" +
                "\tgroup = " + group + "\n" +
                "\tvideoCount = " + videoCount + "\n" +
                "\tsubscriberCount = " + subscriberCount + "\n" +
                "\tclipCount = " + clipCount + "\n" +
                "\ttopTopics = " + topTopics + "\n" +
                "\tinactive = " + inactive + "\n" +
                "***************************************************************\n";
    }
}
