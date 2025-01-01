package org.example.labreservationsystem.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.labreservationsystem.dox.Appointment;
import org.example.labreservationsystem.dox.Course;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Appointment1DTO {
    private Appointment appointment;
    private Course course;
}
