package br.com.laboratory.util;

public enum Status {

    INATIVE(0),
    ACTIVE(1);

    private int code;

     Status(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }


}
