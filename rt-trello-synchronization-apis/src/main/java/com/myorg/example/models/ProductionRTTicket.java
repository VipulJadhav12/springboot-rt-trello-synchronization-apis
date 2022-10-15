package com.myorg.example.models;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.myorg.example.services.Iservices.RTTicket;

@Component
public class ProductionRTTicket implements RTTicket {
	
	private String ticketDetails;

	public String getTicketDetails() {
		return ticketDetails;
	}

	public void setTicketDetails(String ticketDetails) {
		this.ticketDetails = ticketDetails;
	}

	@Override
	public HashMap<String, RTTicket> getRTTickets() {
		return null;
	}

	@Override
	public String toString() {
		return "ProductionRTTicket [ticketDetails=" + ticketDetails + "]";
	}

}
