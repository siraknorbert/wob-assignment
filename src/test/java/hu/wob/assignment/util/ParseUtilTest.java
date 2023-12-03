package hu.wob.assignment.util;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ParseUtilTest {

    @Test
    public void testGetStringValueValidInput() {
        String result = ParseUtil.getStringValue("Test");
        Assertions.assertEquals("Test", result);
    }

    @Test
    public void testGetStringValueNullInput() {
        String result = ParseUtil.getStringValue(null);
        Assertions.assertNull(result);
    }

    @Test
    public void testGetDoubleValueValidInput() {
        Double result = ParseUtil.getDoubleValue(10);
        Assertions.assertEquals(10.0, result);
    }

    @Test
    public void testGetDoubleValueNullInput() {
        Double result = ParseUtil.getDoubleValue(null);
        Assertions.assertNull(result);
    }

    @Test
    public void testGetIntegerValueValidInput() {
        Integer result = ParseUtil.getIntegerValue(5);
        Assertions.assertEquals(5, result);
    }

    @Test
    public void testGetIntegerValueNullInput() {
        Integer result = ParseUtil.getIntegerValue(null);
        Assertions.assertNull(result);
    }

    @Test
    public void testGetLocalDateValueValidInput() {
        String dateStr = "2023-12-31";
        LocalDate result = ParseUtil.getLocalDateValue(dateStr, "yyyy-MM-dd");
        Assertions.assertEquals(LocalDate.parse(dateStr), result);
    }

    @Test
    public void testGetLocalDateValueNullInput() {
        LocalDate result = ParseUtil.getLocalDateValue(null, "yyyy-MM-dd");
        Assertions.assertNull(result);
    }
}
