package ai_assignment_1;

public class edgeNode {
	private static Node node;

	private int edge;
	public edgeNode(Node node, int edge) {
		this.node = node;
		this.edge = edge;
	}
	
	public edgeNode(Node node) {
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
