import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class crossHair {

    private final ImageView imageView;
    private  Image image;

    /**
     * Constructs a crosshair object with the specified parameters.
     * @param filepath The file path of the image to be used as the crosshair.
     * @param scale The scale factor to adjust the size of the crosshair image.
     * @param xPos The x-coordinate position of the crosshair.
     * @param yPos The y-coordinate position of the crosshair.
     */
    public crossHair(String filepath,double scale,int xPos,int yPos){
        this.setImage(new Image(filepath));
        ImageView imageVi = (new ImageView(new Image(filepath)));
        imageVi.setFitHeight(getImage().getHeight()*scale);
        imageVi.setFitWidth(getImage().getWidth()*scale);
        imageVi.setLayoutX(xPos);
        imageVi.setLayoutY(yPos);
        this.imageView = imageVi;

    }

    public ImageView getImageView() {
        return imageView;
    }


    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
