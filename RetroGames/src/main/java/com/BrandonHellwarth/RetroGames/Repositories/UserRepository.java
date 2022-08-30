package com.BrandonHellwarth.RetroGames.Repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.BrandonHellwarth.RetroGames.Models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	Optional<User> findByEmail(String email);
	Optional<User> findByUserNameContains(String UserName);
}