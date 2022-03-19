package graduation.alcoholic.mypage;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class myCollectionController {

    @GetMapping("/myCollection")
    public void getMyCollectionList (Pageable pageable) {
        //collectionRepository.findByUId();

    }

    @PostMapping("/myCollection")
    public void saveMyCollection () {

    }

    @DeleteMapping("/myCollection/{col_id}")
    public void deleteMyCollection () {

    }
}
