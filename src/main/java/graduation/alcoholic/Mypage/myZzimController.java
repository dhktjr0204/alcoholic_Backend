package graduation.alcoholic.Mypage;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class myZzimController {

    @GetMapping("/myZzim")
    public void getMyZzimList () {

    }

    @DeleteMapping("/myZzim/{}")
    public void deleteMyZzim () {

    }

}
