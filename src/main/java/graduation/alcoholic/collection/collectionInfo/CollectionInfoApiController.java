package graduation.alcoholic.collection.collectionInfo;

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
    public Long save(HttpServletRequest httpRequest, CollectionInfoSaveRequestDto requestDto) {

        String jwtToken= JwtHeaderUtil.getAccessToken(httpRequest);
        return collectionInfoService.save(authService.getMemberId(jwtToken), requestDto);
    }

    @PutMapping("/collectioninfo/{id}")
    public Long update(@PathVariable Long id, CollectionInfoUpdateRequestDto requestDto) {
        return collectionInfoService.update(id, requestDto);
    }


    @GetMapping("/collectioninfo/user")
    public List<CollectionInfoResponseDto> findByUser(HttpServletRequest httpRequest) {

        String jwtToken= JwtHeaderUtil.getAccessToken(httpRequest);
        return collectionInfoService.findByUser(authService.getMemberId(jwtToken));
    }

    @DeleteMapping("/collectioninfo/{id}")
    public void delete(@PathVariable Long id) {
        collectionInfoService.delete(id);
    }
}
