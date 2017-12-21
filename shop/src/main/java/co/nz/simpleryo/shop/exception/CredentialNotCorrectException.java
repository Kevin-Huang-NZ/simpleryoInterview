package co.nz.simpleryo.shop.exception;

/**
 * Created by huanghao on 2016-6-21.
 */
public class CredentialNotCorrectException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2583439956632045930L;
	
	private static final String defaultMessage = "未经授权，访问受限。";
    public CredentialNotCorrectException() {
        super(defaultMessage);
    }

    public CredentialNotCorrectException(String message) {
        super(message);
    }

    public CredentialNotCorrectException(Throwable cause) {
        super(cause);
    }

    public CredentialNotCorrectException(String message, Throwable cause) {
        super(message, cause);
    }
}
