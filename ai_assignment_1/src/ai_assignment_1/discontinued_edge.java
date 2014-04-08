package ai_assignment_1;

public class discontinued_edge {
	private static Node node;
	private int edge;
	
	public discontinued_edge(Node node, int edge) {
		this.node = node;
		this.edge = edge;
	}
	
	public discontinued_edge(Node node) {
		this.node = node;
		this.edge = 0;
	}
	
	public Node getNode() {
		return node;
	}
	
	public int getEdge() {
		return edge;
	}
	
	public void setEdge(int edge) {
		this.edge = edge;
	}
}
