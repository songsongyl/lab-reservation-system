package org.example.labreservationsystem.dox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDateTime;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    @Id
//    @CreatedBy
  private String id;
  private String account;
  private String password;
  private String telephone;
  private String name;
  private String role;
  @ReadOnlyProperty
  private LocalDateTime createTime;
  @ReadOnlyProperty
  private LocalDateTime updateTime;
}
