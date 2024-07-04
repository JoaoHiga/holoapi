package com.takomuraragi.holoapi.holoapi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.takomuraragi.holoapi.holoapi.models.ChannelInformation;
import com.takomuraragi.holoapi.holoapi.models.ChannelInformationList;
import com.takomuraragi.holoapi.holoapi.services.ApiConsult;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;


@SpringBootApplication
public class HoloApiApplication implements CommandLineRunner {
	@Value("${api.key}")
	String API_KEY;

	public static void main(String[] args) {
		SpringApplication.run(HoloApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		ApiConsult apiConsult = new ApiConsult();
		String BASE_URL = "https://holodex.net/api/v2/channels/";
		Map<String, String> queryParameters = new HashMap<>();
		queryParameters.put("type", "vtuber");
		queryParameters.put("org", "Hololive");

		URIBuilder uriBuilder = new URIBuilder(BASE_URL);
		String url =  uriBuilder.addParameters(queryParameters.entrySet().stream()
				.map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
				.collect(toList()))
				.toString();

		String json = apiConsult.obtainData(url, API_KEY);
		System.out.println(json);
		ObjectMapper jsonConverter = new ObjectMapper();
		List<ChannelInformation> channels = jsonConverter.readValue(json, new TypeReference<>() {});
		ChannelInformationList channelsList = new ChannelInformationList(channels);
	}
}
