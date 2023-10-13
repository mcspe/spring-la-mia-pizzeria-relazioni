package org.java.pizzeria.db.serv;

import java.util.List;

import org.java.pizzeria.db.pojo.Ingredient;
import org.java.pizzeria.db.repo.IngredientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {
	
	@Autowired
	private IngredientRepo ingredientRepo;
	
	public void save(Ingredient ingredient) {
		ingredientRepo.save(ingredient);
	}
	
	public List<Ingredient> findAll() {
		return ingredientRepo.findAll();
	}

	public Ingredient findById(int id) {
		return ingredientRepo.findById(id).get();
	}
	
	public void delete(Ingredient ingredient) {
		ingredientRepo.delete(ingredient);
	}

}
