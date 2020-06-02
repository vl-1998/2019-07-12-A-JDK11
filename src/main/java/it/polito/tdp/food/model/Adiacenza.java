package it.polito.tdp.food.model;

public class Adiacenza {
	private int f1;
	private int f2;
	private double peso;
	/**
	 * @param f1
	 * @param f2
	 * @param peso
	 */
	public Adiacenza(int f1, int f2, double peso) {
		super();
		this.f1 = f1;
		this.f2 = f2;
		this.peso = peso;
	}
	public int getF1() {
		return f1;
	}
	public void setF1(int f1) {
		this.f1 = f1;
	}
	public int getF2() {
		return f2;
	}
	public void setF2(int f2) {
		this.f2 = f2;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	
	

}
