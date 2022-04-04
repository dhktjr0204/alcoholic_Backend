package graduation.alcoholic.domain.entity;


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

    private String roletype;

    private String email;

    private String sex;

    private String age_range;

    private BigDecimal capacity;

    private String del_cd;


    @Builder
    public User(String name,String nickname,String roletype, String email, String sex, String age_range, BigDecimal capacity,String del_cd) {
        this.name = name;
        this.nickname=nickname;
        this.roletype=roletype;
        this.email = email;
        this.sex = sex;
        this.age_range = age_range;
        this.capacity = capacity;
        this.del_cd=del_cd;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity= capacity;
    }

    public void setNickname(String nickname) {
        this.nickname=nickname;
    }

    public void setDelete(String del_cd){
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

