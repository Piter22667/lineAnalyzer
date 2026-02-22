package org.example;

import java.awt.geom.Point2D;

public class LineHelper {

    private static final double EPSILON = 1e-8;

    public String analyzePlacement(Line line1, Line line2, Line line3) {

        if (areCoincident(line1, line2) && areCoincident(line2, line3)) {
            return "Прямі співпадають";
        }

        if (areParallel(line1, line2) && areParallel(line2, line3) &&
                !(areCoincident(line1, line2) && areCoincident(line2, line3))) {
            return "Прямі не мають спільних точок";
        }

        //можливий випадок, коли одна з точок з прямої 1 співпадає з переданою точкою для прямої 3
        // АБО прямі паралельніі до осей координат
        Point2D basicIntersectionPoint = findSimpleIntersection(line1, line2, line3);
        if (basicIntersectionPoint != null) {
            return String.format("Очевидний випадок: єдина точка перетину (x0, y0) , x0=%.3f, y0=%.3f",
                    basicIntersectionPoint.getX(), basicIntersectionPoint.getY());
        }

        if (isIntersectInOnePoint(line1, line2, line3)) {
            Point2D p;
            if (!areParallel(line1, line2)) {
                p = getIntersection(line1, line2);
            } else {
                p = getIntersection(line2, line3);
            }
            return String.format("Єдина точка перетину прямих (x0, y0), x0=%.3f, y0=%.3f", p.getX(), p.getY());
        }

        // Клас 4 — Дві точки перетину (одна пара прямих обовязоково паралельна і не перетинаються, третя їх перетинає)
        if (areParallel(line1, line2) && !areCoincident(line1, line2)) {
            Point2D p1 = getIntersection(line1, line3);
            Point2D p2 = getIntersection(line2, line3);
            return String.format("Дві точки перетину прямих (x1, y1) = (%.3f, %.3f), (x2, y2) = (%.3f, %.3f)",
                    p1.getX(), p1.getY(), p2.getX(), p2.getY());
        }

        if (areParallel(line2, line3) && !areCoincident(line2, line3)) {
            Point2D p1 = getIntersection(line2, line1);
            Point2D p2 = getIntersection(line3, line1);
            return String.format("Дві точки перетину прямих (x1, y1) = (%.3f, %.3f), (x2, y2) = (%.3f, %.3f)",
                    p1.getX(), p1.getY(), p2.getX(), p2.getY());
        }

        if (areParallel(line1, line3) && !areCoincident(line1, line3)) {
            Point2D p1 = getIntersection(line1, line2);
            Point2D p2 = getIntersection(line3, line2);
            return String.format("Дві точки перетину прямих (x1, y1) = (%.3f, %.3f), (x2, y2) = (%.3f, %.3f)",
                    p1.getX(), p1.getY(), p2.getX(), p2.getY());
        }


        if (!areParallel(line1, line2) && !areParallel(line2, line3) && !areParallel(line1, line3)) {
            Point2D p1 = getIntersection(line1, line2);
            Point2D p2 = getIntersection(line2, line3);
            Point2D p3 = getIntersection(line3, line1);
            return String.format("Три точки перетину прямих (x1, y1), (x2, y2) і (x3, y3),%n" +
                            "x1=%.3f, y1=%.3f, x2=%.3f, y2=%.3f, x3=%.3f, y3=%.3f",
                    p1.getX(), p1.getY(), p2.getX(), p2.getY(), p3.getX(), p3.getY());
        }

        throw new IllegalArgumentException("Невідомий випадок");
    }

    // A1*B2 - A2*B1 =0 => паралельні
    private boolean areParallel(Line line1, Line line2) {
        double det = line1.getA() * line2.getB() - line2.getA() * line1.getB();
        return Math.abs(det) < EPSILON;
    }

    private boolean areCoincident(Line line1, Line line2) {
        if (!areParallel(line1, line2)) {
            return false;
        }
        double det2 = line1.getA() * line2.getC() - line2.getA() * line1.getC();
        double det3 = line1.getB() * line2.getC() - line2.getB() * line1.getC();

        return Math.abs(det2) <= EPSILON && Math.abs(det3) <= EPSILON;
    }

    // 3 прямі перетинаються в одній точці, перевіряємо, що визначник
    //із коефіцієнтів системи = 0
    private boolean isIntersectInOnePoint(Line line1, Line line2, Line line3) {
        if (!areParallel(line1, line2)) {
            Point2D p = getIntersection(line1, line2);
            return containsPoint(line3, p);
        } else if (!areParallel(line2, line3)) {
            Point2D p = getIntersection(line2, line3);
            return containsPoint(line1, p);
        } else if (!areParallel(line1, line3)) {
            Point2D p = getIntersection(line1, line3);
            return containsPoint(line2, p);
        }
        return false;
    }

    /// Формула Крамара для знаходження точки перетину двох прямих
    private Point2D getIntersection(Line line1, Line line2) {
        double denominator = line1.getA() * line2.getB() - line2.getA() * line1.getB();
        double x = -(line1.getC() * line2.getB() - line2.getC() * line1.getB()) / denominator;
        double y = -(line1.getA() * line2.getC() - line2.getA() * line1.getC()) / denominator;

        return new Point2D.Double(x, y);
    }

    /// Перевіряємо чи належить точка прямій
    private boolean containsPoint(Line line, Point2D point) {
        double val = line.getA() * point.getX() + line.getB() * point.getY() + line.getC();
        return Math.abs(val) < EPSILON;
    }

    /// якщо прямі паралельні осям координат: Ax + C = 0; By + C = 0;
    private Point2D orthogonalIntersection(Line line1, Line line2) {
        if (Math.abs(line1.getB()) < EPSILON && Math.abs(line2.getA()) < EPSILON) {
            return new Point2D.Double(-line1.getC() / line1.getA(), -line2.getC() / line2.getB());
        }
        //By + C = 0; Ax + C = 0;
        if (Math.abs(line1.getA()) < EPSILON && Math.abs(line2.getB()) < EPSILON) {
            return new Point2D.Double(-line2.getC() / line2.getA(), -line1.getC() / line1.getB());
        }

        return null;
    }

    /// Перевіряємо можливі випадки збігу переданих точок АБО розміщення прямих паралельно до осей координат
    private Point2D findSimpleIntersection(Line line1, Line line2, Line line3) {
        for (Point2D p1 : line1.getInputPoints()) {
            for (Point2D p3 : line3.getInputPoints()) {
                if (Math.abs(p1.getX() - p3.getX()) < EPSILON && Math.abs(p1.getY() - p3.getY()) < EPSILON) {
                    if (containsPoint(line2, p1)) {
                        return p1;
                    }
                }
            }
        }

        Point2D point;

        point = orthogonalIntersection(line1, line2);
        //пряма 1 та пряма 2 вертикальна та горизонтальна відповідно
        if (point != null && containsPoint(line3, point)) {
            return point;
        }

        point = orthogonalIntersection(line2, line3);
        //пряма 2 та пряма 3 вертикальна та горизонтальна відповідно
        if (point != null && containsPoint(line1, point)) {
            return point;
        }

        point = orthogonalIntersection(line1, line3);
        //пряма 1 та пряма 3 вертикальна та горизонтальна відповідно
        if (point != null && containsPoint(line2, point)) {
            return point;
        }

        return null;
    }
}
