package com.soa.securityservice.pojo;

import lombok.Data;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
public class RspResult {

    private Integer status;

    private String msg;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
