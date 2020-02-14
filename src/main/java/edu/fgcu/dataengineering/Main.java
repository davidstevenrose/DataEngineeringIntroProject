package edu.fgcu.dataengineering;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Main method class
 *
 * @author drose
 */
public class Main {

  //private static final String JDBC_DRIVER = "org.h2.Driver";
  private static final String DB_URL = "jdbc:sqlite:src/Data/BookStore.db";
  /**
   * The connection to the provided database.
   */
  static Connection conn;

  public static void main(String[] args) throws IOException, CsvValidationException {    

    //setup jdbc
    try {
      //creates a connection for every instance of Main
      conn = DriverManager.getConnection(DB_URL);
    } catch (Exception e) {
      e.printStackTrace();
    }
    //here, we test the opencsv artifact by parsing the elements of a csv file
    CSVReader reader = new CSVReader(new FileReader("src/Data/SEOExample.csv"));
    String[] line;
    while ((line = reader.readNext()) != null) {
      for (String word : line) {
        System.out.print(word + " ");
      }
      System.out.println();
    }
    //end of testing

    //load contents of authors-csv json into array of Authors
    Gson gson = new Gson();

    Type authorType = new TypeToken<ArrayList<Author>>() {
    }.getType();
    ArrayList<Author> authorList = gson.fromJson(new FileReader("src/Data/authors.json"),
        authorType);

    //add author objects as records to DB
    String insert = "insert into author(author_name, author_url, author_email) values(?,?,?);";
    try (PreparedStatement st = conn.prepareStatement(insert)) {
      for (Author a : authorList) {
        System.out.println("entry inserted --> "+a);
        st.setString(1, a.getAuthor_name());
        st.setString(2, a.getAuthor_url());
        st.setString(3, a.getAuthor_email());
        st.executeUpdate();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
