package com.myorg.example.services.Iservices;

import java.util.HashMap;
import java.util.List;

import com.myorg.example.models.TrelloCard;

public interface TrelloBoard {

	public HashMap<String, String> getBoardMembers();

	/**
	 * This method is use to get all the created columns
	 * (called as List) which are in "open" state.
	 * */
	public HashMap<String, String> getBoardColumns();

	public List<TrelloCard> getTrelloCards();

	public int createCard(RTTicket rtTicket, HashMap<String, String> boardLists);

}
