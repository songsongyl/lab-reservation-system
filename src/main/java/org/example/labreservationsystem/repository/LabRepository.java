package org.example.labreservationsystem.repository;
import org.example.labreservationsystem.dox.Lab;
import org.example.labreservationsystem.dox.User;
import org.example.labreservationsystem.dto.EnableEquipmentCount;
import org.example.labreservationsystem.dto.LabCountDTO;
import org.example.labreservationsystem.dto.LabDTO;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabRepository extends CrudRepository<Lab,String> {
    @Query("""
        SELECT state, count(state) as quantity from lab group by state
""")
    List<LabCountDTO> countLabByState();

    @Query("""
        select name,enable_equipment as enable_quantity,(quantity-enable_equipment) as unable_quantity from lab
""")
    List<EnableEquipmentCount> countEnableEquipment();

    @Query("""
            select name,state,quantity,description,manager from lab;
""")
    List<LabDTO> findAllLabs();

//    @Query("""
//
//""")

}

