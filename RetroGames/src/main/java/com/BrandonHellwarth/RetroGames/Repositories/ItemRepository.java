package com.BrandonHellwarth.RetroGames.Repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.BrandonHellwarth.RetroGames.Models.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long>{
	Optional<Item> findByNameContains(String name);
}
