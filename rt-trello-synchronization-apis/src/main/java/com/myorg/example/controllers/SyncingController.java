package com.myorg.example.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myorg.example.models.TrelloCard;
import com.myorg.example.services.MediaEnggRTTicketImpl;
import com.myorg.example.services.ProductionRTTicketImpl;
import com.myorg.example.services.TrelloBoardImpl;
import com.myorg.example.services.Iservices.RTTicket;

@RestController
public class SyncingController {

	@Autowired
	private ProductionRTTicketImpl productionRTTicketImpl;

	@Autowired
	private MediaEnggRTTicketImpl mediaEnggRTTicketImpl;

	@Autowired
	private TrelloBoardImpl trelloBoardImpl;

	@RequestMapping("/sync")
	public HashMap<String, RTTicket> syncRTTrelloBoards() {

		HashMap<String, RTTicket> mapProductionRTTicket = productionRTTicketImpl.getRTTickets();

		HashMap<String, RTTicket> mapMediaEnggRTTicket = mediaEnggRTTicketImpl.getRTTickets();

		HashMap<String, RTTicket> mapRTTicket = new HashMap<String, RTTicket>();

		if(mapProductionRTTicket.size() > 0)
			mapRTTicket.putAll(mapProductionRTTicket);

		if(mapMediaEnggRTTicket.size() > 0)
			mapRTTicket.putAll(mapMediaEnggRTTicket);

		List<TrelloCard> trelloCardsList = trelloBoardImpl.getTrelloCards();

		if(trelloCardsList.size() > 0) {
			for(TrelloCard card : trelloCardsList) {
				String cardId = card.getCardId();
				if(mapRTTicket.containsKey(cardId))
					mapRTTicket.remove(cardId);
			}
		}

		HashMap<String, String> boardColumns = trelloBoardImpl.getBoardColumns();

		if (mapRTTicket.size() > 0 && trelloCardsList.size() > 0) {
			for (TrelloCard card : trelloCardsList) {
				String cardName = card.getCardName();
				Pattern pattern = Pattern.compile("^(([0-9]+)|(AIM-)[0-9]+|(MES-)[0-9]+|(A4T-)[0-9]+|([$&+,:;=?@#|'<>.^*()%!-])*(_)*([a-zA-Z])+|([a-zA-Z]+))");
				Matcher match = null;
				match = pattern.matcher(cardName);

				while (match.find()) {
					cardName = match.group();
				}

				if (!mapRTTicket.containsKey(cardName)) {
					int responseStatus = trelloBoardImpl.createCard(mapRTTicket.get(cardName), boardColumns);
					if (403 == responseStatus)
						mapRTTicket.remove(cardName);
				}
			}
		}
		
		return mapRTTicket;
	}

}
