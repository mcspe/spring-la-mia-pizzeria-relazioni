package org.java.pizzeria.db.repo;

import java.util.List;

import org.java.pizzeria.db.pojo.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepo extends JpaRepository<Pizza, Integer> {
	
	public List<Pizza> findByNameContainingOrDescriptionContaining(String name, String description);

}
