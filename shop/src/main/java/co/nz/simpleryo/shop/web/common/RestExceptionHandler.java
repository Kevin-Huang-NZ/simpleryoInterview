package co.nz.simpleryo.shop.web.common;


import javax.validation.UnexpectedTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import co.nz.simpleryo.shop.exception.ObjectExistException;
import co.nz.simpleryo.shop.exception.ObjectNotExistException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;

/**
 * RestController全局异常处理类。
 * 
 * @author huanghao
 *
 */
@ControllerAdvice(basePackages = {"com.seekgd.ssm.rest"})
public class RestExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    /**
     * 统一的rest接口异常处理器
     *
     * @param e 捕获的异常
     * @return 异常信息
     */
    @SuppressWarnings("unchecked")
	@ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private <T> ResponseResult<T> globalExceptionHandler(Exception e) {
        if ((e instanceof BindException) || (e instanceof MethodArgumentNotValidException) || (e instanceof UnexpectedTypeException)) {  
            return illegalParamsExceptionHandler(e);  
        }  
    	logger.error("API接口异常。", e);
        return RestResultGenerator.genErrorResult(ResponseErrorEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * bean校验未通过异常
     *
     * form提交是BindException 
     * application/json提交是MethodArgumentNotValidException 
     * 
     * @see javax.validation.Valid
     * @see org.springframework.validation.Validator
     * @see org.springframework.validation.DataBinder
     */
    @SuppressWarnings("unchecked")
	@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private <T> ResponseResult<T> illegalParamsExceptionHandler(Exception e) {
        return RestResultGenerator.genErrorResult(ResponseErrorEnum.ILLEGAL_PARAMS, e.getMessage());
    }

    /**
     * 对象已经存在。
     */
    @SuppressWarnings("unchecked")
	@ExceptionHandler(ObjectExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    private <T> ResponseResult<T> objectExistExceptionHandler(ObjectExistException e) {
        return RestResultGenerator.genErrorResult(ResponseErrorEnum.OBJECT_EXIST, e.getMessage());
    }

    /**
     * 对象不存在。
     */
    @SuppressWarnings("unchecked")
	@ExceptionHandler(ObjectNotExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private <T> ResponseResult<T> objectNotExistExceptionHandler(ObjectNotExistException e) {
        return RestResultGenerator.genErrorResult(ResponseErrorEnum.OBJECT_NOT_EXIST, e.getMessage());
    }
}
