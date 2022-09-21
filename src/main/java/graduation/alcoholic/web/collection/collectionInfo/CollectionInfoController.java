package graduation.alcoholic.web.collection.collectionInfo;

import graduation.alcoholic.web.collection.collectionInfo.dto.CollectionInfoResponseDto;
import graduation.alcoholic.web.collection.collectionInfo.dto.CollectionInfoSaveRequestDto;
import graduation.alcoholic.web.collection.collectionInfo.dto.CollectionInfoUpdateRequestDto;
import graduation.alcoholic.web.login.domain.jwt.JwtHeaderUtil;
import graduation.alcoholic.web.login.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CollectionInfoController {

    private final CollectionInfoService collectionInfoService;
    private final AuthService authService;

    @PostMapping("/collectioninfo")
    public Long save(HttpServletRequest httpRequest, @RequestBody CollectionInfoSaveRequestDto requestDto) {

        String jwtToken= JwtHeaderUtil.getAccessToken(httpRequest);
        return collectionInfoService.save(authService.getMemberId(jwtToken), requestDto);
    }

    @PutMapping("/collectioninfo/{id}")
    public Long update(@PathVariable Long id, @RequestBody CollectionInfoUpdateRequestDto requestDto) {
        return collectionInfoService.update(id, requestDto);
    }


    @GetMapping("/collectioninfo/user")
    public List<CollectionInfoResponseDto> findByUser(HttpServletRequest httpRequest) {

        String jwtToken= JwtHeaderUtil.getAccessToken(httpRequest);
        return collectionInfoService.findByUser(authService.getMemberId(jwtToken));
    }


    @DeleteMapping("/collectioninfo/{id}")
    public Long delete(@PathVariable Long id) {
        collectionInfoService.delete(id);
        return id;
    }
}
