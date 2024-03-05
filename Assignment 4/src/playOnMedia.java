import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class playOnMedia {

    private final MediaPlayer media;
    
    /**
     * Plays the media file specified by the given file path.
     * @param filapath The file path of the media file to be played.
     */
    public  playOnMedia(String filapath){
        this.media = new MediaPlayer(new Media(new File(filapath).toURI().toString()));
    }

    public MediaPlayer getMedia() {
        return media;
    }

    /**
     * Plays the media with the specified properties.
     * @param cycleCount The number of times the media should be played in a cycle.
     * @param volume The volume level for the media playback.
     * @param rate The playback rate for the media.
     */
    public void play(int cycleCount,double volume,int rate){
        media.setVolume(volume);
        media.play();
        media.setRate(rate);
        media.setCycleCount(cycleCount);
    }
    public void stop(){
        media.stop();
    }

}
