package ai_assignment_1;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import eis.EILoader;
import eis.EnvironmentInterfaceStandard;
import eis.exceptions.AgentException;
import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.exceptions.RelationException;
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
	static Map<String, Collection<Percept>> percepts;
	static Collection<Percept> ret;
	static EnvironmentInterfaceStandard ei = null;



	public explorerAgent(String name, String type) throws AgentException, RelationException, ManagementException, PerceiveException, NoEnvironmentException, IOException
	{
		this.name = name;
		this.type = type;
		initiate();
	}


	public explorerAgent() {
		// TODO Auto-generated constructor stub
	}

	public void bus() throws PerceiveException, NoEnvironmentException{

		while(true){
			percieve();
			recieveInput(ret);

		}
	}


	public void run() {

		try {
			bus();
		} catch (PerceiveException | NoEnvironmentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			s1.P();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void recieveInput(Collection<Percept> ret){
		this.input = ret;
		System.out.println("Agent " + name + "'s input " + input);
		String retString = "";
		for (Percept percept : ret) {
			retString = percept.toString();
			if(retString.regionMatches(0, "visibleEdge", 0, 10)){
				//				edge();
			}
			else if (retString.regionMatches(0, "position", 0, 7)){
				//				position();
			}
			else if (retString.regionMatches(0, "lastActionResult", 0, 15)){
				//				lastActionResult();	
				//				if (retString.regionMatches(17,"successful",0, 9)){
				//					System.out.println("Success");
				//					success = true;
				//				} else if (retString.regionMatches(17,"failed_res",0, 9)){
				//					System.out.println("Fail");
				//					success = false;
				//					res = true;
				//				} else {
				//					success = false;
				//					res = false;
				//				}

			}
		}
		//		s1.V();
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


	public void initiate() throws IOException, AgentException, RelationException, ManagementException, PerceiveException, NoEnvironmentException{
		String cn = "massim.eismassim.EnvironmentInterface";
		ei = EILoader.fromClassName(cn);
		ei.registerAgent(name);
		ei.associateEntity(name,"connectionA" + name.charAt(1));	
		ei.start();


	}

	public void percieve() throws PerceiveException, NoEnvironmentException{
		percepts = ei.getAllPercepts(name);
		ret = new LinkedList<Percept>();
		for ( Collection<Percept> ps : percepts.values() ) {
			ret.addAll(ps);
		}
	}
}
