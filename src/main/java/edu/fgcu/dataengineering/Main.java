package edu.fgcu.dataengineering;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVParser;
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

    //load contents of authors json into array of Authors
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

    //load contents of book-csv into array of Books
    reader = new CSVReader(new FileReader("src/Data/bookstore_report2.csv"));
    String[] bookLine ;
    ArrayList<Book> bookList = new ArrayList<>();
    Book newBook;
    //skip metadata line
    reader.readNext();
    while ((bookLine = reader.readNext()) != null) {
      newBook = new Book(); // of the below fields, we are only inserting isbn, publisher, author, and title
      newBook.setIsbn(bookLine[0]);
      newBook.setBookTitle(bookLine[1]);
      newBook.setAuthorName(bookLine[2]);
      newBook.setPublisherName(bookLine[3]);
      newBook.setStoreName(bookLine[4]);
      newBook.setStoreLoc(bookLine[5]);
      //insert statement
      insert = "insert into book(isbn, publisher_name, author_name, book_title) values (?,?,?,?);";
      try(PreparedStatement st = conn.prepareStatement((insert))){
        st.setString(1, newBook.getIsbn());
        st.setString(2, newBook.getPublisherName());
        st.setString(3, newBook.getAuthorName());
        st.setString(4, newBook.getBookTitle());
        st.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
