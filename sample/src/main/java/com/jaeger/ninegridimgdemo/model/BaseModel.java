package com.jaeger.ninegridimgdemo.model;

import java.io.Serializable;

/**
 * @author YanLu
 * @since 16/7/6
 */

public class BaseModel implements Serializable {
    private static final long serialVersionUID = 11111L;

    public boolean equals(Object obj) {
        if (obj instanceof BaseModel) {
            BaseModel model = (BaseModel) obj;
            if (model.mid() != null) {
                return mid().equals(model.mid());
            }
        }
        return super.equals(obj);
    }

    public int hashCode() {
        return (mid() != null ? mid().hashCode() : 0) + 529;
    }

    public String mid() {
        return null;
    }
}
