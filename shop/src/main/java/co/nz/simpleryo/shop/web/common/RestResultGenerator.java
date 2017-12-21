package co.nz.simpleryo.shop.web.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.nz.simpleryo.shop.util.JacksonMapper;


/**
 * 统一响应实体生成器。
 * 
 * @author huanghao
 *
 */
public class RestResultGenerator {

    private static final Logger logger = LoggerFactory.getLogger(RestResultGenerator.class);

    /**
     * 成功，不带数据。
     *
     * @param message 成功提示信息
     * @return ResponseResult
     */
    @SuppressWarnings("rawtypes")
	public static ResponseResult genResult(String message) {

        ResponseResult responseResult = ResponseResult.newInstance();
        responseResult.setSuccess(true);
        responseResult.setMessage(message);

        return responseResult;
    }

    /**
     * 成功，带数据。
     *
     * @param data    响应数据
     * @param message 成功提示信息
     * @return ResponseResult<T>
     */
    public static <T> ResponseResult<T> genResult(T data, String message) {

        ResponseResult<T> result = ResponseResult.newInstance();
        result.setSuccess(true);
        result.setData(data);
        result.setMessage(message);

        if (logger.isDebugEnabled()) {
            logger.debug("--------> result:{}", JacksonMapper.toJsonString(result));
        }

        return result;
    }

    /**
     * 失败。不带自定义message，使用默认的message。
     *
     * @param message 自定义错误信息
     * @return ResponseResult
     */
    @SuppressWarnings("rawtypes")
	public static ResponseResult genErrorResult(ResponseErrorEnum responseErrorEnum) {

        ResponseResult result = ResponseResult.newInstance();
        result.setSuccess(false);
        result.setErrorInfo(responseErrorEnum);

        if (logger.isDebugEnabled()) {
            logger.debug("--------> result:{}", JacksonMapper.toJsonString(result));
        }

        return result;
    }

    /**
     * 失败。带自定义message。
     *
     * @param responseErrorEnum 失败信息
     * @return ResponseResult
     */
    @SuppressWarnings("rawtypes")
	public static ResponseResult genErrorResult(ResponseErrorEnum responseErrorEnum, String message) {

        ResponseResult result = ResponseResult.newInstance();
        result.setSuccess(false);
        result.setErrorInfo(responseErrorEnum, message);

        if (logger.isDebugEnabled()) {
            logger.debug("--------> result:{}", JacksonMapper.toJsonString(result));
        }

        return result;
    }

}
