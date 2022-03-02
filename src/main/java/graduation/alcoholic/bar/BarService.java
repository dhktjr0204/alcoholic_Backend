package graduation.alcoholic.bar;


import graduation.alcoholic.domain.Bar;
import graduation.alcoholic.domain.User;
import graduation.alcoholic.login.domain.member.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

    public List<Bar> listAllBars(){
        return barRepository.findAll();
    }

    public Optional<Bar> getBarDetail(Long id) {
        return barRepository.findById(id);
    }


    public ResponseEntity<Bar> updateBar(Long id, BarDTO barDetails){
        Bar bar = barRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bar not exist with id :" + id));

        User user= userRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 아이디가 없습니다"+"\n"));

        Bar barInfo= bar.builder()
                .user(user)
                .title(barDetails.getTitle())
                .location(barDetails.getLocation())
                .content(barDetails.getContent())
                .image(barDetails.getImage())
                .build();


        Bar updatedBoard = barRepository.save(barInfo);
        return ResponseEntity.ok(updatedBoard);
    }


    public ResponseEntity<Map<String, Boolean>> deleteBar(@PathVariable Long id) {
        Bar bar = barRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board not exist with id :" + id));

        barRepository.delete(bar);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
