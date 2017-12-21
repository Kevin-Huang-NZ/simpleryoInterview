package co.nz.simpleryo.shop.exception;

/**
 * Created by huanghao on 2016-8-7.
 */
public class ResourceNotEnough extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4653336975370799942L;
	private static final String defaultMessage = "资源不足。";
    public ResourceNotEnough(){
        super(defaultMessage);
    }

    public ResourceNotEnough(String message) {
        super(message);
    }

    public ResourceNotEnough(Throwable cause) {
        super(cause);
    }

    public ResourceNotEnough(String message, Throwable cause) {
        super(message, cause);
    }
}
