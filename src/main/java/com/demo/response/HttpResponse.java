package com.demo.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponse {

    private String timeStamp;
    private String message;
    private String path;
    private String errorCode;

}
