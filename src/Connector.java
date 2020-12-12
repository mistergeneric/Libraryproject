import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Connector {

    private final String url = "jdbc:postgresql://devweb2020.cis.strath.ac.uk:5432/cs253";
    private final String username;
    private final String password;
    private Connection connection;

    public Connector(String username, String password) {
        this.username = username;
        this.password = password;
    }


    private void connect() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
        } catch (SQLException throwable) {
            System.out.println("Password/Username error");
            throwable.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void queryLoanedMembers(String userInput) {
        try {
            connect();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT first_name from member m WHERE " +
                    "m.member_id IN " +
                    "(SELECT m2.member_id from member m2, loan l, loan_item li, item i, " + userInput + " t " +
                    "where m2.member_id = l.member_id and l.loan_id = li.loan_id and li.item_id = t.item_id)");
            ResultSet rs = preparedStatement.executeQuery();

            int i = 0;
            List<String> memberNames = new ArrayList<>();
            while (rs.next()) {
                String firstName = rs.getString("first_name");
                memberNames.add(firstName);
            }
            System.out.println("Members with " + userInput + "'s on loan:");
            memberNames.forEach(System.out::println);
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Problem querying data");
        }
    }

    public void insert(String[] inputs) {
        connect();
        String insertStatement = "INSERT INTO Member (member_id, first_name, last_name, gender) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);

            preparedStatement.setInt(1, Integer.parseInt(inputs[0]));
            preparedStatement.setString(2, inputs[1]);
            preparedStatement.setString(3, inputs[2]);
            //setCharacter does not exist - setString is recommended but will be added to db as char
            preparedStatement.setString(4, String.valueOf(inputs[3]));

            int row = preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("Update successful - " + row + " rows added");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Inserting to Database failed");
        }
    }
}
