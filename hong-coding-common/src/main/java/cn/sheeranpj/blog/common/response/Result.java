package cn.sheeranpj.blog.common.response;

import cn.sheeranpj.blog.common.enums.ResCodeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author sheeran
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "code", "message", "data", "timestamp" })
public class Result<T> implements Serializable {

    private Integer code;
    private String message;
    private T data;
    /**
     * 返回时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime timestamp;

    public Result(ResCodeEnum responseCode, T data) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMsg();
        this.timestamp = LocalDateTime.now();
        this.data = data;

        if (data != null && BaseOperateResponse.class.isAssignableFrom(data.getClass())) {
            // 前端需要将Reason放入到msg中
            String reason = ((BaseOperateResponse) data).getReason();
            if (StringUtils.hasText(reason)) {
                this.message = reason;
            }
        }
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResCodeEnum.ok.getCode());
        result.setMessage("success");
        result.setData(data);
        result.setTimestamp(LocalDateTime.now());
        return result;
    }

    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode(ResCodeEnum.NO_SERVICE.getCode());
        result.setMessage(message);
        result.setTimestamp(LocalDateTime.now());
        return result;
    }

    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setTimestamp(LocalDateTime.now());
        return result;
    }
} 