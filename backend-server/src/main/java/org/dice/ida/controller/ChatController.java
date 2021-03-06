package org.dice.ida.controller;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import org.dice.ida.model.ChatUserMessage;
import org.dice.ida.model.ChatMessageResponse;

@Controller
public class ChatController {
	/**
	 * A controller to manage messages from web socket
	 * @param message
	 */

	@MessageMapping("/msg")
	@SendTo("/topic/msgs")
	public ChatMessageResponse handleMessaging(ChatUserMessage message) throws Exception {
		return new ChatMessageResponse(message.getMessage());
	}

	@MessageExceptionHandler
	@SendTo("/topic/errors")
	public ChatMessageResponse handleException(Throwable exception) {

		ChatMessageResponse myError = new ChatMessageResponse("An error happened.");
		return myError;
	}
}
