package ai_assignment_1;

import java.lang.reflect.Array;
import java.util.Collection;

import eis.iilang.Action;
import eis.iilang.Percept;

public class explorerAgent extends Thread {
	
	private String name;
	private String type;
	private Node position;
	private int energy;
	private int health;
	private int visiblity;
	private Action action;
	private semaphore s1 = new semaphore(0);
	private Collection<Percept> input;
	
	
	
	public explorerAgent(String name, String type)
	{
		this.name = name;
		this.type = type;
	}


	public explorerAgent() {
		// TODO Auto-generated constructor stub
	}


	public void run() {
		try {
			s1.P();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		perceptInput();
		// TODO Auto-generated method stub
	}
	
	private void perceptInput() {
		// TODO Auto-generated method stub
		
	}


	public void recieveInput(Collection<Percept> ret){
		this.input = ret;
		System.out.println("Agent " + name + "'s input " + input);
		s1.V();
	}
	
	public String getname(){
		return name;
	}

	public Node getPosition() {
		return position;
	}

	public void setPosition(Node position) {
		this.position = position;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getVisiblity() {
		return visiblity;
	}

	public void setVisiblity(int visiblity) {
		this.visiblity = visiblity;
	}


	public Action getAction() {
		return action;
	}


	public void setAction(Action action) {
		this.action = action;
	}
	
}
