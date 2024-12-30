package org.example.labreservationsystem.dox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @Id
    @CreatedBy
    private String id;
    private String teacher;
    private String course;
    private String semester;
    private String nature;
    private String labId;
    private String labName;
    private int week;
    private int dayofweek;
    private int section;
}
