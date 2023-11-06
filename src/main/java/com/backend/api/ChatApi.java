package com.backend.api;

import com.backend.business.ChatBusiness;
import com.backend.exception.BaseException;
import com.backend.model.ChatMessageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatApi {

    private final ChatBusiness business;

    public ChatApi(ChatBusiness business){
        this.business = business;
    }

    @PostMapping("/message")
    public ResponseEntity<Void> post(@RequestBody ChatMessageRequest request) throws BaseException {
        business.post(request);
        return ResponseEntity.ok().build();
    }
}
