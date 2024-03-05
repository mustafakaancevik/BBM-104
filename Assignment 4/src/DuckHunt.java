import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DuckHunt extends Application {
    static Double scale = 3.0;
    Double volume = 0.025;
    static backGrounds mainBackground = new backGrounds("assets/welcome/1.png");
    levels levels = new levels();
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * The entry point of the JavaFX application. This method is called when the application is launched.
     * It sets up the main window and initializes the scenes for the title, selection, and game.
     * @param primaryStage The primary stage for the application.
     */
    @Override
    public void start(Stage primaryStage) {
        int windowWidth = (int) (mainBackground.getBackGroundImage().getWidth()*scale);
        int windowHeight = (int) (mainBackground.getBackGroundImage().getHeight()*scale);

        Pane firstLayout = new Pane();
        Pane selectLayout = new Pane();

        Scene titleScene = new Scene(firstLayout,windowWidth, windowHeight);
        Scene selectScene = new Scene(selectLayout,windowWidth,windowHeight);
        Scene gameScene = new Scene(new Pane(),windowWidth,windowHeight);

        levels.TitleScene(primaryStage,titleScene,selectScene,scale,volume,windowWidth,windowHeight,firstLayout);
        levels.selectScene(primaryStage,titleScene,selectScene,gameScene,scale,volume,windowWidth,windowHeight,selectLayout);

        primaryStage.show();
    }
}