package uk.ac.york.cs.eng2.books;

import io.micronaut.http.annotation.*;

@Controller("/book-microservice")
public class BookMicroserviceController {

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Example Response";
    }
}