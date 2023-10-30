package com.backend.business;

import com.backend.exception.BaseException;
import com.backend.exception.EmailException;
import com.backend.service.EmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
public class EmailBusiness {

    private final EmailService emailService;

    public EmailBusiness(EmailService emailService){
        this.emailService = emailService;
    }

    public void sendActivateUserEmail(String email, String name, String token)throws BaseException {
        // Prepare html
        String html = null;
        try {
            html = readEmailTemplate("email.activate.user.html");
        } catch (IOException e) {
            throw EmailException.templateNotFound();
        }

        String finalLink = "http://localhost:8080/activate/" + token;
        html = html.replace("${P_NAME}", name);
        html = html.replace("${LINK}", finalLink);

        // Prepare content
        String subject = "Please activate your account";

        emailService.send(email, subject, html);

    }

    private String readEmailTemplate(String filename) throws IOException {
        File file = ResourceUtils.getFile("classpath:Email/" + filename);
        return FileCopyUtils.copyToString(new FileReader(file));
    }
}
