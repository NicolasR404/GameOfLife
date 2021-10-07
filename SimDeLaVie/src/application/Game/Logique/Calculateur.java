package application.Game.Logique;

import application.Game.Gui.Case;
import application.Game.Gui.PaneGame;
import javafx.scene.control.CheckBox;
import javafx.util.Duration;

public class Calculateur extends Thread{

	private boolean partieEnCours;
	private int[][] tabcalc;
	
	private Case[][] tab;
	private CheckBox[] tabsurvie;
	private CheckBox[] tabnaissance;
	

	
	public Calculateur() {}
	public Calculateur(int[][] infoplateau, Case[][] plateauTab, CheckBox[] tabsurv, CheckBox[] tabnaiss) {
		
		partieEnCours= true;
		tab = plateauTab;
		tabcalc = infoplateau;
		tabsurvie = tabsurv;
		tabnaissance = tabnaiss;
	}
	
	public synchronized void run() {
		System.out.println("calculs");
		
		while (partieEnCours) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				partieEnCours = false;
				return;
			}
			
			//calculs
			
			for (int i =0; i < 60; i++) {
				for (int j =0; j < 60; j++) {
					
					int voisins =0;
					if (tab[(i-1 + 60)%60][(j-1 + 60)%60].IsOcupee()) {
						voisins++;
					}
					if (tab[i][(j-1 + 60)%60].IsOcupee()) {
						voisins++;
					}
					if (tab[(i+1)%60][(j-1 + 60)%60].IsOcupee()) {
						voisins++;
					}
					if (tab[(i+1)%60][j].IsOcupee()) {
						voisins++;
					}
					if (tab[(i+1)%60][(j+1)%60].IsOcupee()) {
						voisins++;
					}
					if (tab[i][(j+1)%60].IsOcupee()) {
						voisins++;
					}
					if (tab[(i-1 + 60)%60][(j+1)%60].IsOcupee()) {
						voisins++;
					}
					if (tab[(i-1 + 60)%60][j].IsOcupee()) {
						voisins++;
					}
			
					if (tab[i][j].IsOcupee()) {
						// condition de deces :
						if (!tabsurvie[voisins].isSelected()) {
							tabcalc[i][j] = 0;
						}
						
					} else {
						// condition de naissance
						if (tabnaissance[voisins].isSelected()) {
							tabcalc[i][j] = 1;
						}
						
					}
				}
			}
	}
	}
	
	public void setupPaneGame(PaneGame pg){
		pg.getActualisation().play();
	}

	public boolean isPartieEnCours() {
		return partieEnCours;
	}

	public void swapPartieEnCours() {
		this.partieEnCours = !partieEnCours;
	}
	
}
