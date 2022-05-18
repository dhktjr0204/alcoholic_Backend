package graduation.alcoholic.web.bar.dto;

import graduation.alcoholic.domain.entity.Bar;
import graduation.alcoholic.web.S3.S3Service;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Pattern;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Getter
public class BarResponseDto {

    private Long id;
    private String nickname;
    private String title;
    private String content;
    private String location;
    private String location_detail;
    private List<String> image;
    private String modified_date;


    public BarResponseDto(Bar entity) {

        this.id = entity.getId();


        if(entity.getUser()==null){
            this.nickname="탈퇴한 유저입니다.";
        }else {
            this.nickname = entity.getUser().getNickname();
        }

        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.location = entity.getLocation();
        this.location_detail=entity.getLocation_detail();
        this.image = StringTofileNameList(entity.getImage());
        this.modified_date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(entity.getModifiedDate());
    }

    public List<String> StringTofileNameList(String fileNameString) {
        return new ArrayList<String>(Arrays.asList(fileNameString.split(",")));
    }
}
