package com.codemarathon.user.event;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ApplicationUrl {

    public String messageUrl(HttpServletRequest request){
        return "http://" +request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
}
