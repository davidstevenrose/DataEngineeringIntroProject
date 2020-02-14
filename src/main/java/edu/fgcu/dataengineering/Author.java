package edu.fgcu.dataengineering;

/**
 * A template for the 'author' table used in the database scheme
 *
 * @author drose
 */
public class Author {
  private String author_name;
  private String author_email;
  private String author_url;

  public String getAuthor_name() {
    return author_name;
  }

  public void setAuthor_name(String author_name) {
    this.author_name = author_name;
  }

  public String getAuthor_email() {
    if(author_email.isEmpty())
      return null;
    return author_email;
  }

  public void setAuthor_email(String author_email) {
    this.author_email = author_email;
  }

  public String getAuthor_url() {
    if(author_url.isEmpty())
      return null;
    return author_url;
  }

  public void setAuthor_url(String author_url) {
    this.author_url = author_url;
  }

  @Override
  public String toString(){
    return "Author[name = "+author_name+", email = "+author_email+", url = "+author_url+"]";
  }
}
