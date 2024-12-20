package org.example.labreservationsystem.repository;

import org.example.labreservationsystem.dox.User;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ListCrudRepository<User, String> {

    User findByAccount(String account);
}
