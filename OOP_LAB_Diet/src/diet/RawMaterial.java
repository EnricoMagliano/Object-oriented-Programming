package diet;

public class RawMaterial implements NutritionalElement{

	public String name;
	public double calories;
	public double proteins;
	public double carbs;
	public double fat;
	
	public RawMaterial(String name, double calories, double proteins, double carbs, double fat) {
		super();
		this.name = name;
		this.calories = calories;
		this.proteins = proteins;
		this.carbs = carbs;
		this.fat = fat;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public double getCalories() {
		return this.calories;
	}

	@Override
	public double getProteins() {
		return this.proteins;
	}

	@Override
	public double getCarbs() {
		return this.carbs;
	}

	@Override
	public double getFat() {
		return this.fat;
		}

	@Override
	public boolean per100g() {
		return true;
	}


}
