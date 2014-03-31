package ai_assignment_1;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Node {

	int value;
	String name;
	boolean isSurveyed;
	boolean isProbed;
	
	ArrayList<edgeNode> nodeList = new ArrayList<>();
	static semaphore s1 = new semaphore(1);
	
	public Node(String name){
		this.name = name;
	}

	public ArrayList<edgeNode> getSucc() {
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

	public void updateNode(edgeNode node1) throws InterruptedException {
		if(isSurveyed) return;
		s1.P();
		boolean flag = false;
		for (edgeNode node : nodeList) {
			if(node.getNode().getName().equals(node1.getNode().getName())){
				if(node.getEdge()==0){
					node.setEdge(node1.getEdge());
				}
				flag = true;
			}
		}
		if(!flag){
			nodeList.add(node1);
		}
		s1.V();
	}
	public void updateNode(Node node1) throws InterruptedException {
		if(isSurveyed) return;
		s1.P();
		boolean flag = false;
		for (edgeNode node : nodeList) {
			if(node.getNode().getName().equals(node1.getName())) flag = true;
		}
		if(!flag){
			edgeNode temp = new edgeNode(node1);
			nodeList.add(temp);
		}
		s1.V();
	}
	
	public boolean isProbed() {
		return isProbed;
	}
	
	public void setProbed(boolean isProbed) {
		this.isProbed = isProbed;
	}
}
