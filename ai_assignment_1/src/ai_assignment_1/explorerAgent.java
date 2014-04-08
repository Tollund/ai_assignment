package ai_assignment_1;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import org.w3c.dom.NodeList;

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

public class explorerAgent extends Thread implements Runnable {

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
		Action action;
		while(true){
			try {
				percieve();
			} catch (PerceiveException | NoEnvironmentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			recieveInput();
			
			
			
//			action = canProbe();
//			if(action != null){
//				try {
//					ei.performAction(name, action);
//				} catch (ActException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}


		}

	}

//	public Action canProbe(){
//		Action probeAction;
//		if(!this.position.isProbed && energy > 2 && !this.position.isBeingProbed){
//			this.position.setBeingProbed(true);
//			probeAction = new Action("probe");
//			//			System.out.println("Probing my own pos");
//			//			for (edgeNode node : position.getNodeList()) {
//			//				System.out.println("Agent " + name + " har " + node.getNode().getName() + " i sin liste");
//			//			}
//			return probeAction;
//		} else if(this.position.isProbed && energy > 2){
//			for (edge eN : this.position.getEdgeList()) {
//				//				System.out.println("Agent " + name + " prøver at probe: " + eN.getNode().getName());
//				if(!eN.getNode().isProbed && !eN.getNode().isBeingProbed){
//					eN.getNode().setBeingProbed(true);
//					probeAction = new Action("probe",new Identifier(eN.getNode().getName()));
//					return probeAction;
//				}
//			}
//		} else if(this.position.isProbed && energy > 2){
//			for (edge eN1 : this.position.getEdgeList()) {
//				if(eN1.getNode().isProbed){
//					for (edge eN2 : eN1.getNode().getEdgeList()) {
//						if(!eN2.getNode().isProbed && !eN2.getNode().isBeingProbed){
//							eN2.getNode().setBeingProbed(true);
//							probeAction = new Action("probe", new Identifier(eN2.getNode().getName()));
//							return probeAction;
//						}
//					}
//				}
//			}
//		}
//		return null;
//	}

	@SuppressWarnings("deprecation")
	public void recieveInput(){

		String retString = "";

		for (Percept percept : ret) {

			retString = percept.toString();

			if(retString.regionMatches(0, "visibleEdge", 0, 10)){
				//					System.out.println("edgein " + name);
				edge(retString);
				//					System.out.println("edgeout " + name);
			}
			else if (retString.regionMatches(0, "position", 0, 7)){
				//				System.out.println("posin " + name);
				position(retString);
				//				System.out.println("posout " + name);

			}
			else if (retString.regionMatches(0, "lastActionResult", 0, 15)){
				//				System.out.println("lastin " + name);
				lastActionResult(retString);	
				//				System.out.println("lastout " + name);
			}
			else if (retString.regionMatches(0, "visibleEntity", 0, 12)){
				//				System.out.println("visentin " + name);
				visibleEntity(retString);
				//				System.out.println("visentout " + name);
			}
			else if (retString.regionMatches(0, "energy", 0, 5)){
				//				System.out.println("energyin " + name);
				energyUpdate(retString);
				//				System.out.println("energyout " + name);
			}
			else if(retString.regionMatches(0, "surveyedEdge", 0, 11)){
				//				System.out.println("Surveyedin " + name);
				surveyedEdge(retString);
				//				System.out.println("Surveyedout " + name);
			}
			else if(retString.regionMatches(0, "probedVertex", 0, 11)){
				probedEdge(retString);
			}
		}
	}

