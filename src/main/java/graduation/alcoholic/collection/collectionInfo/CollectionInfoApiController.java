package graduation.alcoholic.collection.collectionInfo;

import graduation.alcoholic.login.domain.auth.jwt.AuthTokenProvider;
import graduation.alcoholic.login.domain.auth.jwt.JwtHeaderUtil;
import graduation.alcoholic.login.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CollectionInfoApiController {

    private final CollectionInfoService collectionInfoService;
    private final AuthService authService;

    @PostMapping("/collectioninfo")
    public Long save(CollectionInfoSaveRequestDto collectionInfoSaveRequestDto) {


        return collectionInfoService.save(collectionInfoSaveRequestDto);
    }

    @PutMapping("/collectioninfo/{id}")
    public Long update(@PathVariable Long id, CollectionInfoUpdateRequestDto collectionInfoUpdateRequestDto) {
        return collectionInfoService.update(id, collectionInfoUpdateRequestDto);
    }


//    @GetMapping("/collectioninfo/user")
//    public List<CollectionInfoResponseDto> findByUser(HttpServletRequest httpRequest) {
//
//        String jwtToken= JwtHeaderUtil.getAccessToken(httpRequest);
//        return collectionInfoService.findByUser(authService.getMemberId(jwtToken););
//    }

    @DeleteMapping("/collectioninfo/{id}")
    public void delete(@PathVariable Long id) {
        collectionInfoService.delete(id);
    }
}
