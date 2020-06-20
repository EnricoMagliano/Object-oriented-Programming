package diet;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a recipe of the diet.
 * 
 * A recipe consists of a a set of ingredients that are given amounts of raw materials.
 * The overall nutritional values of a recipe can be computed
 * on the basis of the ingredients' values and are expressed per 100g
 * 
 *
 */
public class Recipe implements NutritionalElement {
    
	public String name;
	public Food food;
	public Map<String, Double> ingredients = new LinkedHashMap<>();

	/**
	 * Adds a given quantity of an ingredient to the recipe.
	 * The ingredient is a raw material.
	 * 
	 * @param material the name of the raw material to be used as ingredient
	 * @param quantity the amount in grams of the raw material to be used
	 * @return the same Recipe object, it allows method chaining.
	 */
	
	public Recipe(String name, Food f) {
		super();
		this.food = f;
		this.name = name;
	}

	
	public Recipe addIngredient(String material, double quantity) {
		this.ingredients.put(material, quantity);
		return this;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public double getCalories() {
		double cal = 0.0;
		for(String s : this.ingredients.keySet()) {
			cal = cal + (this.food.rawMaterials.get(s).getCalories()/100.0)*this.ingredients.get(s);
		}
		cal = cal*100/this.quantity();
		return cal;
	}

	@Override
	public double getProteins() {
		double pro = 0.0;
		for(String s : this.ingredients.keySet()) {
			pro = pro + (this.food.rawMaterials.get(s).getProteins()/100.0)*this.ingredients.get(s);
		}
		pro = pro*100/this.quantity();
		return pro;
	}

	@Override
	public double getCarbs() {
		double carb = 0.0;
		for(String s : this.ingredients.keySet()) {
			carb = carb + (this.food.rawMaterials.get(s).getCarbs()/100.0)*this.ingredients.get(s);
		}
		carb = carb*100/this.quantity();
		return carb;
	}

	@Override
	public double getFat() {
		double fat = 0.0;
		for(String s : this.ingredients.keySet()) {
			fat = fat + (this.food.rawMaterials.get(s).getFat()/100.0)*this.ingredients.get(s);
		}
		fat = fat*100/this.quantity();
		return fat;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Recipe} class it must always return {@code true}:
	 * a recipe expresses nutritional values per 100g
	 * 
	 * @return boolean indicator
	 */
	
	@Override
	public boolean per100g() {
		return true;
	}
	
	public double quantity() {
		return this.ingredients.values().stream().mapToDouble(v->v).sum();
	}
	
	/**
	 * Returns the ingredients composing the recipe.
	 * 
	 * A string that contains all the ingredients, one per per line, 
	 * using the following format:
	 * {@code "Material : ###.#"} where <i>Material</i> is the name of the 
	 * raw material and <i>###.#</i> is the relative quantity. 
	 * 
	 * Lines are all terminated with character {@code '\n'} and the ingredients 
	 * must appear in the same order they have been added to the recipe.
	 */
	@Override
	public String toString() {
		String str = "";
		for(String s : this.ingredients.keySet()) {
			str +=s + " : " + this.ingredients.get(s).toString() + "\n";
			
		}
		return str;
	}
}
