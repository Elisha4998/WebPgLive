package net.codejava;

import java.io.*;
import java.sql.*;

public class AdvancedDb2CsvExporter {

	private BufferedWriter fileWriter;
    
    public void export(String table, String sql1) //table is any table from userregistration db passed to export() t Reposi
    {
    	
    	String url = "jdbc:mysql://localhost/javascv";
		String user = "root";
		String password = "";
		
		String sql = "SELECT * FROM " .concat(table);
		
		//File name of csv
		String csvFileName = table.concat("_Export.csv"); 
        
        try (Connection connection = DriverManager.getConnection(url, user, password))
        {
        	
             
             Statement statement = connection.createStatement();
             
             
             
           //To execute the given query, sql
             ResultSet result1 = statement.executeQuery(sql1);
              
             fileWriter = new BufferedWriter(new FileWriter(csvFileName));
             
             while (result1.next()) {
            	 String line = "";
            	 
            	 fileWriter.newLine();
                 fileWriter.write(line);  
             }
              result1.close();
              
            //To execute the given query, sql
              ResultSet result = statement.executeQuery(sql);
              int columnCount = writeHeaderLine(result);
             while (result.next()) {
                 String line = "";
                  // Make sure you extract only One line from DB Table and Write that into CSV.. i'm not sure this is the right place.. 
                  // Another thing.. This Java App should keep running and never exit.. if you have hit the last line of DB, go back and pick the first line.. have atleast 4-5 sec delay between csv updates
                  
                 for (int i = 1; i <= columnCount; i++) {
                     Object valueObject = result.getObject(i);
                     String valueString = "";
                      
                     if (valueObject != null) valueString = valueObject.toString();
                      
                     if (valueObject instanceof String) {
                         valueString = "\"" + escapeDoubleQuotes(valueString) + "\"";
                     }
                      
                     line = line.concat(valueString);
                      
                     if (i != columnCount) {
                         line = line.concat(",");
                     }
                 }
                  
                 fileWriter.newLine();
                 fileWriter.write(line);            
             }
              
             result.close();
             statement.close();
             fileWriter.close();
        }
        catch (SQLException e)//Catch for db connection error
        {
            System.out.println("Datababse error:");
            e.printStackTrace();
        } 
        catch (IOException e)//Catch for file I/O errer
        {
            System.out.println("File IO error:");
            e.printStackTrace();
        }
    }
    
    public int writeHeaderLine(ResultSet result) throws SQLException, IOException {
        // write header line containing column names
        ResultSetMetaData metaData = result.getMetaData();
        int numberOfColumns = metaData.getColumnCount();
        String headerLine = "";
         
        for (int i = 1; i <= numberOfColumns; i++) {
            String columnName = metaData.getColumnName(i);
            headerLine = headerLine.concat(columnName).concat(",");
        }
         
        fileWriter.write(headerLine.substring(0, headerLine.length() - 1));
         
        return numberOfColumns;
    }
    
    public String escapeDoubleQuotes(String value) {
        return value.replaceAll("\"", "\"\"");
    }
     
    public static void main(String[] args) //To pass the table to export()
    {
    	//To print all the db details as a csv file
   	 String sql1 = "select *from categories ORDER BY id DESC LIMIT 1;";
        AdvancedDb2CsvExporter exporter = new AdvancedDb2CsvExporter();
        exporter.export("categories", sql1);
    } 
	
}