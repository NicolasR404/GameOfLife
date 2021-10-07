package application;
	
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;


public class Main extends Application {
	
	FXMLLoader load = new FXMLLoader();
	Stage stageprinciaple= new Stage();
	FPControlleur fpc ; 

	

	@Override
	public void start(Stage primaryStage) {
		try {
			
			load.setLocation(Main.class.getResource("FPMap.fxml"));

			AnchorPane root = load.load();
			root.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, null, null)));
			Scene scene = new Scene(root);

			fpc = load.getController();
			fpc.init();
			
			
			// setup Stage
			stageprinciaple.setScene(scene);
			
			stageprinciaple.setOnCloseRequest(e-> {
				fpc.pg.getCalculs().interrupt();
				Platform.exit();
			});
		
			stageprinciaple.setTitle("SimDeLaVie");
			stageprinciaple.show();
			
			
	
			
			
		} catch(Exception e) {
				e.printStackTrace();
			}
	}
			
	public static void main(String[] args) {
		launch(args);
	}

}
