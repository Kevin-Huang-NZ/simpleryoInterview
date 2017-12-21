package co.nz.simpleryo.shop.web.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 响应实体。
 * 
 * @author huanghao
 *
 * @param <T>
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseResult<T> {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ResponseResult.class);
	
	private boolean success;
	private String message;
	private T data;
	private String errorCode;
	
	private ResponseResult(){
		
	}
	
	public static <T> ResponseResult<T> newInstance(){
		return new ResponseResult<>();
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getErrorCode() {
		return errorCode;
	}
	
//	public void setErrorCode(String errorCode) {
//		this.errorCode = errorCode;
//	}

    public void setErrorInfo(ResponseErrorEnum responseErrorEnum, String... messages ) {
        this.errorCode = responseErrorEnum.getCode();
        if (messages == null || messages.length == 0) {
            this.message = responseErrorEnum.getMessage();
        } else {
//        	logger.info("messages:{}{}{}", messages);
            this.message = messages[0];
        }
    }
	
}
