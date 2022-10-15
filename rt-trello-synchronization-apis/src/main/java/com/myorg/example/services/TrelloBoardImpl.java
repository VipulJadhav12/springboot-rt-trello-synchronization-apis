package com.myorg.example.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.myorg.example.configurations.ConfigurationProperties;
import com.myorg.example.models.MediaEnggRTTicket;
import com.myorg.example.models.TrelloCard;
import com.myorg.example.services.Iservices.RTTicket;
import com.myorg.example.services.Iservices.TrelloBoard;

@Service
public class TrelloBoardImpl implements TrelloBoard {

	@Autowired
	ConfigurationProperties configurationProperties;

	@Override
	public List<TrelloCard> getTrelloCards() {

		System.out.println("==================================");
		System.out.println("Cards on Trello Board" + "\n" + "==================================");

		ArrayList<TrelloCard> trelloCardsList = new ArrayList<TrelloCard>();
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();

			HttpGet httpget = new HttpGet(configurationProperties.TRELLO_BASE_URI + configurationProperties.TRELLO_BOARD + "/cards?" +
					"key=" + configurationProperties.TRELLO_KEY + "&token=" + configurationProperties.TRELLO_TOKEN);
			httpget.setConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build());

			CloseableHttpResponse response = httpclient.execute(httpget);
			String details = null;

			HttpEntity resEntity = response.getEntity();

			if (resEntity != null) {
				InputStream inputStream = resEntity.getContent();
				System.out.println(resEntity.getContentType());
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				details = reader.readLine();
				reader.close();

				JSONArray json = new JSONArray(details);

				if (json.length() != 0) {
					for (int i = 0; i < json.length(); i++) {
						details = json.getJSONObject(i).getString("name");
						TrelloCard trelloCard = new TrelloCard();
						trelloCard.setCardId(details.split("\n")[0]);
						trelloCard.setCardName(details);
						trelloCardsList.add(trelloCard);
					}
				}
			}

			for(TrelloCard card : trelloCardsList) {
				System.out.println(card.toString());
			}

			System.out.println("==================================");

			response.close();
			httpclient.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
		}

		return trelloCardsList;

	}

	@Override
	public HashMap<String, String> getBoardMembers() {

		System.out.println("==================================");
		System.out.println("Members on Trello Board" + "\n" + "==================================");

		HashMap<String, String> boardMembersList = new HashMap<String, String>();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {

			HttpGet httpget = new HttpGet(configurationProperties.TRELLO_BASE_URI + configurationProperties.TRELLO_BOARD + "/members?" +
					"key=" + configurationProperties.TRELLO_KEY + "&token=" + configurationProperties.TRELLO_TOKEN);
			httpget.setConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build());

			response = httpclient.execute(httpget);
			String details = null;

			HttpEntity resEntity = response.getEntity();

			if (resEntity != null) {
				InputStream inputStream = resEntity.getContent();
				System.out.println(resEntity.getContentType());
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				details = reader.readLine();
				reader.close();

				JSONArray json = new JSONArray(details);

				if (json.length() != 0) {
					for (int i = 0; i < json.length(); i++) {
						boardMembersList.put(json.getJSONObject(i).getString("fullName"), json.getJSONObject(i).getString("id"));
					}
				}
			}

			for(String memberFullName : boardMembersList.keySet()) {
				System.out.println(boardMembersList.get(memberFullName));
			}

			System.out.println("==================================");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return boardMembersList;
	}

	@Override
	public HashMap<String, String> getBoardColumns() {

		System.out.println("==================================");
		System.out.println("Lists on Trello Board" + "\n" + "==================================");

		HashMap<String, String> boardLists = new HashMap<String, String>();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {

			HttpGet httpget = new HttpGet(configurationProperties.TRELLO_BASE_URI + configurationProperties.TRELLO_BOARD + "/lists?" +
					"key=" + configurationProperties.TRELLO_KEY + "&token=" + configurationProperties.TRELLO_TOKEN + "&fields=name");
			httpget.setConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build());

			response = httpclient.execute(httpget);
			String details = null;

			HttpEntity resEntity = response.getEntity();

			if (resEntity != null) {
				InputStream inputStream = resEntity.getContent();
				System.out.println(resEntity.getContentType());
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				details = reader.readLine();
				reader.close();

				JSONArray json = new JSONArray(details);

				if (json.length() != 0) {
					for (int i = 0; i < json.length(); i++) {
						boardLists.put(json.getJSONObject(i).getString("name"), json.getJSONObject(i).getString("id"));
					}
				}
			}

			for(String listName : boardLists.keySet()) {
				System.out.println(boardLists.get(listName));
			}

			System.out.println("==================================");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return boardLists;

	}

	@Override
	public int createCard(RTTicket rtTicket, HashMap<String, String> boardLists) {

		System.out.println("==================================");
		System.out.println("Create Cards on Trello Board" + "\n" + "==================================");

		MediaEnggRTTicket mediaEnggRTTicket = null;
		if(rtTicket instanceof MediaEnggRTTicket)
			mediaEnggRTTicket = (MediaEnggRTTicket) rtTicket;

		String listId = null;
		if(mediaEnggRTTicket.getTicketDetails().contains("Status"))
			listId = setCardListId(mediaEnggRTTicket.getTicketDetails(), boardLists);

		HttpResponse<String> response = null;
		try {
			response = Unirest.post(configurationProperties.TRELLO_CREATE_CARD_URI)
					.queryString("key", configurationProperties.TRELLO_KEY)
					.queryString("token", configurationProperties.TRELLO_TOKEN)
					.queryString("name", mediaEnggRTTicket.getTicketDetails())
					.queryString("idList", listId)
					.queryString("pos", "top")
					.asString();
			System.out.println(response.getBody());
		} catch (UnirestException e) {
			return 403;
		}

		return response.getStatus();

	}

	private String setCardListId(String ticketDetails, HashMap<String, String> boardLists) {

		if(ticketDetails.contains("Status: new") && ticketDetails.contains("Owner: Nobody"))
			return boardLists.getOrDefault("Ondeck", null);

		if(ticketDetails.contains("Status: open") && !ticketDetails.contains("Owner: Nobody"))
			return boardLists.getOrDefault("In Progress", null);

		if(ticketDetails.contains("Status: stalled"))
			return boardLists.getOrDefault("Blocked", null);

		return boardLists.getOrDefault("Ondeck", null);
	}

}
