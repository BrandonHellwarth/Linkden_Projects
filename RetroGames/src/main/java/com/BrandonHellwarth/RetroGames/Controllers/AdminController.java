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

import com.BrandonHellwarth.RetroGames.Models.Admin;
import com.BrandonHellwarth.RetroGames.Models.Category;
import com.BrandonHellwarth.RetroGames.Models.Item;
import com.BrandonHellwarth.RetroGames.Models.LoginAdmin;
import com.BrandonHellwarth.RetroGames.Services.AdminService;
import com.BrandonHellwarth.RetroGames.Services.CategoryService;
import com.BrandonHellwarth.RetroGames.Services.ItemService;

@Controller
public class AdminController {
	
	@Autowired
	private AdminService adminServ;
	@Autowired
	private ItemService itemServ;
	@Autowired
	private CategoryService categoryServ;
	
	@RequestMapping("/admins/login")
	public String adminLogin(@ModelAttribute("admin") LoginAdmin admin, HttpSession session) {
		if(session.getAttribute("admin") != null || session.getAttribute("user") != null) {
			session.setAttribute("admin", null);
			session.setAttribute("user", null);
			return "redirect:/dashboard/0";
		}
		return "adminLogin.jsp";
	}
	
	@RequestMapping("/admins/addItem/{id}")
	public String addItem(@ModelAttribute("item") Item item, HttpSession session, Model model, @PathVariable("id") Long id) {
		if(session.getAttribute("admin") == null) {
			session.setAttribute("admin", null);
			session.setAttribute("user", null);
			return "redirect:/dashboard/0";
		}
		model.addAttribute("categories", categoryServ.allCategories());
		model.addAttribute("admin", adminServ.findById(id));
		return "addItem.jsp";
	}
	
	@RequestMapping("/admins/editItem/{aid}/{iid}")
	public String editItem(@ModelAttribute("item1") Item item, HttpSession session, Model model, @PathVariable("aid") Long aid, @PathVariable("iid") Long iid) {
		if(session.getAttribute("admin") == null) {
			session.setAttribute("admin", null);
			session.setAttribute("user", null);
			return "redirect:/dashboard/0";
		}
		model.addAttribute("admin", adminServ.findById(aid));
		model.addAttribute("item", itemServ.findById(iid));
		return "editItem.jsp";
	}
	
	@RequestMapping("/admins/addCategory/{id}")
	public String addCategory(@ModelAttribute("category") Category c, @PathVariable("id") Long id, HttpSession session, Model model) {
		if(session.getAttribute("admin") == null) {
			session.setAttribute("admin", null);
			session.setAttribute("user", null);
			return "redirect:/dashboard/0";
		}
		model.addAttribute("admin", adminServ.findById(id));
		return "addCategory.jsp";
	}
	
	//ACTION ROUTES
	
	@PostMapping("/processAdminLogin")
	public String loginAdmin(@Valid @ModelAttribute("admin") LoginAdmin admin, BindingResult result, HttpSession session) {
		adminServ.login(admin, result);
		if(result.hasErrors()) {
			return "adminLogin.jsp";
		}
		Admin admin1 = adminServ.findByEmail(admin.getEmail());
		session.setAttribute("admin", admin1);
		return "redirect:/dashboard/" + admin1.getId();
	}
	
	@RequestMapping("/logoutAdmin")
	public String logout(HttpSession session) {
		if(session.getAttribute("user") != null) {
			session.removeAttribute("user");
			return "redirect:/dashboard/0";
		}
		session.removeAttribute("admin");
		return "redirect:/dashboard/0";
	}
}
