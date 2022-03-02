package graduation.alcoholic.bar;


import graduation.alcoholic.domain.Bar;
import graduation.alcoholic.domain.User;
import graduation.alcoholic.login.domain.member.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BarService {
    @Autowired
    private final BarRepository barRepository;

    private final UserRepository userRepository;

    public Bar createBar(Long id, BarDTO bar){
        User user= userRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 아이디가 없습니다"+"\n"));
        Bar barInfo= Bar.builder()
                .user(user)
                .title(bar.getTitle())
                .location(bar.getLocation())
                .content(bar.getContent())
                .image(bar.getImage())
                .build();

        return barRepository.save(barInfo);
    }

    public Optional<List<Bar>> listAllBars(Pageable pageable){
        Page<Bar> entities = barRepository.findAll(pageable);
        return Optional.of(entities.getContent());
//        return barRepository.findAll();
    }


    public Optional<Bar> getBarDetail(Long barId) {
        return barRepository.findById(barId);
    }



    @Transactional
    public ResponseEntity<Map<String,String>> updateBar(Long barId, BarUpdateDTO barDetails){
        Bar bar = barRepository.findById(barId)
                .orElseThrow(() -> new ResourceNotFoundException("Board not exist with id :" + barId));

        bar.update(barDetails.getTitle(),barDetails.getContent(),barDetails.getLocation(),barDetails.getImage());
        barRepository.save(bar);
        Map<String, String> response = new HashMap<>();
        response.put("update", "수정완료");
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<Map<String, Boolean>> deleteBar(@PathVariable Long barId) {
        Bar bar = barRepository.findById(barId)
                .orElseThrow(() -> new ResourceNotFoundException("Board not exist with id :" + barId));

        barRepository.delete(bar);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
