package graduation.alcoholic.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String nickname;

    private String email;

    private String sex;

    private String age_range;

    private BigDecimal capacity;

    private String del_cd;


    @Builder
    public User(String name,String nickname, String email, String sex, String age_range, BigDecimal capacity) {
        this.name = name;
        this.nickname=nickname;
        this.email = email;
        this.sex = sex;
        this.age_range = age_range;
        this.capacity = capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity= capacity;
    }

    public void setDelete(String nickname,String del_cd){
        this.nickname=nickname;
        this.del_cd=del_cd;
    }

    public void signInUpdate(String nickname,BigDecimal capacity){
        this.nickname=nickname;
        this.capacity=capacity;
    }

    public void updateUserInfo(String age_range){
        this.age_range=age_range;
    }
}
