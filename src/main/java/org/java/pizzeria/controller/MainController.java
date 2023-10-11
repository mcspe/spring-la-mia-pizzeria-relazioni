package org.java.pizzeria.controller;

import java.util.List;

import org.java.pizzeria.db.pojo.Pizza;
import org.java.pizzeria.db.pojo.PizzaDTO;
import org.java.pizzeria.db.serv.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class MainController {
	
	@Autowired
	private PizzaService pizzaService;
	
	@GetMapping
	public String getIndex(@RequestParam(required = false) String pizza, Model model) {
		
		List<Pizza> pizzas = (pizza == null || pizza.isBlank())	? pizzaService.findAll() 
												: pizzaService.search(pizza);
		model.addAttribute("pizzas", pizzas);
		
		return "index";	
	}
	
	@GetMapping("/details/{id}")
	public String getShow(@PathVariable int id, Model model) {
		
		String pageTitle = "Pizza Detail";
		Pizza pizza = pizzaService.findById(id);
		
		float priceVal = pizza.getPrice() / 100f;
		String priceFormat = String.format("€%,.2f", priceVal);
		
		model.addAttribute("pageTitle", pageTitle);
		model.addAttribute("pizza", pizza);
		model.addAttribute("priceFormat", priceFormat);
		
		return "detail";
	}

	@GetMapping("/create")
	public String getCreate(Model model) {
		
		String pageTitle = "Add a new Pizza";
		
		model.addAttribute("pageTitle", pageTitle);
		model.addAttribute("pizzaDTO", new PizzaDTO());
		
		return "create";
	}
	
	@PostMapping("/create")
	public String store(
			@Valid @ModelAttribute PizzaDTO pizzaDTO, 
			BindingResult bindingResult, 
			Model model
			) {
				
		return savePizza(pizzaDTO, bindingResult, model, new Pizza(), true);
		
	}
	
	@GetMapping("/edit/{id}")
	public String getUpdate(@PathVariable int id, Model model) {
		
		String pageTitle = "Edit Pizza";
		
		Pizza pizza = pizzaService.findById(id);
		float fPrice = (float)(pizza.getPrice() / 100f);
		PizzaDTO pizzaDTO = new PizzaDTO(pizza.getName(), pizza.getDescription(), pizza.getImg(), fPrice);
		
		model.addAttribute("pageTitle", pageTitle);
		model.addAttribute("pizzaDTO", pizzaDTO);
		
		return "edit";
	}
	
	@PostMapping("/edit/{id}")
	public String update(
			@Valid @ModelAttribute PizzaDTO pizzaDTO, 
			BindingResult bindingResult, 
			Model model,
			@PathVariable int id
		) {
		
		Pizza pizza = pizzaService.findById(id);
		
		return savePizza(pizzaDTO, bindingResult, model, pizza, false);
		
	}
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		
		Pizza pizza = pizzaService.findById(id);
		pizzaService.delete(pizza);
		
		return "redirect:/";
	}
	
	private String savePizza(PizzaDTO pizzaDTO, BindingResult bindingResult, Model model, Pizza pizza, boolean newPizza) {
		
		if(bindingResult.hasErrors()) {
			System.out.println("Error:");
			bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).forEach(System.out::println);
					
			return newPizza ? "create" : "edit";
		}

		pizza = dtoToEntity(pizza, pizzaDTO);
		
		pizzaService.save(pizza);
		
		return "redirect:/details/" + pizza.getId();
		
	}
	
	private Pizza dtoToEntity(Pizza pizza, PizzaDTO pizzaDTO) {
		pizza.setName(pizzaDTO.getName());
		pizza.setDescription(pizzaDTO.getDescription());
		pizza.setImg(pizzaDTO.getImg());
		pizza.setPrice(pizzaDTO.getIntPrice());
		
		return pizza;
	}
	
}
