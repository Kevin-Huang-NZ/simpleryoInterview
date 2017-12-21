package co.nz.simpleryo.shop.exception;

/**
 * Created by huanghao on 2016-6-20.
 */
public class ObjectNotExistException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1642793546527758338L;
	private static final String defaultMessage = "您访问的对象不存在。";
    public ObjectNotExistException(){
        super(defaultMessage);
    }

    public ObjectNotExistException(String message){
        super(message);
    }

    public ObjectNotExistException(Throwable cause){
        super(cause);
    }

    public ObjectNotExistException(String message, Throwable cause){
        super(message, cause);
    }
}