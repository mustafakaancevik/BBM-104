import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class levels {
    playOnMedia title = new playOnMedia("assets/effects/Title.mp3");
    playOnMedia gameCompleted = new playOnMedia("assets/effects/GameCompleted.mp3");
    playOnMedia gameOver = new playOnMedia("assets/effects/GameOver.mp3");
    playOnMedia gunShot = new playOnMedia("assets/effects/Gunshot.mp3");
    playOnMedia levelCompleted = new playOnMedia("assets/effects/LevelCompleted.mp3");

    playOnMedia duckFalls = new playOnMedia("assets/effects/DuckFalls.mp3");

    playOnMedia intro = new playOnMedia("assets/effects/Intro.mp3");

    createLabel createLabel = new createLabel();
    static backGrounds mainBackground = new backGrounds("assets/welcome/1.png");
    ArrayList<crossHair> crossHairArrayList = new ArrayList<>();
    ArrayList<backGrounds> backGroundsArrayList = new ArrayList<>();
    ArrayList<backGrounds> foregorundsArraylist = new ArrayList<>();
    int x = 0;
    int z = 0;

    /**
     * Displays the title scene of the game.
     * @param primaryStage The primary stage of the JavaFX application.
     * @param titleScene The scene containing the title screen.
     * @param selectScene The scene containing the selection screen.
     * @param scale The scaling factor for the scene elements.
     * @param volume The volume level for the background music.
     * @param windowWidth The width of the game window.
     * @param windowHeight The height of the game window.
     * @param firstLayout The root pane of the title scene.
     */

    public void TitleScene(Stage primaryStage,Scene titleScene,Scene selectScene,double scale,double volume,int windowWidth,int windowHeight,Pane firstLayout){
        primaryStage.getIcons().add(new Image("assets/favicon/1.png"));
        primaryStage.setTitle("HUBBM Duck Hunt");
        Label firstlabel = createLabel.Label("PRESS ENTER TO START\n    PRESS ESC TO EXIT",scale,Color.YELLOW,"Arial", (int) (16*scale/3),"-fx-font-weight: bold;",(windowWidth*10/9)/3,windowHeight*2/3);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, event -> firstlabel.setVisible(true)),
                new KeyFrame(Duration.seconds(0.75), event -> firstlabel.setVisible(false)),
                new KeyFrame(Duration.seconds(1.5), event -> firstlabel.setVisible(true))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        title.play(MediaPlayer.INDEFINITE,volume,1);
        firstLayout.setBackground(mainBackground.getBackground1());
        firstLayout.getChildren().addAll(firstlabel);
        primaryStage.setScene(titleScene);
        timeline.play();
        titleScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {

                primaryStage.setScene(selectScene);
            } else if (event.getCode() == KeyCode.ESCAPE) {

                primaryStage.close();
                title.stop();
            }
        });

    }

    /**
     * Displays the select scene of the game.
     * @param primaryStage The primary stage of the JavaFX application.
     * @param titleScene The scene containing the title screen.
     * @param selectScene The scene containing the selection screen.
     * @param scale The scaling factor for the scene elements.
     * @param volume The volume level for the background music.
     * @param windowWidth The width of the game window.
     * @param windowHeight The height of the game window.
     * @param selectLayout The root pane of the select scene.
     * @param gameScene The scene containing the playing game screen.
     */
    public void selectScene(Stage primaryStage,Scene titleScene,Scene selectScene,Scene gameScene,double scale,double volume,int windowWidth,int windowHeight,Pane selectLayout){

        Label secondLabel = createLabel.Label("USE ARROW KEYS TO NAVIGATE\n     PRESS ENTER TO START\n        PRESS ESC TO EXIT",scale,Color.ORANGE,"Arial", (int) (10*scale/3),"-fx-font-weight: bold;",(windowWidth*5/4)/3,windowHeight/6);
        for (int j = 1;j < 7;j++){
            backGroundsArrayList.add(new backGrounds("assets/background/"+j+".png"));
        }for (int j = 1;j < 7;j++){
            foregorundsArraylist.add(new backGrounds("assets/foreground/"+j+".png"));
        }
        for (int y = 1;y < 8;y++){
            crossHairArrayList.add(new crossHair("assets/crosshair/"+y+".png",scale,windowWidth/2,windowHeight/2));
        }
        z = 0;
        x = 0;
        selectLayout.setBackground(backGroundsArrayList.get(0).getBackground1());
        selectLayout.getChildren().addAll(secondLabel,crossHairArrayList.get(0).getImageView());
        selectScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.RIGHT){
                x++;
                selectLayout.setBackground(backGroundsArrayList.get(Math.abs(x%6)).getBackground1());
            }else if (event.getCode() == KeyCode.LEFT){
                x--;
                selectLayout.setBackground(backGroundsArrayList.get(Math.abs(x%6)).getBackground1());

            } else if (event.getCode() == KeyCode.UP) {
                z++;
                selectLayout.getChildren().set(1,crossHairArrayList.get(Math.abs(z%7)).getImageView());

            }else if (event.getCode() == KeyCode.DOWN) {
                z--;
                selectLayout.getChildren().set(1,crossHairArrayList.get(Math.abs(z%7)).getImageView());

            }
            else if (event.getCode() == KeyCode.ESCAPE) {
                primaryStage.setScene(titleScene);
                x = 0;
                z = 0;
                selectLayout.setBackground(backGroundsArrayList.get(0).getBackground1());
                selectLayout.getChildren().set(1,crossHairArrayList.get(0).getImageView());


            } else if (event.getCode() == KeyCode.ENTER) {

                title.stop();
                intro.play(1,volume,1);

                intro.getMedia().setOnEndOfMedia(() -> {

                    Cursor imageCursor = new ImageCursor(crossHairArrayList.get(Math.abs(z%7)).getImage(),
                            crossHairArrayList.get(Math.abs(z%7)).getImage().getWidth()/2,
                            crossHairArrayList.get(Math.abs(z%7)).getImage().getHeight()/2);
                    gameScene.setRoot(level1(gameScene,selectScene,titleScene,scale,backGroundsArrayList.get(Math.abs(x%6)),foregorundsArraylist.get(Math.abs(x%6)).getBackGroundImage(),windowWidth,windowHeight,imageCursor,volume,primaryStage));
                    gameScene.setCursor(imageCursor);
                    primaryStage.setScene(gameScene);
                    x = 0;
                    z = 0;
                    selectLayout.setBackground(backGroundsArrayList.get(0).getBackground1());
                    selectLayout.getChildren().set(1,crossHairArrayList.get(0).getImageView());
                    intro.stop();
                });

            }
        });

    }

    /**
     * Creates the levels of the game with ducks and various UI elements.
     * There are 6 level methods, this javadoc is valid for all of them.
     * @param gamescene    The main scene of the game.
     * @param selectScene  The scene for selecting levels.
     * @param titleScene   The scene for the game's title screen.
     * @param scale        The scale factor for resizing elements.
     * @param Background   The background object for the level.
     * @param foreImage    The foreground image for the level.
     * @param windowWidth  The width of the game window.
     * @param windowHeight The height of the game window.
     * @param cursor       The cursor object for the game.
     * @param volume       The volume level for audio.
     * @param stage        The JavaFX stage object.
     * @return The created Parent object representing the current level.
     */

    public Parent level1(Scene gamescene, Scene selectScene,Scene titleScene, double scale, backGrounds Background, Image foreImage, int windowWidth, int windowHeight, Cursor cursor, double volume, Stage stage){
        ImageView foreGround = new ImageView(foreImage);
        foreGround.setFitWidth(foreImage.getWidth()*scale);
        foreGround.setFitHeight(foreImage.getHeight()*scale);
        ArrayList<Duck> ducks = new ArrayList<Duck>(){};
        ducks.add(new Duck("assets/duck_black/4.png","assets/duck_black/5.png",
                "assets/duck_black/6.png","assets/duck_black/8.png","assets/duck_black/7.png",scale,Background,0,windowHeight*2/7,"H",scale*22,scale*22));
        Label levelLabel = createLabel.Label("Level 1/6",scale,Color.ORANGE,"Arial",(int) (9*scale/3),"-fx-font-weight: bold;",(windowWidth*6/5)/3,windowHeight/30);
        Label ammoLabel = createLabel.Label("Ammo Left: 3",scale, Color.ORANGE,"Arial",(int) (9*scale/3),"-fx-font-weight: bold;",(windowWidth*5/6),windowHeight/30);

        Pane level1 = new Pane();
        level1.setBackground(Background.getBackground1());
        level1.getChildren().addAll(ducks.get(0).getBirdImageView(),foreGround,levelLabel,ammoLabel);

        AtomicInteger duckNumber = new AtomicInteger(3);
        AtomicInteger aliveDuck = new AtomicInteger(1);
        level1.setOnMouseClicked(event -> playGame(ammoLabel,duckNumber,aliveDuck,volume,scale,ducks,windowWidth,windowHeight
                , (int) event.getX(), (int) event.getY(),level1,gamescene,selectScene,titleScene,Background,foreImage,cursor,stage,2));
        gamescene.setRoot(level1);

        for (Duck duck : ducks){
            duck.getTimeline().play();
            duck.play();
        }

        return level1;
    }
    public Parent level2(Scene gamescene, Scene selectScene,Scene titleScene, double scale, backGrounds Background, Image foreImage, int windowWidth, int windowHeight, Cursor cursor, double volume,Stage stage) {

        ImageView foreGround = new ImageView(foreImage);
        foreGround.setFitWidth(foreImage.getWidth()*scale);
        foreGround.setFitHeight(foreImage.getHeight()*scale);
        ArrayList<Duck> ducks = new ArrayList<Duck>(){};
        ducks.add(new Duck("assets/duck_blue/4.png","assets/duck_blue/5.png",
                "assets/duck_blue/6.png","assets/duck_blue/8.png","assets/duck_blue/7.png",scale,Background,windowWidth/2,windowHeight*2/5,"V",scale*22,-scale*22));

        Label levelLabel = createLabel.Label("Level 2/6",scale,Color.ORANGE,"Arial",(int) (9*scale/3),"-fx-font-weight: bold;",(windowWidth*6/5)/3,windowHeight/30);

        Label ammoLabel = createLabel.Label("Ammo Left: 3",scale, Color.ORANGE,"Arial",(int) (9*scale/3),"-fx-font-weight: bold;",(windowWidth*5/6),windowHeight/30);
        Pane level2 = new Pane();
        level2.setBackground(Background.getBackground1());
        level2.getChildren().addAll(ducks.get(0).getBirdImageView(),foreGround,levelLabel,ammoLabel);

        AtomicInteger duckNumber = new AtomicInteger(3);
        AtomicInteger aliveDuck = new AtomicInteger(1);
        level2.setOnMouseClicked(event -> playGame(ammoLabel,duckNumber,aliveDuck,volume,scale,ducks,windowWidth,windowHeight
                , (int) event.getX(), (int) event.getY(),level2,gamescene,selectScene,titleScene,Background,foreImage,cursor,stage,3));
        gamescene.setRoot(level2);
        for (Duck duck : ducks){
            duck.getTimeline().play();
            duck.play();
        }


        return level2;
    }
    public Parent level3(Scene gamescene, Scene selectScene,Scene titleScene, double scale, backGrounds Background, Image foreImage, int windowWidth, int windowHeight, Cursor cursor, double volume,Stage stage) {

        ImageView foreGround = new ImageView(foreImage);
        foreGround.setFitWidth(foreImage.getWidth()*scale);
        foreGround.setFitHeight(foreImage.getHeight()*scale);
        ArrayList<Duck> ducks = new ArrayList<Duck>(){};
        ducks.add(new Duck("assets/duck_red/4.png","assets/duck_red/5.png",
                "assets/duck_red/6.png","assets/duck_red/8.png","assets/duck_red/7.png",scale,Background,windowWidth*6/7,windowHeight*2/7,"H",-scale*22,scale*22));
        ducks.add(new Duck("assets/duck_blue/4.png","assets/duck_blue/5.png", "assets/duck_blue/6.png","assets/duck_blue/8.png","assets/duck_blue/7.png",scale,Background,0,windowHeight/7,"H",scale*22,scale*22));


        Label levelLabel = createLabel.Label("Level 3/6",scale,Color.ORANGE,"Arial",(int) (9*scale/3),"-fx-font-weight: bold;",(windowWidth*6/5)/3,windowHeight/30);

        Label ammoLabel = createLabel.Label("Ammo Left: 6",scale, Color.ORANGE,"Arial",(int) (9*scale/3),"-fx-font-weight: bold;",(windowWidth*5/6),windowHeight/30);
        Pane level3 = new Pane();
        level3.setBackground(Background.getBackground1());
        level3.getChildren().addAll(ducks.get(0).getBirdImageView(),ducks.get(1).getBirdImageView(),foreGround,levelLabel,ammoLabel);

        AtomicInteger duckNumber = new AtomicInteger(6);
        AtomicInteger aliveDuck = new AtomicInteger(2);

        level3.setOnMouseClicked(event -> playGame(ammoLabel,duckNumber,aliveDuck,volume,scale,ducks,windowWidth,windowHeight
                , (int) event.getX(), (int) event.getY(),level3,gamescene,selectScene,titleScene,Background,foreImage,cursor,stage,4));
        gamescene.setRoot(level3);
        for (Duck duck : ducks){
            duck.getTimeline().play();
            duck.play();
        }

        return level3;
    }
    public Parent level4(Scene gamescene, Scene selectScene,Scene titleScene, double scale, backGrounds Background, Image foreImage, int windowWidth, int windowHeight, Cursor cursor, double volume,Stage stage) {

        ImageView foreGround = new ImageView(foreImage);
        foreGround.setFitWidth(foreImage.getWidth()*scale);
        foreGround.setFitHeight(foreImage.getHeight()*scale);
        ArrayList<Duck> ducks = new ArrayList<Duck>(){};
        ducks.add(new Duck("assets/duck_black/4.png","assets/duck_black/5.png",
                "assets/duck_black/6.png","assets/duck_black/8.png","assets/duck_black/7.png",scale,Background,windowWidth/2,windowHeight*5/7,"V",-scale*22,-scale*22));
        ducks.add(new Duck("assets/duck_red/4.png","assets/duck_red/5.png",
                "assets/duck_red/6.png","assets/duck_red/8.png","assets/duck_red/7.png",scale,Background,windowWidth/2,windowHeight*5/7,"V",scale*22,-scale*22));


        Label levelLabel = createLabel.Label("Level 4/6",scale,Color.ORANGE,"Arial",(int) (9*scale/3),"-fx-font-weight: bold;",(windowWidth*6/5)/3,windowHeight/30);

        Label ammoLabel = createLabel.Label("Ammo Left: 6",scale, Color.ORANGE,"Arial",(int) (9*scale/3),"-fx-font-weight: bold;",(windowWidth*5/6),windowHeight/30);
        Pane level4 = new Pane();
        level4.setBackground(Background.getBackground1());
        level4.getChildren().addAll(ducks.get(0).getBirdImageView(),ducks.get(1).getBirdImageView(),foreGround,levelLabel,ammoLabel);

        AtomicInteger duckNumber = new AtomicInteger(6);
        AtomicInteger aliveDuck = new AtomicInteger(2);

        level4.setOnMouseClicked(event -> playGame(ammoLabel,duckNumber,aliveDuck,volume,scale,ducks,windowWidth,windowHeight
                , (int) event.getX(), (int) event.getY(),level4,gamescene,selectScene,titleScene,Background,foreImage,cursor,stage,5));
        gamescene.setRoot(level4);
        for (Duck duck : ducks){
            duck.getTimeline().play();
            duck.play();
        }

        return level4;
    }
    public Parent level5(Scene gamescene, Scene selectScene,Scene titleScene, double scale, backGrounds Background, Image foreImage, int windowWidth, int windowHeight, Cursor cursor, double volume,Stage stage) {

        ImageView foreGround = new ImageView(foreImage);
        foreGround.setFitWidth(foreImage.getWidth()*scale);
        foreGround.setFitHeight(foreImage.getHeight()*scale);
        ArrayList<Duck> ducks = new ArrayList<Duck>(){};
        ducks.add(new Duck("assets/duck_black/4.png","assets/duck_black/5.png",
                "assets/duck_black/6.png","assets/duck_black/8.png","assets/duck_black/7.png",scale,Background,0,windowHeight/5,"H",scale*22,scale*22));
        ducks.add(new Duck("assets/duck_red/4.png","assets/duck_red/5.png",
                "assets/duck_red/6.png","assets/duck_red/8.png","assets/duck_red/7.png",scale,Background,windowWidth*6/7,windowHeight*2/5,"H",-scale*22,scale*22));
        ducks.add(new Duck("assets/duck_blue/4.png","assets/duck_blue/5.png",
                "assets/duck_blue/6.png","assets/duck_blue/8.png","assets/duck_blue/7.png",scale,Background,windowWidth/2,windowHeight*5/7,"V",scale*22,-scale*22));

        Label levelLabel = createLabel.Label("Level 5/6",scale,Color.ORANGE,"Arial",(int) (9*scale/3),"-fx-font-weight: bold;",(windowWidth*6/5)/3,windowHeight/30);
        Label ammoLabel = createLabel.Label("Ammo Left: 9",scale, Color.ORANGE,"Arial",(int) (9*scale/3),"-fx-font-weight: bold;",(windowWidth*5/6),windowHeight/30);

        Pane level5 = new Pane();
        level5.setBackground(Background.getBackground1());
        level5.getChildren().addAll(ducks.get(0).getBirdImageView(),ducks.get(1).getBirdImageView(),ducks.get(2).getBirdImageView(),foreGround,levelLabel,ammoLabel);

        AtomicInteger duckNumber = new AtomicInteger(9);
        AtomicInteger aliveDuck = new AtomicInteger(3);
        level5.setOnMouseClicked(event -> playGame(ammoLabel,duckNumber,aliveDuck,volume,scale,ducks,windowWidth,windowHeight
                , (int) event.getX(), (int) event.getY(),level5,gamescene,selectScene,titleScene,Background,foreImage,cursor,stage,6));
        gamescene.setRoot(level5);
        for (Duck duck : ducks){
            duck.getTimeline().play();
            duck.play();
        }
        return level5;
    }

    public Parent level6(Scene gamescene, Scene selectScene,Scene titleScene, double scale, backGrounds Background, Image foreImage, int windowWidth, int windowHeight, Cursor cursor, double volume,Stage stage) {

        ImageView foreGround = new ImageView(foreImage);
        foreGround.setFitWidth(foreImage.getWidth()*scale);
        foreGround.setFitHeight(foreImage.getHeight()*scale);
        ArrayList<Duck> ducks = new ArrayList<Duck>(){};
        ducks.add(new Duck("assets/duck_black/4.png","assets/duck_black/5.png",
                "assets/duck_black/6.png","assets/duck_black/8.png","assets/duck_black/7.png",scale,Background,0,windowHeight/5,"V",scale*22,-scale*22));
        ducks.add(new Duck("assets/duck_red/4.png","assets/duck_red/5.png",
                "assets/duck_red/6.png","assets/duck_red/8.png","assets/duck_red/7.png",scale,Background,windowWidth*6/7,windowHeight*2/5,"V",-scale*22,-scale*22));
        ducks.add(new Duck("assets/duck_blue/4.png","assets/duck_blue/5.png",
                "assets/duck_blue/6.png","assets/duck_blue/8.png","assets/duck_blue/7.png",scale,Background,windowWidth/2,windowHeight*5/7,"V",scale*22,-scale*22));


        Label levelLabel = createLabel.Label("Level 6/6",scale,Color.ORANGE,"Arial",(int) (9*scale/3),"-fx-font-weight: bold;",(windowWidth*6/5)/3,windowHeight/30);
        Label ammoLabel = createLabel.Label("Ammo Left: 9",scale, Color.ORANGE,"Arial",(int) (9*scale/3),"-fx-font-weight: bold;",(windowWidth*5/6),windowHeight/30);
        Pane level6 = new Pane();

        level6.setBackground(Background.getBackground1());
        level6.getChildren().addAll(ducks.get(0).getBirdImageView(),ducks.get(1).getBirdImageView(),ducks.get(2).getBirdImageView(),foreGround,levelLabel,ammoLabel);

        AtomicInteger duckNumber = new AtomicInteger(9);
        AtomicInteger aliveDuck = new AtomicInteger(3);
        level6.setOnMouseClicked(event -> {
            gunShot.stop();
            ammoLabel.setText("Ammo Left: "+duckNumber.decrementAndGet());
            gunShot.play(1,volume,2);
            for (Duck duck : ducks){
                if (duck.isHit(event.getX(),event.getY())){
                    duck.getTimeline().stop();
                    duck.stop();
                    duck.fallAnimation(scale);
                    aliveDuck.decrementAndGet();
                }
            }
            if (chechkAmmo(duckNumber.get(),level6,scale,windowWidth,windowHeight)){
                level6.setOnMouseClicked(event1 -> {});
                gameOver.play(1,volume,1);
                gamescene.setOnKeyPressed(event1 -> {
                    if (event1.getCode() == KeyCode.ENTER){
                        gamescene.setRoot(level1(gamescene,selectScene,titleScene,scale,Background,foreImage,windowWidth,windowHeight,cursor,volume,stage));
                        gameOver.stop();
                    }else if (event1.getCode() == KeyCode.ESCAPE) {
                        titleScene.setCursor(Cursor.HAND);
                        stage.setScene(titleScene);
                        title.play(MediaPlayer.INDEFINITE,volume,1);

                    }
                });
            }

            if (aliveDuck.get() == 0){
                level6.setOnMouseClicked(event1 -> {});
                gameCompleted.play(1,volume,1);

                gameFinished(level6,scale,windowWidth,windowHeight);
                gamescene.setOnKeyPressed(event1 -> {
                    if (event1.getCode() == KeyCode.ENTER){
                        gamescene.setRoot(level1(gamescene,selectScene,titleScene,scale,Background,foreImage,windowWidth,windowHeight,cursor,volume,stage));
                        gameCompleted.stop();
                    } else if (event1.getCode() == KeyCode.ESCAPE) {
                        titleScene.setCursor(Cursor.HAND);
                        stage.setScene(titleScene);
                        gameCompleted.stop();
                        title.play(MediaPlayer.INDEFINITE,volume,1);

                    }
                });
            }

        });
        gamescene.setRoot(level6);
        for (Duck duck : ducks){
            duck.getTimeline().play();
            duck.play();
        }

        return level6;
    }
    /**
     * Checks the amount of ammo.
     * Displays a "GAME OVER" label and instructions if the ammo is zero or less.
     * @param number The number of ammo remaining.
     * @param pane The pane where the labels will be added.
     * @param scale The scaling factor for the labels.
     * @param windowWidth The width of the game window.
     * @param windowHeight The height of the game window.
     * @return true if the ammo is zero or less, false otherwise.
     */
    public boolean chechkAmmo(int number, Pane pane,  double scale, int windowWidth, int windowHeight){
        Label gameOver = createLabel.Label("GAME OVER!",scale,Color.ORANGE,"Arial", (int) (14*scale/3),"-fx-font-weight: bold;",(windowWidth*3/7),windowHeight*3/7);

        Label gameOverText = createLabel.Label("Press ENTER to play again\n     Press ESC to exit",scale,Color.ORANGE,"Arial",(int) (14*scale/3),"-fx-font-weight: bold;",(windowWidth*3/7),(windowHeight*3/7)*7/6);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, event -> gameOverText.setVisible(true)),
                new KeyFrame(Duration.seconds(0.75), event -> gameOverText.setVisible(false)),
                new KeyFrame(Duration.seconds(1.5), event -> gameOverText.setVisible(true))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        if (number<=0){
          pane.getChildren().addAll(gameOver,gameOverText);
          return true;
      }
        return false;
    }

    /**
     * Displays a "YOU WIN!" message and prompts the user to press ENTER to play the next level.
     * @param pane The JavaFX pane where the messages will be displayed.
     * @param scale The scaling factor for the messages' size.
     * @param windowWidth The width of the game window.
     * @param windowHeight The height of the game window.
     */

    public void nextLevel(Pane pane,double scale,int windowWidth,int windowHeight){
        Label youWin = createLabel.Label("YOU WIN!",scale,Color.ORANGE,"Arial",(int) (14*scale/3),"-fx-font-weight: bold;",(windowWidth*3/7),windowHeight*3/7);
        Label pressEnter = createLabel.Label("Press ENTER to play next level!",scale,Color.ORANGE,"Arial",(int) (14*scale/3),"-fx-font-weight: bold;",(windowWidth*4/11), (windowHeight*3/7)*8/7);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, event -> pressEnter.setVisible(true)),
                new KeyFrame(Duration.seconds(0.75), event -> pressEnter.setVisible(false)),
                new KeyFrame(Duration.seconds(1.5), event -> pressEnter.setVisible(true))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        pane.getChildren().addAll(youWin,pressEnter);
    }

    /**
     * Displays a "You have completed the game!" message and provides options to play again or exit.
     * @param pane The JavaFX pane where the messages will be displayed.
     * @param scale The scaling factor for the messages' size.
     * @param windowWidth The width of the game window.
     * @param windowHeight The height of the game window.
     */

    public void gameFinished(Pane pane,double scale,int windowWidth,int windowHeight){
        Label completeGame = createLabel.Label("You have completed the game!",scale,Color.ORANGE,"Arial",(int) (14*scale/3),"-fx-font-weight: bold;",(windowWidth*5/14),windowHeight*3/7);
        Label pressEnter = createLabel.Label("Press ENTER to play again\n     Press ESC to exit",scale,Color.ORANGE,"Arial",(int) (14*scale/3),"-fx-font-weight: bold;",(windowWidth*4/11),(windowHeight*3/7)*4/3);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, event -> pressEnter.setVisible(true)),
                new KeyFrame(Duration.seconds(0.75), event -> pressEnter.setVisible(false)),
                new KeyFrame(Duration.seconds(1.5), event -> pressEnter.setVisible(true))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        pane.getChildren().addAll(completeGame,pressEnter);
    }

    /**
     * Performs various game-related actions, such as updating the ammo count, checking if ducks are hit,
     * handling game completion, and responding to key presses.
     * @param ammoLabel The label displaying the remaining ammo count.
     * @param duckNumber The atomic integer representing the number of ducks.
     * @param aliveDuck The atomic integer representing the number of alive ducks.
     * @param volume The volume level for sound effects.
     * @param scale The scaling factor for the game elements' size.
     * @param ducks An array of Duck objects representing the ducks in the game.
     * @param windowWidth The width of the game window.
     * @param windowHeight The height of the game window.
     * @param getX The x-coordinate of the mouse click.
     * @param getY The y-coordinate of the mouse click.
     * @param levelNow The JavaFX pane representing the current game level.
     * @param gamescene The JavaFX scene for the game.
     * @param selectScene The JavaFX scene for level selection.
     * @param titleScene The JavaFX scene for the title screen.
     * @param Background The background object for the game.
     * @param foreImage The foreground image for the game.
     * @param cursor The cursor style for the game.
     * @param stage The JavaFX stage where the scenes are displayed.
     * @param nextLevel The level number of the next level to be played.
     */

    public void playGame(Label ammoLabel, AtomicInteger duckNumber, AtomicInteger aliveDuck, Double volume, Double scale, ArrayList<Duck> ducks,
                         int windowWidth, int windowHeight,int getX,int getY,Pane levelNow,Scene gamescene,Scene selectScene,Scene titleScene,
                         backGrounds Background,Image foreImage,Cursor cursor,Stage stage,int nextLevel){
        gunShot.stop();
        ammoLabel.setText("Ammo Left: "+duckNumber.decrementAndGet());
        gunShot.play(1,volume,2);
        Duck deleteDuck = null;
        for (Duck duck : ducks) {
            if (duck.isHit(getX, getY)) {
                duck.getTimeline().stop();
                duck.stop();
                duck.fallAnimation(scale);
                duckFalls.stop();
                duckFalls.play(1, volume, 1);
                deleteDuck = duck;
                aliveDuck.decrementAndGet();
            }
        }
        ducks.remove(deleteDuck);

        if (aliveDuck.get() == 0){
            levelNow.setOnMouseClicked(event1 -> {});
            levelCompleted.play(1,volume,1);

            nextLevel(levelNow,scale,windowWidth,windowHeight);
            gamescene.setOnKeyPressed(event1 -> {
                if (event1.getCode() == KeyCode.ENTER){
                    duckFalls.stop();
                    switch (nextLevel) {
                        case 2:
                            gamescene.setRoot(level2(gamescene, selectScene, titleScene, scale, Background, foreImage, windowWidth, windowHeight, cursor, volume, stage));
                            levelCompleted.stop();
                            break;
                        case 3:
                            gamescene.setRoot(level3(gamescene, selectScene, titleScene, scale, Background, foreImage, windowWidth, windowHeight, cursor, volume, stage));
                            levelCompleted.stop();
                            break;
                        case 4:
                            gamescene.setRoot(level4(gamescene, selectScene, titleScene, scale, Background, foreImage, windowWidth, windowHeight, cursor, volume, stage));
                            levelCompleted.stop();
                            break;
                        case 5:
                            gamescene.setRoot(level5(gamescene, selectScene, titleScene, scale, Background, foreImage, windowWidth, windowHeight, cursor, volume, stage));
                            levelCompleted.stop();
                            break;
                        case 6:
                            gamescene.setRoot(level6(gamescene, selectScene, titleScene, scale, Background, foreImage, windowWidth, windowHeight, cursor, volume, stage));
                            levelCompleted.stop();
                            break;
                        case 7:
                            stage.setScene(titleScene);
                            title.play(MediaPlayer.INDEFINITE,volume,1);
                    }
                }
            });
        }else if (chechkAmmo(duckNumber.get(),levelNow,scale,windowWidth,windowHeight)){
            levelNow.setOnMouseClicked(event1 -> {});
            gameOver.play(1,volume,1);
            gamescene.setOnKeyPressed(event1 -> {
                if (event1.getCode() == KeyCode.ENTER){
                    duckFalls.stop();
                    gamescene.setRoot(level1(gamescene,selectScene,titleScene,scale,Background,foreImage,windowWidth,windowHeight,cursor,volume,stage));
                    gameOver.stop();
                }else if (event1.getCode() == KeyCode.ESCAPE) {
                    duckFalls.stop();
                    titleScene.setCursor(Cursor.HAND);
                    stage.setScene(titleScene);
                    title.play(MediaPlayer.INDEFINITE,volume,1);
                }
            });
        }
    }
}
