package org.example.labreservationsystem.vo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.labreservationsystem.exception.Code;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO {
    //vo类，返回操作业务码，和对应的信息
    private int code;       //操作出错，返回业务码
    private String message; // 操作出错，返回出错信息
    private Object data;   //操作成功，返回200业务码和具体信息，因为不知道什么类型，所以使用object


    //操作成功，返回
    public static ResultVO success(Object data) {
        return ResultVO.builder()
                .code(200)
                .data(data)
                .build();
    }

    //操作失败，通用错误枚举
    public static ResultVO error(Code code) {
        return ResultVO.builder()
                .code(code.getCode())
                .message(code.getMessage())
                .build();
    }

    public static ResultVO error(int code, String message) {
        return ResultVO.builder()
                .code(code)
                .message(message)
                .build();
    }
}