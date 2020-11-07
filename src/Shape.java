import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;

public class Shape {

    private Point location;
    private int edges;
    private double radius;
    private double rotation;
    private ArrayList<Point> edgePoints;


    private final static Timer keyOperation = new Timer(100, true);
    private Vector2 clickShift;
    private boolean isDragging = false;
    private final static double speed = 5;
    private static final Font font = new Font("res/font/VeraMono.ttf", 24);

    public Shape(int edges, double radius) {
        this.location = new Point(0.5* Window.getWidth(), 0.5*Window.getHeight());
        this.edges = edges;
        edgePoints = new ArrayList<>();

        changeShape(edges, radius);
    }

    public void update(Input input, double rotateAngle){
        this.draw();

        boolean isCool = keyOperation.isCool();
        if(isCool && input.isDown(Keys.A) && edges>1){
            changeShape(edges-1, radius);
        }else if(isCool && input.isDown(Keys.D)){
            changeShape(edges+1, radius);
        }

        if(input.isDown(Keys.LEFT)){
            moveShape(new Point(-speed, 0));
        }
        if(input.isDown(Keys.RIGHT)){
            moveShape(new Point(+speed, 0));
        }
        if(input.isDown(Keys.UP)){
            moveShape(new Point(0, -speed));
        }
        if(input.isDown(Keys.DOWN)){
            moveShape(new Point(0, +speed));
        }

        Point curMouse = input.getMousePosition();
        if(input.isDown(MouseButtons.LEFT) && curMouse.distanceTo(location)<radius){
            rotation += rotateAngle;
        }

        if(input.isDown(MouseButtons.LEFT) && curMouse.distanceTo(location)< radius){
            if(!isDragging) {
                clickShift = new Vector2(curMouse.x - location.x, curMouse.y - location.y);
                isDragging = true;
            }

            setLocation(new Point(curMouse.x-clickShift.x, curMouse.y-clickShift.y));

        }else{
            isDragging = false;
        }

    }

    public void draw(){
        draw(2);
    }

    public void draw(double thickness){
        draw(thickness, Colour.BLACK);
    }

    public void draw(double thickness, Colour colour){
        edgePoints.forEach(point -> Drawing.drawCircle(point, thickness/2.2, colour));
        for(int i=0;i<edges;i++){
            Drawing.drawLine(edgePoints.get(i), edgePoints.get((i+1)%edges), thickness, colour);
        }
        drawBorder(
                new Point(location.x - radius - thickness, location.y - radius - thickness),
                radius*2 + 2*thickness,
                radius*2 + 1.5*thickness,
                thickness,
                new Colour(0.75294, 0.75294, 0.75294)
        );

        Drawing.drawCircle(location,3, Colour.BLACK);

        String log = "Edges: " + edges;
        font.drawString(log, 50,50 );
    }

    private Point rotate_point(double angle, Point p, Point centerPoint) {

        // translate point back to origin:
        p = new Point(p.x - centerPoint.x, p.y - centerPoint.y);

        // rotate point
        double xNew = p.x * Math.cos(angle) - p.y * Math.sin(angle);
        double yNew = p.x * Math.sin(angle) + p.y * Math.cos(angle);

        // translate point back:
        p = new Point(xNew + centerPoint.x, yNew + centerPoint.y);

        return p;
    }

    public void rotateShape(double angle){

        ArrayList<Point> newEdge = new ArrayList<>();
        edgePoints.forEach(point -> newEdge.add(rotate_point(angle, point, location)));
        this.edgePoints = newEdge;
    }

    public void changeShape(int edges, double radius){
        edgePoints.clear();
        this.edges = edges;
        this.radius = radius;
        Point edgePoint = new Point(location.x, location.y - radius);
        for(int i=0;i<edges;i++){
            edgePoints.add(rotate_point(rotation, edgePoint, location) );
            edgePoint = rotate_point(2*Math.PI/edges, edgePoint, location);
        }
    }

    public void moveShape(Point shift) {

        location = new Point(location.x+shift.x, location.y+ shift.y);
        ArrayList<Point> newEdge = new ArrayList<>();
        edgePoints.forEach(point -> newEdge.add(new Point(point.x + shift.x, point.y + shift.y) ));
        this.edgePoints = newEdge;
    }

    public void setLocation(Point location) {
        this.location = location;

        edgePoints.clear();
        Point edgePoint = new Point(location.x, location.y - radius);
        for(int i=0;i<edges;i++){
            edgePoints.add(rotate_point(rotation, edgePoint, location) );
            edgePoint = rotate_point(2*Math.PI/edges, edgePoint, location);
        }

    }

    public static void drawBorder(Point barTopLeft, double wid, double hei, double thickness){

        drawBorder(barTopLeft, wid, hei, thickness, Colour.BLACK);
    }

    public static void drawBorder(Point barTopLeft, double wid, double hei, double thickness, Colour colour){

        Point borderTopLeft = new Point(barTopLeft.x-thickness/2.0, barTopLeft.y);
        Point borderBottomLeft = new Point(barTopLeft.x-thickness/2.0, barTopLeft.y + hei+thickness/2.0);
        Point borderTopRight = new Point(barTopLeft.x + wid+thickness/2.0, barTopLeft.y);
        Point borderBottomRight = new Point(barTopLeft.x + wid+thickness/2.0, barTopLeft.y + hei+thickness/2.0);

        Drawing.drawLine(new Point(borderTopLeft.x + thickness/2.0, borderTopLeft.y), new Point(borderBottomLeft.x + thickness/2.0, borderBottomLeft.y), thickness, colour);
        Drawing.drawLine(new Point(borderTopRight.x - thickness/2.0, borderTopRight.y), new Point(borderBottomRight.x - thickness/2.0, borderBottomRight.y), thickness, colour);
        Drawing.drawLine(borderTopLeft, borderTopRight, thickness, colour);
        Drawing.drawLine(borderBottomLeft, borderBottomRight, thickness, colour);

    }

}
