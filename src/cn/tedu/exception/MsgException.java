package cn.tedu.exception;

@SuppressWarnings("serial")
public class MsgException extends Exception {
    public MsgException() {
    }

    public MsgException(String msg) {
        super(msg);
    }
}
