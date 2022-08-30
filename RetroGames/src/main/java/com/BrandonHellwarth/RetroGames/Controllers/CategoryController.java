package com.BrandonHellwarth.RetroGames.Controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.BrandonHellwarth.RetroGames.Models.Category;
import com.BrandonHellwarth.RetroGames.Services.AdminService;
import com.BrandonHellwarth.RetroGames.Services.CategoryService;
import com.BrandonHellwarth.RetroGames.Services.UserService;

@Controller
public class CategoryController {
	@Autowired
	private CategoryService categoryServ;
	@Autowired
	private UserService userServ;
	@Autowired
	private AdminService adminServ;
	
	
	@RequestMapping("/categories/{name}/{id}/{cid}")
	public String category(@PathVariable("name") String name, @PathVariable("id") Long id, @PathVariable("cid") Long cid, HttpSession session, Model model) {
		if(session.getAttribute("user") == userServ.findById(id)) {
			model.addAttribute("category1", categoryServ.findById(cid));
			model.addAttribute("categories", categoryServ.allCategories());
			model.addAttribute("user", userServ.findById(id));
			return "showCategory.jsp";
		}
		if(session.getAttribute("admin") == adminServ.findById(id)) {
			model.addAttribute("category1", categoryServ.findById(cid));
			model.addAttribute("categories", categoryServ.allCategories());
			model.addAttribute("admin", adminServ.findById(id));
			return "showCategory.jsp";
		}
		model.addAttribute("category1", categoryServ.findById(cid));
		model.addAttribute("categories", categoryServ.allCategories());
		return "showCategory.jsp";
	}
	
	//ACTION ROUTES
	@PostMapping("/processAddCategory/{id}")
	public String processAddCategory(@Valid @ModelAttribute("category") Category c, BindingResult result, @PathVariable("id") Long id) {
		if(result.hasErrors()) {
			return "addCategory.jsp";
		}
		categoryServ.saveCategory(c);
		return "redirect:/dashboard/" + id;
	}
}
