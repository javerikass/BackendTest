package by.clevertec.check;

import by.clevertec.check.dao.CheckDao;
import by.clevertec.check.dao.DiscountCardDao;
import by.clevertec.check.entity.DiscountCard;
import by.clevertec.check.entity.Item;
import by.clevertec.check.exception.ItemNotFoundException;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.Objects;

@Component
public class Check {

    public static final String SPACE = "      ";
    public static final String DASH = "-";
    public static final String DISCOUNT_CARD = "card";
    private static final String CHECK_PARAMETERS = "QTY    Name     Price     Total";
    String itemName;
    String itemId;
    double itemPrice;
    int itemQuantity;
    int counterItemWithDiscount;
    double itemPriceWithDiscount;
    double cardDiscount;
    double sumPromotionalItemWithDiscount;
    double sumPromotionalItemWithoutDiscount;
    double amountDiscount;
    boolean isPromotionalItem;
    double checkSum;
    double totalItemPrice;

    static StringBuilder checkForRest = new StringBuilder();
    File checkFile = new File("src/main/resources/checkFile.txt");


    /**
     * Метод для рассчёта чека,
     *
     * @param items - список параметров
     * @throws NumberFormatException - получены не корректные данные
     */
    public void checkCalculate(List<String> items) throws NumberFormatException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(checkFile, true)))) {
            writeIntoCheck(writer, CHECK_PARAMETERS);
            for (String itemParameters : items) {
                String[] split = itemParameters.split(DASH);
                if (split.length == 2 && split[1].matches("[1-9]+")) {
                    itemQuantity = Integer.parseInt(split[1]);
                    itemId = split[0];
                } else {
                    System.out.println("Invalid data");
                    throw new NumberFormatException("Invalid data");
                }
                /*
                 * @totalItemPrice - полная цена товара
                 */
                totalItemPrice = calculatePrice(itemId, itemQuantity);
                /*
                 * @outputParameters - строка данных для вывода/записи в консоль и файл
                 */
                String outputParameters = itemQuantity + SPACE + itemName + SPACE + itemPrice + SPACE + totalItemPrice;
                /*
                 * Запись не акционного товара
                 * @checkSum - полная сумма чека
                 */
                if (!Objects.equals(itemId, DISCOUNT_CARD) && !isPromotionalItem) {
                    writeIntoCheck(writer, outputParameters);
                    checkSum += totalItemPrice;
                    /*
                     * Запись акционного товара
                     * @sumPromotionalItemWithoutDiscount - сумма акционных товаров в случае наличия их в чеке >=5
                     * @sumPromotionalItemWithDiscount - сумма акционных товаров в случае наличия их в чеке <5
                     */
                } else if (isPromotionalItem) {
                    sumPromotionalItemWithoutDiscount += itemPrice;
                    sumPromotionalItemWithDiscount += totalItemPrice;
                    isPromotionalItem = false;
                    writeIntoCheck(writer, outputParameters);
                }
            }
            /*
             * Проверка счётчика акционных товаров
             * @amountDiscount - сумма полной скидки по чеку
             * Рассчёт скидки в 10% по позиции "акционные товары"
             * @sumPromotionalItemWithoutDiscount - сумма акционных товаров в случае наличия их в чеке >=5
             */
            if (counterItemWithDiscount >= 5) {
                amountDiscount = sumPromotionalItemWithoutDiscount * 0.01;
                checkSum += sumPromotionalItemWithoutDiscount -= amountDiscount;
                /*
                 * В случае наличия только 4 и меньше акционных товаров,
                 * в сумму чека входит цена товара только со своей скидкой
                 * @sumPromotionalItemWithDiscount - сумма акционных товаров в случае наличия их в чеке <5
                 */
            } else {
                checkSum += sumPromotionalItemWithDiscount;
            }
            /*
             * Рассчёт общей скидки по чеку, при наличии скидочной карты.
             */
            if (cardDiscount > 0) {
                double amountCardDiscount = checkSum * cardDiscount / 100;
                checkSum -= amountCardDiscount;
                if (counterItemWithDiscount >= 5) {
                    amountDiscount += amountCardDiscount;
                } else {
                    amountDiscount = amountCardDiscount;
                }
            }
            String discountAndTotalFormat = String.format("Discount: %.2f%nTotal: %.2f", amountDiscount, checkSum);
            writeIntoCheck(writer, discountAndTotalFormat);
        } catch (ItemNotFoundException | IOException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Метод для рассчёта цены товара,
     *
     * @return - рассчитанную цену товара
     * @throws ItemNotFoundException - по переданному id item не был найден
     */
    public double calculatePrice(String itemId, int quantity) throws ItemNotFoundException {
        /*
         * При передаче id скидочной карты, этот блок кода
         * устанавливает скидку в поле @cardDiscount
         */
        if (Objects.equals(itemId, DISCOUNT_CARD)) {
            DiscountCardDao discountCardDao = new DiscountCardDao();
            DiscountCard cardById = discountCardDao.getCardById(itemId);
            if (Objects.isNull(cardById)) {
                throw new ItemNotFoundException();
            } else {
                cardDiscount = cardById.getDiscount();
            }
            return 0;
        }
        CheckDao checkDao = new CheckDao();
        Item item = checkDao.getItemById(Integer.parseInt(itemId));
        if (Objects.isNull(item)) {
            throw new ItemNotFoundException();
        }
        itemPrice = item.getPrice();
        itemName = item.getName();
        /*
          Определяет являеться ли товар акционным.
          Устанавливается счётчик @counterItemWithDiscount
          для определения 5 и более акционных товаров, а также флаг @isPromotionalItem
          для последующей работы с акционным товаром.
         */
        if (item.isPromotionalItem()) {
            counterItemWithDiscount++;
            isPromotionalItem = true;
            itemPriceWithDiscount = itemPrice - (itemPrice * item.getDiscount() / 100);
            itemPrice *= quantity;
            return itemPriceWithDiscount * quantity;
        }
        return itemPrice * quantity;
    }


    /**
     * Выводит/записывате полученные данные чека,
     * в консоль, строку и файл
     *
     * @param writer - BufferedWriter для записи данных в файл
     * @param s      - данные чека
     */
    private void writeIntoCheck(BufferedWriter writer, String s) throws IOException {
        System.out.println(s);
        checkForRest.append(s).append("<br>");
        writer.write(s);
        writer.newLine();
    }

    public static StringBuilder getCheckForRest() {
        return checkForRest;
    }
}

