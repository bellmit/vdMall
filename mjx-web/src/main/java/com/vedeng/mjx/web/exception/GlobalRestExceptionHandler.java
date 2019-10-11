package com.vedeng.mjx.web.exception;

import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.exception.VedengErrorCode;
import com.vedeng.mjx.common.exception.VedengException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 全局异常处理，针对ajax响应
 * @author: Wyatt
 * @Date: 2019-05-30 13:58
 */
@ControllerAdvice(annotations = RestController.class)
public class GlobalRestExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultInfo exception(HttpServletRequest reqest, HttpServletResponse response, Exception exception) {
        logger.error("global exception"+reqest.getRequestURL().toString(), exception);
        if (exception instanceof VedengException) {
            return ResultInfo.fail(exception);
        }
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResultInfo.fail(VedengErrorCode.UNKNOWN_EXCEPTION);
    }




}

