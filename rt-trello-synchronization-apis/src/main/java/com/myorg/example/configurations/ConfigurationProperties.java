package com.myorg.example.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:configuration.properties")
public class ConfigurationProperties {
	
	@Value( "${rt.search.uri}" )
	public String RT_SEARCH_URI;

	@Value( "${rt.user}" )
	public String RT_USER;

	@Value( "${rt.password}" )
	public String RT_PASSWORD;
	
	@Value( "${rt.production.query}" )
	public String RT_PRODUCTION_QUERY;

	@Value( "${rt.production.queue.fields}" )
	public String RT_PRODUCTION_QUEUE_FIELDS;
	
	@Value( "${rt.media.query}" )
	public String RT_MEDIA_QUERY;

	@Value( "${rt.media.queue.fields}" )
	public String RT_MEDIA_QUEUE_FIELDS;

	@Value( "${trello.base.uri}" )
	public String TRELLO_BASE_URI;
	
	@Value( "${trello.create.card.uri}" )
	public String TRELLO_CREATE_CARD_URI;

	@Value( "${trello.key}" )
	public String TRELLO_KEY;

	@Value( "${trello.token}" )
	public String TRELLO_TOKEN;

	@Value( "${trello.board}" )
	public String TRELLO_BOARD;

}
