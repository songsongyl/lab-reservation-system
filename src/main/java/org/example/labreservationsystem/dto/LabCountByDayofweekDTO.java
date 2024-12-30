package org.example.labreservationsystem.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LabCountByDayofweekDTO {
    private int dayofweek;
    private int quantity;
}
