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
	
	private ArrayList<edgeNode> nodeList;
	private static semaphore s1;
	private static semaphore s2;
	
	
	public Node(String name){
		this.name = name;
		this.isBeingProbed = false;
		this.isProbed = false;
		this.isSurveyed = false;
		this.nodeList = new ArrayList<>();
		this.s1 = new semaphore(1);
		this.s2 = new semaphore(1);
	}

	public ArrayList<edgeNode> getNodeList() {
		return nodeList;
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

	
	public void addNodeToList(Node addedNode){
		try {
			s1.P();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean isInList = false;
		String addedName = "";
		String searchName = "";
		addedName = addedNode.getName();
		for (edgeNode eN : nodeList) {
			searchName = eN.getNode().getName();
			System.out.println("Searchname: " + searchName);
			System.out.println("addedname: " + addedName);
			if(searchName.equals(addedName)){
				
				isInList = true;
			}
		}		
		for (edgeNode eN : nodeList) {
			System.out.println("Nodelistindhold before :" + eN.getNode().getName() + " for " + name);
		}
		if(!isInList){
			edgeNode addedEN = new edgeNode(addedNode);
			System.out.println("Adding " + addedName + " to " + name);
			this.nodeList.add(addedEN);
		}
		for (edgeNode eN : nodeList) {
			System.out.println("Nodelistindhold after :" + eN.getNode().getName() + " for " + name);
		}
		s1.V();
	}
	public void addEdgeToNode(Node addedNode, int weight) {
		try {
			s1.P();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean inNodeList = false;
		String addedNodeName = addedNode.getName();
		String edgeNodeName = "";
		for (edgeNode eN : this.nodeList) {
			edgeNodeName = eN.getNode().getName();
			if(edgeNodeName.equals(addedNodeName)){
				eN.setEdge(weight);
				inNodeList = true;
			}
		}
		if(!inNodeList){
			edgeNode newEdgeNode = new edgeNode(addedNode,weight);
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
