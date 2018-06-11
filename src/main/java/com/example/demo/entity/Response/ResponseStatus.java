package com.example.demo.entity.Response;

import java.io.Serializable;

/**
 * @author huangwieyue
 * @date 2018-02-01 10:19
 */
public enum ResponseStatus implements Serializable{
    SUCCESS,FAIL;
    public boolean isSuccess() {
        return this == SUCCESS;
    }
}
