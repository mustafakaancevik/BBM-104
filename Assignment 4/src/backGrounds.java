import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class backGrounds {

    private final Image backGroundImage;
    private final Background background1;

    /**
     * Sets the background image for the application window using the specified file path.
     * @param filePath The file path of the image to be used as the background.
     */
    public backGrounds(String filePath){
        this.backGroundImage = new Image(filePath);
        BackgroundImage backgroundImage = new BackgroundImage(backGroundImage, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT
                , BackgroundPosition.CENTER,new BackgroundSize(backGroundImage.getWidth(),backGroundImage.getHeight(),true,true,true,true));
        this.background1 = new Background(backgroundImage);


    }

    public Image getBackGroundImage() {
        return backGroundImage;
    }


    public Background getBackground1() {
        return background1;
    }


}
