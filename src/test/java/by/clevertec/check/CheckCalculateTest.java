package by.clevertec.check;

import by.clevertec.check.exception.ItemNotFoundException;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckCalculateTest {

    Check check = new Check();

    @Test
    void check() throws IOException {
        List<String> itemList = Arrays.asList("1-1", "2-2", "3-3", "4-4", "5-5", "6-6", "7-7", "card-1234");
        check.checkCalculate(itemList);
        File actual = new File("src/main/resources/checkFile.txt");
        File expected = new File("src/test/resources/testFile.txt");
        assertTrue(FileUtils.contentEquals(actual, expected));
        cleanData();
    }

    void cleanData() throws IOException {
        new FileOutputStream("src/main/resources/checkFile.txt", false).close();
    }

    @Test
    void calculatePrice() throws ItemNotFoundException {
        double v = check.calculatePrice("1", 3);
        assertEquals(15, v);
    }
}