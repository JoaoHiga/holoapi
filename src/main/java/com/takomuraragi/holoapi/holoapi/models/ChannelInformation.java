package com.takomuraragi.holoapi.holoapi.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ChannelInformation(
        @JsonProperty("id") String channelId,
        @JsonProperty("name") String channelName,
        @JsonProperty("english_name") String vtuberName,
        @JsonProperty("group") String group,
        @JsonProperty("video_count") Integer videoCount,
        @JsonProperty("subscriber_count") Integer subscriberCount,
        @JsonProperty("clip_count") Integer clipCount,
        @JsonProperty("top_topics") List<String> topTopics,
        @JsonProperty("inactive") Boolean inactive
) {
}
