package graduation.alcoholic.web.bar;


import graduation.alcoholic.domain.entity.Bar;
import graduation.alcoholic.web.S3.S3Service;
import graduation.alcoholic.domain.repository.BarRepository;
import graduation.alcoholic.web.bar.dto.BarResponseDto;
import graduation.alcoholic.web.bar.dto.BarSaveRequestDto;
import graduation.alcoholic.web.bar.dto.BarUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BarService {

    private final BarRepository barRepository;
    private final S3Service s3Service;


    @Transactional
    public ResponseEntity<Map<String,Long>> saveBar(BarSaveRequestDto requestDto, List<MultipartFile> fileList){
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
    public Page<BarResponseDto> getBars(Pageable pageable){
        Page<Bar> page=barRepository.findAll(pageable);
        int totalElements=(int) page.getTotalElements();

        //탈퇴유저확인
        for(int i=0;i<page.getContent().size();i++) {
            Bar barInfo=page.getContent().get(i);
            if(barInfo.getUser()!=null) {
                if (barInfo.getUser().getDel_cd() != null) {
                    barInfo.setDel();
                }
            }
        }
        return  new PageImpl<BarResponseDto>(page.getContent()
                .stream()
                .map(bar -> new BarResponseDto(bar))
                .collect(Collectors.toList()),pageable,totalElements);
    }


    @Transactional(readOnly = true)
    public List<BarResponseDto> getBarDetail(Long barId) {
        Bar bar = barRepository.findById(barId)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다. id" +barId));
        return  barRepository.findById(barId).stream()
                .map(BarResponseDto::new)
                .collect(Collectors.toList());
    }



    @Transactional
    public ResponseEntity<Map<String, Boolean>> updateBar(Long barId, BarUpdateRequestDto requestDto, List<MultipartFile> fileList) {
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

    @Transactional(readOnly = true)
    public Page<BarResponseDto> searchByTitle(String title, Pageable pageable){
        Page<Bar> page=barRepository.findByTitleContains(title,pageable);
        int totalElements=(int) page.getTotalElements();
        return new PageImpl<BarResponseDto>(page.getContent()
                .stream()
                .map(bar -> new BarResponseDto(bar))
                .collect(Collectors.toList()),pageable,totalElements);
    }

    @Transactional(readOnly = true)
    public Page<BarResponseDto> searchByLocation(String location, Pageable pageable){
        Page<Bar> page=barRepository.findByLocationContains(location, pageable);
        int totalElements=(int) page.getTotalElements();
        return  new PageImpl<BarResponseDto>(page.getContent()
                .stream()
                .map(bar -> new BarResponseDto(bar))
                .collect(Collectors.toList()),pageable,totalElements);
    }

}
