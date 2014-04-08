package ai_assignment_1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import org.w3c.dom.traversal.NodeIterator;

import eis.EILoader;
import eis.EnvironmentInterfaceStandard;
import eis.exceptions.AgentException;
import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.exceptions.RelationException;
import eis.iilang.Percept;

public class newmain{

	static ArrayList explorers = new ArrayList<explorerAgent>();
	static explorerAgent agent = new explorerAgent();
	static Map<String, Collection<Percept>> percepts;
	static Collection<Percept> ret;
	public static ArrayList<Node> nodeDb = new ArrayList<>();
	public static semaphore s1 = new semaphore(1);
	public static semaphore s2 = new semaphore(1);

//	public static void addNode(Node node1, Node node2) throws InterruptedException {
//		s1.P();
//		boolean flag1 = false;
//		boolean flag2 = false;
//		System.out.println("node 1: " + node1.getName());
//		System.out.println("node 2: " + node2.getName());
//		for (Node node : nodeDb) {
//			if(node.getName().equals(node1.getName())){
//				if(node.isSurveyed) {
//					s1.V();
//					return;
//				}
//				flag1 = true;
//			}
//			else if(node.getName().equals(node2.getName())){
//				if(node.isSurveyed) {
//					s1.V();
//					return;
//				}
//				flag2 = true;
//			}
//		}
//		s1.V();
//		if(!flag1){
//			s1.P();
//			nodeDb.add(node1);
//			s1.V();
//		}
//		if(!flag2){
//			s1.P();
//			nodeDb.add(node2);
//			s1.V();
//		}
//	}
//	public static void addNode(Node position) throws InterruptedException {
//		s1.P();
//		for (Node node : nodeDb) {
//			if(node.getName().equals(position.getName())){
//				s1.V();
//				return;
//			}
//		}
//		nodeDb.add(position);
//		s1.V();
//	}
	
	public static void main(String[] args) throws IOException, AgentException, RelationException, ManagementException, PerceiveException, NoEnvironmentException, InterruptedException {
		EnvironmentInterfaceStandard ei1 = null;

		String cn = "massim.eismassim.EnvironmentInterface";
		ei1 = EILoader.fromClassName(cn);


		ei1.registerAgent("a1");
		ei1.associateEntity("a1","connectionA1");
//		ei1.registerAgent("a2");
//		ei1.associateEntity("a2","connectionA2");
//		ei1.registerAgent("a3");
//		ei1.associateEntity("a3","connectionA3");
//		ei1.registerAgent("a4");
//		ei1.associateEntity("a4","connectionA4");
//		ei1.registerAgent("a5");
//		ei1.associateEntity("a5","connectionA5");
//		ei1.registerAgent("a6");
//		ei1.associateEntity("a6","connectionA6");
//		ei1.registerAgent("a7");
//		ei1.associateEntity("a7","connectionA7");
//		ei1.registerAgent("a8");
//		ei1.associateEntity("a8","connectionA8");

		explorerAgent a1 = new explorerAgent("a1", "Explorer", ei1);
//		explorerAgent a2 = new explorerAgent("a2", "Explorer", ei1);
//		explorerAgent a3 = new explorerAgent("a3", "Explorer", ei1);
//		explorerAgent a4 = new explorerAgent("a4", "Explorer", ei1);
//		explorerAgent a5 = new explorerAgent("a5", "Explorer", ei1);
//		explorerAgent a6 = new explorerAgent("a6", "Explorer", ei1);
//		explorerAgent a7 = new explorerAgent("a7", "Explorer", ei1);
//		explorerAgent a8 = new explorerAgent("a8", "Explorer", ei1);

		ei1.start();
		explorers.add(a1);
//		explorers.add(a2);
//		explorers.add(a3);
//		explorers.add(a4);
//		explorers.add(a5);
//		explorers.add(a6);
//		explorers.add(a7);
//		explorers.add(a8);

		for(int i = 0; i < explorers.size(); i++)
		{
			agent = (explorerAgent) explorers.get(i);

			agent.start();
		}
	}
	
	public static ArrayList<Node> getNodeDb() throws InterruptedException {
		ArrayList<Node> tempNodeDb = nodeDb;
		return tempNodeDb;
	}

	public static Node getNode(String nodeName) throws InterruptedException{
		s1.P();
		for (Node nodeInDB : nodeDb) {
			if(nodeInDB.getName().equals(nodeName)){
				s1.V();
				return nodeInDB;
			}
		}
		s1.V();
		return null;
	}
	
	public static boolean nodeIsInDB(Node nodeInDB){
		for (Node dbNode : nodeDb) {
			if(dbNode.getName().equals(nodeInDB.getName())){
				return true;
			}
		}
		return false;
	}
	
	public static void createNode(String nodeToCreate){
		try {
			s1.P();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String dbNodename = "";
		Node tempNode = null;
		try {
			tempNode = getNode(nodeToCreate);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		boolean indb = nodeIsInDB(tempNode);
		
		if(!indb){
			Node newNode = new Node(nodeToCreate);
			nodeDb.add(newNode);
		}
		s1.V();
	}
	
}