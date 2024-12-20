package org.example.labreservationsystem.dox;

import com.fasterxml.jackson.annotation.JsonProperty;
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
  public static final String USER = "wewe";
  public static final String ADMIN = "sqWf";
    @Id
    @CreatedBy
  private String id;
  private String account;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;
  private String telephone;
  private String name;
  private String role;
  @ReadOnlyProperty
  private LocalDateTime createTime;
  @ReadOnlyProperty
  private LocalDateTime updateTime;
}
