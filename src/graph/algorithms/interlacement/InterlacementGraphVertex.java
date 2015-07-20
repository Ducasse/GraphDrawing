package graph.algorithms.interlacement;

import graph.elements.Edge;
import graph.elements.Path;
import graph.elements.Vertex;

import java.awt.Dimension;

public class InterlacementGraphVertex<V extends Vertex,E extends Edge<V>> implements Vertex{

	private Path<V,E> content;
	

	public InterlacementGraphVertex(Path<V, E> content) {
		super();
		this.content = content;
	}
	
	@Override
	public Dimension getSize() {
		return null;
	}

	@Override
	public Object getContent() {
		return content; 
	}

	@Override
	public String toString() {
		return "InterlacementGraphVertex [content=" + content + "]";
	}

	

}