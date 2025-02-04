package View;

import Model.IModel;
import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.util.Optional;

public class Main extends Application {
    public Scene startScene;
    public Scene gameScene;

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Pokemon Go");
        //primaryStage.getIcons().add(new Image("/Images/icon.png"));
        FXMLLoader startSceneFXML = new FXMLLoader(getClass().getResource("StartScreen.fxml"));
        FXMLLoader myViewSceneFXML = new FXMLLoader(getClass().getResource("MyView.fxml"));

        //save the load of the scene
        Parent root = startSceneFXML.load();
        Parent game = myViewSceneFXML.load();

        //get the controller so we can do manipulate on them like set things
        MyViewController viewController = myViewSceneFXML.getController();
        startController startController = startSceneFXML.getController();

        //chose the scene size window
        startScene = new Scene(root,420,400);
        gameScene = new Scene(game,700,710);

        //sett stage and scene to pass when push the start buttomn
        startController.setStage(primaryStage);
        startController.setScene(gameScene);

        //connection of the mvvm
        IModel model = new MyModel();
        MyViewModel myViewModel = new MyViewModel(model);
        viewController.setViewModel(myViewModel);

        closeGame(viewController,primaryStage);


        viewController.setPrimaryStage(primaryStage);
        primaryStage.setScene(startScene);
        primaryStage.show();


    }



    private void closeGame(MyViewController viewController , Stage stage) {

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        public void handle(WindowEvent WE){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setContentText("Are You Sure?");
        ButtonType continueGame = new ButtonType("Continue playing");
        ButtonType closeGame = new ButtonType("Exit Game");

        alert.getButtonTypes().setAll(continueGame, closeGame);

        Optional<ButtonType> chosed = alert.showAndWait();

        if(chosed.get() == closeGame){

            viewController.stopServers();
            System.exit(0);
            alert.close();

        }
        else{
            WE.consume();
        }
    }});}


    public static void main(String[] args) {
        launch(args);
    }
}
