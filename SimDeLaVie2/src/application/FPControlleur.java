package application;

import application.Game.Gui.PaneGame;
import application.Game.Gui.PaneParametreGame;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class FPControlleur {

	@FXML
	Pane PaneP;
	
	PaneParametreGame ppg = new PaneParametreGame();
	PaneGame pg = new PaneGame();

	
	public void init() {
		
		pg.setMaxSize(PaneP.getPrefHeight(), PaneP.getPrefWidth());
		ppg.setLayoutY(600);
		ppg.setPrefWidth(100);
		
		PaneP.getChildren().addAll(pg, ppg);
		pg.setupRaffraichissementGame();
	}
	
}
