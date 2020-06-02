package it.polito.tdp.food.model;

public class FoodCalories implements Comparable <FoodCalories>{
	private Double calorie;
	private Food f;
	/**
	 * @param calorie
	 * @param f
	 */
	public FoodCalories(Double calorie, Food f) {
		super();
		this.calorie = calorie;
		this.f = f;
	}
	public Double getCalorie() {
		return calorie;
	}
	public void setCalorie(Double calorie) {
		this.calorie = calorie;
	}
	public Food getF() {
		return f;
	}
	public void setF(Food f) {
		this.f = f;
	}
	@Override
	public int compareTo(FoodCalories o) {
		return -(this.calorie.compareTo(o.getCalorie()));
	}
	@Override
	public String toString() {
		return "Cibo: "+f+". Numero calorie: "+calorie;
	}
	
	
	
	
	
	

}
