package org.example.labreservationsystem.exception;

import lombok.*;
import org.example.labreservationsystem.exception.Code;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class XException extends RuntimeException{
    private Code code;
    private int number;
    private String message;

}
