package com.myorg.example.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myorg.example.models.TrelloCard;
import com.myorg.example.services.TrelloBoardImpl;

@RestController
public class TrelloBoardController {

	@Autowired
	private TrelloBoardImpl trelloBoardsImpl;

	@RequestMapping(value = "/trello-board/cards", method = RequestMethod.GET)
	@ResponseBody
	public List<TrelloCard> getTrelloBoardCards() {
		return trelloBoardsImpl.getTrelloCards();
	}

	@RequestMapping(value = "/trello-board/lists", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, String> getTrelloBoardLists() {
		return trelloBoardsImpl.getBoardColumns();
	}

	@RequestMapping(value = "/trello-board/members", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, String> getTrelloBoardMembers() {
		return trelloBoardsImpl.getBoardMembers();
	}

}
