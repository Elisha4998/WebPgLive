package net.codejava;

import java.io.*;
import java.sql.*;

public class AdvancedDb2CsvExporter {

	private BufferedWriter fileWriter;
    
    public void export(String table) //table is any table from userregistration db passed to export() t Reposi
    {
    	
    	String url = "jdbc:mysql://localhost:3306/userregistration";
		String user = "root";
		String password = "";
		
		//File name of csv
		String csvFileName = table.concat("_Export.csv"); 
        
        try (Connection connection = DriverManager.getConnection(url, user, password))
        {
        	//To print all the db details as a csv file
        	 String sql = "SELECT * FROM ".concat(table);
             
             Statement statement = connection.createStatement();
             
             //To execute the given query, sql
             ResultSet result = statement.executeQuery(sql);
              
             fileWriter = new BufferedWriter(new FileWriter(csvFileName));
              
             int columnCount = writeHeaderLine(result);
              
             while (result.next()) {
                 String line = "";
                  
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
        AdvancedDb2CsvExporter exporter = new AdvancedDb2CsvExporter();
        exporter.export("usertable");
    } 
	
}
