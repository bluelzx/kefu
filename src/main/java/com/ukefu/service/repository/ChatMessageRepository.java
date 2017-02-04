package com.ukefu.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ukefu.util.server.message.ChatMessage;


/**
 * 
 * @author admin
 *
 */
public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {
	public List<ChatMessage> findBySession(String session) ;
}
