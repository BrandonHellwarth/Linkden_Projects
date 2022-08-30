package com.BrandonHellwarth.RetroGames.Controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.BrandonHellwarth.RetroGames.Models.ChargeRequest;
import com.BrandonHellwarth.RetroGames.Models.Item;
import com.BrandonHellwarth.RetroGames.Models.LoginUser;
import com.BrandonHellwarth.RetroGames.Models.User;
import com.BrandonHellwarth.RetroGames.Services.AdminService;
import com.BrandonHellwarth.RetroGames.Services.CategoryService;
import com.BrandonHellwarth.RetroGames.Services.ItemService;
import com.BrandonHellwarth.RetroGames.Services.UserService;

@Controller
public class UserController {
	@Value("pk_test_51Lbve0BCPnUk3hBuTTmMk6RzTXy2qDgqkSPrLbqrUdOcvXE5mwlemEwolDs8Gbeys2fr4mzRKw3JZWAfAK3GwJLI00SOjds14E")
    private String stripePublicKey;
	@Autowired
	private AdminService adminServ;
	@Autowired
	private UserService userServ;
	@Autowired
	private ItemService itemServ;
	@Autowired
	private CategoryService categoryServ;
	
	
	@RequestMapping("/dashboard/{id}")
	public String userDashboard(@PathVariable("id") Long id, Model model, HttpSession session) {
		if(session.getAttribute("user") == userServ.findById(id)) {
			model.addAttribute("categories", categoryServ.allCategories());
			model.addAttribute("items", itemServ.popularItems());
			model.addAttribute("user", userServ.findById(id));
			return "dashboard.jsp";
		}
		if(session.getAttribute("admin") == adminServ.findById(id)) {
			model.addAttribute("categories", categoryServ.allCategories());
			model.addAttribute("items", itemServ.popularItems());
			model.addAttribute("admin", adminServ.findById(id));
			return "dashboard.jsp";
		}
		model.addAttribute("categories", categoryServ.allCategories());
		model.addAttribute("items", itemServ.popularItems());
		return "dashboard.jsp";
	}
	
	@RequestMapping("/users/register")
	public String registration(@ModelAttribute("user") User user, HttpSession session) {
		if(session.getAttribute("admin") != null || session.getAttribute("user") != null) {
			session.setAttribute("admin", null);
			session.setAttribute("user", null);
			return "redirect:/dashboard/0";
		}
		return "register.jsp";
	}
	
	@RequestMapping("/users/login")
	public String login(@ModelAttribute("userLogin") LoginUser userLogin, HttpSession session) {
		if(session.getAttribute("admin") != null || session.getAttribute("user") != null) {
			session.setAttribute("admin", null);
			session.setAttribute("user", null);
			return "redirect:/dashboard/0";
		}
		return "login.jsp";
	}
	
	@RequestMapping("/users/accountInfo/{id}")
	public String accountInfo(@PathVariable("id") Long id, HttpSession session, Model model) {
		User user1 = (User)session.getAttribute("user");
		if(user1 == null || user1.getId() != id) {
			session.setAttribute("admin", null);
			session.setAttribute("user", null);
			return "redirect:/dashboard/0";
		}
		model.addAttribute("user", userServ.findById(id));
		return "accountInfo.jsp";
	}
	
	@RequestMapping("/users/accountEdit/{id}")
	public String editAccount(@ModelAttribute("user1") User user1, @PathVariable("id") Long id, HttpSession session, Model model) {
		User user0 = (User)session.getAttribute("user");
		if(user0 == null || user0.getId() != id) {
			session.setAttribute("admin", null);
			session.setAttribute("user", null);
			return "redirect:/dashboard/0";
		}
		model.addAttribute("user", userServ.findById(id));
		return "accountEdit.jsp";
	}
	
	@RequestMapping("/users/cart/{id}")
	public String cart(@PathVariable("id") Long id, HttpSession session, Model model) {
		User user0 = (User)session.getAttribute("user");
		if(user0 == null || user0.getId() != id) {
			session.setAttribute("admin", null);
			session.setAttribute("user", null);
			return "redirect:/dashboard/0";
		}
		User user9 = userServ.findById(id);
		Float total = 0.00f;
		for(Item item : user9.getItems()) {
			total = total + item.getPrice();
		}
        model.addAttribute("amount", total * 100); // in cents
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.USD);
		model.addAttribute("user", userServ.findById(id));
		return "cart.jsp";
	}
	
	//ACTION ROUTES
	@PostMapping("/processRegister")
	public String register(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
		userServ.register(user, result);
		if(result.hasErrors()) {
			return "register.jsp";
		}
		session.setAttribute("user", userServ.findByEmail(user.getEmail()));
		return "redirect:/dashboard/" + userServ.findByEmail(user.getEmail()).getId();
	}
	
	
	@PostMapping("/processLogin")
	public String login(@Valid @ModelAttribute("userLogin") LoginUser userLogin, BindingResult result, HttpSession session) {
		userServ.login(userLogin, result);
		if(result.hasErrors()) {
            return "login.jsp";
        }
		User user = userServ.findByEmail(userLogin.getEmail());
		session.setAttribute("user", user);
		return "redirect:/dashboard/" + user.getId();
	}
	
	@PutMapping("/processEditUser/{id}")
	public String editUser(@Valid @ModelAttribute("user1") User user1, BindingResult result, @PathVariable("id") Long id) {
		userServ.updateUser(id, user1.getUserName(), user1.getEmail(), user1.getPassword(), user1.getConfirm(), result);
		if(result.hasErrors()) {
			System.out.println(result);
			return "accountEdit.jsp";
		}
		return "redirect:/users/accountInfo/" + id;
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		if(session.getAttribute("admin") != null) {
			session.removeAttribute("admin");
			return "redirect:/dashboard/0";
		}
		session.removeAttribute("user");
		return "redirect:/dashboard/0";
	}
}
