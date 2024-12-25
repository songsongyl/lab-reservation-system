package org.example.labreservationsystem.repository;

import org.example.labreservationsystem.dox.Lab;
import org.example.labreservationsystem.dox.User;
import org.example.labreservationsystem.dto.LabCountDTO;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AdminRepository extends CrudRepository<Lab,String> {
    @Transactional
    @Query("""
       SELECT
           CASE
               WHEN state = '0' THEN 'repairLab'
               WHEN state = '1' THEN 'leisureLab'
               ELSE 'useLab'
               END AS state_name,
           count(state) AS account
       FROM lab
       GROUP BY state;
       
""")
    List<LabCountDTO> countLabByState();

}
