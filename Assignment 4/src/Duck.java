import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Duck {

    private final ImageView birdImageView;
    private final Image[] birdImages = new Image[3];
    private Image falling;
    private Image shotted;

    private SpriteAnimation animation;
    private boolean isHit;
    private Timeline timeline;

    private double birdVelocityX;
    private double birdVelocityY;
    private double birdX;
    private double birdY;

    /**
     * Constructs a Duck object with the specified images, position, scale, type, and velocities.
     * @param duck1         The filename of the first duck image.
     * @param duck2         The filename of the second duck image.
     * @param duck3         The filename of the third duck image.
     * @param fallingDuck   The filename of the falling duck image.
     * @param shottedDuck   The filename of the shotted duck image.
     * @param scale         The scale factor for resizing the duck images.
     * @param mainBackground The background object for reference.
     * @param xPos          The initial x-coordinate position of the duck.
     * @param yPos          The initial y-coordinate position of the duck.
     * @param type          The type of animation for the duck ("H" for horizontal, "V" for horizontal and vertical).
     * @param birdVelocityX The initial velocity of the duck in the x-direction.
     * @param birdVelocityY The initial velocity of the duck in the y-direction.
     */
    public Duck(String duck1,String duck2,String duck3,String fallingDuck,String shottedDuck,double scale,backGrounds mainBackground,int xPos,int yPos,String type,double birdVelocityX,double birdVelocityY){
        getBirdImages()[0] = new Image(duck1);
        getBirdImages()[1] = new Image(duck2);
        getBirdImages()[2] = new Image(duck3);
        this.setFalling(new Image(fallingDuck));
        this.setShotted(new Image(shottedDuck));
        this.birdImageView = new ImageView(getBirdImages()[0]);

        getBirdImageView().setFitWidth(getBirdImages()[0].getWidth()*scale);
        getBirdImageView().setFitHeight(getBirdImages()[0].getHeight()*scale);



        this.birdImageView.setLayoutX(xPos);
        this.birdImageView.setLayoutY(yPos);
        this.birdVelocityX = birdVelocityX;
        this.birdVelocityY = birdVelocityY;

        if (type.equals("H")){
            horizontalAnimaton(scale,mainBackground,xPos,yPos);
        } else if (type.equals("V")) {
            horizontalAndVerticalAnimation(scale,mainBackground,xPos,yPos);

        }

        this.isHit = false;
    }
    /**
     * Initializes the horizontal animation for the duck.
     * @param scale          The scale factor for resizing the duck images.
     * @param mainBackground The background object for reference.
     * @param xPos           The initial x-coordinate position of the duck.
     * @param yPos           The initial y-coordinate position of the duck.
     */
    public void horizontalAnimaton(double scale,backGrounds mainBackground,int xPos,int yPos){
        this.animation = new SpriteAnimation(getBirdImageView(), Duration.seconds(0.99), 3);
        this.animation.setCycleCount(Animation.INDEFINITE);
        setBirdX(xPos);
        setBirdY(yPos);
        getBirdImageView().setScaleX(birdImageView.getScaleX()*Integer.signum((int) getBirdVelocityX()));
        setTimeline(new Timeline(new KeyFrame(Duration.seconds(0.33), event -> {

            setBirdX(getBirdX() + getBirdVelocityX());
            setBirdY(getBirdY() + getBirdVelocityY());

            if (getBirdX() <= 0 || getBirdX() >= mainBackground.getBackGroundImage().getWidth()*scale - getBirdImageView().getFitWidth()*3/2) {
                setBirdVelocityX(getBirdVelocityX() * -1);
                getBirdImageView().setScaleX(-birdImageView.getScaleX());
            }

            getBirdImageView().setLayoutX(getBirdX());
        })));
        getTimeline().setCycleCount(Animation.INDEFINITE);

    }

    /**
     * Initializes the horizontal and vertical animation for the duck.
     * @param scale          The scale factor for resizing the duck images.
     * @param mainBackground The background object for reference.
     * @param xPos           The initial x-coordinate position of the duck.
     * @param yPos           The initial y-coordinate position of the duck.
     */
    public void  horizontalAndVerticalAnimation(double scale,backGrounds mainBackground,int xPos,int yPos){
        this.animation = new SpriteAnimation(getBirdImageView(), Duration.seconds(0.99), 3);
        this.animation.setCycleCount(Animation.INDEFINITE);
        setBirdX(xPos);
        setBirdY(yPos);
        getBirdImageView().setScaleX(birdImageView.getScaleX()*Integer.signum((int) getBirdVelocityX()));
        setTimeline(new Timeline(new KeyFrame(Duration.seconds(0.33), event -> {
            setBirdX(getBirdX() + getBirdVelocityX());
            setBirdY(getBirdY() + getBirdVelocityY());

            if (getBirdX() <= 0 || getBirdX() >= mainBackground.getBackGroundImage().getWidth()*scale - getBirdImageView().getFitWidth()*3/2) {
                setBirdVelocityX(getBirdVelocityX() * -1);
                getBirdImageView().setScaleX(-birdImageView.getScaleX());
            }
            if (getBirdY() <= 0+getBirdImageView().getFitHeight()/4 || getBirdY() >= mainBackground.getBackGroundImage().getHeight()*scale - getBirdImageView().getFitHeight()) {
                setBirdVelocityY(getBirdVelocityY() * -1);
                getBirdImageView().setScaleY(-birdImageView.getScaleY());
            }

            getBirdImageView().setLayoutX(getBirdX());
            getBirdImageView().setLayoutY(getBirdY());
        })));
        getTimeline().setCycleCount(Animation.INDEFINITE);

    }

    /**
     * Initiates the fall animation for the duck.
     * @param scale The scale factor for resizing the duck images.
     */
    public void fallAnimation(double scale) {
        if (birdImageView.getScaleY() < 0){
            birdImageView.setScaleY(-birdImageView.getScaleY());
        }
        if (birdImageView.getScaleX() < 0){
            birdImageView.setScaleY(-birdImageView.getScaleX());
        }
        birdImageView.setImage(getShotted());
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> birdImageView.setVisible(true)));
        timeline.setCycleCount(1);
        timeline.play();
        Timeline fallTime = new Timeline(new KeyFrame(Duration.seconds(0.33), event -> {
            setBirdY(getBirdImageView().getBoundsInParent().getMinY()+scale*22);
            getBirdImageView().setLayoutY(getBirdY());
        }));
        fallTime.setCycleCount(Animation.INDEFINITE);
        timeline.setOnFinished(event -> {
            birdImageView.setImage(falling);
            fallTime.play();
        });

    }

    public Image[] getBirdImages() {
        return birdImages;
    }



    public ImageView getBirdImageView() {
        return birdImageView;
    }

    public Image getFalling() {
        return falling;
    }

    public void setFalling(Image falling) {
        this.falling = falling;
    }

    public Image getShotted() {
        return shotted;
    }

    public void setShotted(Image shotted) {
        this.shotted = shotted;
    }



    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public double getBirdVelocityX() {
        return birdVelocityX;
    }

    public void setBirdVelocityX(double birdVelocityX) {
        this.birdVelocityX = birdVelocityX;
    }

    public double getBirdVelocityY() {
        return birdVelocityY;
    }

    public void setBirdVelocityY(double birdVelocityY) {
        this.birdVelocityY = birdVelocityY;
    }

    public double getBirdX() {
        return birdX;
    }

    public void setBirdX(double birdX) {
        this.birdX = birdX;
    }

    public double getBirdY() {
        return birdY;
    }

    public void setBirdY(double birdY) {
        this.birdY = birdY;
    }

    /**
     * The SpriteAnimation class represents an animation that cycles through a series of images in an ImageView.
     * It extends the Transition class and overrides the interpolate method to update the ImageView's image
     * based on the current animation frame.
     */
    private class SpriteAnimation extends Transition {
        private final ImageView imageView;
        private final int frameCount;

        /**
         * Constructs a SpriteAnimation object with the specified ImageView, duration, and frame count.
         * @param imageView  The ImageView that will display the animation frames.
         * @param duration   The total duration of the animation.
         * @param frameCount The number of frames in the animation.
         */
        public SpriteAnimation(ImageView imageView, Duration duration, int frameCount) {
            this.imageView = imageView;
            this.frameCount = frameCount;
            setCycleDuration(duration);
            setInterpolator(Interpolator.LINEAR);
        }
        /**
         * Interpolates the animation based on the given fraction and updates the ImageView's image accordingly.
         * @param frac The fraction of the animation progress.
         */
        @Override
        protected void interpolate(double frac) {
            final int index = Math.min((int) Math.floor(frac * frameCount), frameCount - 1);
            this.imageView.setImage(getBirdImages()[index]);
        }
    }
    public void play(){
        this.animation.play();
    }
    public void  stop(){
        this.animation.stop();
    }
    public boolean isHit(double x, double y) {

        return (getBirdImageView().getBoundsInParent().contains(x,y));

    }
}
