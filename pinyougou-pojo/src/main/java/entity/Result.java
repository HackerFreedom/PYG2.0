package entity;

import java.io.Serializable;

/**
 * 返回结果封装
 */
public class Result implements Serializable {
    private String msg;
    private int type;
    private int ex;

    public Result(String msg, int type, int ex) {
        this.msg = msg;
        this.type = type;
        this.ex = ex;
    }

    public int getEx() {
        return ex;
    }

    public void setEx(int ex) {
        this.ex = ex;
    }

    public Result(String msg, int type) {
        this.msg = msg;
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
