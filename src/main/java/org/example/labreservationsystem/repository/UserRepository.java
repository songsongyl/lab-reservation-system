package org.example.labreservationsystem.repository;

import org.example.labreservationsystem.dox.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
