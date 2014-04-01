package ai_assignment_1;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Node {

	int value;
	String name;
	boolean isSurveyed;
	boolean isProbed;
	boolean isBeingProbed;

	private static semaphore s1;
	private static semaphore s2;


	public Node(String name){
		this.name = name;
		this.isBeingProbed = false;
		this.isProbed = false;
		this.isSurveyed = false;
		this.s1 = new semaphore(1);
		this.s2 = new semaphore(1);
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

	public boolean isSurveyed() {
		return isSurveyed;
	}

	public void setSurveyed(boolean isSurveyed) {
		this.isSurveyed = isSurveyed;
	}

	public void addNodeToDB(Node addedNode){
		try {
			s1.P();
		} catch (Exception e) {
			// TODO: handle exception
		}
		boolean inDB = false;
		String addedNodeName = addedNode.getName();
		String dbNodeName = "";
		try {
			for (agentMap aM : main.getNodeDb()) {
				dbNodeName = aM.getMainNode().getName();
				if(dbNodeName.equals(addedNodeName)){
					inDB = true;
					s1.V();
					return;
				}
			}
			if(!inDB){
				agentMap newAgentMap = new agentMap(addedNode, new ArrayList<edge>());
				main.getNodeDb().add(newAgentMap);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addEdgeToMap(Node addedNode, int weight){
		try {
			s1.P();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean inEdge = false;
		String addedNodeName = addedNode.getName();
		String edgeNodeName = "";
		if(main.getAgentMapForNode(this.getName()) != null){
			for (edge edgeInAM : main.getAgentMapForNode(this.getName()).getEdgeList()) {
				edgeNodeName = edgeInAM.getNode().getName();
				if(edgeNodeName.equals(addedNodeName)){
					edgeInAM.setEdge(weight);
					inEdge = true;
				}
			}
		}
		if(!inEdge){
			edge newEdge = new edge(addedNode, weight);
			System.out.println("Adding the node " + addedNodeName + " to the edgeList of " + this.getName());
			main.getAgentMapForNode(this.name).addToEdgeList(newEdge);
		}
		s1.V();
	}


	public boolean isProbed() {
		return isProbed;
	}

	public void setProbed(boolean isProbed) {
		this.isProbed = isProbed;
	}

	public boolean isBeingProbed() {
		return isBeingProbed;
	}

	public void setBeingProbed(boolean isBeingProbed) {
		this.isBeingProbed = isBeingProbed;
	}
}
