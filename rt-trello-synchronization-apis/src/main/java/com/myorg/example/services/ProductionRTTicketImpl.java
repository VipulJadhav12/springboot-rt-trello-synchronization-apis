package com.myorg.example.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.myorg.example.configurations.ConfigurationProperties;
import com.myorg.example.models.MediaEnggRTTicket;
import com.myorg.example.services.Iservices.RTTicket;

@Service
public class ProductionRTTicketImpl implements RTTicket {

	@Autowired
	ConfigurationProperties configurationProperties;

	@Override
	public HashMap<String, RTTicket> getRTTickets() {

		System.out.println("==================================");
		System.out.println("Tickets in Production Engineering Queue" + "\n" + "==================================");

		HashMap<String, RTTicket> rtTickets  = new HashMap<String, RTTicket>();
		String details = null;
		String rtTicketId = null;
		StringBuilder rtTicketDetails = null;
		HttpResponse<String> response = null;
		try {

			response = Unirest.get(configurationProperties.RT_SEARCH_URI)
					.queryString("user", configurationProperties.RT_USER)
					.queryString("pass", configurationProperties.RT_PASSWORD)
					.queryString("query", configurationProperties.RT_PRODUCTION_QUERY)
					.queryString("orderby", "+Created")
					.queryString("format", "l")
					.queryString("fields", configurationProperties.RT_PRODUCTION_QUEUE_FIELDS)
					.asString();

			if(response != null) {

				InputStream inputStream = response.getRawBody();

				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				details = reader.readLine();
				while ((details = reader.readLine()) != null) {

					if(!details.endsWith("--") && details.length() != 0) {

						if(details.startsWith("id")) {
							rtTicketDetails = new StringBuilder(details);
							rtTicketId = details;
							continue;
						}

						if(rtTicketDetails != null)
							rtTicketDetails.append("\n" + details);

					} else {
						if(rtTicketDetails != null) {
							MediaEnggRTTicket mediaEnggRTTicket = new MediaEnggRTTicket();
							mediaEnggRTTicket.setTicketDetails(rtTicketDetails.toString());
							rtTickets.put(rtTicketId, mediaEnggRTTicket);
						}
					}
				}

				for(String ticketId : rtTickets.keySet()) {
					System.out.println("ticketId: " + ticketId);
				}

				System.out.println("==================================");

			}

		} catch (UnirestException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return rtTickets;
	}

}
