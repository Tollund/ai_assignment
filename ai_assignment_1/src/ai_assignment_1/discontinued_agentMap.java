package ai_assignment_1;

import java.util.ArrayList;

public class discontinued_agentMap {
	
	private Node mainNode;
	private ArrayList<discontinued_edge> edgeList;
	
	public discontinued_agentMap(Node mainNode, ArrayList<discontinued_edge> edgeList) {
		this.mainNode = mainNode;
		this.edgeList = edgeList;
	}

	public Node getMainNode() {
		return mainNode;
	}

	public void setMainNode(Node mainNode) {
		this.mainNode = mainNode;
	}

	public ArrayList<discontinued_edge> getEdgeList() {
		return edgeList;
	}

	public void setEdgeList(ArrayList<discontinued_edge> edgeList) {
		this.edgeList = edgeList;
	}
}
