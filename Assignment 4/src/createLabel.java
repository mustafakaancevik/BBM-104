import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class createLabel {
    /**
     * Creates and configures a JavaFX Label with the provided text, scaling, color, font, size, style, and position.
     * @param text The text to be displayed in the Label.
     * @param scale The scaling factor for the Label's size.
     * @param value The color of the Label's text.
     * @param font The font family for the Label's text.
     * @param size The font size for the Label's text.
     * @param style The CSS style to be applied to the Label.
     * @param xPos The x-coordinate position of the Label.
     * @param yPos The y-coordinate position of the Label.
     * @return A configured JavaFX Label object.
     */

    public Label Label(String text, double scale, Color value, String font, int size,String style,int xPos,int yPos){
        Label label = new Label(text);
        label.setScaleX(scale);
        label.setScaleY(scale);
        label.setTextFill(value);
        label.setFont(Font.font(font,size));
        label.setStyle(style);
        label.setLayoutX(xPos);
        label.setLayoutY(yPos);
        return label;


    }
}
