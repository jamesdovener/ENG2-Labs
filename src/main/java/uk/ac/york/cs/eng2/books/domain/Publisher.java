package uk.ac.york.cs.eng2.books.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import uk.ac.york.cs.eng2.books.dto.PublisherDTO;

import java.util.HashSet;
import java.util.Set;

@Entity
@EqualsAndHashCode
public class Publisher {

    @Id
    @Setter @Getter
    @GeneratedValue
    private Long id;

    @Column
    @Setter @Getter
    private String name;

    @Setter @Getter
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "publisher")
    private Set<Book> books = new HashSet<>();

    public Publisher() {}

    public Publisher(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public PublisherDTO toDTO() {
        PublisherDTO dto = new PublisherDTO();
        dto.setId(id);
        dto.setName(name);
        return dto;
    }
}
