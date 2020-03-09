package GUI;


import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SFText {

    private static final  String color = "#b0cde1";
    private static final String font = "verdana";
    private static final int size = 15;

    public static Text create(String s){
        return create(s,size);
    }
    public static Text create(String s, int size){
        Text txt = new Text(s);
        txt.setFont(Font.font(font, FontWeight.BOLD, FontPosture.REGULAR, size));
        txt.setFill(Paint.valueOf(color));
        return txt;
    }
}
