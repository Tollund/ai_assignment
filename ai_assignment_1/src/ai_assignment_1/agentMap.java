package ai_assignment_1;

import java.util.ArrayList;

public class agentMap {
	
	private Node mainNode;
	private ArrayList<edge> edgeList;
	
	public agentMap(Node mainNode, ArrayList<edge> edgeList) {
		this.mainNode = mainNode;
		this.edgeList = edgeList;
	}

	public Node getMainNode() {
		return mainNode;
	}

	public void setMainNode(Node mainNode) {
		this.mainNode = mainNode;
	}

	public ArrayList<edge> getEdgeList() {
		return edgeList;
	}

	public void setEdgeList(ArrayList<edge> edgeList) {
		this.edgeList = edgeList;
	}
	
	public void addToEdgeList(edge edge){
		boolean isInEdge = false;
		for (edge eL : this.getEdgeList()) {
			if(eL.getNode().getName().equals(edge.getNode().getName())){
				isInEdge = true;
			}
		}
		if(!isInEdge){
			this.edgeList.add(edge);
		}
	}
	
	public void printEdgeList(){
		for (edge edge : this.getEdgeList()) {
			System.out.println(edge.getNode().getName());
		}
	}
}
