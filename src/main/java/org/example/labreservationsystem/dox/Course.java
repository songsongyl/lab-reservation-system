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
public class Course {
    @Id
    @CreatedBy
    private String id;
    private String name;
    private int quantity;
    private String semester;
    private String clazz;
    private int type;
    private String teacherId;
    private int experimentHour;
}
