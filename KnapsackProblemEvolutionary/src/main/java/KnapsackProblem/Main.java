package KnapsackProblem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
	try {
	    Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
	    Scene scene = new Scene(root);
	    scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
	    primaryStage.setScene(scene);
	    primaryStage.show();

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) {
	if (args.length > 0) {
	    String path = args[0];
	    MainController c = new MainController();
	    c.readItems(path);
	    int gen = Integer.parseInt(args[1]);
	    while (gen > 0) {
		gen--;
		c.solver.nextGenerationTournament();
	    }
	}
	launch(args);
    }
}
