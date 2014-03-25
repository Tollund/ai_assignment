package ai_assignment_1;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Node {

	int cost;
	String name;
	boolean isExplored;
	Node pred;
	ArrayList<Node> succ = new ArrayList<>();
	
	public Node(String name){
		this.name = name;
		this.pred = null;
	}

	public ArrayList<Node> getSucc() {
		return succ;
	}

	public void addSucc(Node succ) {
		boolean n = this.succ.contains(succ);
		//System.out.println("n " + n);
		if (!n) {
			this.succ.add(succ);
			System.out.println("Added " + succ.name + " to " + this.name);
		}
		//this.succ.add(succ);
	}
	
	public void setSucc(ArrayList<Node> succ) {
		this.succ = succ;
		for (int i = 0; i < this.succ.size(); i++) {
			System.out.println("Succesor : " + this.succ.get(i).getName() + " to node : " + this.name);
		}
		//System.out.println("Set Successors");
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


	public boolean isExplored() {
		return isExplored;
	}

	public void setExplored(boolean isExplored) {
		this.isExplored = isExplored;
	}
	
}
