import org.example.Main;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainValidationRangeTest {

    @Nested
    class BelowAvailableValues{

        @Test
        void validateRange_lowerValues_ThrowIllegalArgumentException() {
            double value = -143.001;

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                Main.validateRange(value);
            });

            assertTrue(exception.getMessage().contains("виходить за межі"));
        }

        @Test
        void validateRange_minDoubleValue_ThrowIllegalArgumentException() {
            double value = -Double.MAX_VALUE;

            assertThrows(IllegalArgumentException.class, () -> {
                Main.validateRange(value);
            });
        }

        @Test
        void validateRange_belowRange_ThrowIllegalArgumentException() {
            double value = -500.0;

            assertThrows(IllegalArgumentException.class, () -> {
                Main.validateRange(value);
            });
        }
    }

    @Nested
    class OverAvailableValues {

        @Test
        void validateRange_higherValues_ThrowIllegalArgumentException() {
            double value = 143.001;

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                Main.validateRange(value);
            });

            assertTrue(exception.getMessage().contains("виходить за межі"));
        }

        @Test
        void validateRange_maxDoubleValue_ThrowIllegalArgumentException() {
            double value = Double.MAX_VALUE;

            assertThrows(IllegalArgumentException.class, () -> {
                Main.validateRange(value);
            });
        }

        @Test
        void validateRange_OverRange_ThrowIllegalArgumentException() {
            double value = 500.0;

            assertThrows(IllegalArgumentException.class, () -> {
                Main.validateRange(value);
            });
        }
    }
}
