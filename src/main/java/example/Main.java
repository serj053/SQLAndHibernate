package example;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import static example.ConnectionManagerUtil.open;

class Main {
    public static void main(String[] args) {

        Connection connection;
        Statement statement;
        Statement statement2;
        try {
            connection = open();
            statement = connection.createStatement();
            statement2 = connection.createStatement();
            ResultSet resultSet = statement
                    .executeQuery("SELECT DISTINCT course_name  FROM purchaselist");
            while (resultSet.next()) {
                String str = resultSet.getString("course_name");
                System.out.println("Интенсивность продаж " + getSQLSet(str, statement2) + "\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static double getSQLSet(String sql, Statement statement2) throws SQLException {
        LocalDate time1 = null;
        LocalDate time2;
        ResultSet resultSet;
        double n = 0;
        resultSet = statement2.executeQuery("SELECT  `course_name`" +
                ", DATE_FORMAT(`subscription_date`, '%d.%m.%Y') date" +
                " FROM `purchaselist`  where course_name =  '" + sql +
                "' ORDER BY `subscription_date`;");

        while (resultSet.next()) {
            if (resultSet.isFirst()) {
                time1 = DateFromSQLDate.get(resultSet.getString("date"));
            }
            System.out.println(resultSet.getString("course_name") + "  "
                    + resultSet.getString("date"));
            n++;
        }

        resultSet.previous();
        if (resultSet.getString("date") != null) {
            time2 = DateFromSQLDate.get(resultSet.getString("date"));
        } else {
            return 0;
        }

        assert time1 != null;
        long diff = time1.until(time2, ChronoUnit.MONTHS);
        return (n / (diff + 1));

    }
}
