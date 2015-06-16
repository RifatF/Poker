/**
 * Created by Rigen on 06.12.14.
 */
public interface Strings {
    public final String START = "Игра началась!";
    public final String FIRST_ROUND_START = "Начинается первый круг. Сейчас вам будут розданы три карты.";
    public final String SECOND_ROUND_START = "Начинается второй круг. Сейчас вам раздадут карту.";
    public final String THIRD_ROUND_START = "Начинается третий круг. Сейчас вам раздадут карту.";
    public final String EXCHANGE = "Напишите через пробел номера карт, которые вы хотите сменить. Напишите \"0\" если не хотите менять карты.";
    public final String TRADE = "Начинается круг торгов. Пожалуйста, введите сумму ставки. Она должна быть не меньше уже принятой ставки и не больше вашего счёта.";
    public final String BET_OK = "Ставка принята.";
    public final String BET_UNCORRECT = "Пожалуйста, введите ставку равную, или превышающую максимальную предыдущую ставку.";
    public final String BET_AGAIN = "Ставку увеличили. Пожалуйста, введите ставку равную, или превышающую максимальную предыдущую ставку.";
    public final String BET_LAST = "Минимальная ставка: ";
    public final String EXCHANGE_NULL = "null";
    public final String BET_REWRITE = "Пожалуйста, введите корректную ставку.";
    public final String REGEX= "^Начинается ((второй)|(третий)) круг.*";
    public final String EXCHANGE_UNCORRECT = "Неправильно введены номера карт. Пожалуйста, введите номера в формате \"1 3 4\".";
    public final String WIN = "Поздравляю, вы выйграли.";
    public final String LOSE = "Вы проиграли.";
    public final String OPEN_HAND = "Вскрываем карты.";
    public final String ANNOUNCEMENT = "Start";

}
