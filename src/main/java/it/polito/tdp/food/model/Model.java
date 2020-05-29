package it.polito.tdp.food.model;

import java.util.List;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private List<Food> cibi ;
	
	public Model() {
		
	}
	
	public List<Food> getFoods(int portions) {
		FoodDao dao = new FoodDao() ;
		this.cibi = dao.getFoodsByPortions(portions) ;
		return this.cibi ;
	}

}
