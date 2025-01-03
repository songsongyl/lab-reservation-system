package org.example.labreservationsystem.repository;

import org.example.labreservationsystem.dox.Lab;
import org.example.labreservationsystem.dto.LabCountDTO;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AdminRepository extends CrudRepository<Lab,String> {
    @Transactional
    @Query("""
      SELECT state, count(state) as quantity from lab group by state
""")
    List<LabCountDTO> countLabByState();

    @Modifying
    @Query("""
        update user set password=:newPassword where id=:userId
""")
    void updatePasswordByUserId(String userId, String newPassword);
}
