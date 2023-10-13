package org.java.pizzeria.controller;

import java.util.List;

import org.java.pizzeria.db.dto.PizzaDTO;
import org.java.pizzeria.db.pojo.Offer;
import org.java.pizzeria.db.pojo.Pizza;
import org.java.pizzeria.db.serv.IngredientService;
import org.java.pizzeria.db.serv.OfferService;
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

	@Autowired private PizzaService pizzaService;
	
	@Autowired private IngredientService ingredientService;

	@Autowired private OfferService offerService;
	
	/**** PIZZA ****/

	@GetMapping
	public String getIndex(@RequestParam(required = false) String pizza, Model model) {

		List<Pizza> pizzas = (pizza == null || pizza.isBlank()) ? pizzaService.findAll() : pizzaService.search(pizza);
		model.addAttribute("pizzas", pizzas);

		return "index";
	}

	@GetMapping("/pizza/{id}")
	public String getShow(@PathVariable int id, Model model) {

		String pageTitle = "Pizza Detail";
		Pizza pizza = pizzaService.findById(id);

		float priceVal = pizza.getPrice() / 100f;
		String priceFormat = String.format("€%,.2f", priceVal);

		model.addAttribute("pageTitle", pageTitle);
		model.addAttribute("pizza", pizza);
		model.addAttribute("priceFormat", priceFormat);

		return "pizzas/detail";
	}

	@GetMapping("/pizza/create")
	public String getCreate(Model model) {

		String pageTitle = "Add a new Pizza";

		model.addAttribute("pageTitle", pageTitle);
		model.addAttribute("pizzaDTO", new PizzaDTO());

		return "pizzas/create";
	}

	@PostMapping("/pizza/create")
	public String store(@Valid @ModelAttribute PizzaDTO pizzaDTO, BindingResult bindingResult, Model model) {

		return savePizza(pizzaDTO, bindingResult, model, new Pizza(), true);
	}

	@GetMapping("/pizza/edit/{id}")
	public String getUpdate(@PathVariable int id, Model model) {

		String pageTitle = "Edit Pizza";

		Pizza pizza = pizzaService.findById(id);
		float fPrice = (float) (pizza.getPrice() / 100f);
		PizzaDTO pizzaDTO = new PizzaDTO(pizza.getName(), pizza.getDescription(), pizza.getImg(), fPrice);

		model.addAttribute("pageTitle", pageTitle);
		model.addAttribute("pizzaDTO", pizzaDTO);

		return "pizzas/edit";
	}

	@PostMapping("/pizza/edit/{id}")
	public String update(@Valid @ModelAttribute PizzaDTO pizzaDTO, BindingResult bindingResult, Model model,
			@PathVariable int id) {

		Pizza pizza = pizzaService.findById(id);

		return savePizza(pizzaDTO, bindingResult, model, pizza, false);
	}

	@PostMapping("/pizza/delete/{id}")
	public String delete(@PathVariable int id) {

		Pizza pizza = pizzaService.findById(id);
		pizzaService.delete(pizza);

		return "redirect:/";
	}

	private String savePizza(PizzaDTO pizzaDTO, BindingResult bindingResult, Model model, Pizza pizza,
			boolean newPizza) {

		if (bindingResult.hasErrors()) {
			System.out.println("Error:");
			bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).forEach(System.out::println);

			return newPizza ? "pizzas/create" : "pizzas/edit";
		}

		pizza = dtoToEntity(pizza, pizzaDTO);

		pizzaService.save(pizza);

		return "redirect:/pizza/" + pizza.getId();

	}

	private Pizza dtoToEntity(Pizza pizza, PizzaDTO pizzaDTO) {
		pizza.setName(pizzaDTO.getName());
		pizza.setDescription(pizzaDTO.getDescription());
		pizza.setImg(pizzaDTO.getImg());
		pizza.setPrice(pizzaDTO.getIntPrice());

		return pizza;
	}

	/**** INGREDIENTS ****/
	
	/**** OFFER ****/

	@GetMapping("/offer/create")
	public String getOfferCreate(Model model) {

		String pageTitle = "Add a new Offer";
		List<Pizza> pizzas = pizzaService.findAll();

		model.addAttribute("pageTitle", pageTitle);
		model.addAttribute("offer", new Offer());
		model.addAttribute("pizzas", pizzas);

		return "offers/create";
	}

	@PostMapping("/offer/create")
	public String offerStore(@Valid @ModelAttribute Offer offer, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {

			String pageTitle = "Add a new Offer";
			List<Pizza> pizzas = pizzaService.findAll();

			model.addAttribute("pageTitle", pageTitle);
			model.addAttribute("pizzas", pizzas);

			return "offers/create";
		}

		offerService.save(offer);

		return "redirect:/pizza/" + offer.getPizza().getId();
	}

	@GetMapping("/offer/edit/{id}")
	public String getOfferUpdate(@PathVariable int id, Model model) {

		String pageTitle = "Edit Offer";
		Offer offer = offerService.findById(id);
		List<Pizza> pizzas = pizzaService.findAll();

		model.addAttribute("pageTitle", pageTitle);
		model.addAttribute("offer", offer);
		model.addAttribute("pizzas", pizzas);
		
		System.out.println("edit get");
		System.out.println("id " + offer.getId());
		System.out.println(offer);

		return "offers/edit";
	}

	@PostMapping("/offer/edit/{id}")
	public String offerUpdate(@Valid @ModelAttribute Offer offer, BindingResult bindingResult, Model model,
			@PathVariable int id) {

		System.out.println("edit post");
		System.out.println("id " + offer.getId());
		System.out.println(offer);
		
		if (bindingResult.hasErrors()) {

			String pageTitle = "Edit Offer";
			List<Pizza> pizzas = pizzaService.findAll();

			model.addAttribute("pageTitle", pageTitle);
			model.addAttribute("pizzas", pizzas);

			return "offers/edit";
		}
		
		offerService.save(offer);

		return "redirect:/pizza/" + offer.getPizza().getId();
	}
	
	@PostMapping("/offer/delete/{id}")
	public String deleteOffer(@PathVariable int id) {

		Offer offer = offerService.findById(id);
		int pId = offer.getPizza().getId();
		offerService.delete(offer);

		return "redirect:/pizza/" + pId;
	}

}
