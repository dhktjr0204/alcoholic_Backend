package graduation.alcoholic.login.domain.member;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long u_id;
    @Column(columnDefinition = "TEXT",nullable = false)
    private String username;
    private String email;
    private String age_range;
    private String sex;
    private String capacity;
//    @Convert(converter = RoleConverter.class)
    private String roletype;

    @Builder
    public User(Long u_id, String username, String email, String age_range, String sex,String capacity,String roletype) {
        this.u_id = u_id;
        this.username = username;
        this.email = email;
        this.age_range = age_range;
        this.sex = sex;
        this.capacity = capacity;
        this.roletype =roletype;
    }
}


