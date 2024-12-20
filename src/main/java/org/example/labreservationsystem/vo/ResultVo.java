package org.example.labreservationsystem.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.labreservationsystem.exception.Code;

@Data
@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultVo {
    private int code;
    private String msg;
    private Object data;
    private static final ResultVo EMPTY = ResultVo.builder().code(200).build();
    public static ResultVo ok() {
        return EMPTY;
    }

    public static ResultVo success(Object data) {
        return ResultVo.builder().code(200).data(data).build();
    }

    public static ResultVo error(Code code) {
        return ResultVo.builder().code(code.getCode()).msg(code.getMessage()).build();
    }

    public static ResultVo error(int code, String msg) {
        return ResultVo.builder().code(code).msg(msg).build();
    }

}
