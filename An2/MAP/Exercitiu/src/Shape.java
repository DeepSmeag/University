import functionalInterface.Area;

import java.util.Arrays;
import java.util.List;

public class Shape {
    public static <E> void printArea(List<E> l, Area<E> f) {
        l.forEach(x -> System.out.println(f.computeArea(x)));
    }

    public static void main(String[] args) {
        Area<Circle> circleArea = (Circle circle) -> (float) (Math.PI * circle.getRadius() * circle.getRadius());
        Circle circle1 = new Circle(1f);
        Circle circle2 = new Circle(2f);
        float areaC1 = circleArea.computeArea(circle1);
        System.out.println(areaC1);
        printArea(List.of(circle1, circle2), circleArea);

        Area<Square> squareArea = (Square square) -> square.getWidth() * square.getWidth();
        Square square1 = new Square(2f);
        Square square2 = new Square(3f);
        printArea(List.of(square1, square2), squareArea);
    }
}

class Circle {
    private float radius;

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Circle(float radius) {
        this.radius = radius;
    }
}

class Square {
    private float width;

    public float getWidth() {
        return width;
    }

    public void setWidth(float edge) {
        this.width = edge;
    }

    public Square(float edge) {
        this.width = edge;
    }
}