package com.BrandonHellwarth.RetroGames.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BrandonHellwarth.RetroGames.Models.Category;
import com.BrandonHellwarth.RetroGames.Repositories.CategoryRepository;
import com.BrandonHellwarth.RetroGames.Repositories.ItemRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepo;
	@Autowired
	private ItemRepository itemRepo;
	
	public Iterable<Category> allCategories(){
		return categoryRepo.findAll();
	}
	
	public Category saveCategory(Category c) {
		categoryRepo.save(c);
		return c;
	}
	
	public Category findById(Long id) {
    	Optional<Category> potentialCategory = categoryRepo.findById(id);
    	if(potentialCategory.isPresent()) {
    		return potentialCategory.get();
    	}
    	return null;
    }
	
	public void addItem(Long cid, Long iid) {
		Category category = categoryRepo.findById(cid).get();
		category.getItems().add(itemRepo.findById(iid).get());
		categoryRepo.save(category);
	}
}
