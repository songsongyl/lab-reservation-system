package org.example.labreservationsystem.repository;


import org.example.labreservationsystem.dox.User;

import org.example.labreservationsystem.dto.Appointment1DTO;
import org.example.labreservationsystem.mapper.Appointment1DTOResultSetExtractor;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends ListCrudRepository<User, String> {

    User findByAccount(String account);

    @Query("""
select * from user u where id = :id;
""")
    User findByUserId(String id);


}
