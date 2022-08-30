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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.BrandonHellwarth.RetroGames.Models.Item;
import com.BrandonHellwarth.RetroGames.Models.User;
import com.BrandonHellwarth.RetroGames.Services.AdminService;
import com.BrandonHellwarth.RetroGames.Services.CategoryService;
import com.BrandonHellwarth.RetroGames.Services.ItemService;
import com.BrandonHellwarth.RetroGames.Services.UserService;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemServ;
	@Autowired
	private UserService userServ;
	@Autowired
	private AdminService adminServ;
	@Autowired
	private CategoryService categoryServ;
	
	
	@RequestMapping("/items/show/{id}/{iid}")
	public String showItem(@PathVariable("id") Long id, @PathVariable("iid") Long iid, HttpSession session, Model model) {
		if(session.getAttribute("user") == userServ.findById(id)) {
			model.addAttribute("categories", categoryServ.allCategories());
			model.addAttribute("item", itemServ.findById(iid));
			model.addAttribute("user", userServ.findById(id));
			return "showItem.jsp";
		}
		if(session.getAttribute("admin") == adminServ.findById(id)) {
			model.addAttribute("categories", categoryServ.allCategories());
			model.addAttribute("item", itemServ.findById(iid));
			model.addAttribute("admin", adminServ.findById(id));
			return "showItem.jsp";
		}
		model.addAttribute("categories", categoryServ.allCategories());
		model.addAttribute("item", itemServ.findById(iid));
		return "showItem.jsp";
	}
	
	//ACTION ROUTES
	
	@PostMapping("/processAddItem/{id}")
	public String addItem(RedirectAttributes redirectAttributes, @RequestParam("category") Long cid, @PathVariable("id") Long id, @Valid @ModelAttribute("item") Item item, BindingResult result) {
		if(result.hasErrors()) {
			return "addItem.jsp";
		}
		if(itemServ.findByName(item.getName()) != null) {
			redirectAttributes.addFlashAttribute("error", "Product name already taken.");
			return "redirect:/admins/addItem/" + id;
		}
		itemServ.saveItem(item);
		categoryServ.addItem(cid, itemServ.findByName(item.getName()).getId());
		return "redirect:/dashboard/" + id;
	}
	
	@PutMapping("/processEditItem/{id}/{iid}")
	public String editItem(@Valid @ModelAttribute("item1") Item item, BindingResult result, @PathVariable("id") Long id, @PathVariable("iid") Long iid) {
		if(result.hasErrors()) {
			return "editItem.jsp";
		}
		itemServ.updateItem(iid, item.getName(), item.getDescription(), item.getPrice(), item.getStock());
		return "redirect:/items/show/" + adminServ.findById(id).getId() + "/" + itemServ.findById(iid).getId();
	}
	
	@RequestMapping("/items/delete/{aid}/{iid}")
	public String deleteItem(@PathVariable("aid") Long aid, @PathVariable("iid") Long iid, HttpSession session) {
		if(session.getAttribute("admin") == null) {
			session.setAttribute("admin", null);
			session.setAttribute("user", null);
			return "redirect:/dashboard/0";
		}
		itemServ.deleteItem(iid);
		return "redirect:/dashboard/" + adminServ.findById(aid).getId();
	}
	
	@RequestMapping("/items/addToCart/{uid}/{iid}")
	public String addToCart(@PathVariable("uid") Long uid, @PathVariable("iid") Long iid, HttpSession session) {
		User user0 = (User)session.getAttribute("user");
		if(user0 == null || user0.getId() != uid) {
			session.setAttribute("admin", null);
			session.setAttribute("user", null);
			return "redirect:/dashboard/0";
		}
		userServ.addItem(uid, iid);
		return "redirect:/users/cart/" + uid;
	}
}
