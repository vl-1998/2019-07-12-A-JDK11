package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.food.model.Event.EventType;
import it.polito.tdp.food.model.Food.StatoPreparazione;

public class Simulator {
	
	//MODELLO DEL MONDO
	private List <Stazione> stazioni;
	private List <Food> cibi;
	
	private Graph <Food, DefaultWeightedEdge> grafo; //oggetto statico che fa parte della simulazione
	private Model model;
	
	//PARAMETRI SIMULAZIONE
	private int K = 5; //numero di stazioni disponibili
	
	//RISULTATI CALCOLATI
	private Double tempoPreparazione;
	private int cibiPreparati;
	
	//CODA DEGLI EVENTI
	private PriorityQueue<Event> queue;
	
	
	//Il simulatore riceve una copia del grafo
	public Simulator(Graph<Food, DefaultWeightedEdge> grafo, Model model) {
		this.grafo = grafo;
		this.model = model;
	}
	
	//predispongo tutte le variabili, stazioni e cibi
	public void init(Food partenza) {
		this.tempoPreparazione=0.0;
		this.cibiPreparati=0;
		
		this.cibi= new ArrayList<>(this.grafo.vertexSet());
		for (Food cibo: this.cibi) {
			cibo.setPreparazione(StatoPreparazione.DA_PREPARARE);
		}
		//lista che inizia vuota e la riempio di k elementi
		this.stazioni= new ArrayList<>();
		for (int i=0; i<this.K; i++) {
			this.stazioni.add(new Stazione(true,null));//create come stazioni libera che non stanno preparando alcun cibo
		}
		
		this.queue= new PriorityQueue <Event>();
		//inserisco l'evento iniziale nella coda
		List <FoodCalories> vicini = this.model.calorieCongiunte(partenza);
		
		//analizzo l'elenco dei vicini fino a un valore massimo di k vicini
		for (int i=0; i<this.K && i<vicini.size(); i++) {
			this.stazioni.get(i).setLibera(false);
			this.stazioni.get(i).setFood(vicini.get(i).getF());
			
			//genero l'evento di fine preparazione
			Event e = new Event(vicini.get(i).getCalorie(), stazioni.get(i), vicini.get(i).getF(),EventType.FINE_PREPARAZIONE);
			this.queue.add(e);
		}
		
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			//estraggo l'evento
			Event e = this.queue.poll();
			System.out.println(e);
			processEvent(e);
		}
		
	}
	
	private void processEvent(Event e) {
		switch (e.getType()) {
		case INIZIO_PREPARAZIONE:
			//in una certa stazione è stato terminato un cibo, scegli il nuovo cibo da preparare, trovando i vicini
			//del food che possiede l'evento
			List<FoodCalories> vicini = model.calorieCongiunte(e.getFood());
			FoodCalories prossimo = null;
			for (FoodCalories vicino: vicini) {
				if (vicino.getF().getPreparazione()==StatoPreparazione.DA_PREPARARE) {
					prossimo = vicino;
					break; //non proseguo nel ciclo, ho trovato tra gli adiacenti un food che mi va bene
				}
			}
			
			if (prossimo != null) {
				//schedulo la fine della preparazione di questo cibo, prossimo diventa un cibo da preparare e devo
				//modificarne lo stato
				prossimo.getF().setPreparazione(StatoPreparazione.IN_CORSO);
				e.getStazione().setLibera(false);
				e.getStazione().setFood(prossimo.getF());
				
				Event e2 = new Event(e.getTime()+prossimo.getCalorie(), e.getStazione(), prossimo.getF(), EventType.FINE_PREPARAZIONE);
				this.queue.add(e2);
			}
			
			break;
			
			
			
		case FINE_PREPARAZIONE:
			//aggiorno lo stato del mondo
			this.cibiPreparati++;
			this.tempoPreparazione=e.getTime();
			e.getStazione().setLibera(true);
			e.getFood().setPreparazione(StatoPreparazione.PREPARATO);
			
			//devo dire alla stazione che deve iniziare una nuova preparazione
			//QUETSA COSA NON MI CONVINCE
			Event e2 = new Event (e.getTime(), e.getStazione(), e.getFood(), EventType.INIZIO_PREPARAZIONE);
			
			this.queue.add(e2);
			break;
		}
	}

	//deve essere disponibile all'esterno
	public Double getTempoPreparazione() {
		return tempoPreparazione;
	}
	public int getCibiPreparati() {
		return cibiPreparati;
	}
	
	//deve anche essere inizializzato
	public int getK() {
		return K;
	}
	public void setK(int k) {
		K=k;
	}


}
