package ai_assignment_1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import eis.exceptions.AgentException;
import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.exceptions.RelationException;




public class newMain {
	
	static ArrayList explorers = new ArrayList<explorerAgent>();
	static explorerAgent agent = new explorerAgent();
	static ArrayList<Node> nodeDb = new ArrayList<>();
	static semaphore s1 = new semaphore(1);

	
	public static ArrayList<Node> getNodeDb() throws InterruptedException {
		return nodeDb;
	}


	public static void setNodeDb(Node node1, Node node2) throws InterruptedException {
		s1.P();
		for (Node node : nodeDb) {
			if(node.getName().equals(node1)){
				node1.updateNode(node2);
			}
			else{
				nodeDb.add(node1);
				node1.updateNode(node2);
			}
		}
		s1.V();
	}
	


	public static void main(String[] args) throws AgentException, RelationException, ManagementException, PerceiveException, NoEnvironmentException, IOException {
		inputThread it = new inputThread();
		it.start();
		nodeDb.add(new Node("What she said"));
		
		String position = "";
		String cn = "massim.eismassim.EnvironmentInterface";

		explorerAgent a1 = new explorerAgent("a1", "Explorer");
		explorerAgent a2 = new explorerAgent("a2", "Explorer");
		explorerAgent a3 = new explorerAgent("a3", "Explorer");
		explorerAgent a4 = new explorerAgent("a4", "Explorer");
		explorerAgent a5 = new explorerAgent("a5", "Explorer");
		explorerAgent a6 = new explorerAgent("a6", "Explorer");
		explorerAgent a7 = new explorerAgent("a7", "Explorer");
		explorerAgent a8 = new explorerAgent("a8", "Explorer");
		explorers.add(a1);
		explorers.add(a2);
		explorers.add(a3);
		explorers.add(a4);
		explorers.add(a5);
		explorers.add(a6);
		explorers.add(a7);
		explorers.add(a8);
		
		String tempName = "";
		for (int i = 0; i < explorers.size(); i++) {
			agent = (explorerAgent) explorers.get(i);
			agent.start();
		}	
	}

}
