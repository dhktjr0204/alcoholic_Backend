package graduation.alcoholic.web.bar;


import graduation.alcoholic.domain.entity.Bar;
import graduation.alcoholic.domain.entity.User;
import graduation.alcoholic.domain.repository.UserRepository;
import graduation.alcoholic.web.S3.S3Service;
import graduation.alcoholic.domain.repository.BarRepository;
import graduation.alcoholic.web.bar.dto.BarResponseDto;
import graduation.alcoholic.web.bar.dto.BarSaveRequestDto;
import graduation.alcoholic.web.bar.dto.BarUpdateRequestDto;
import graduation.alcoholic.web.login.AuthService;
import graduation.alcoholic.web.login.domain.jwt.JwtHeaderUtil;
import graduation.alcoholic.web.review.dto.ReviewSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BarService {

    private final BarRepository barRepository;
    private final S3Service s3Service;
    private final AuthService authService;
    private final UserRepository userRepository;


    @Transactional
    public ResponseEntity<Map<String,Long>> saveBar(Long id,BarSaveRequestDto requestDto, List<MultipartFile> fileList){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        requestDto.setUser(user);

        if (fileList != null) {
            List<String> fileNameList = s3Service.uploadImage(fileList);
            String fileNameString  = fileNameListToString(fileNameList);
            requestDto.setImage(fileNameString);
        }
        Long Id=barRepository.save(requestDto.toEntity()).getId();

        Map<String, Long> response = new HashMap<>();
        response.put("bar_id", Id);
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

        return new PageImpl<BarResponseDto>(page.getContent()
                .stream()
                .map(bar -> new BarResponseDto(bar))
                .collect(Collectors.toList()),pageable,totalElements);
    }


    @Transactional(readOnly = true)
    public List<BarResponseDto> getBarDetail(Long barId) {
        Bar bar = barRepository.findById(barId)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다. id" +barId));
        return barRepository.findById(barId).stream()
                .map(BarResponseDto::new)
                .collect(Collectors.toList());
    }



    @Transactional
    public ResponseEntity<Map<String, Boolean>> updateBar(HttpServletRequest request, Long barId, BarUpdateRequestDto requestDto, List<MultipartFile> fileList) {
        Bar bar = barRepository.findById(barId)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다. id" + barId));

        //jwt 인증받기
        String jwtToken = JwtHeaderUtil.getAccessToken(request);

        //관리자거나 작성자임을 확인
        if (bar.getUser().getRoletype().toString().equals("ADMIN") || bar.getUser().getId() == authService.getMemberId(jwtToken)) {
            //기존에 있던 사진 리스트화
            List<String> imageList = StringTofileNameList(bar.getImage());

            //삭제된 리스트에 포함되어 있는 사진 클라우드에서 삭제
            if (requestDto.getImage() != null) {
                List<String> deleteImageList = requestDto.getDeleteImgList();
                for (int i = 0; i < deleteImageList.size(); i++) {
                    s3Service.deleteImage(deleteImageList.get(i));
                    imageList.remove(deleteImageList.get(i));
                }
            }

            //받은 사진들 클라우드에 저장
            if (fileList != null) {
                List<String> saveNewFile = s3Service.uploadImage(fileList);
                for(int i=0;i<saveNewFile.size();i++) {
                    imageList.add(saveNewFile.get(i));
                }
            }
            bar.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getLocation(), requestDto.getLocationDetail(),fileNameListToString(imageList));
            Map<String, Boolean> response = new HashMap<>();
            response.put("update", Boolean.TRUE);
            return ResponseEntity.ok(response);
            } else {
                Map<String, Boolean> response = new HashMap<>();
                response.put("no permissions", Boolean.FALSE);
                return ResponseEntity.ok(response);
            }
        }


    @Transactional
    public ResponseEntity<Map<String, Boolean>> deleteBar(HttpServletRequest request,Long barId) {
        Bar bar = barRepository.findById(barId)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다. id" +barId));
        String jwtToken = JwtHeaderUtil.getAccessToken(request);

        //관리자거나 작성자임을 확인
        if(bar.getUser().getRoletype().toString().equals("ADMIN") || bar.getUser().getId()==authService.getMemberId(jwtToken)) {
            //삭제된 리스트에 포함되어 있는 사진 클라우드에서 삭제
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
        }else {
            Map<String, Boolean> response = new HashMap<>();
            response.put("no permissions",Boolean.FALSE);
            return ResponseEntity.ok(response);
        }
    }

    @Transactional(readOnly = true)
    public Page<BarResponseDto> searchByContents(String location,String contents, Pageable pageable){
        Page<Bar> page;
        if((contents!=null)&&(location!=null)) {
            page = barRepository.Search(location, contents, pageable);
        }else if(contents==null){
            page = barRepository.findByLocationContains(location, pageable);
            System.out.println(barRepository.findByLocationContains(location,pageable));
        }
        else{
            page=barRepository.findByContents(contents,pageable);
        }
        int totalElements=(int) page.getTotalElements();
        return new PageImpl<BarResponseDto>(page.getContent()
                .stream()
                .map(bar -> new BarResponseDto(bar))
                .collect(Collectors.toList()),pageable,totalElements);
    }
}
