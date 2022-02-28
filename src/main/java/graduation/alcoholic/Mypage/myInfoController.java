package graduation.alcoholic.Mypage;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class myInfoController {

    @GetMapping
    public void getMyInfo () {

    }

    @PostMapping
    public void modifyMyInfo () {
        //주량 정보 수정

    }

}
