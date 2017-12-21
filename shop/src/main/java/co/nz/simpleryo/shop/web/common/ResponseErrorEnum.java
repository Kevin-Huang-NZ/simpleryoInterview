package co.nz.simpleryo.shop.web.common;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 错误信息。
 * 
 * @author huanghao
 *
 */
public enum ResponseErrorEnum {
	
    ILLEGAL_PARAMS("ILLEGAL_PARAMS", "请求参数不合法。"),
    OBJECT_EXIST("OBJECT_EXIST", "对象已经存在。"),
    OBJECT_NOT_EXIST("OBJECT_NOT_EXIST", "对象不存在。"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "接口内部异常。"),
    NOT_ENOUGH_RESOURCE("NOT_ENOUGH_RESOURCE", "资源不足。");

    private ResponseErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @JsonValue
    private Map<String, Object> serialize() {
        Map<String, Object> valueMap = new HashMap<>(2);
        valueMap.put("code", this.code);
        valueMap.put("message", this.message);
        return valueMap;
    }

    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
