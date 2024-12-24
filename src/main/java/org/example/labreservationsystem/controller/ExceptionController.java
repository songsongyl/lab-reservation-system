package org.example.labreservationsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.exception.Code;
import org.example.labreservationsystem.exception.XException;
import org.example.labreservationsystem.vo.ResultVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(XException.class)
    public ResultVO handleXException(XException e) {
        if(e.getCode() != null){
            return ResultVO.error(e.getCode());
        }
        log.error(e.getMessage());
        return ResultVO.error(e.getNumber(),e.getMessage());
    }
    //兜底异常
    @ExceptionHandler(Exception.class)
    public ResultVO handleException(Exception e) {
        log.error(e.getMessage());
        return ResultVO.error(Code.ERROR,e.getMessage());
    }

}
