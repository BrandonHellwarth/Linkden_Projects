package com.BrandonHellwarth.RetroGames.Repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.BrandonHellwarth.RetroGames.Models.Admin;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Long>{
	Optional<Admin> findByEmail(String email);
}
