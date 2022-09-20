package graduation.alcoholic.web.main;

import graduation.alcoholic.domain.entity.CollectionInfo;
import graduation.alcoholic.web.collection.collectionContent.dto.CollectionContentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MainController {

    private final MainService mainService;

    @GetMapping("/main")
    public List<MainResponseDto> getRandomCollection () {
        //Random ran = new Random();
        //System.out.println("login: "+ SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return mainService.getRandomCollection();
    }
}
