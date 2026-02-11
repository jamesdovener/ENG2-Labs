package uk.ac.york.cs.eng2.books.resources;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.Arrays;
import java.util.List;

@Controller("/books")
public class BooksController {
    @Get("/titles")
    public List<String> getTitles(){
     return Arrays.asList("title1", "title2");
    }
}




