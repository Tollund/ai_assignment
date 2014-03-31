package ai_assignment_1;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import eis.EILoader;
import eis.EnvironmentInterfaceStandard;
import eis.exceptions.ActException;
import eis.exceptions.AgentException;
import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.exceptions.RelationException;
import eis.iilang.Action;
import eis.iilang.Identifier;
import eis.iilang.Percept;

public class explorerAgent extends Thread {

	private String name;
	private String type;
	private Node position;
	private int energy;
	private int health;
	private int visiblity;
	private Action action;
	static boolean success = true;
	static boolean res = false;
	private semaphore s1 = new semaphore(0);
	private Collection<Percept> input;
	static Map<String, Collection<Percept>> percepts;
	private static Collection<Percept> ret;
	static EnvironmentInterfaceStandard ei = null;	
	private static semaphore s2 = new semaphore(1);
	private static semaphore s3 = new semaphore(1);

	public explorerAgent(String name, String type, EnvironmentInterfaceStandard ei1) throws AgentException, RelationException, ManagementException, PerceiveException, NoEnvironmentException, IOException
	{
		this.name = name;
		this.type = type;
		this.ei = ei1;
	}

	public explorerAgent() {
	}

	public void run() {

		try {
			percieve();
		} catch (PerceiveException | NoEnvironmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		recieveInput();

		Action move = new Action("survey");
		try {
			ei.performAction(name, move);
		} catch (ActException e) {
			//e.printStackTrace();
		}
		try {
			percieve();
		} catch (PerceiveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoEnvironmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		recieveInput();
	}

	@SuppressWarnings("deprecation")
	public void recieveInput(){
		//		this.input = ret;
		//		System.out.println("Agent " + name + "'s input " + input);
		String retString = "";
		try {
			s2.P();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Percept percept : ret) {
			try {
				s3.P();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			retString = percept.toString();
			if(retString.regionMatches(0, "visibleEdge", 0, 10)){
					edge(retString);
			}
			else if (retString.regionMatches(0, "position", 0, 7)){
				position(retString);

			}
			else if (retString.regionMatches(0, "lastActionResult", 0, 15)){
				lastActionResult(retString);	

			}
			else if (retString.regionMatches(0, "visibleEntity", 0, 12)){
				visibleEntity(retString);
			}
			else if (retString.regionMatches(0, "energy", 0, 5)){
				energyUpdate(retString);
			}
			else if(retString.regionMatches(0, "surveyedEdge", 0, 11)){
				surveyedEdge(retString);
			}
			s3.V();
		}
		s2.V();
		System.out.println("done with recieveinput for agent " + name);
		//		s1.V();
	}

	private void surveyedEdge(String retString) {
		String vertex1 = "";
		String vertex2 = "";
		String weight = "";
		System.out.println("er i surveyededge");
		vertex1 = findVertex(retString, 10);
		vertex2 = findVertex(retString, 11+vertex1.length());
		weight = findWeight(retString, 12+vertex1.length()+vertex2.length());
	}

	private String findWeight(String retString, int i) {
		String weight = "";
		System.out.println("Er i vægt");
		System.out.println(retString);
		if (retString.regionMatches(14+i,")",0, 1)){
			weight = retString.substring(14+i, 15+i);
			System.out.println("Vægten er " + weight);
		} else if (retString.regionMatches(15+i,")",0, 1)){
			weight = retString.substring(14+i, 16+i);
			System.out.println("Vægten er " + weight);
		}
		return weight;
	}

	private void energyUpdate(String retString) {
		String energy = "";
		if (retString.regionMatches(8,")",0, 1)){
			energy = retString.substring(7, 8);
		}
		if (retString.regionMatches(9,")",0, 1)){
			energy = retString.substring(7, 9);
		}
		this.energy = Integer.parseInt(energy);
	}

	private void visibleEntity(String retString) {
		// TODO Log hvilke modstandere er hvor.
	}

	private void lastActionResult(String retString) {
		if (retString.regionMatches(17,"successful",0, 9)){
			System.out.println("Success");
			success = true;
		} else if (retString.regionMatches(17,"failed_res",0, 9)){
			System.out.println("Fail");
			success = false;
			res = true;
		} else {
			success = false;
			res = false;
		}
	}

	private void position(String retString){
		String position = "";
		if (retString.regionMatches(12,")",0, 1)){
			position = retString.substring(9, 11);
		} else if (retString.regionMatches(13,")",0, 1)){
			position = retString.substring(9, 12);
		} else if (retString.regionMatches(14,")",0, 1)){
			position = retString.substring(9, 13);
		}
		try {
			test.s1.P();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			for (Node node : test.getNodeDb()) {
				if(node.getName().equals(position)){
					this.position = node;
					System.out.println("Sætter position til " + this.position.getName());
					test.s1.V();
					return;
				}
			}
			this.position = new Node(position);
			System.out.println("Sætter position til " + this.position.getName());
			test.addNode(this.position);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.s1.V();
	}

	private void edge(String retString){
		String vertex1 = "";
		String vertex2 = "";
		vertex1 = findVertex(retString, 10);
		vertex2 = findVertex(retString, 11+vertex1.length());
		nodeUpdater(vertex1, vertex2);
	}

	private void nodeUpdater(String vertex1, String vertex2){
		Node node1 = new Node(vertex1);
		Node node2 = new Node(vertex2);
		try {
			test.addNode(node1,node2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(test.getNode(vertex1)!=null){
				node1 = test.getNode(vertex1);
			}
			if(test.getNode(vertex2)!=null){
				node2 = test.getNode(vertex2);
			}
		} catch (Exception e) {
		}
		node1.updateNode(node2);
		node2.updateNode(node1);
	}

	private static String findVertex(String retString, int i) {
		String edge = "";
		//TODO fix slutparanthes på visibleedge
		if(retString.regionMatches(0, "visibleEdge", 0, 10)){
			if (retString.regionMatches(i+4,",",0, 1)){
				edge = retString.substring(i+2, i+4);
			}else if (retString.regionMatches(i+5,",",0, 1)){
				edge = retString.substring(i+2, i+5);
			}else if (retString.regionMatches(i+6,",",0, 1)){
				edge = retString.substring(i+2, i+6);
			}
		}
		return edge;
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

	public void percieve() throws PerceiveException, NoEnvironmentException{
		percepts = ei.getAllPercepts(name);
		ret = new LinkedList<Percept>();
		for ( Collection<Percept> ps : percepts.values() ) {
			ret.addAll(ps);
		}
	}
}
