package cn.sheeranpj.blog.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author: sheeran
 * @create: 2025/03/26
 */
@Data
public class BaseOperateResponse {

    @Schema(description = "是否成功")
    private boolean success = false;

    @Schema(description = "失败原因")
    private String reason;
}
