package com.example.demo.entity.Response;

import groovy.util.logging.Slf4j;
import org.springframework.data.domain.Example;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author huangwieyue
 * @date 2018-02-01 10:18
 */
@Slf4j
public class Response<T> implements Serializable {
    private ResponseStatus status;
    private int responseCode;
    private String message;
    private String time;
    private Integer total; //返回数据总数
    private T data; //NOSONAR


    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public Response() {
        super();
    }


    public Response(ResponseStatus status, int responseCode, String message, String time) {
        this.status = status;
        this.responseCode = responseCode;
        this.message = message;
        this.time = time;
    }


    public boolean ifSuccess() {
        return status.isSuccess();
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getMessage() {
        return message;
    }

    public Response(boolean status, String message, T data) {
        this.status = status ? ResponseStatus.SUCCESS : ResponseStatus.FAIL;
        this.responseCode = status ? 200 : 500;
        this.message = message;
        this.time = sdf.format(new Date());
        this.data = data;
    }

    public Response(boolean status, String message, T data, Example e) {
        this.status = status ? ResponseStatus.SUCCESS : ResponseStatus.FAIL;
        this.responseCode = status ? 200 : 500;
        this.message = message;
        this.time = sdf.format(new Date());
        this.data = data;

    }

    public Response(boolean status, String message) {
        this.status = status ? ResponseStatus.SUCCESS : ResponseStatus.FAIL;
        this.responseCode = status ? 200 : 500;
        this.message = message;
    }

}
