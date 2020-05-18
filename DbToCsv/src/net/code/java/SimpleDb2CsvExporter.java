package net.code.java;

import java.io.*;
import java.sql.*;

public class SimpleDb2CsvExporter {
	
	public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/javascv";
        String username = "root";
        String password = "";
         
        String csvFilePath = "categories-export-simple2.csv";
        
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            String sql = "SELECT * FROM (SELECT * FROM categories ORDER BY id DESC LIMIT 2) sub ORDER BY id ASC;";
             
            Statement statement = connection.createStatement();
             
            ResultSet result = statement.executeQuery(sql);
             
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));
             
            // write header line containing column names       
            fileWriter.write("id, name, val, created_at");
             
            while (result.next()) {
            	int id = result.getInt("id");
                String name = result.getString("name");
                int val = result.getInt("val");
                Timestamp created_at = result.getTimestamp("created_at");

                 
                String line = String.format("\"%d\",%s, %d, %s",
                        id, name, val, created_at);
                 
                fileWriter.newLine();
                fileWriter.write(line);            
            }
             
            statement.close();
            fileWriter.close();
             
        } catch (SQLException e) {
            System.out.println("Datababse error:");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File IO error:");
            e.printStackTrace();
        }
         
    }

}
