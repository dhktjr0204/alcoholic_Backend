package graduation.alcoholic.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class CollectionInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String title;

    private String description;

    @OneToMany(mappedBy = "id.collection_id")
    private List<CollectionContent> collectionContents = new ArrayList<>();


    @Builder
    public CollectionInfo(User user, String title, String description) {
        this.user = user;
        this.title = title;
        this.description = description;
    }
}
