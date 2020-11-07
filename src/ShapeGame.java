import bagel.*;

import java.util.ArrayList;


// Press A/D to decrease/increase edge, click to rotate, drag to move
// Press shift to change rotation direction
public class ShapeGame extends AbstractGame {

    private final ArrayList<Shape> shapes;
    private double rotation = 2*Math.PI / 300;

    public ShapeGame() {

        shapes = new ArrayList<>();
        /*
        // Draw a dozen of figures
        for(int i=0;i<50;i++){
            shapes.add(new Shape(6, 50+ 2.0/(0.007*i*i) * 100));
        }*/

        shapes.add(new Shape(6, 300));
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {

        ShapeGame shapeGame = new ShapeGame();
        shapeGame.run();
    }

    @Override
    public void update(Input input) {

        shapes.forEach(shape -> shape.update(input, rotation));

        if(input.wasReleased(Keys.LEFT_SHIFT)){
            rotation = -rotation;
        }

    }

}
