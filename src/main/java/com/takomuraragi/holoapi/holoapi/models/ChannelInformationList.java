package com.takomuraragi.holoapi.holoapi.models;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record ChannelInformationList(
        List<ChannelInformation> channelInformations
) {
}
