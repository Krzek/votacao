package com.krzek.votacao.mq;


import com.krzek.votacao.dto.VotacaoDto;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {

	@Autowired
	private RabbitTemplate template;

	@Autowired
	private Queue queue;
	
	public void send(VotacaoDto v) {
		this.template.convertAndSend(queue.getName(), v);
	}

}
