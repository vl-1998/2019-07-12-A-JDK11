package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	private Graph <Food, DefaultWeightedEdge> grafo;
	private Map <Integer, Food> idMap;
	private FoodDao dao;
	
	public Model () {
		idMap= new HashMap<>();
		dao = new FoodDao();
	}
	
	public void creaGrafo (int x) {
		this.grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		dao.listFood(idMap, x);
		Graphs.addAllVertices(this.grafo, idMap.values());
		
		for (Adiacenza a : dao.listAdiacenze()) {
			if(idMap.containsKey(a.getF1())&& idMap.containsKey(a.getF2()))
			Graphs.addEdge(this.grafo, idMap.get(a.getF1()), idMap.get(a.getF2()), a.getPeso());
		}
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	
	public List<Food> getDescrizione(){
		List <Food> result = new ArrayList<> ();
		for (Food f: this.grafo.vertexSet()) {
			result.add(f);
		}
		Collections.sort(result);
		return result;
	}
	
	public List<FoodCalories> calorieCongiunte(Food f) {
		List<Food> vicini = Graphs.neighborListOf(this.grafo, f);
		List <FoodCalories> temp = new ArrayList<>();
		
		
		for (Food n : vicini) {
			double calorie = this.grafo.getEdgeWeight(this.grafo.getEdge(f, n));
			temp.add(new FoodCalories(calorie,n));
		}
		Collections.sort(temp);
		return temp;
	}
}
