package org.java.pizzeria;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.java.pizzeria.db.pojo.Ingredient;
import org.java.pizzeria.db.pojo.Offer;
import org.java.pizzeria.db.pojo.Pizza;
import org.java.pizzeria.db.serv.IngredientService;
import org.java.pizzeria.db.serv.OfferService;
import org.java.pizzeria.db.serv.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringLaMiaPizzeriaCrudApplication implements CommandLineRunner {
	
	@Autowired
	private PizzaService pizzaService;
	
	@Autowired
	private OfferService offerService;
	
	@Autowired
	private IngredientService ingredientService;

	public static void main(String[] args) {
		SpringApplication.run(SpringLaMiaPizzeriaCrudApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Ingredient i1 = new Ingredient("Pomodoro");
		Ingredient i2 = new Ingredient("Mozzarella");
		Ingredient i3 = new Ingredient("Basilico");
		Ingredient i4 = new Ingredient("Spianata calabra");
		Ingredient i5 = new Ingredient("Aglio");
		Ingredient i6 = new Ingredient("Origano");
		Ingredient i7 = new Ingredient("Verdure grigliate");
		Ingredient i8 = new Ingredient("Funghi");
		Ingredient i9 = new Ingredient("Prosciutto cotto");
		Ingredient i10 = new Ingredient("Carciofi");
		Ingredient i11 = new Ingredient("Bresaola");
		Ingredient i12 = new Ingredient("Grana a scaglie");
		Ingredient i13 = new Ingredient("Ragù napoletano");
		Ingredient i14 = new Ingredient("Polpette");
		Ingredient i15 = new Ingredient("Mozzarella di Bufala");
		
		ingredientService.save(i1);
		ingredientService.save(i2);
		ingredientService.save(i3);
		ingredientService.save(i4);
		ingredientService.save(i5);
		ingredientService.save(i6);
		ingredientService.save(i7);
		ingredientService.save(i8);
		ingredientService.save(i9);
		ingredientService.save(i10);
		ingredientService.save(i11);
		ingredientService.save(i12);
		ingredientService.save(i13);
		ingredientService.save(i14);
		ingredientService.save(i15);
		
		Pizza p1 = new Pizza("Margherita", 
							"Pomodoro, mozzarella e basilico", 
							"https://images.prismic.io/eataly-us/ed3fcec7-7994-426d-a5e4-a24be5a95afd_pizza-recipe-main.jpg?auto=compress,format", 
							650,
							i1, i2, i3);
		Pizza p2 = new Pizza("Diavola", 
							"Pomodoro, mozzarella e spianata calabra", 
							"https://www.silviocicchi.com/pizzachef/wp-content/uploads/2015/03/d2.jpg", 
							850,
							i1, i2, i4);
		Pizza p3 = new Pizza("Marinara", 
							"Pomodoro, aglio e origano", 
							"https://www.melarossa.it/wp-content/uploads/2022/05/pizza-marinara.jpg", 
							600,
							i1, i5, i6);
		Pizza p4 = new Pizza("Vegetariana",
							"Pomodoro, mozzarella e verdure grigliate", 
							"https://patrioli.it/wp-content/uploads/2022/01/immagine-1.png", 
							750,
							i1, i2, i7);
		Pizza p5 = new Pizza("Capricciosa", 
							"Pomodoro, mozzarella, funghi, prosciutto cotto e carciofi", 
							"https://primochef.it/wp-content/uploads/2018/05/SH_pizza_capricciosa.jpg", 
							850,
							i1, i2, i8, i9, i10);
		Pizza p6 = new Pizza("Valtellina", 
							"Pomodoro, mozzarella, bresaola, grana a scaglie",
							"https://www.isitsalumi.it/wp-content/uploads/2021/01/07_BRASAOLA_FIN.jpg",
							900,
							i1, i2, i11, i12);
		Pizza p7 = new Pizza("Gourmet", 
							"Ragù napoletano, polpette e mozzarella di Bufala", 
							"https://www.giginoefigli.it/wp-content/uploads/2019/10/Golosa-pizza-con-ragu%CC%80-e-polpettine-del-mese-di-Ottobre.jpg", 
							1200,
							i13, i14, i15);
		
		pizzaService.save(p1);
		pizzaService.save(p2);
		pizzaService.save(p3);
		pizzaService.save(p4);
		pizzaService.save(p5);
		pizzaService.save(p6);
		pizzaService.save(p7);
		
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d/MM/uuuu");
		
		Offer o1 = new Offer("Offerta autunno", 15, LocalDate.parse("21/09/2023", formatters), LocalDate.parse("20/12/2023", formatters), p6);
		Offer o2 = new Offer("Offerta inverno", 18, LocalDate.parse("21/12/2023", formatters), LocalDate.parse("20/03/2024", formatters), p2);
		Offer o3 = new Offer("Offerta primavera", 10, LocalDate.parse("21/03/2024", formatters), LocalDate.parse("20/06/2024", formatters), p4);
		Offer o4 = new Offer("Offerta estate", 20, LocalDate.parse("21/06/2024", formatters), LocalDate.parse("20/09/2024", formatters), p7);
		
		offerService.save(o1);
		offerService.save(o2);
		offerService.save(o3);
		offerService.save(o4);
		
		System.out.println("Insert OK!");
	}

}
