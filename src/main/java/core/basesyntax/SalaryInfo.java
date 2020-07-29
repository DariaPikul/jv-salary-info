package core.basesyntax;

import core.basesyntax.exception.IllegalDateParametersException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SalaryInfo {

    /**
     * <p>Реализуйте метод getSalaryInfo(String[] names, String[] data,
     * String dateFrom, String dateTo)
     * вычисляющий зарплату сотрудников. На вход методу подаётся 2 массива и 2 даты,
     * определяющие период за который надо вычислить зарплату, первый массив содержит имена
     * сотрудников организации, второй массив информацию о рабочих часах и ставке. Формат данных
     * второго массива следующий: дата, имя сотрудника, количество отработанных часов,
     * ставка за 1 час. Метод должен вернуть отчёт за период, который передали в метод
     * (обе даты включительно) составленный по следующей форме: Отчёт за период
     * #дата_1# - #дата_2# Имя сотрудника - сумма заработанных средств за этот период
     * Создать пакет exception и в нём класс-ошибку IllegalDateParametersException. Сделать так,
     * чтобы метод getSalaryInfo выбрасывал IllegalDateParametersException,
     * если dateFrom > dateTo, с сообщeнием "Wrong parameters"</p>
     *
     * <p>Пример ввода: date from = 01.04.2019 date to = 30.04.2019</p>
     *
     * <p>names:
     * Сергей
     * Андрей
     * София</p>
     *
     * <p>data:
     * 26.04.2019 Сергей 60 50
     * 26.04.2019 Андрей 3 200
     * 26.04.2019 Сергей 7 100
     * 26.04.2019 София 9 100
     * 26.04.2019 Сергей 11 50
     * 26.04.2019 Андрей 3 200
     * 26.04.2019 Сергей 7 100
     * 26.04.2019 София 9 100
     * 26.04.2019 Сергей 11 50</p>
     *
     * <p>Пример вывода:
     * Отчёт за период 01.04.2019  - 30.04.2019
     * Сергей - 1550
     * Андрей - 600
     * София - 900</p>
     */
    public String getSalaryInfo(String[] names, String[] data, String dateFrom, String dateTo)
            throws Exception {

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate fromDate = LocalDate.parse(dateFrom, formatter);
        LocalDate toDate = LocalDate.parse(dateTo, formatter);

        if (fromDate.compareTo(toDate) > 0) {
            throw new IllegalDateParametersException("Wrong parameters");
        }

        StringBuilder salaryForInterval =
                new StringBuilder("Отчёт за период " + dateFrom + " - " + dateTo + "\n");

        for (int i = 0; i < names.length; i++) {
            salaryForInterval.append(names[i]).append(" - ");
            int totalSalary = 0;

            for (String d : data) {
                String[] dataSplit = d.split(" ");
                int amountOfHours = Integer.parseInt(dataSplit[2]);
                int salaryPerHour = Integer.parseInt(dataSplit[3]);
                String nameData = dataSplit[1];
                LocalDate dateData = LocalDate.parse(dataSplit[0], formatter);
                totalSalary = (nameData.equals(names[i])
                        && dateData.compareTo(fromDate) >= 0
                        && dateData.compareTo(toDate) <= 0)
                        ? totalSalary + salaryPerHour * amountOfHours
                        : totalSalary;
            }
            salaryForInterval.append(totalSalary);
            if (names.length > i + 1) {
                salaryForInterval.append("\n");
            }
        }

        return salaryForInterval.toString();
    }
}
