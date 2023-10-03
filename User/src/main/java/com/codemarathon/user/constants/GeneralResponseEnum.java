package com.codemarathon.user.constants;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum GeneralResponseEnum {

    SUCCESS("000","SUCCESS","Registration Completed successfully"),
    FAILED("900","FAILED","Operation failed");

    private final String code;
    private final String Status;
    private final String message;

    GeneralResponseEnum(String code,String status,String message){
        this.code= code;
        this.Status = status;
        this.message=message;
    }

}
