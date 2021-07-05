package com.studb.poemNote.error;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import com.studb.poemNote.utils.ApiResultForm;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class CustomErrorController implements ErrorController{

    @RequestMapping("/error")
    public ResponseEntity<?> handleError(HttpServletRequest request) throws IOException {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if ( status != null ){
            int statusCode = Integer.valueOf(status.toString());
            if (statusCode == 404){
                // return new ResponseEntity<>(ApiResultForm.error(statusCode, status.toString(), null, null), HttpStatus.NOT_FOUND);
                ClassPathResource index = new ClassPathResource("static/index.html");
                InputStream indexStream = index.getInputStream();
                String body = new BufferedReader(
                    new InputStreamReader(indexStream, StandardCharsets.UTF_8)
                ).lines().collect(Collectors.joining("\n"));
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.TEXT_HTML);
                return new ResponseEntity<String>(body, headers, HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(ApiResultForm.error(statusCode, status.toString(), null, null), HttpStatus.valueOf(statusCode));
            }
        }

        return null;
    }

    @Override
    public String getErrorPath() {
        return null;
    }
    
}