	private void probedEdge(String retString) {
		
		
		String vertex1 = "";
		int value;
		vertex1 = findVertex(retString, 11);
		value = findValue(retString, vertex1.length());
		try {
			Node temp = main.getNode(vertex1);
			if(temp!=null){
			temp.setValue(value);
			temp.setProbed(true);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private int findValue(String retString, int i) {
		String value = "";
		if (retString.regionMatches(15+i,")",0, 1)){
			value = retString.substring(14+i, 15+i);
		} else if (retString.regionMatches(16+i,")",0, 1)){
			value = retString.substring(14+i, 16+i);
		}
		return Integer.parseInt(value);
	}

	private void surveyedEdge(String retString) {
		String vertex1 = "";
		String vertex2 = "";
		int weight;
		vertex1 = findVertex(retString, 11);
		vertex2 = findVertex(retString, 12+vertex1.length());
		weight = findWeight(retString, vertex1.length()+vertex2.length());
		try {
			Node temp1 = main.getNode(vertex1);
			Node temp2 = main.getNode(vertex2);
			temp1.setSurveryed(true);
			temp1.updateNodeList(temp2, weight);
			temp2.updateNodeList(temp1, weight);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private int findWeight(String retString, int i) {
		String weight = "";
		if (retString.regionMatches(16+i,")",0, 1)){
			weight = retString.substring(15+i, 16+i);
		} else if (retString.regionMatches(17+i,")",0, 1)){
			weight = retString.substring(15+i, 17+i);
		}
		return Integer.parseInt(weight);
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
			success = true;
		} else if (retString.regionMatches(17,"failed_res",0, 9)){
			success = false;
			res = true;
		} else {
			success = false;
			res = false;
		}
	}

	private void position(String retString){
		String position = "";
		if (retString.regionMatches(11,")",0, 1)){
			position = retString.substring(9, 11);
		} else if (retString.regionMatches(12,")",0, 1)){
			position = retString.substring(9, 12);
		} else if (retString.regionMatches(13,")",0, 1)){
			position = retString.substring(9, 13);
		}

		main.createNode(position);

	}

	private void edge(String retString){
		String vertex1 = "";
		String vertex2 = "";
		vertex1 = findVertex(retString, 10);
		vertex2 = findEndVertex(retString, 11+vertex1.length());
		main.createNode(vertex1);
		main.createNode(vertex2);
		try {
			Node temp1 = main.getNode(vertex1);
			Node temp2 = main.getNode(vertex2);
			temp1.addNodeToList(temp2);
			temp2.addNodeToList(temp1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//	private void nodeUpdater(String vertex1, String vertex2){
	//		Node node1 = new Node(vertex1);
	//		Node node2 = new Node(vertex2);
	//		try {
	//			test.addNode(node1,node2);
	//		} catch (InterruptedException e) {
	//			e.printStackTrace();
	//		}
	//		try {
	//			if(test.getNode(vertex1)!=null){
	//				node1 = test.getNode(vertex1);
	//			}
	//			if(test.getNode(vertex2)!=null){
	//				node2 = test.getNode(vertex2);
	//			}
	//		} catch (Exception e) {
	//		}
	//		System.out.println("Navnene på nodes");
	//		System.out.println("Node " + node2.getName() + " is trying to be added to " + node1.getName());
	//		node1.addNodeToList(node2);
	//
	//	}

	private static String findVertex(String retString, int i) {
		String edge = "";

		if (retString.regionMatches(i+4,",",0, 1)){
			edge = retString.substring(i+2, i+4);
		}else if (retString.regionMatches(i+5,",",0, 1)){
			edge = retString.substring(i+2, i+5);
		}else if (retString.regionMatches(i+6,",",0, 1)){
			edge = retString.substring(i+2, i+6);
		}

		return edge;
	}

	private static String findEndVertex(String retString, int i) {
		String edge = "";

		if (retString.regionMatches(i+4,")",0, 1)){
			edge = retString.substring(i+2, i+4);
		}else if (retString.regionMatches(i+5,")",0, 1)){
			edge = retString.substring(i+2, i+5);
		}else if (retString.regionMatches(i+6,")",0, 1)){
			edge = retString.substring(i+2, i+6);
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
		try {
			main.s2.P();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		percepts = ei.getAllPercepts(name);
		ret = new LinkedList<Percept>();
		for ( Collection<Percept> ps : percepts.values() ) {
			ret.addAll(ps);
		}
		main.s2.V();
	}
}
