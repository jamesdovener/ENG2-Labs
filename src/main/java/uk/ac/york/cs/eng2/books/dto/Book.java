package uk.ac.york.cs.eng2.books.dto;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class Book {
    private String title;
    private String author;
    private int id;

    public String getTitle(){
        return this.title;
    }
    public void setTitle(String newTitle){
        this.title = newTitle;
    }

}
