package cn.sheeranpj.blog.gateway.handler;

import cn.sheeranpj.blog.common.enums.ResCodeEnum;
import cn.sheeranpj.blog.common.response.Result;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: sheeran
 * @create: 2025/03/24
 */
@Component
@Slf4j
public class SentinelExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException ex) throws Exception {
        ResCodeEnum resCodeEnum = ResCodeEnum.UNKNOWN;
        if (ex instanceof FlowException) {
            resCodeEnum = ResCodeEnum.SENTINEL_FLOW_ERROR;
        } else if (ex instanceof DegradeException) {
            resCodeEnum = ResCodeEnum.SENTINEL_DEGRADE_ERROR;
        } else if (ex instanceof ParamFlowException) {
            resCodeEnum = ResCodeEnum.SENTINEL_PARAM_FLOW_ERROR;
        } else if (ex instanceof SystemBlockException) {
            resCodeEnum = ResCodeEnum.SENTINEL_SYSTEM_ERROR;
        } else if (ex instanceof AuthorityException) {
            resCodeEnum = ResCodeEnum.SENTINEL_AUTHORITY_ERROR;
        }
        // http状态码
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        response.setContentType("application/json;charset=utf-8");
        String errJson = JSON.toJSONString(Result.error(resCodeEnum.getCode(), resCodeEnum.getMsg()));
        log.warn("sentinel限流异常：{}", errJson);
        new ObjectMapper().writeValue(response.getWriter(), errJson);
    }

}
