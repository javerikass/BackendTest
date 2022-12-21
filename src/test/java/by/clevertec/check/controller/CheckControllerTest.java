package by.clevertec.check.controller;

import by.clevertec.check.exception.InvalidFormatException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;

class CheckControllerTest {

    @Autowired
    CheckController checkController;

    @Test
    void getListParam() throws InvalidFormatException {
        String[] itemId = {"1", "2", "3"};
        String[] qty = {"1", "2", "3"};
        List<String> actual = checkController.getListParam(itemId, qty);
        List<String> expected = Arrays.asList("1-1", "2-2", "3-3");
        assertLinesMatch(expected, actual);
    }
}