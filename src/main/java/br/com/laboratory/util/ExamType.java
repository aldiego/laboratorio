package br.com.laboratory.util;

public enum ExamType {

    ANALYSIS(0),
    CLINIC(1),
    IMAGE(2);

    private int code;
    ExamType(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
