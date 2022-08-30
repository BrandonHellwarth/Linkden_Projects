package com.BrandonHellwarth.RetroGames.Services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.BrandonHellwarth.RetroGames.Models.Admin;
import com.BrandonHellwarth.RetroGames.Models.LoginAdmin;
import com.BrandonHellwarth.RetroGames.Repositories.AdminRepository;

@Service
public class AdminService {
	
	
	@Autowired
	private AdminRepository adminRepo;
	
	
	public Admin login(LoginAdmin newLoginObject, BindingResult result) {
    	Optional<Admin> potentialAdmin = adminRepo.findByEmail(newLoginObject.getEmail());
    	if(!potentialAdmin.isPresent()) {
    		result.rejectValue("email", "Matches", "Admin not found!");
    		return null;
    	}
    	if(!BCrypt.checkpw(newLoginObject.getPassword(), potentialAdmin.get().getPassword())) {
    		result.rejectValue("password", "Matches", "Invalid Password");
    		return null;
    	}
    	if(result.hasErrors()) {
    		return null;
    	}
        return potentialAdmin.get();
    }
	
	public Admin findByEmail(String email) {
    	Optional<Admin> potentialAdmin = adminRepo.findByEmail(email);
    	if(potentialAdmin.isPresent()) {
    		return potentialAdmin.get();
    	}
    	return null;
    }
	
	public Admin findById(Long id) {
    	Optional<Admin> potentialAdmin = adminRepo.findById(id);
    	if(potentialAdmin.isPresent()) {
    		return potentialAdmin.get();
    	}
    	return null;
    }
}
