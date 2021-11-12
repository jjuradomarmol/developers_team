package model.ticketMemento;

public class Memento {

	private Ticket state;

	   public Memento(Ticket state){
	      this.state = state;
	   }

	   public Ticket getState(){
	      return state;
	   }	
}
