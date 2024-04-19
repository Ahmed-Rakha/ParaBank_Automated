package POM_Pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnector {

    //    SQL CREDENTIALS
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/Para_Bank";
    private final String DB_USERNAME = "root";
    private final String DB_PASSWORD = "password";

    /* I've moved the "Class.forName" into a static block to avoid redundancy and ensures
       it is executed when the class is loaded.*/
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Set connection and Prepare statement
    private PreparedStatement preparedStatement(String SQL_QUERY) throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL, DB_USERNAME, DB_PASSWORD);
        return connection.prepareStatement(SQL_QUERY);
    }

    // Method to Insert data into the database
    public void insertUsersData(String first_name, String last_name, String street_Address, String city, String country, String zipCode, String phoneNumber, String SSN, String username, String user_password) {
        // SQL insert statement
        String INSERT_SQL = "INSERT INTO users (first_name, last_name, street_Address, city, country, zipCode, phoneNumber, SSN, username, user_password) VALUES (?,?,?,?,?,?,?,?,?,?)";


        try (
                PreparedStatement pstmt = preparedStatement(INSERT_SQL);
        ) {
            // Set parameters for the prepared statement
            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, street_Address);
            pstmt.setString(4, city);
            pstmt.setString(5, country);
            pstmt.setString(6, zipCode);
            pstmt.setString(7, phoneNumber);
            pstmt.setString(8, SSN);
            pstmt.setString(9, username);
            pstmt.setString(10, user_password);
            // Execute the insert statement
            pstmt.executeUpdate();
            System.out.println("Data inserted successfully to users table !.");
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }

    public void insertBillData(String payeeName, String street_Address, String city, String state, String zipCode, String phoneNumber, String accountNumber, String amount) {
        // SQL insert statement
        String INSERT_SQL = "INSERT INTO bill_data ( payeeName,  address, city, state, zipCode, phoneNumber, accountNumber, amount)VALUES(?,?,?,?,?,?,?,?)";

        try (
                PreparedStatement pstmt = preparedStatement(INSERT_SQL);
        ) {
            // Set parameters for the prepared statement
            pstmt.setString(1, payeeName);
            pstmt.setString(2, street_Address);
            pstmt.setString(3, city);
            pstmt.setString(4, state);
            pstmt.setString(5, zipCode);
            pstmt.setString(6, phoneNumber);
            pstmt.setString(7, accountNumber);
            pstmt.setString(8, amount);
            // Execute the insert statement
            pstmt.executeUpdate();
            System.out.println("Data inserted successfully to bill_data table !");
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }

    public void updateContactInfo(String targetColumn, String updatedValue, String username) {
        String UPDATE_SQL = "UPDATE users SET " + targetColumn + " = ? WHERE username=?";
        try (
                PreparedStatement pstmt = preparedStatement(UPDATE_SQL);
        ) {
            pstmt.setString(1, updatedValue);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
            System.out.println("Value/s updated successfully !");
        } catch (SQLException e) {
            System.err.println("Error updating data: " + e.getMessage());
        }
    }
}

