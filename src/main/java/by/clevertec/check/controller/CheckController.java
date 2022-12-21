package by.clevertec.check.controller;

import by.clevertec.check.Check;
import by.clevertec.check.exception.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class CheckController {

    @Autowired
    Check check;

    @GetMapping("/check")
    public String getCheck(@RequestParam(name = "itemId", required = false) String[] itemId,
                           @RequestParam(name = "qty", required = false) String[] qty) {

        if (Objects.nonNull(itemId) && Objects.nonNull(qty) && itemId.length == qty.length) {
            try {
                check.checkCalculate(getListParam(itemId, qty));
            } catch (InvalidFormatException e) {
                return e.getMessage();
            }
        }
        return Check.getCheckForRest().toString();
    }

    /**
     * Преобразует полученные массивы параметров,
     * в соответствующий формат
     *
     * @param itemId - массив полученных itemId
     * @param qty    - массив полученных qty
     * @return - List если данные были корректны, иначе исключение
     * @throws InvalidFormatException - получены не корректные данные
     */
    public List<String> getListParam(String[] itemId, String[] qty) throws InvalidFormatException {
        List<String> listParam = new ArrayList<>();
        int counter = 0;
        for (String itemQty : qty) {
            if (itemQty.matches("[1-9]+")) {
                listParam.add(itemId[counter] + "-" + itemQty);
                counter++;
            } else {
                throw new InvalidFormatException();
            }
        }
        return listParam;
    }
}

