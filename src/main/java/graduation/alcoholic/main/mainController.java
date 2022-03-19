package graduation.alcoholic.main;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequiredArgsConstructor
public class mainController {

    @GetMapping("/")
    public List<Object> getRandomCollection () {
        Random ran = new Random();
        System.out.println("login: "+ SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        //int listSize = collection_info.size();
       // int randomId = ran.nextInt(listSize)-1;
        //repository에서 랜덤으로 결정된 col_id에 해당하는 컬렉션을 가져옴

        return null;
    }
}
