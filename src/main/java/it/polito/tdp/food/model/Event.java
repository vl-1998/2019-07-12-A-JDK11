package it.polito.tdp.food.model;

public class Event implements Comparable<Event>{
	public enum EventType{
		INIZIO_PREPARAZIONE, //viene assegnato un cibo a una stazione
		FINE_PREPARAZIONE, //ha completato la preparazione del cibo
	}
	private Double time; //tempo in minuti
	private Stazione stazione; //riferimento alla stazione nel quale inizia o finisce la preparazion
	private Food food;
	private EventType type;
	/**
	 * @param time
	 * @param stazione
	 * @param food
	 */
	public Event(double time, Stazione stazione, Food food, EventType type) {
		super();
		this.time = time;
		this.stazione = stazione;
		this.food = food;
		this.type=type;
	}
	public double getTime() {
		return time;
	}
	
	public Stazione getStazione() {
		return stazione;
	}
	
	public Food getFood() {
		return food;
	}
	
	public EventType getType() {
		return type;
	}
	@Override
	public int compareTo(Event o) {
		return this.time.compareTo(o.time);
	}
	@Override
	public String toString() {
		return "time=" + time + ", food=" + food + ", type=" + type ;
	}
	
	
	
	 
	
	
}
