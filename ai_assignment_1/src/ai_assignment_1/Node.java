package ai_assignment_1;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Node {

	int value;
	String name;
	private Map<Node, Integer> listOfNodes;
	boolean isSurveryed;
	boolean isProbed;

	//	Map <Integer, Point2D.Double> hm = new HashMap<Integer, Point2D>();

	public Node(String name){
		this.name = name;
		this.value = 1;
		this.listOfNodes = new HashMap<Node, Integer>();
		this.isProbed = false;
		this.isSurveryed = false;
	}

	public void addNodeToList(Node addNode){
		if(!isNodeInList(addNode)){
			listOfNodes.put(addNode, Integer.MAX_VALUE);
		}
		printMap();
	}

	public void updateNodeList(Node updateNode, int edgeCost){
		if(isNodeInList(updateNode)){
			listOfNodes.replace(updateNode, Integer.MAX_VALUE, edgeCost);
		}
		printMap();
	}
	
	public boolean isNodeInList(Node findNode){
		if(listOfNodes.containsKey(findNode)){
			return true;
		}
		return false;
	}
	
	public int getEdgeCost(Node findCost){
		if(isNodeInList(findCost)){
			return listOfNodes.get(findCost); 
		}
		return Integer.MAX_VALUE;
	}
	
	public void printMap(){
		System.out.println("Printing map of nodes in node: " + name);
		System.out.println(listOfNodes.keySet());
	}

	public Map<Node, Integer> getNodeList(){
		return listOfNodes;
	}

	public boolean isSurveryed() {
		return isSurveryed;
	}

	public void setSurveryed(boolean isSurveryed) {
		this.isSurveryed = isSurveryed;
	}

	public boolean isProbed() {
		return isProbed;
	}

	public void setProbed(boolean isProbed) {
		this.isProbed = isProbed;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	

	//	public void addNodeToDB(Node addedNode){
	//		try {
	//			s1.P();
	//		} catch (Exception e) {
	//			// TODO: handle exception
	//		}
	//		boolean inDB = false;
	//		String addedNodeName = addedNode.getName();
	//		String dbNodeName = "";
	//		try {
	//			for (discontinued_agentMap aM : main.getNodeDb()) {
	//				dbNodeName = aM.getMainNode().getName();
	//				if(dbNodeName.equals(addedNodeName)){
	//					inDB = true;
	//					s1.V();
	//					return;
	//				}
	//			}
	//			if(!inDB){
	//				discontinued_agentMap newAgentMap = new discontinued_agentMap(addedNode, new ArrayList<discontinued_edge>());
	//				main.getNodeDb().add(newAgentMap);
	//			}
	//		} catch (InterruptedException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//	}

	//	public void addEdgeToMap(Node addedNode, int weight){
	//		try {
	//			s1.P();
	//		} catch (InterruptedException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		boolean inEdge = false;
	//		String addedNodeName = addedNode.getName();
	//		String edgeNodeName = "";
	//		if(main.getAgentMapForNode(this.getName()) != null){
	//			for (discontinued_edge edgeInAM : main.getAgentMapForNode(this.getName()).getEdgeList()) {
	//				edgeNodeName = edgeInAM.getNode().getName();
	//				if(edgeNodeName.equals(addedNodeName)){
	//					edgeInAM.setEdge(weight);
	//					inEdge = true;
	//				}
	//			}
	//		}
	//		if(!inEdge){
	//			discontinued_edge newEdge = new discontinued_edge(addedNode, weight);
	//			System.out.println("Adding the node " + addedNodeName + " to the edgeList of " + this.getName());
	//			main.getAgentMapForNode(this.name).addToEdgeList(newEdge);
	//		}
	//		s1.V();
	//	}

}