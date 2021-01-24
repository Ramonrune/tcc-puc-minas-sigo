package com.sigo.api;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitAmqpSigoProcessoIndustrialItemConfiguration {

	public static final String SIGO_PROCESSO_INDUSTRIAL = "sigo.processo.industrial.item";
	public static final String SIGO_PROCESSO_INDUSTRIAL_EXCHANGE = SIGO_PROCESSO_INDUSTRIAL + ".exchange";
	public static final String SIGO_PROCESSO_INDUSTRIAL_FANOUT_DLX = SIGO_PROCESSO_INDUSTRIAL + ".dlx";
	public static final String SIGO_PROCESSO_INDUSTRIAL_DLQ = SIGO_PROCESSO_INDUSTRIAL + ".dlq";

	@Bean
	public CachingConnectionFactory rabbitConnectionFactory(RabbitProperties config) throws Exception {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();

		connectionFactory.getRabbitConnectionFactory().setUri(System.getenv("AMQP_HOST"));

		return connectionFactory;
	}

	@Bean
	Queue sigoProcessoIndustrialItem() {
		return QueueBuilder.durable(SIGO_PROCESSO_INDUSTRIAL)
				.withArgument("x-dead-letter-exchange", SIGO_PROCESSO_INDUSTRIAL_FANOUT_DLX).build();
	}

	@Bean
	FanoutExchange sigoProcessoIndustrialFanoutExchangeItem() {
		return new FanoutExchange(SIGO_PROCESSO_INDUSTRIAL_FANOUT_DLX, true, false);
	}

	@Bean
	Queue sigoProcessoIndustrialDlqItem() {
		return QueueBuilder.durable(SIGO_PROCESSO_INDUSTRIAL_DLQ).build();
	}

	@Bean
	Binding sigoProcessoIndustrialDeadLetterBindingItem() {
		return BindingBuilder.bind(sigoProcessoIndustrialDlqItem()).to(sigoProcessoIndustrialFanoutExchangeItem());
	}

	@Bean
	DirectExchange sigoProcessoIndustrialMessagesExchangeItem() {
		return new DirectExchange(SIGO_PROCESSO_INDUSTRIAL_EXCHANGE);
	}

	@Bean
	Binding bindingMessagesItem() {
		return BindingBuilder.bind(sigoProcessoIndustrialItem()).to(sigoProcessoIndustrialMessagesExchangeItem())
				.with(SIGO_PROCESSO_INDUSTRIAL);
	}





}