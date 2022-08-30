package com.BrandonHellwarth.RetroGames.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.BrandonHellwarth.RetroGames.Models.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long>{

}
