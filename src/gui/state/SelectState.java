package gui.state;

import gui.main.frame.MainFrame;
import gui.model.GraphEdge;
import gui.model.GraphElement;
import gui.model.GraphVertex;
import gui.view.GraphView;

import java.awt.event.MouseEvent;

public class SelectState extends State{

	
	public SelectState(GraphView view) {
		this.view = view;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GraphElement hitElement = view.elementAtPoint(e.getPoint());
		if (hitElement != null){
			if (hitElement instanceof GraphVertex){
				GraphVertex hitVertex = (GraphVertex)hitElement;
				if (e.isControlDown()){
					if (view.getSelectionModel().isSelected(hitVertex))
						view.getSelectionModel().removeVertexFromSelection(hitVertex);
					else
						view.getSelectionModel().addVertexToSelection(hitVertex);
				}
				else
					view.getSelectionModel().selecteVertex(hitVertex);
			}
			else if (hitElement instanceof GraphEdge){
				GraphEdge hitEdge = (GraphEdge)hitElement;	
				if (e.isControlDown()){
					if (view.getSelectionModel().isSelected(hitEdge))
						view.getSelectionModel().removeEdgeFromSelection(hitEdge);
					else
						view.getSelectionModel().addEdgeToSelection(hitEdge);
				}
				else
					view.getSelectionModel().selecteEdge(hitEdge);
			}
		}
		else
			view.getSelectionModel().clearSelection();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		GraphElement hitElement = view.elementAtPoint(e.getPoint());
		if (hitElement == null){
			//lasso
			MainFrame.getInstance().changeToLassoSelect();
		}
		
	}
}
