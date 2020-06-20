package diet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a complete menu.
 * 
 * It can be made up of both packaged products and servings of given recipes.
 *
 */
public class Menu implements NutritionalElement {
	
	public String name;
	public Food food;
	public Map<String, Double> recipes = new HashMap<>();
	public ArrayList<String> products = new ArrayList<>();
	
	public Menu(String name, Food food) {
		super();
		this.name = name;
		this.food = food;
	}

	/**
	 * Adds a given serving size of a recipe.
	 * 
	 * The recipe is a name of a recipe defined in the
	 * {@Link Food} in which this menu has been defined.
	 * 
	 * @param recipe the name of the recipe to be used as ingredient
	 * @param quantity the amount in grams of the recipe to be used
	 * @return the same Menu to allow method chaining
	 */
	public Menu addRecipe(String recipe, double quantity) {
		this.recipes.put(recipe, quantity);
		return this;
	}

	/**
	 * Adds a unit of a packaged product.
	 * The product is a name of a product defined in the
	 * {@Link Food} in which this menu has been defined.
	 * 
	 * @param product the name of the product to be used as ingredient
	 * @return the same Menu to allow method chaining
	 */
	public Menu addProduct(String product) {
		this.products.add(product);
		return this;
	}

	/**
	 * Name of the menu
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Total KCal in the menu
	 */
	@Override
	public double getCalories() {
		Double cal = 0.0;
		for(String s : this.products) {
			cal += this.food.products.get(s).getCalories();
		}
		for(String s : this.recipes.keySet()) {
			cal += this.food.recipes.get(s).getCalories()/100.0*this.recipes.get(s);
		}
		return cal;
	}

	/**
	 * Total proteins in the menu
	 */
	@Override
	public double getProteins() {
		Double pro = 0.0;
		for(String s : this.products) {
			pro += this.food.products.get(s).getProteins();
		}
		for(String s : this.recipes.keySet()) {
			pro += this.food.recipes.get(s).getProteins()/100.0*this.recipes.get(s);
		}
		return pro;
	}

	/**
	 * Total carbs in the menu
	 */
	@Override
	public double getCarbs() {
		Double carb = 0.0;
		for(String s : this.products) {
			carb += this.food.products.get(s).getCarbs();
		}
		for(String s : this.recipes.keySet()) {
			carb += this.food.recipes.get(s).getCarbs()/100.0*this.recipes.get(s);
		}
		return carb;
	}

	/**
	 * Total fats in the menu
	 */
	@Override
	public double getFat() {
		Double fat = 0.0;
		for(String s : this.products) {
			fat += this.food.products.get(s).getFat();
		}
		for(String s : this.recipes.keySet()) {
			fat += this.food.recipes.get(s).getFat()/100.0*this.recipes.get(s);
		}
		return fat;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Menu} class it must always return {@code false}:
	 * nutritional values are provided for the whole menu.
	 * 
	 * @return boolean 	indicator
	 */
	@Override
	public boolean per100g() {
		// nutritional values are provided for the whole menu.
		return false;
	}
}
