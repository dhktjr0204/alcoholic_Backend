package graduation.alcoholic.S3;

import graduation.alcoholic.login.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class S3ApiController {

    private final S3Service s3Service;

    @PostMapping("/image")
    public ResponseEntity<List<String>> uploadImage(@RequestParam List<MultipartFile> multipartfileList) {
        return ApiResponseDto.success(s3Service.uploadImage(multipartfileList));
    }

    @DeleteMapping("/image")
    public ResponseEntity<Void> deleteImage(@RequestParam String fileName) {
        return ApiResponseDto.success(null);
    }

}
