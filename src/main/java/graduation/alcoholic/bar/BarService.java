package graduation.alcoholic.bar;


import graduation.alcoholic.domain.Bar;
import graduation.alcoholic.review.S3Service;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BarService {
    @Autowired
    private final BarRepository barRepository;
    private final S3Service s3Service;

    @Transactional
    public ResponseEntity<Map<String,Long>> createBar(BarSaveDTO requestDto, List<MultipartFile> fileList){
        if (fileList != null) {
            List<String> fileNameList = s3Service.uploadImage(fileList);
            String fileNameString  = fileNameListToString(fileNameList);
            requestDto.setImage(fileNameString);
        }
        Long Id=barRepository.save(requestDto.toEntity()).getId();

        Map<String, Long> response = new HashMap<>();
        response.put("주점 글 아이디", Id);
        return ResponseEntity.ok(response);
    }

    public String fileNameListToString(List<String> fileNameList) {

        return StringUtils.join(fileNameList, ",");
    }

    public List<String> StringTofileNameList(String fileNameString) {

        return new ArrayList<String>(Arrays.asList(fileNameString.split(",")));
    }


    @Transactional
    public List<BarResponseDTO> listAllBars(Pageable pageable){
        return  barRepository.findAll(pageable).stream()
                .map(BarResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<BarResponseDTO> getBarDetail(Long barId) {
        barRepository.findById(barId);
        return  barRepository.findById(barId).stream()
                .map(BarResponseDTO::new)
                .collect(Collectors.toList());
    }



    @Transactional
    public ResponseEntity<Map<String, Boolean>> updateBar(Long barId, BarUpdateDTO requestDto, List<MultipartFile> fileList) {
        Bar bar = barRepository.findById(barId)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다. id" + barId));

        if (bar.getImage() != null) {
            List<String> fileNameList = StringTofileNameList(bar.getImage());
            for (int i = 0; i < fileNameList.size(); i++) {
                s3Service.deleteImage(fileNameList.get(i));
            }
        }

        if (fileList != null) {
            List<String> fileNameList = s3Service.uploadImage(fileList);
            String fileNameString  = fileNameListToString(fileNameList);
            requestDto.setImage(fileNameString);
        }

        bar.update(requestDto.getTitle(),requestDto.getContent(),requestDto.getLocation(), requestDto.getImage());


        Map<String, Boolean> response = new HashMap<>();
        response.put("update", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


    @Transactional
    public ResponseEntity<Map<String, Boolean>> deleteBar(Long barId) {
        Bar bar = barRepository.findById(barId)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다. id" +barId));

        if (bar.getImage() != null) {
            List<String> fileNameList = StringTofileNameList(bar.getImage());
            for (int i=0; i<fileNameList.size(); i++) {
                s3Service.deleteImage(fileNameList.get(i));
            }
        }

        barRepository.delete(bar);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
