package com.backend.business;

import com.backend.exception.BaseException;
import com.backend.exception.EmailException;
import com.backend.service.EmailService;
import com.example.common.EmailRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Service
public class EmailBusiness {

    private final KafkaTemplate<String, EmailRequest> kafkaEmailTemplate;

    public EmailBusiness(KafkaTemplate<String, EmailRequest> kafkaEmailTemplate){
        this.kafkaEmailTemplate = kafkaEmailTemplate;
    }

    public void sendActivateUserEmail(String email, String name, String token)throws BaseException {
        // Prepare html
        String html = null;
        try {
            html = readEmailTemplate("email.activate.user.html");
        } catch (IOException e) {
            throw EmailException.templateNotFound();
        }


        EmailRequest request = new EmailRequest();
        request.setTo(email);
        request.setContent(html);

        String finalLink = "http://localhost:8080/activate/" + token;
        html = html.replace("${P_NAME}", name);
        html = html.replace("${P_LINK}", finalLink);

        // Prepare content
        String subject = "Please activate your account";

        CompletableFuture<SendResult<String, EmailRequest>> future = kafkaEmailTemplate.send("activation-email", request);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Kafka send success");
                log.info(result);
            } else {
                log.error("Kafka send failed");
                log.error(ex.getMessage());
            }
        });

        log.info(token);

    }

    private String readEmailTemplate(String filename) throws IOException {
        File file = ResourceUtils.getFile("classpath:Email/" + filename);
        return FileCopyUtils.copyToString(new FileReader(file));
    }
}
