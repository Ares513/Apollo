package ui;

import java.awt.Point;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.team1ofus.apollo.DataTile;
import com.team1ofus.apollo.TILE_TYPE;

public class UndoData {
	private Stack<Point[]> undoPoints = new Stack<Point[]>();
	private Stack<TILE_TYPE[]> undoData = new Stack<TILE_TYPE[]>(); 
	private Stack<Point> undoReferencePt = new Stack<Point>(); 
	
	public void push(Point[] points, TILE_TYPE[] data, Point reference) {
		if(size() > 90) {
			undoPoints.remove(size()-1);
			undoData.remove(size()-1);
			undoReferencePt.remove(size()-1);
			//cap list at 30
			
		}
		undoPoints.push(points);
		undoData.push(data);
		undoReferencePt.push(reference);
	}
	public void undo(CellRenderer render) {
		if(undoData.empty()) {
			JOptionPane.showMessageDialog(new JFrame(), "Nothing to undo.");
			return;
		}
		Point[] last = undoPoints.pop();
		TILE_TYPE[] data = undoData.pop();
		Point ref = undoReferencePt.pop();
		for(int i=0; i<last.length; i++) {
			render.editTile(ref.x + last[i].x, ref.y + last[i].y, new DataTile(data[i]));
		}
	}
	public int size() {
		return undoPoints.size();
	}
	
}
