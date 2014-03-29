package ai_assignment_1;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Node {

	int cost;
	String name;
	boolean isSurveyed;
	boolean isProbed;

	Node pred;
	ArrayList<Node> succ = new ArrayList<>();
	
	public Node(String name){
		this.name = name;
		this.pred = null;
	}

	public ArrayList<Node> getSucc() {
		return succ;
	}


	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public Node getPred() {
		return pred;
	}

	public boolean hasGotPred() {
		//System.out.println("Pred : " + pred);
		if (pred == null){
			return false;
		} else {
			return true;
		}
	}
	
	public void setPred(Node pred) {
		this.pred = pred;
	}
	
	public void clearPred() {
		this.pred = null;
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

	public void updateNode(Node node1) {
		for (Node node : succ) {
			if(node.getName().equals(node1.getName())) succ.add(node);
		}
		//TODO sæt edge cost når det er.
	}
	
	public boolean isProbed() {
		return isProbed;
	}
	
	public void setProbed(boolean isProbed) {
		this.isProbed = isProbed;
	}
}
