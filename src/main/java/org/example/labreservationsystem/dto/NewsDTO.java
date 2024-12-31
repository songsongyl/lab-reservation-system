package org.example.labreservationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTO {
    private String id;
    private String title;
    private String author;
    private String content;
    private LocalDateTime updateTime;
}
