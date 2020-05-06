package WorldBuilder;


import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *  Class to output a graphic to show the galaxy map and system maps
 */
public class Diagrams {

    /**Anchoring point for the center of the graphic*/
    private static double centerX;
    private static double centerY;

    /**
     * Draws the local galaxy map of every system within 15lys of the inputted system
     * @param system System to set as the center
     * @param root main GUI pane
     */
    public static void drawLocalMap(StarSystem system, BorderPane root){
        StackPane pane = new StackPane();
        Canvas canvas = new Canvas(1000, 1000);
        canvas.setWidth(1000);
        canvas.setHeight(1000);
        Group group = new Group();

        Rectangle border = new Rectangle();
        border.setHeight(root.getHeight()-root.getHeight()/8);
        border.setWidth(root.getWidth()-root.getWidth()/8);
        border.setFill(Color.BLACK);
        border.setX(root.getWidth()/16);
        border.setY(root.getHeight()/16);
        centerX = root.getWidth()/2;
        centerY = root.getHeight()/2;
        group.getChildren().add(border);

        addAxis(group,canvas);
        addSystems(system,group);

        pane.getChildren().addAll(group,canvas);
        Pane p = new Pane(group,canvas);
        root.setCenter(p);
    }

    /**
     * Adds each of the current system to the graphic
     * @param system Main System at the center
     * @param group group of shapes to add to.
     */
    private static void addSystems(StarSystem system, Group group){
        double x,y,z;
        x = 0;
        y = 0;
        z = 0;
        Circle circle = new Circle();
        circle.setFill(Color.YELLOW);
        circle.setRadius(10);
        circle.setCenterX(centerX+(x*30));
        circle.setCenterY(centerY-(y*5)-(z*15));
        group.getChildren().add(circle);
        ArrayList<StarSystem> systems = new ArrayList<>();
        try {
            systems = GalaxyDataBase.findNearBySystems(system,15);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("-------Near BY Systems-------");
        for (StarSystem s:systems) {
            System.out.println(s.getName());
        }
        for (int i = 0; i < systems.size(); i++) {
            x = (system.getX())-(systems.get(i).getX());
            y = (system.getY())-(systems.get(i).getY());
            z = (system.getZ())-(systems.get(i).getZ());
            Circle circle2 = new Circle();
            circle2.setFill(Color.YELLOW);
            circle2.setRadius(5);
            circle2.setCenterX(centerX+(x*30));
            circle2.setCenterY(centerY-(y*5)-(z*15));
            group.getChildren().add(circle2);
            drawLine(x,y,z,group);
        }
    }

    /**
     * Adds the main axis markers to the graphic.
     * @param group group of shapes to draw
     * @param canvas canvas object used for the graphic.
     */
    private static void addAxis(Group group,Canvas canvas){
        Ellipse mark15 = new Ellipse();
        mark15.radiusXProperty().setValue(450);
        mark15.radiusYProperty().setValue(75);
        mark15.setCenterX(centerX);
        mark15.setCenterY(centerY);
        mark15.fillProperty().setValue(Color.rgb(255,255,255,.4));

        Ellipse mark10 = new Ellipse();
        mark10.radiusXProperty().setValue(300);
        mark10.radiusYProperty().setValue(50);
        mark10.setCenterX(centerX);
        mark10.setCenterY(centerY);
        mark10.fillProperty().setValue(Color.rgb(255,255,255,.5));

        Ellipse mark5= new Ellipse();
        mark5.radiusXProperty().setValue(150);
        mark5.radiusYProperty().setValue(25);
        mark5.setCenterX(centerX);
        mark5.setCenterY(centerY);
        mark5.fillProperty().setValue(Color.rgb(255,255,255,.75));

        Ellipse zAxis = new Ellipse();
        zAxis.setRadiusX(450);
        zAxis.setRadiusY(225);
        zAxis.setCenterX(centerX);
        zAxis.setCenterY(centerY);
        zAxis.fillProperty().setValue(Color.rgb(255,255,255,.1));

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.strokeText("15ly",centerX-450,centerY);
        gc.strokeText("10ly",centerX-300,centerY);
        gc.strokeText("5ly",centerX-150,centerY);

        group.getChildren().addAll(mark15,mark10,mark5,zAxis);
    }

    /**
     * Draws the main axis lines to the system for the local map.
     * @param x systems x value
     * @param y systems y value
     * @param z systems z value
     * @param group group of objects to be added.
     */
    private static void drawLine(double x, double y, double z,Group group) {
        Color fill;
        if (z<0){
            fill = Color.rgb(240,51,108,.25);
        } else {
            fill = Color.rgb(240,51,108,.5);
        }
        Line line1 = new Line();
        line1.setStartX(centerX);
        line1.setStartY(centerY);
        line1.setEndX(centerX+(x*30));
        line1.setEndY(centerY-(y*5));
        line1.setStroke(Color.rgb(240,51,108,.5));
        line1.setStrokeWidth(3);

        Line line2 = new Line();
        line2.setStartX(centerX+(x*30));
        line2.setStartY(centerY-(y*5));
        line2.setEndX(centerX+(x*30));
        line2.setEndY(centerY-(y*5)-(z*15));
        line2.setStroke(fill);
        line2.setStrokeWidth(3);

        group.getChildren().addAll(line1,line2);
    }
}
