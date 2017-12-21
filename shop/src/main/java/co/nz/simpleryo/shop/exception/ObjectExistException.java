package co.nz.simpleryo.shop.exception;

/**
 * Created by huanghao on 2016-6-20.
 */
public class ObjectExistException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5654276291668867771L;
	private static final String defaultMessage = "你要创建的对象已存在，不能重复创建。";
    public ObjectExistException(){
        super(defaultMessage);
    }

    public ObjectExistException(String message){
        super(message);
    }

    public ObjectExistException(Throwable cause){
        super(cause);
    }

    public ObjectExistException(String message, Throwable cause){
        super(message, cause);
    }
}
