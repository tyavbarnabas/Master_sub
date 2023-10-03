package com.codemarathon.user.event;

import com.codemarathon.user.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@Getter
@ToString
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {
    private User user;
    private String messageUrl;
    public RegistrationCompleteEvent(User user,String messageUrl) {
        super(user);
        this.user= user;
        this.messageUrl=messageUrl;
    }
}
