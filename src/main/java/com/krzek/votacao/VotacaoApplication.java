package com.krzek.votacao;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableRabbit
public class VotacaoApplication {

	@Value("${queue.name}")
	private String queueName;

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public Queue queue() {
		return new Queue(queueName, true);
	}

	public static void main(String[] args) {
		SpringApplication.run(VotacaoApplication.class, args);
	}

}
