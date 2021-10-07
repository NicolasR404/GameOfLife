package application.Game.Gui;

import static application.Game.Gui.PaneGame.getActualisation;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class PaneParametreGame extends Pane{

	Text texte1 = new Text();
	Text texte2 = new Text();
	
	HBox boite1 = new HBox();
	HBox boite2 = new HBox();
	HBox boite21 = new HBox();
	HBox boite22 = new HBox();
	
	FlowPane fpsurvie = new FlowPane();
	FlowPane fpnaissance = new FlowPane();
	
	String stexte1 = "Nombre de voisins pour survivre",
		stexte2 = "Nombre de voisins pour naitre";
	
	CheckBox[] tabsurvie, tabnaissance;
	private Slider slider = new Slider();
	
	Duration duree = new Duration(0);
	
	private boolean ButtonStart = false;

	public PaneParametreGame(){
		
		this.setPrefWidth(100);
		
		texte1 = new Text(stexte1);
		texte1.setFont(Font.font("Bold Serif Italic", FontWeight.EXTRA_LIGHT, 20));
		
		texte2 = new Text(stexte2);
		texte2.setFont(Font.font("Bold Serif Italic", FontWeight.EXTRA_LIGHT, 20));
		
		boite1.setPrefHeight(100);
		boite1.setPrefWidth(150);
		boite1.setSpacing(20);
		boite1.getChildren().addAll(texte1,texte2);
		
		boite2.setLayoutY(50);
		boite2.setPrefHeight(50);
		boite2.setPrefWidth(100);
		
		boite2.getChildren().addAll(fpsurvie, fpnaissance);
		
		fpsurvie.getChildren().add(boite21);
		fpnaissance.getChildren().add(boite22);
		
		// stockage des valeurs :
		tabsurvie = new CheckBox[10];
		
		for (int i= 0; i<= 9; i++) {
			tabsurvie[i] =  new CheckBox(String.valueOf(i));
			tabsurvie[i].setFont(Font.font("Bold Serif", FontWeight.EXTRA_BOLD, 12.125));
			boite21.getChildren().add(tabsurvie[i]);
		}
		
		tabsurvie[2].setSelected(true);
		tabsurvie[3].setSelected(true);
		
		tabnaissance = new CheckBox[10];
		
		for (int i= 0; i<= 9; i++) {
			tabnaissance[i] =  new CheckBox(String.valueOf(i));
			tabnaissance[i].setFont(Font.font("Bold Serif", FontWeight.EXTRA_BOLD, 12.125));
			boite22.getChildren().add(tabnaissance[i]);
		}
		
		tabnaissance[3].setSelected(true);
		
		// Buton play pause :
		HBox boite3 = new HBox();
		boite3.setLayoutY(100);
		
		Button bstart = new Button("Start");
		bstart.setPrefSize(200, 50);
		
		bstart.setOnAction(event -> {
			
			if (!ButtonStart) {
				// on commence
				bstart.setText("Stop");
				ButtonStart = true;
				PaneGame pg = (PaneGame) this.getParent().getChildrenUnmodifiable().get(0);
				getActualisation().play();
				pg.setupRaffraichissementGame();
				
			} else {
				// on arret
				bstart.setText("Start");
				ButtonStart = false;
				PaneGame pg = (PaneGame) this.getParent().getChildrenUnmodifiable().get(0);
				getActualisation().stop();
			}
		});
		
		// bouton clear
		Button bclear = new Button("Effacer");
		bclear.setPrefSize(200, 50);
		bclear.setOnAction(e -> {

			PaneGame pg = (PaneGame) this.getParent().getChildrenUnmodifiable().get(0);
			for (int i = 0; i< 60; i++) { //600-100/5
				for (int j =0; j< 60; j++) {
					pg.infoPlateau[i][j] = 0;
					pg.plateauTab[i][j].deces();
				}
			}
		});
		
		// Slider vitesse
		
		getSlider().setMin(1);
		getSlider().setMax(459);
		getSlider().setPrefSize(200, 50);
		getSlider().setValue(400);
		
		//duree
		getSlider().valueProperty().addListener(e ->{
			
			PaneGame pg = (PaneGame) this.getParent().getChildrenUnmodifiable().get(0);
			pg.getActualisation().stop();
			
			KeyFrame key = new KeyFrame(Duration.millis(1000.00 -getSlider().getValue() ),new EventHandler <ActionEvent>() {
			
				public void handle(ActionEvent arg0) {
					System.out.println("ok 2");

					for (int i =0; i<60; i++) {
						for (int j =0; j < 60; j++) {
							if (pg.infoPlateau[i][j] == 1 && !pg.plateauTab[i][j].IsOcupee()) {
								pg.plateauTab[i][j].naissance();
							} 
							if (pg.infoPlateau[i][j] == 0 && pg.plateauTab[i][j].IsOcupee()) {
								pg.plateauTab[i][j].deces();
							} 
						}
					}
					
					synchronized(pg.getCalculs()){
						pg.getCalculs().notify();
					}
				}
			});
			
			pg.getActualisation().getKeyFrames().setAll(key);
			pg.getActualisation().play();
			
			});

		boite3.getChildren().addAll(bstart, bclear, getSlider());
		
		this.getChildren().addAll(boite1, boite2, boite3);
	}

	public  Boolean isInExecution() {
		return this.ButtonStart;
	}

	public Slider getSlider() {
		return slider;
	}

	public void setSlider(Slider slider) {
		this.slider = slider;
	}
}
