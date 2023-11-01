package com.dl.officialsite.common.advice;


import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.exception.BizException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {


    @ResponseBody
    @ExceptionHandler(value = DataAccessException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public BaseResponse handleSqlException(DataAccessException dataAccessException) {
        log.warn("catch sql exception", dataAccessException);
        BaseResponse bre = BaseResponse.failWithReason("3001", dataAccessException.getLocalizedMessage());
        return bre;
    }


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResponse handleMethodArgumentNotValidException(HttpServletRequest req, Exception e)
            throws JsonProcessingException {
        log.error("", e);
        if (e instanceof BizException) {
            BizException bizException = (BizException) e;
            return BaseResponse.failWithReason(bizException.getCode(), bizException.getMsg());
        }
        return BaseResponse.failWithReason("99999", e.getMessage());
    }

}
