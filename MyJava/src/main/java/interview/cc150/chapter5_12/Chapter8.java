package interview.cc150.chapter5_12;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Chapter8 Object-Oriented Design
 * 
 * @author yazhoucao
 * 
 */
public class Chapter8 {

	public static void main(String[] args) {

	}

	/**
	 * 8.1 Design the data structures for a generic deck of cards. Explain how
	 * you would subclass the data structures to implement blackjack.
	 */
	public static class Q1 {
		public enum Suit {
			club, diamond, heart, spade;
		}

		public static class Card {
			/**
			 * 2-10, 11 as Jack, 12 as Queen, 13 as King, 14 as Ace
			 */
			private int value;
			private Suit suit;

			public Card(Suit suitIn, int valueIn) {
				suit = suitIn;
				value = valueIn;
			}

			public int getValue() {
				return value;
			}

			public Suit getSuit() {
				return suit;
			}
		}
	}

	/**
	 * 8.2
	 * Imagine you have a call center with three levels of employees:
	 * respondent, manager, and director. An incoming telephone call must be
	 * first allocated to a respondent who is free. If the respondent can't
	 * handle the call, he or she must escalate the call to a manager. If the
	 * manager is not free or notable to handle it, then the call should be
	 * escalated to a director. Design the classes and data structures for this
	 * problem. Implement a method dispatchCall() which assigns a call to the
	 * first available employee
	 * 
	 * @author yazhoucao
	 * 
	 */
	public static class Q2 {
		public static class CallDispatcher{
			private static CallDispatcher singleton;
			
			private Queue<Respondent> freeEmployees;
			private CallDispatcher(){
				freeEmployees = new LinkedBlockingQueue<Respondent>();
			}
			
			public static CallDispatcher getInstance(){
				if(singleton==null){
					singleton = new CallDispatcher();
				}
				return singleton;
			}
			
			public void addRespondents(Collection<Respondent> resp){
				freeEmployees.addAll(resp);
			}
			/***************************** Above is initialization  *****************************/
			
			public void dispatchCall(Call callIn){
				Respondent respdt = freeEmployees.poll();
				if(respdt!=null){
					CallJob job = new CallJob(callIn, freeEmployees);
					job.run();
				}
			}
		}
		
		public static class CallJob implements Runnable{
			private Call call;
			private Queue<Respondent> respondents;
			public CallJob(Call callIn, Queue<Respondent> emps){
				call =  callIn;
				respondents = emps;
			}
			
			@Override
			public void run() {
				Employee operator = respondents.poll();
				while(!operator.handleCall(call)){
					//Handle call failed
					operator = operator.getSupervisor();
					if(operator==null)
						break;
				}
				respondents.add((Respondent)operator);
			}
		}
		
		public static class Call{}
		
		public static abstract class Employee{
			private boolean free;
			private Employee supervisor;
			
			public Employee(Employee sup){
				supervisor = sup;
				free = true;
			}
			
			public boolean isFree(){ return free; }
			public void setFree(boolean freeIn){ free = freeIn; }
			
			public Employee getSupervisor(){ return supervisor; }
			public void setSupervisor(Employee emp){ supervisor = emp; }
			
			public abstract boolean handleCall(Call call);
		}
		
		public static class Respondent extends Employee{
			public Respondent(Employee supervisor){ super(supervisor); }
			public boolean handleCall(Call call){ 
				//handling...
				return true;
			}
		}
		
		public static class Manager extends Employee{
			public Manager(Employee supervisor){ super(supervisor); }
			public boolean handleCall(Call call){ 
				//handling...
				return true;
			}
		}
		
		public static class Director extends Employee{
			public Director(){ super(null); }
			public boolean handleCall(Call call){ 
				//handling...
				return true;
			}
		}
	}
	
	
	/**
	 * 8.4 Design a parking lot using object-oriented principles. 
	 * For our purposes right now, we'll make the following assumptions. We made these
	 * specific assumptions to add a bit of complexity to the problem without
	 * adding too much:
	 * • The parking lot has multiple levels. Each level has multiple rows of spots. 
	 * • The parking lot can park motorcycles, cars, and buses. 
	 * • The parking lot has motorcycle spots, compact spots, and large spots. 
	 * • A motorcycle can park in any spot. 
	 * • A car can park in either a single compact spot or a single large spot. 
	 * • A bus can park in five large spots that are consecutive and within the 
	 * same row. It cannot park in small
	 * spots.
	 * 
	 * @author yazhoucao
	 * 
	 */
	public static class q3{
		
		public enum SpotType { motorcycle, compact, large }
		public enum AutoType{ motorcycle, car, bus }
		
		public static class ParkingLot{
			List<Level> levels;
		}
		
		public static class Level{
			Spot[][] rows;
		}
		
		public static class Spot{
			SpotType type;
			boolean occupied;
		}
		
		public static abstract class Vehicle{
			private AutoType type;
			
			public Vehicle(AutoType typeIn){
				type = typeIn;
			}
			
			public AutoType getAutoType(){ return type; }

			public abstract void park(Spot spot);
		}
		
		public static class Motorcycle extends Vehicle{
			public Motorcycle(){
				super(AutoType.motorcycle);
			}
			
			@Override
			public void park(Spot spot) {
				// TODO Auto-generated method stub
				
			}
		}

		public static class Car extends Vehicle{
			public Car(){
				super(AutoType.car);
			}
			
			@Override
			public void park(Spot spot) {
				// TODO Auto-generated method stub
				
			}
		}
		
		public static class Bus extends Vehicle{
			public Bus(){
				super(AutoType.bus);
			}
			
			
			@Override
			public void park(Spot spot) {
				// TODO Auto-generated method stub
				
			}
		}
	}
}
