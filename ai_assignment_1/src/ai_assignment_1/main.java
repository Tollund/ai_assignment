package ai_assignment_1;

import java.io.BufferedReader;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import javax.swing.text.Position;

import eis.*;
import eis.exceptions.*;
import eis.iilang.Action;
import eis.iilang.DataContainer;
import eis.iilang.Identifier;
import eis.iilang.Percept;



public class main {

	enum type {Explorer,Saboteur,Inspector,Repairer,Sentinel}
	static ArrayList adjLists = new ArrayList<ArrayList>(1000);
	static ArrayList explored = new ArrayList<String>(1000);
	static ArrayList nodes = new ArrayList<Node>(1000);
	static String newPos = "";
	static LinkedList latest = new LinkedList();
	static LinkedList edgeList = new LinkedList(); 
	static boolean success = true;
	static boolean res = false;
	static LinkedList queue = new LinkedList();
	static ArrayList checkNodes = new ArrayList<Node>(1000);
	static LinkedList path = new LinkedList();
	static boolean foundIt = false;
	static boolean newInput = false;
	static boolean rets = false;
	static String newInputString = "";

	
	
	public static void main(String[] args) throws IOException {
		inputThread it = new inputThread();
		it.start();
		EnvironmentInterfaceStandard ei = null;
		String position = "";
		try {
			String cn = "massim.eismassim.EnvironmentInterface";
			ei = EILoader.fromClassName(cn);
		} catch (IOException e) {
			//e.printStackTrace();
		}
		try {
			ei.registerAgent("a1");
		} catch (AgentException e1) {
			//e1.printStackTrace();
		}
		try {
			ei.associateEntity("a1","connectionA1");
		} catch (RelationException e) {
			//e.printStackTrace();
		}
		try {
			ei.start();
		} catch (ManagementException e) {
			//e.printStackTrace();
		}
		try {
			Map<String, Collection<Percept>> percepts = ei.getAllPercepts("a1");
			Collection<Percept> ret = new LinkedList<Percept>();
			for ( Collection<Percept> ps : percepts.values() ) {
				ret.addAll(ps);
			}
			String retString = ret.toString();
			for (Percept percept : ret) {
				System.out.println(percept);
			}
			System.out.println(ret);
		} catch (PerceiveException e)  {
			// TODO handle the exception
		} catch (NoEnvironmentException e) {
			// TODO handle the exception
		}
		while (true){
			
			
			try {
				
				Map<String, Collection<Percept>> percepts = ei.getAllPercepts("a1");
				Collection<Percept> ret = new LinkedList<Percept>();
				for ( Collection<Percept> ps : percepts.values() ) {
					ret.addAll(ps);
				}
				String retString = "";
				String vertex1 = "";
				String vertex2 = "";
				int o = 0;
				for (Percept percept : ret) {
					retString = percept.toString();
					if(retString.regionMatches(0, "visibleEdge", 0, 10)){
						edgeList.add(retString);
					}
					if (retString.regionMatches(0, "position", 0, 7)){
						if (retString.regionMatches(11,")",0, 1)){
							position = retString.substring(9, 11);
						} else if (retString.regionMatches(12,")",0, 1)){
							position = retString.substring(9, 12);
						} else if (retString.regionMatches(13,")",0, 1)){
							position = retString.substring(9, 13);
						}
					}
					if (retString.regionMatches(0, "lastActionResult", 0, 15)){
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
				}
				Node positionNode = null;
				for (int j = 0; j < nodes.size(); j++) {
					Node checkNode = (Node) nodes.get(j);
					if (checkNode.getName().equals(position)) {
						positionNode = checkNode;
					}
				}
				if (!explored.contains(position)){
					explored.add(position);
					Node newNode = new Node(position);
					System.out.println("Explored nodes : " + nodes.size());
				}
				for (int i = 0; i < edgeList.size(); i++) {
					vertex1 = findVertex1((String) edgeList.get(i), 10);
					o = vertex1.length();
					vertex2 = findVertex2((String) edgeList.get(i), 11+o);
					Node position3Node = null;
					for (int j = 0; j < nodes.size(); j++) {
						Node checkNode = (Node) nodes.get(j);
						if (checkNode.getName().equals(vertex1)) {
							position3Node = checkNode;
						}
					}
					if (position3Node == null){
						Node newNode = new Node(vertex1);
						nodes.add(newNode);
					}
					Node position4Node = null;
					for (int j = 0; j < nodes.size(); j++) {
						Node checkNode = (Node) nodes.get(j);
						if (checkNode.getName().equals(vertex2)) {
							position4Node = checkNode;
						}
					}
					if (position4Node == null){
						Node newNode = new Node(vertex2);
						nodes.add(newNode);
					}
					boolean b = false;
					boolean c = false;
					for (int k = 0; k < adjLists.size(); k++) {
						ArrayList newList = (ArrayList) adjLists.get(k);
						if (newList.get(0).equals(vertex1)) {
							b = true;
							if (!newList.contains(vertex2)){
								newList.add(vertex2);
							}
						}
						if (newList.get(0).equals(vertex2)) {
							c = true;
							if (!newList.contains(vertex1)){
								newList.add(vertex1);
							}
						}
					}
					if (b != true) {
						ArrayList adjList = new ArrayList<String>(1000);
						adjList.add(vertex1);
						adjList.add(vertex2);
						adjLists.add(adjList);
					}
					if (c != true) {
						ArrayList adjList = new ArrayList<String>(1000);
						adjList.add(vertex2);
						adjList.add(vertex1);
						adjLists.add(adjList);
					}
				}
				for (int k = 0; k < adjLists.size(); k++) {
					ArrayList newList = (ArrayList) adjLists.get(k);
					if (newList.get(0).equals(position)) {
						Node position2Node = null;
						for (int j = 0; j < nodes.size(); j++) {
							Node checkNode = (Node) nodes.get(j);
							if (checkNode.getName().equals(position)) {
								position2Node = checkNode;
								ArrayList<Node> nList = new ArrayList<Node>(1000);
								for (int i = 1; i < newList.size(); i++) {
									for (int l = 0; l < nodes.size(); l++) {
										Node checksNode = (Node) nodes.get(l);
										if (checksNode.getName().equals(newList.get(i))) {
											checkNode.addSucc(checksNode);
										}
									}
								}
							}
						}
						for (int j = 1; j < newList.size(); j++) {
							for (int l = 0; l < adjLists.size(); l++) {
								ArrayList newList2 = (ArrayList) adjLists.get(l);
								if (newList2.get(0). equals(newList.get(j))) {
									boolean explore = true;
									for (int m = 1; m < newList2.size(); m++) {
										Node positionsNode = null;
										for (int w = 0; w < nodes.size(); w++) {
											Node checkNode = (Node) nodes.get(w);
											if (checkNode.getName().equals((String) newList2.get(m))) {
												positionsNode = checkNode;
											}
										}
										if (positionsNode == null) {
											explore = false;
										}
									}
									Node positionsNode = null;
									for (int w = 0; w < nodes.size(); w++) {
										Node checkNode = (Node) nodes.get(w);
										if (checkNode.getName().equals((String) newList2.get(0))) {
											positionsNode = checkNode;
										}
									}
									if (explore && positionsNode == null) {
										Node newNode = new Node((String) newList2.get(0));
										nodes.add(newNode);
										explored.add(newList2.get(0));
										System.out.println("Explored sub-node : " + newList2.get(0));
									}
								}
							}
						}
					}
				}
				edgeList.clear();
				if (success == true && !(path.size() == 0) && !rets){
					path.removeFirst();
					if (path.size() == 0){
						for (int i = 0; i < nodes.size(); i++) {
							Node nn = (Node) nodes.get(i);
							nn.clearPred();
						}
						System.out.println("Cleared all predecessors");
					}
				}
				if ((success == true && rets == false) || newPos.equals("")) {
					exploreNewPos(position);
				} else {
				}
			} catch (PerceiveException e)  {
				// TODO handle the exception
			} catch (NoEnvironmentException e) {
				// TODO handle the exception
			}
			if (it.checkInput()){
				String inputNode = it.getInput();
				System.out.println("InputNode " + inputNode);
				findNode(inputNode,position);
			}
			
			
			
			if(success == true || res == false){
				rets = false;
				if (!(path.size() == 0)){
					Node posNode = (Node) path.getFirst();
					newPos = posNode.getName();
					//if (!(latest.getFirst().equals(position))){
						latest.addFirst(position);
					//}
				}
				Action move = new Action("goto",new Identifier(newPos));
				try {
					ei.performAction("a1", move);
				} catch (ActException e) {
					//e.printStackTrace();
				}
			} else{
				Action rech = new Action("recharge");
				success = true;
				rets = true;
				try {
					ei.performAction("a1", rech);
				} catch (ActException e) {
					//e.printStackTrace();
				}
			}
			System.out.println("Latest " + latest.toString());
		}
		
	}

	private static String findVertex1(String retString, int i) {
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
	private static String findVertex2(String retString, int i) {
		String edge = "";
		if(retString.regionMatches(0, "visibleEdge", 0, 10)){
			if (retString.regionMatches(i+4,")",0, 1)){
				edge = retString.substring(i+2, i+4);
			}else if (retString.regionMatches(i+5,")",0, 1)){
				edge = retString.substring(i+2, i+5);
			}else if (retString.regionMatches(i+6,")",0, 1)){
				edge = retString.substring(i+2, i+6);
			}
		}
		return edge;
	}

	private static void exploreNewPos(String position) {
		ArrayList aList = null;
		System.out.println("Explored nodes : " + explored.size());
		boolean sat = false;
		for (int i = 0; i < adjLists.size(); i++) {
			aList = (ArrayList) adjLists.get(i);
			if (aList.get(0).equals(position)) {
				for (int j = 1; j < aList.size(); j++) {
					if (explored.contains(aList.get(j))){
					} else {
						newPos = (String) aList.get(j);
						//if (!(latest.size() == 0)){
							//if (!(latest.getFirst().equals(position))){
								//latest.addFirst(position);
							//}
						//} else{
							latest.addFirst(position);
						//}
						System.out.println("aList : " + aList.toString());
						System.out.println("NewPos new : " + newPos);
						sat = true;
						break;
					}
				}
				if (sat == false) {
					newPos = (String) latest.getFirst();
					System.out.println("newPos " + newPos + " position " + position);
					while (newPos.equals(position)){
						latest.pop();
						newPos = (String) latest.getFirst();
					}
					System.out.println("new newPos " + newPos + " position " + position);
					System.out.println("NewPos old : " + newPos);
					latest.pop();
				}
				break;
			}
		}
	}
	public static void findNode(String node, String position){
		Node foundNode = null;
		for (int i = 0; i < nodes.size(); i++) {
			Node findNode = (Node) nodes.get(i);
			if (findNode.getName().equals(node)) {
				System.out.println("Found!");
				foundNode = findNode;
				Node endNode = null;
				for (int k = 0; k < nodes.size(); k++) {
					Node eNode = (Node) nodes.get(k);
					if (eNode.getName().equals(position)){
						endNode = eNode;
						System.out.println("EndNode set");
						break;
					}
				}
				if (!(foundNode == null)) {
					queue.add(foundNode);
					findPath(foundNode,endNode);
				}
			} else {
			}
		}
		
	}
	public static Node findPath(Node startNode, Node endNode){
		Node finalNode = null;
		if (startNode == endNode){
			finalNode = endNode;
			System.out.println("finalNode : " + finalNode.getName());
		} else {
			while (!queue.isEmpty()){
				System.out.println("counter");
				Node lNode = (Node) queue.getFirst();
				System.out.println("lNode name : " + lNode.getName() + " successors : " + lNode.getSucc().toString());
				queue.removeFirst();
				if (lNode == endNode) {
					finalNode = lNode;
					System.out.println("finalNode : " + finalNode.getName());
					break;
				}
				for (int i = 0; i < lNode.getSucc().size(); i++) {
					if (lNode.getSucc().get(i).getPred() == null && (lNode.getSucc().get(i) != lNode.getPred()) && !(checkNodes.contains(lNode.getSucc().get(i)))){
						lNode.getSucc().get(i).setPred(lNode);
						queue.add(lNode.getSucc().get(i));
						checkNodes.add(lNode.getSucc().get(i));
					}
				}
			}
		}
		boolean print = true;
		System.out.print("Path : ");
		Node nextNode = finalNode;
		if (!(finalNode == null)){
		System.out.println("NextNode Name : "+ nextNode.getName());
		while (nextNode.hasGotPred() && nextNode.getPred()!=finalNode){
			path.add(nextNode.getPred());
			System.out.print(" " + nextNode.getPred().getName());
			nextNode = nextNode.getPred();
		}
		System.out.println("");
		foundIt = true;
		}
		return finalNode;
		
	}
	
}
