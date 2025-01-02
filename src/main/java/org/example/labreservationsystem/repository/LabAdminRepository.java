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
public interface LabAdminRepository extends CrudRepository<Lab,String> {
    @Query("""
update lab set state = :state where id = :id;
""")
    @Modifying
    void updateState(String id,int state);

}
