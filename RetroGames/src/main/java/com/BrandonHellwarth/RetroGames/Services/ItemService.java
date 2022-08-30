package com.BrandonHellwarth.RetroGames.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BrandonHellwarth.RetroGames.Models.Category;
import com.BrandonHellwarth.RetroGames.Models.Item;
import com.BrandonHellwarth.RetroGames.Repositories.CategoryRepository;
import com.BrandonHellwarth.RetroGames.Repositories.ItemRepository;
import com.BrandonHellwarth.RetroGames.Repositories.UserRepository;

@Service
public class ItemService {
	@Autowired
	private ItemRepository itemRepo;
	@Autowired
	private CategoryRepository categoryRepo;
	@Autowired
	private UserRepository userRepo;
	
	
	public Iterable<Item> allItems(){
		return itemRepo.findAll();
	}
	
	public ArrayList<Item> popularItems(){
		Iterable<Item> items = itemRepo.findAll();
		Integer popularity = 0;
		for(Item item : items) {
			if(item.getPopularity() > popularity) {
				popularity = item.getPopularity();
			}
		}
		ArrayList<Item> newArr = new ArrayList<Item>();
		for(int i=popularity;i>=0;i--) {
			for(Item item : items) {
				if(newArr.size() == 20) {
					return newArr;
				}
				if(item.getPopularity() == i) {
					newArr.add(item);
				}
			}
		}
		return newArr;
	}
	
	public Item findByName(String name) {
		Optional<Item> optionalItem = itemRepo.findByNameContains(name);
		if(optionalItem.isEmpty()) {
			return null;
		}
		return itemRepo.findByNameContains(name).get();
	}
	
	public Item findById(Long id) {
    	Optional<Item> potentialItem = itemRepo.findById(id);
    	if(potentialItem.isPresent()) {
    		return potentialItem.get();
    	}
    	return null;
    }
	
	public Item saveItem(Item item) {
		itemRepo.save(item);
		return item;
	}
	
	public Item updateItem(Long id, String name, String description, Float price, Integer stock) {
		Optional<Item> optionalItem = itemRepo.findById(id);
		if(optionalItem.isPresent()) {
			optionalItem.get().setName(name);
			optionalItem.get().setDescription(description);
			optionalItem.get().setPrice(price);
			optionalItem.get().setStock(stock);
			return itemRepo.save(optionalItem.get());
		}
		else {
			return null;
		}
	}
	
	public void deleteItem(Long id) {
		itemRepo.delete(findById(id));
	}
	
	public void addUser(Long uid, Long iid) {
		Item item = itemRepo.findById(iid).get();
		item.getUsers().add(userRepo.findById(uid).get());
		itemRepo.save(item);
	}
	
	public void addCategory(Long cid, Long iid) {
		Item item = itemRepo.findById(iid).get();
		List<Category> categories = item.getCategories();
		categories.add(categoryRepo.findById(cid).get());
		item.setCategories(categories);
		itemRepo.save(item);
	}
	
//	public void saveImageFile(Long itemId, MultipartFile file) {
//
//	try {
//	    Item item = itemRepo.findById(itemId).get();
//
//	    Byte[] byteObjects = new Byte[file.getBytes().length];
//
//	    int i = 0;
//
//	    for (byte b : file.getBytes()){
//	        byteObjects[i++] = b;
//	    }
//
//	    item.setImage(byteObjects);
//
//	    itemRepo.save(item);
//	} catch (IOException e) {
//	    System.out.println("error occured");
//	}
//	}
}
