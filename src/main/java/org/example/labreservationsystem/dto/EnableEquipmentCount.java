package org.example.labreservationsystem.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnableEquipmentCount {
    private String name;
    private int enableQuantity;
    private int unableQuantity;
}
