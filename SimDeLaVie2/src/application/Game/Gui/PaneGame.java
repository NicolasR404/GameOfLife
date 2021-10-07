package application.Game.Gui;

import javafx.animation.Timeline;
import application.Game.Logique.Calculateur;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class PaneGame extends Pane{
	// pane ou le jeu se deroule
	
	// dim : 600/600
	int dimTabX = 60; // div par 10
	int dimTabY = 60;
	boolean clickDroit = false;
	private double duree = 500.00;
	
	int[][] infoPlateau = new int [dimTabX][dimTabY];
	Case[][] plateauTab = new Case[dimTabX][dimTabY];
	private Calculateur calculs = new Calculateur();
	
	private static Timeline actualisation;

	public PaneGame() {
		
		GridPane plateau = new GridPane();
		plateau.setVgap(2);
		plateau.setHgap(2);
		plateau.setPadding(new Insets(3,0,0,3));
		this.getChildren().add(plateau);
		
		for (int i = 0; i< dimTabX; i++) { 
	
			for (int j =0; j< dimTabY; j++) {
				plateauTab[i][j] = new Case(i,j);
				plateau.add(plateauTab[i][j], i, j);
				}
		}

		setActualisation(new Timeline (new KeyFrame(Duration.millis(1000.00 - duree),
				new EventHandler <ActionEvent>() {
					
			public void handle(ActionEvent arg0) {
				System.out.println("ok 2");
				
				synchronized(getCalculs()){
					getCalculs().notify();
				}
				
				for (int i =0; i<60; i++) {
					for (int j =0; j < 60; j++) {
						if (infoPlateau[i][j] == 1 && !plateauTab[i][j].IsOcupee()) {
							plateauTab[i][j].naissance();
						} 
						if (infoPlateau[i][j] == 0 && plateauTab[i][j].IsOcupee()) {
							plateauTab[i][j].deces();
						} 
					}
				}
			}
		})));
		
		getActualisation().setCycleCount(Timeline.INDEFINITE);

		this.setOnMouseDragged(e -> {
			
			System.out.println("MD clickDroit value "+getClickDroit());
			if (e.getButton().equals(MouseButton.SECONDARY) ) { // && clickDroit
			
				int cx = (int) e.getX()/10,
						cy = (int) e.getY()/10;
				
				plateauTab[cx][cy].swapValueBoolean();
				
				if (plateauTab[cx][cy].IsOcupee()) {
					infoPlateau[cx][cy] = 0;
				}else {
					infoPlateau[cx][cy] = 1;
				}	
			}
		});
		
		this.setOnMouseClicked(e -> {
			
			// Mouse Drag :
			if (e.getButton().equals(MouseButton.SECONDARY)) {
				
				this.swapValueClickDroit();
				System.out.println("Mc Value clickDroit "+getClickDroit());

			} else if (e.getButton().equals(MouseButton.PRIMARY)) {
				int cx = (int) e.getX() / 10, // /60
						cy = (int) e.getY() / 10;
				
				if (plateauTab[cx][cy].IsOcupee()) {
					plateauTab[cx][cy].deces();
					infoPlateau[cx][cy] = 0;
				
				} else {
					plateauTab[cx][cy].naissance();
					infoPlateau[cx][cy] = 1;
				}
			}
		});

	}
	
	public void setupRaffraichissementGame() {
		
		// rafraichissement de l image :
		setCalculs(new Calculateur(infoPlateau, plateauTab,
				((PaneParametreGame)this.getParent().getChildrenUnmodifiable().get(1)).tabsurvie,
				((PaneParametreGame)this.getParent().getChildrenUnmodifiable().get(1)).tabnaissance
				));
		
		getCalculs().start();
	}
	
	public Boolean getClickDroit() {
		return this.clickDroit;
	}
	
	public void swapValueClickDroit() {
		if (this.getClickDroit()) {
			 this.clickDroit = false;
		} else {
			this.clickDroit = true;	
		}
	}

	public static Timeline getActualisation() {
		return actualisation;
	}

	public static void setActualisation(Timeline actualisation) {
		PaneGame.actualisation = actualisation;
	}

	public double getDuree() {
		return duree;
	}

	public void setDuree(double time) {
		duree = time;
	}

	public Calculateur getCalculs() {
		return calculs;
	}

	public void setCalculs(Calculateur calculs) {
		this.calculs = calculs;
	}
}
