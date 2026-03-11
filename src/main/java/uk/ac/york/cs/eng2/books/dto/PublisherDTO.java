package uk.ac.york.cs.eng2.books.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import uk.ac.york.cs.eng2.books.domain.Publisher;

@Serdeable
@Setter @Getter
@EqualsAndHashCode
public class PublisherDTO {
    private Long id;
    private String name;

    public PublisherDTO() {}

    public Publisher toPublisher() {
        Publisher publisher = new Publisher();
        publisher.setId(this.id);
        publisher.setName(this.name);
        return publisher;
    }

}
