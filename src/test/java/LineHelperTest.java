import org.example.Line;
import org.example.LineHelper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LineHelperTest {

    LineHelper lineHelper = new LineHelper();

    @Nested
    class CoincideTests {

        //1.всі три прямі - ліва границя діапазону
        @Test
        void allThreePoints_leftBoundary() {
            Line line1 = Line.fromTwoPoints(-143, -143, -142, -143);
            Line line2 = Line.fromSlopeAndIntercept(0, -143);
            Line line3 = Line.fromPointAndNormal(-143, -143, 0, 1);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals("Прямі співпадають", result);
        }

        //2.всі три прямі - права границя діапазону
        @Test
        void allThreePoints_rightBoundary() {
            Line line1 = Line.fromTwoPoints(142, 143, 143, 143);
            Line line2 = Line.fromSlopeAndIntercept(0, 143);
            Line line3 = Line.fromPointAndNormal(143, 143, 0, 1);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals("Прямі співпадають", result);
        }

        //3. всі три прямі типове(середнє ) значення класу
        @Test
        void allThreePoints_average() {
            Line line1 = Line.fromTwoPoints(0, 5, 10, 5);
            Line line2 = Line.fromSlopeAndIntercept(0, 5);
            Line line3 = Line.fromPointAndNormal(5, 5, 0, 1);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals("Прямі співпадають", result);
        }

        // 4.одна пряма найближче до лівої, друга - середнє значення, третя - найближче до правої
        @Test
        void leftBoundary_averageBoundary_rightBoundary() {
            Line line1 = Line.fromTwoPoints(-143, 5, -142, 5);
            Line line2 = Line.fromSlopeAndIntercept(0, 5);
            Line line3 = Line.fromPointAndNormal(143, 5, 0, 1);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals("Прямі співпадають", result);
        }

        // 5. дві прямі – найближче до лівої границі та наступне за ним, третьої – типове значення
        @Test
        void leftBoundary_nextLeft_average() {
            Line line1 = Line.fromTwoPoints(-143, -142.5, -142, -142.5);
            Line line2 = Line.fromTwoPoints(-142, -142.5, -141, -142.5);
            Line line3 = Line.fromPointAndNormal(0, -142.5, 0, 1);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals("Прямі співпадають", result);
        }

        // 6. дві прямих – найближче до правої границі й те, що передує йому, третьої – типове значення
        @Test
        void rightBoundary_previous_average() {
            Line line1 = Line.fromTwoPoints(143.0, -50.55, 142.0, -50.55);
            Line line2 = Line.fromTwoPoints(142.9, -50.55, 141.9, -50.55);
            Line line3 = Line.fromSlopeAndIntercept(0.0, -50.55);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals("Прямі співпадають", result);
        }
    }

    @Nested
    class NoCommonPoints {
        //1.всі три прямі - ліва границя діапазону
        @Test
        void allThreePoints_leftBoundary_NoCommonPoints() {
            Line line1 = Line.fromTwoPoints(-10, -143, 10, -143);
            Line line2 = Line.fromSlopeAndIntercept(0, -143);
            Line line3 = Line.fromPointAndNormal(0, -142, 0, 1);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals("Прямі не мають спільних точок", result);
        }

        @Test
        //2.всі три прямі - права границя діапазону
        public void allRightBoundaries_NoCommonPoints() {
            Line line1 = Line.fromTwoPoints(-5, 142, 5, 142);
            Line line2 = Line.fromSlopeAndIntercept(0, 143);
            Line line3 = Line.fromPointAndNormal(0, 143, 0, 1);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals("Прямі не мають спільних точок", result);
        }

        @Test
        //3. всі три прямі типове(середнє ) значення класу
        public void allAverage_NoCommonPoints() {
            Line line1 = Line.fromTwoPoints(-10, 0, 10, 0);
            Line line2 = Line.fromSlopeAndIntercept(0, 1);
            Line line3 = Line.fromPointAndNormal(0, -1, 0, 1);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals("Прямі не мають спільних точок", result);
        }

        @Test
        // 4.одна пряма найближче до лівої, друга - середнє значення, третя - найближче до правої
        public void left_average_right_NoCommonPoints() {
            Line line1 = Line.fromTwoPoints(-100, 0, 100, 0);
            Line line2 = Line.fromSlopeAndIntercept(0, -143);
            Line line3 = Line.fromPointAndNormal(50, 143, 0, 1);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals("Прямі не мають спільних точок", result);
        }

        @Test
        // 5. дві прямі – найближче до лівої границі та наступне за ним, третьої – типове значення
        public void twoLeft_oneMiddle_NoCommonPoints() {
            Line line1 = Line.fromTwoPoints(0, -142.5, 10, -142.5);
            Line line2 = Line.fromSlopeAndIntercept(0, -143);
            Line line3 = Line.fromPointAndNormal(0, 0, 0, 1);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals("Прямі не мають спільних точок", result);
        }

        @Test
        // 6. дві прямих – найближче до правої границі й те, що передує йому, третьої – типове значення
        public void twoRight_oneMiddle_NoCommonPoints() {
            Line line1 = Line.fromTwoPoints(-10, 142.5, -5, 142.5);
            Line line2 = Line.fromSlopeAndIntercept(0, 143);
            Line line3 = Line.fromPointAndNormal(0, 0, 0, 1);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals("Прямі не мають спільних точок", result);
        }
    }

    @Nested
    class SingleIntersectionPoint {
        public String expectedResult(double x, double y) {
            return String.format("Єдина точка перетину прямих (x0, y0), x0=%.3f, y0=%.3f", x, y);
        }

        //1.всі три прямі - ліва границя діапазону
        @Test
        void allLeftBoundaries_SingleIntersectionPoint() {
            Line line1 = Line.fromTwoPoints(-144.0, -144.0, -142.0, -142.0);
            Line line2 = Line.fromSlopeAndIntercept(2.0, 143.0);
            Line line3 = Line.fromPointAndNormal(-140.0, -143.0, 0.0, 1.0);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals(expectedResult(-143.0, -143.0), result);
        }

        //2.всі три прямі - права границя діапазону
        @Test
        void allRightBoundaries_SingleIntersectionPoint() {
            Line line1 = Line.fromTwoPoints(142.0, 142.0, 144.0, 144.0);
            Line line2 = Line.fromSlopeAndIntercept(2.0, -143.0);
            Line line3 = Line.fromPointAndNormal(143.0, 100.0, 1.0, 0.0);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals(expectedResult(143.0, 143.0), result);
        }

        //3. всі три прямі типове(середнє ) значення класу
        @Test
        void allAverage_SingleIntersectionPoint() {
            Line line1 = Line.fromTwoPoints(0.0, 0.0, 2.0, 2.0);
            Line line2 = Line.fromSlopeAndIntercept(-1.0, 2.0);
            Line line3 = Line.fromPointAndNormal(1.0, 1.0, 2.0, -1.0);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals(expectedResult(1.0, 1.0), result);
        }

        // 4.одна пряма найближче до лівої, друга - середнє значення, третя - найближче до правої
        @Test
        void left_average_right_SingleIntersectionPoint() {
            Line line1 = Line.fromTwoPoints(-143.0, -142.0, -140.0, -139.0);
            Line line2 = Line.fromSlopeAndIntercept(-1.0, 1.0);
            Line line3 = Line.fromPointAndNormal(143.0, 1.0, 0.0, 1.0);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals(expectedResult(0.0, 1.0), result);
        }

        @Test
            // 5. дві прямі – найближче до лівої границі та наступне за ним, третьої – типове значення
        void twoLeft_oneMiddle_SingleIntersectionPoint() {
            Line line1 = Line.fromPointAndNormal(-143.0, -100.0, 1.0, 0.0);
            Line line2 = Line.fromTwoPoints(-142.0, -142.0, -141.0, -141.0);
            Line line3 = Line.fromSlopeAndIntercept(-1.0, -286.0);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals(expectedResult(-143.0, -143.0), result);
        }

        // 6. дві прямих – найближче до правої границі й те, що передує йому, третьої – типове значення
        @Test
        void twoRight_oneMiddle_SingleIntersectionPoint() {
            Line line1 = Line.fromPointAndNormal(100.0, 143.0, 0.0, 1.0);
            Line line2 = Line.fromTwoPoints(142.0, 142.0, 140.0, 140.0);
            Line line3 = Line.fromSlopeAndIntercept(2.0, -143.0);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals(expectedResult(143.0, 143.0), result);
        }
    }

    @Nested
    class TwoIntersectionPoints {

        public String expectedResult(double x1, double y1, double x2, double y2) {
            return String.format("Дві точки перетину прямих (x1, y1) = (%.3f, %.3f), (x2, y2) = (%.3f, %.3f)",
                    x1, y1, x2, y2);
        }

        //1.всі три прямі - ліва границя діапазону
        @Test
        void allLeftBoundaries_TwoPoints() {
            Line line1 = Line.fromTwoPoints(-142.0, 0.0, -142.0, 10.0);
            Line line2 = Line.fromPointAndNormal(-143.0, 0.0, 1.0, 0.0);
            Line line3 = Line.fromSlopeAndIntercept(0.0, -143.0);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals(expectedResult(-142.0, -143.0, -143.0, -143.0), result);
        }

        //2.всі три прямі - права границя діапазону
        @Test
        void allRightBoundaries_TwoPoints() {
            Line line1 = Line.fromTwoPoints(143.0, 0.0, 143.0, 10.0);
            Line line2 = Line.fromPointAndNormal(142.0, 50.0, 1.0, 0.0);
            Line line3 = Line.fromSlopeAndIntercept(0.0, 143.0);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals(expectedResult(143.0, 143.0, 142.0, 143.0), result);
        }

        //3. всі три прямі типове(середнє ) значення класу
        @Test
        void allAverage_TwoPoints() {
            Line line1 = Line.fromTwoPoints(0.0, 10.0, 0.0, -10.0);
            Line line2 = Line.fromSlopeAndIntercept(0.0, 1.0);
            Line line3 = Line.fromPointAndNormal(5.0, -1.0, 0.0, 1.0);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals(expectedResult(0.0, 1.0, -0.0, -1.0), result);
        }

        // 4.одна пряма найближче до лівої, друга - середнє значення, третя - найближче до правої
        @Test
        void left_average_right_TwoPoints() {
            Line line1 = Line.fromTwoPoints(143.0, -10.0, 143.0, 10.0);
            Line line2 = Line.fromPointAndNormal(-143.0, 0.0, 1.0, 0.0);
            Line line3 = Line.fromSlopeAndIntercept(0.0, 1.0);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals(expectedResult(143.0, 1.0, -143.0, 1.0), result);
        }

        // 5. дві прямі – найближче до лівої границі та наступне за ним, третьої – типове значення
        @Test
        void twoLeft_oneMiddle_TwoPoints() {
            Line line1 = Line.fromTwoPoints(-142.0, 0.0, -142.0, 5.0);
            Line line2 = Line.fromPointAndNormal(-143.0, 0.0, 1.0, 0.0);
            Line line3 = Line.fromSlopeAndIntercept(0.0, 1.0);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals(expectedResult(-142.0, 1.0, -143.0, 1.0), result);
        }

        // 6. дві прямих – найближче до правої границі й те, що передує йому, третьої – типове значення
        @Test
        void twoRight_oneMiddle_TwoPoints() {
            Line line1 = Line.fromTwoPoints(142.0, -5.0, 142.0, 5.0);
            Line line2 = Line.fromSlopeAndIntercept(0.0, 1.0);
            Line line3 = Line.fromPointAndNormal(143.0, 0.0, 1.0, 0.0);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals(expectedResult(142.0, 1.0, 143.0, 1.0), result);
        }
    }

    @Nested
    class ThreeIntersectionPointsTests {
        public String expectedResult(double x1, double y1, double x2, double y2, double x3, double y3) {
            return String.format("Три точки перетину прямих (x1, y1), (x2, y2) і (x3, y3),%n" +
                            "x1=%.3f, y1=%.3f, x2=%.3f, y2=%.3f, x3=%.3f, y3=%.3f", x1, y1, x2, y2, x3, y3);
        }

        //1.всі три прямі - ліва границя діапазону
        @Test
        void allLeftBoundaries_ThreePoints() {
            Line line1 = Line.fromPointAndNormal(-143.0, -100.0, 1.0, 0.0);
            Line line2 = Line.fromSlopeAndIntercept(0.0, -143.0);
            Line line3 = Line.fromTwoPoints(-142.0, -143.0, -144.0, -141.0);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals(expectedResult(-143.0, -143.0, -142.0, -143.0, -143.0, -142.0), result);
        }

        //2.всі три прямі - права границя діапазону
        @Test
        void allRightBoundaries_ThreePoints() {
            Line line1 = Line.fromTwoPoints(143.0, 0.0, 143.0, 10.0);
            Line line2 = Line.fromSlopeAndIntercept(0.0, 143.0);
            Line line3 = Line.fromTwoPoints(142.0, 143.0, 144.0, 141.0);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals(expectedResult(143.0, 143.0, 142.0, 143.0, 143.0, 142.0), result);
        }

        //3. всі три прямі типове(середнє ) значення класу
        @Test
        void allMiddle_ThreePoints() {
            Line line1 = Line.fromTwoPoints(0.0, -5.0, 0.0, 5.0);
            Line line2 = Line.fromPointAndNormal(5.0, 0.0, 0.0, 1.0);
            Line line3 = Line.fromSlopeAndIntercept(-1.0, 1.0);


            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals(expectedResult(-0.0, -0.0, 1.0, -0.0, -0.0, 1.0), result);
        }

        // 4.одна пряма найближче до лівої, друга - середнє значення, третя - найближче до право
        @Test
        void left_average_right_ThreePoints() {
            Line line1 = Line.fromPointAndNormal(-143.0, 10.0, 1.0, 0.0);
            Line line2 = Line.fromTwoPoints(-10.0, 0.0, 10.0, 0.0);
            Line line3 = Line.fromSlopeAndIntercept(-1.0, 143.0);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals(expectedResult(-143.0, 0.0, 143.0, 0.0, -143.0, 286.0), result);
        }

        // 5. дві прямі – найближче до лівої границі та наступне за ним, третьої – типове значення
        @Test
        void twoLeft_oneMiddle_ThreePoints() {
            Line line1 = Line.fromPointAndNormal(-143.0, 0.0, 1.0, 0.0);
            Line line2 = Line.fromSlopeAndIntercept(1.0, 142.0);
            Line line3 = Line.fromTwoPoints(-10.0, 0.0, 10.0, 0.0);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals(expectedResult(-143.0, -1.0, -142.0, 0.0, -143.0, -0.0), result);
        }

        // 6. дві прямих – найближче до правої границі й те, що передує йому, третьої – типове значення
        @Test
        void twoRight_oneMiddle_ThreePoints() {
            Line line1 = Line.fromPointAndNormal(143.0, 0.0, 1.0, 0.0);
            Line line2 = Line.fromSlopeAndIntercept(-1.0, 142.0);
            Line line3 = Line.fromTwoPoints(-10.0, 0.0, 10.0, 0.0);

            String result = lineHelper.analyzePlacement(line1, line2, line3);
            assertEquals(expectedResult(143.0, -1.0, 142.0, 0.0, 143.0, 0.0), result);
        }
    }
}
