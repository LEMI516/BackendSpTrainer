package com.sptrainer.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResult<T extends Object> {
    public Integer status;
    public String msj;
    public T objeto;
    public boolean response;

    public static <T extends Object> MessageResult<T> result(T obj, String msj, int status) {
        MessageResult<T> m = new MessageResult<T>();
        m.setMsj(msj);
        m.setObjeto(obj);
        m.setStatus(status);
        m.setResponse(false);
        return m;
    }

    public static <T extends Object> MessageResult<T> successful(T obj, String msj) {
        MessageResult<T> m = new MessageResult<T>();
        m.setMsj(msj);
        m.setObjeto(obj);
        m.setStatus(200);
        m.setResponse(true);
        return m;
    }

    public static <T extends Object> MessageResult<T> error(T obj, String msj) {
        MessageResult<T> m = new MessageResult<T>();
        m.setMsj(msj);
        m.setObjeto(obj);
        m.setStatus(500);
        m.setResponse(false);
        return m;
    }

}


