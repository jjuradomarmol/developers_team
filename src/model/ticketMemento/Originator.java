package model.ticketMemento;

public class Originator {

	private Ticket state;

	   public void setState(Ticket state){
	      this.state = state;
	   }

	   public Ticket getState(){
	      return state;
	   }

	   public Memento saveStateToMemento(){
	      return new Memento(state);
	   }

	   public void getStateFromMemento(Memento memento){
	      state = memento.getState();
	   }
	   
}
