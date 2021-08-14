package com.example.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {
    private String id;
	@Autowired
	UserRepository repo;
	@GetMapping("/")
	  public String login() {
		  return "login";
	  }
	@GetMapping("/register")
	public String home() {
		return "register";
	}
	
	@PostMapping("/registersubmit")
	  public String registerSubmit(User user) {
		if(user.getName().length()>0 && user.getEmail().length()>0 && user.getPassword().length()>0) {
		  repo.save(user);
		  return "login";
		}
		else
			return "register";
		
	  }
	@PostMapping("/loginsubmit")
	  public ModelAndView loginSubmit(User user) {
	  User tempperson;
	  tempperson = repo.findByEmail(user.getEmail());
	  id = tempperson.getEmail();
	  ModelAndView mv  = new ModelAndView("home");
	  ModelAndView emv = new ModelAndView("register");  
	  mv.addObject(tempperson);
	  
//	  System.out.println("the user is "+id);
//	  tempperson.setAmount(100);
//	  repo.save(tempperson);
	  
	  if(tempperson!=null && tempperson.getPassword().equals(user.getPassword())) {
		 return mv;
	  }
	  return emv;
	  }
	@PostMapping("/amountsubmit")
	public ModelAndView amountSubmit(User user) {
		ModelAndView mav = new ModelAndView("home");
		User tperson = repo.findByEmail(id);
		tperson.setAmount(user.getAmount()+tperson.getAmount());
		
		repo.save(tperson);
		mav.addObject(tperson);
		return mav;
	}
	@GetMapping("/makeexpense")
	  public String expensepage(Model model) {
		  int amount = repo.findByEmail(id).getAmount();
		  model.addAttribute("amount",amount);
		  return "makeexpense";
	  }
	@PostMapping("/expensesubmit")
    public ModelAndView expsubmit(User user) {
		 User person = repo.findByEmail(id);
		  person.setAmount(person.getAmount()-user.getExpenseamount());
		  repo.save(person);
		ModelAndView mav = new ModelAndView("home");
		mav.addObject(person);
		 
		  return mav;
		 
	  }
}
