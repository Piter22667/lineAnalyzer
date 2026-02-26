package org.example;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Line {
    private final double a;
    private final double b;
    private final double c;

    private final List<Point2D> inputPoints = new ArrayList<>();

    private Line(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public static Line fromTwoPoints(double x1, double y1, double x2, double y2){
        if (Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) == 0){
            throw new IllegalArgumentException("Помилка: точки не можуть співпадати");
        }

        double a = y2 - y1;
        double b = x1 - x2;
        double c = y1*x2 - y2*x1;

        Line line = new Line(a, b, c);
        line.inputPoints.add(new Point2D.Double(x1, y1));
        line.inputPoints.add(new Point2D.Double(x2, y2));

        return line;
    }

    public static Line fromSlopeAndIntercept(double slope, double intercept){
        if(intercept == 0){
            throw new IllegalArgumentException("Помилка: вільний коефіцієнт не може бути рівним нулю за умовою завдання.");
        }

        double a = slope;
        double b = -1.0;
        double c = intercept;

        Line line = new Line(a, b, c);
        line.inputPoints.add(new Point2D.Double(0, c)); //точка перетину прямої y = kx + b з віссю Y => (0; b)

        return  line;
    }

    public static Line fromPointAndNormal(double x0, double y0, double normA, double normB){
        if(Math.pow(normA, 2) + Math.pow(normB,2) == 0){
            throw new IllegalArgumentException("Помилка: нормальний вектор не може бути нульовим.");
        }

        double a = normA;
        double b = normB;
        double c = -normA*x0 - normB*y0;

        Line line = new Line(a, b, c);
        line.inputPoints.add(new Point2D.Double(x0, y0));

        return line;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public List<Point2D> getInputPoints() {
        return inputPoints;
    }
}
