package com.myorg.example.controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myorg.example.services.MediaEnggRTTicketImpl;
import com.myorg.example.services.ProductionRTTicketImpl;
import com.myorg.example.services.Iservices.RTTicket;

@RestController
public class RTController {
	
	@Autowired
	private ProductionRTTicketImpl productionRTTicketsImpl;
	
	@Autowired
	private MediaEnggRTTicketImpl mediaEnggRTTicketsImpl;
	
	@RequestMapping(value = "/rt/production/tickets", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, RTTicket> getProductionRTTickets() {
		return productionRTTicketsImpl.getRTTickets();
	}
	
	@RequestMapping(value = "/rt/media/tickets", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, RTTicket> getMediaEnggRTTickets() {
		return mediaEnggRTTicketsImpl.getRTTickets();
	}

}
