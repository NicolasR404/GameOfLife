package application.Game.Gui;


import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Case extends Parent {
	
	// Case du jeu
	private int emplacementX;
	private int emplacementY;
	private SimpleDoubleProperty h;
	private SimpleDoubleProperty l;
	
	private Circle cell; // le dot, representation d une creature
	private boolean occupee = false;
	
	
	public Case(int X, int Y) {
		 this.setX(X);
		 this.setY(Y);

		 h = new SimpleDoubleProperty();
		 l = new SimpleDoubleProperty();
		 l.set(8);
		 h.set(8);
		 l.bind(h);
		 		 
		 // Fond
		 
		 Rectangle fond = new Rectangle(0,0,8,8);
		 fond.setFill(Color.DARKGOLDENROD);
		 fond.heightProperty().bind(h);
		 fond.widthProperty().bind(l);
		 
		 cell = new Circle(4,4,4);
		 cell.setFill(Color.BLUE);
		 cell.setVisible(false);
		 
		 this.getChildren().add(fond);
		 this.getChildren().add(cell);
	}
	
	public void setX(int X) {
		emplacementX = X;
	}
	
	public int getX() {
		return emplacementX;
	}
	
	public void setY(int Y) {
		emplacementY = Y;
	}
	
	public int getY() {
		return emplacementY;
	}

	public SimpleDoubleProperty HProperty() {
		return h;
	}
	
	public SimpleDoubleProperty LProperty() {
		return l;
	}
	
	
	public Boolean IsOcupee() {
		return occupee;
	}
	
	public void naissance() {
		cell.setVisible(true);
		occupee = true;
	}

	public void deces() {
		cell.setVisible(false);
		occupee = false;
	}
	
	public void swapValueBoolean() {
		if(occupee) {
			deces();
		} else {
			naissance();
		}
	}
	
	public boolean getValueParent() {
		
		PaneParametreGame ppg = (PaneParametreGame) this.getParent().getParent().getParent().getChildrenUnmodifiable().get(1); //
		PaneGame pg = (PaneGame) this.getParent().getParent();
		System.out.println("Teste donnee chopper sur .getParent().equals : "+( this.getParent().getParent().getParent().getClass()));
		
		boolean truc;
		
		if (ppg.isInExecution() && pg.getClickDroit()) { //
			return truc = true;
		} else {
			return truc = false;
		}
	}
}
