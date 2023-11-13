package com.backend.business;

import com.backend.Util.SecurityUtil;
import com.backend.exception.BaseException;
import com.backend.exception.ChatException;
import com.backend.exception.EmailException;
import com.backend.model.ChatMessage;
import com.backend.model.ChatMessageRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatBusiness {

    private final SimpMessagingTemplate template;

    public ChatBusiness(SimpMessagingTemplate template){
        this.template = template;
    }

    public void post(ChatMessageRequest request) throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();

        if (opt.isEmpty()) {
            throw ChatException.accessDenied();
        }

        // Validate message

        final String destination = "/topic/chat";
        ChatMessage payload = new ChatMessage();
        payload.setFrom(opt.get());
        payload.setMessage(request.getMessage());

        template.convertAndSend(destination, payload);
    }
}
