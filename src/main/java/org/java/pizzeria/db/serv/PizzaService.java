package org.java.pizzeria.db.serv;

import java.util.List;

import org.java.pizzeria.db.pojo.Pizza;
import org.java.pizzeria.db.repo.PizzaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {

	@Autowired
	private PizzaRepo pizzaRepo;

	public void save(Pizza pizza) {
		pizzaRepo.save(pizza);
	}

	public List<Pizza> findAll() {
		return pizzaRepo.findAll();
	}

	public Pizza findById(int id) {
		return pizzaRepo.findById(id).get();
	}
	
	public List<Pizza> search(String pizza) {
		return pizzaRepo.findByNameContainingOrDescriptionContaining(pizza, pizza);
	}
	
	public void delete(Pizza pizza) {
		pizzaRepo.delete(pizza);
	}

}
