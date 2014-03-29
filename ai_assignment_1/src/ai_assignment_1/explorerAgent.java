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
	static boolean success = true;
	static boolean res = false;
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
	}

	public void bus() throws PerceiveException, NoEnvironmentException, InterruptedException{

		while(true){
			try {
				System.out.println();
				System.out.println(newMain.getNodeDb());
				System.out.println();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println();
			System.out.println(newMain.getNodeDb());
			System.out.println();
		}
	}


	public void run() {

//		try {
//			bus();
//		} catch (PerceiveException | NoEnvironmentException | InterruptedException e1) {
//			e1.printStackTrace();
//		}


	

	}

	public void recieveInput(Collection<Percept> ret) throws InterruptedException, PerceiveException, NoEnvironmentException{
		this.input = ret;
		System.out.println("Agent " + name + "'s input " + input);
		String retString = "";
		for (Percept percept : ret) {
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
			else if (retString.regionMatches(0, "visibleEntity", 0, 13)){
				visibleEntity(retString);
			}
		}
		bus();
		//		s1.V();
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


	private void position(String retString) throws InterruptedException {
		String position = "";
		if (retString.regionMatches(11,")",0, 1)){
			position = retString.substring(9, 11);
		} else if (retString.regionMatches(12,")",0, 1)){
			position = retString.substring(9, 12);
		} else if (retString.regionMatches(13,")",0, 1)){
			position = retString.substring(9, 13);
		}
		for (Node node : newMain.getNodeDb()) {
			if(node.getName().equals(position)) this.position = node;
		}
	}


	private void edge(String retString) throws InterruptedException {
		String vertex1 = "";
		String vertex2 = "";
		vertex1 = findVertex(retString, 10);
		vertex2 = findVertex(retString, 11+vertex1.length());
		nodeUpdater(vertex1, vertex2);
	}

	private void nodeUpdater(String vertex1, String vertex2) throws InterruptedException {
		Node node1 = new Node(vertex1);
		Node node2 = new Node(vertex2);
		newMain.setNodeDb(node1,node2);
		newMain.setNodeDb(node2,node1);

	}


	private static String findVertex(String retString, int i) {
		String edge = "";
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


	public void initiate() throws IOException, AgentException, RelationException, ManagementException, PerceiveException, NoEnvironmentException{
		String cn = "massim.eismassim.EnvironmentInterface";
		ei = EILoader.fromClassName(cn);
		ei.registerAgent(name);
		ei.associateEntity(name,"connectionA" + name.charAt(1));	
		ei.start();
	}

	public void percieve() throws PerceiveException, NoEnvironmentException{
		percepts = ei.getAllPercepts("a1");
		ret = new LinkedList<Percept>();
		for ( Collection<Percept> ps : percepts.values() ) {
			ret.addAll(ps);
		}
	}
}
