package com.sigo.api;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitAmqpLegadoProcessoIndustrialConfiguration {

	public static final String LEGADO_PROCESSO_INDUSTRIAL = "legado.processo.industrial";
	public static final String LEGADO_PROCESSO_INDUSTRIAL_EXCHANGE = LEGADO_PROCESSO_INDUSTRIAL + ".exchange";
	public static final String LEGADO_PROCESSO_INDUSTRIAL_FANOUT_DLX = LEGADO_PROCESSO_INDUSTRIAL + ".dlx";
	public static final String LEGADO_PROCESSO_INDUSTRIAL_DLQ = LEGADO_PROCESSO_INDUSTRIAL + ".dlq";

	@Bean
	Queue legadoProcessoIndustrial() {
		return QueueBuilder.durable(LEGADO_PROCESSO_INDUSTRIAL)
				.withArgument("x-dead-letter-exchange", LEGADO_PROCESSO_INDUSTRIAL_FANOUT_DLX).build();
	}

	@Bean
	FanoutExchange legadoProcessoIndustrialFanoutExchange() {
		return new FanoutExchange(LEGADO_PROCESSO_INDUSTRIAL_FANOUT_DLX, true, false);
	}

	@Bean
	Queue legadoProcessoIndustrialDlq() {
		return QueueBuilder.durable(LEGADO_PROCESSO_INDUSTRIAL_DLQ).build();
	}

	@Bean
	Binding legadoProcessoIndustrialDeadLetterBinding() {
		return BindingBuilder.bind(legadoProcessoIndustrialDlq()).to(legadoProcessoIndustrialFanoutExchange());
	}

	@Bean
	DirectExchange legadoProcessoIndustrialMessagesExchange() {
		return new DirectExchange(LEGADO_PROCESSO_INDUSTRIAL_EXCHANGE);
	}

	@Bean
	Binding bindingMessages() {
		return BindingBuilder.bind(legadoProcessoIndustrial()).to(legadoProcessoIndustrialMessagesExchange())
				.with(LEGADO_PROCESSO_INDUSTRIAL);
	}

}