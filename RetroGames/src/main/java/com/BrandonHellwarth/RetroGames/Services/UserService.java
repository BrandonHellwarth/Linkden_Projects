package com.BrandonHellwarth.RetroGames.Services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.BrandonHellwarth.RetroGames.Models.Item;
import com.BrandonHellwarth.RetroGames.Models.LoginUser;
import com.BrandonHellwarth.RetroGames.Models.User;
import com.BrandonHellwarth.RetroGames.Repositories.ItemRepository;
import com.BrandonHellwarth.RetroGames.Repositories.UserRepository;

@Service
public class UserService {
	
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ItemRepository itemRepo;
	
	public User register(User newUser, BindingResult result) {
		
		Optional<User> potentialUser = userRepo.findByEmail(newUser.getEmail());
		Optional<User> potentialUser2 = userRepo.findByUserNameContains(newUser.getUserName());
		
		if(!newUser.getPassword().equals(newUser.getConfirm())) {
		    result.rejectValue("confirm", "Matches", "The Confirm Password must match Password!");
		}
		
		if(potentialUser.isPresent()) {
			result.rejectValue("email", "Matches", "Email is already registered to a user.");
		}
		
		if(potentialUser2.isPresent()) {
			result.rejectValue("userName", "Matches", "User name is taken.");
		}
		
    	if(result.hasErrors()) {
    		return null;
    	}
    	
    	String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
    	newUser.setPassword(hashed);
        return userRepo.save(newUser);
    }
	
	
    public User login(LoginUser newLoginObject, BindingResult result) {
    	Optional<User> potentialUser = userRepo.findByEmail(newLoginObject.getEmail());
    	if(!potentialUser.isPresent()) {
    		result.rejectValue("email", "Matches", "User not found!");
    		return null;
    	}
    	if(!BCrypt.checkpw(newLoginObject.getPassword(), potentialUser.get().getPassword())) {
    		result.rejectValue("password", "Matches", "Invalid Password");
    	}
    	if(result.hasErrors()) {
    		System.out.println("working");
    		return null;
    	}
        return potentialUser.get();
    }
    
    public User updateUser(Long id, String userName, String email, String password, String confirm, BindingResult result) {
		Optional<User> optionalUser = userRepo.findById(id);
		if(optionalUser.isPresent()) {
			if(!password.equals(confirm)) {
				result.rejectValue("confirm", "Matches", "The Confirm Password must match Password!");
				return null;
			}
			optionalUser.get().setUserName(userName);
			optionalUser.get().setEmail(email);
			String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
			optionalUser.get().setPassword(hashed);
			optionalUser.get().setConfirm(confirm);
			return userRepo.save(optionalUser.get());
		}
		else {
			return null;
		}
	}
    
    public User findById(Long id) {
    	Optional<User> potentialUser = userRepo.findById(id);
    	if(potentialUser.isPresent()) {
    		return potentialUser.get();
    	}
    	return null;
    }
    
    public User findByEmail(String email) {
    	Optional<User> potentialUser = userRepo.findByEmail(email);
    	if(potentialUser.isPresent()) {
    		return potentialUser.get();
    	}
    	return null;
    }
    
    public void addItem(Long uid, Long iid) {
		User user = userRepo.findById(uid).get();
		user.getItems().add(itemRepo.findById(iid).get());
		userRepo.save(user);
	}
    
    public void clearitems(Long id) {
    	User user = userRepo.findById(id).get();
    	for(Item item : user.getItems()) {
    		item.setPopularity(item.getPopularity() + 1);
    		itemRepo.save(item);
    	}
    	user.getItems().clear();
    	userRepo.save(user);
    }
}