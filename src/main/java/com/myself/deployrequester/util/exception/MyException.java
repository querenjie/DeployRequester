package com.myself.deployrequester.util.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;


/**
 * Created by QueRenJie on ${date}
 */
public class MyException extends Exception {
    private String errorCode;
    private String errorMsg;
    private Exception exception;
    private transient String trace;

    public MyException(Exception e) {
        super(e);
        this.exception = e;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("错误代码:" + getErrorCode());
        sb.append("\r\n");
        sb.append("错误信息:" + getErrorMsg());
        sb.append("\r\n");
        sb.append("StackTrace:" + getTrace());
        sb.append("\r\n");

        return sb.toString();
    }

    public String getTrace() {
        if (this.trace == null) {
            makeTrace();
        }
        return this.trace;
    }

    private void makeTrace() {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        PrintWriter po = new PrintWriter(bo);
        this.exception.printStackTrace(po);
        po.flush();
        byte[] ba = bo.toByteArray();
        po.close();
        bo = null;
        this.trace = new String(ba);
    }

    public static void main(String[] args) {
        Exception e = new Exception();
        MyException a = new MyException(e);
        a.setErrorCode("1001");
        a.setErrorMsg("示范错误的原因是....");
        System.out.println(a);
    }
}
