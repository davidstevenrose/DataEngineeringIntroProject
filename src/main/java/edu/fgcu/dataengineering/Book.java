package edu.fgcu.dataengineering;

public class Book {
  private String isbn;
  private int bookYear;
  private String bookTitle;
  private float bookPrice;
  private String publisherName;
  private String authorName;
  private String storeName;
  private String storeLoc;

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public int getBookYear() {
    return bookYear;
  }

  public void setBookYear(int bookYear) {
    this.bookYear = bookYear;
  }

  public String getBookTitle() {
    return bookTitle;
  }

  public void setBookTitle(String bookTitle) {
    this.bookTitle = bookTitle;
  }

  public float getBookPrice() {
    return bookPrice;
  }

  public void setBookPrice(float bookPrice) {
    this.bookPrice = bookPrice;
  }

  public String getPublisherName() {
    return publisherName;
  }

  public void setPublisherName(String publisherName) {
    this.publisherName = publisherName;
  }

  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  @Override
  public String toString(){
    return "Book["+getIsbn()+", "+getBookTitle()+"]";
  }

  public String getStoreName() {
    return storeName;
  }

  public void setStoreName(String storeName) {
    this.storeName = storeName;
  }

  public String getStoreLoc() {
    return storeLoc;
  }

  public void setStoreLoc(String storeLoc) {
    this.storeLoc = storeLoc;
  }
}
