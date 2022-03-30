package graduation.alcoholic.web.review;

import graduation.alcoholic.web.S3.S3Service;
import graduation.alcoholic.domain.repository.AlcoholRepository;
import graduation.alcoholic.domain.entity.Alcohol;
import graduation.alcoholic.domain.entity.Review;
import graduation.alcoholic.domain.entity.User;
import graduation.alcoholic.domain.enums.Taste;
import graduation.alcoholic.domain.repository.ReviewRepository;
import graduation.alcoholic.domain.repository.UserRepository;
import graduation.alcoholic.web.review.dto.ReviewResponseDto;
import graduation.alcoholic.web.review.dto.ReviewSaveRequestDto;
import graduation.alcoholic.web.review.dto.ReviewTotalResponseDto;
import graduation.alcoholic.web.review.dto.ReviewUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

import static graduation.alcoholic.domain.enums.Taste.*;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final AlcoholRepository alcoholRepository;
    private final S3Service s3Service;



    @Transactional
    public Long save(Long id, ReviewSaveRequestDto requestDto, List<MultipartFile> fileList) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        requestDto.setUser(user);

 //       checkReviewDuplication(requestDto);
        requestDto.setImage(saveFileList(fileList));

        return reviewRepository.save(requestDto.toEntity()).getId();
    }


    @Transactional
    public Long update(Long id, ReviewUpdateRequestDto requestDto, List<MultipartFile> fileList) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. id" + id));

        if (review.getImage() != ",") {
            List<String> fileNameList = StringTofileNameList(review.getImage());
            for (int i=0; i<fileNameList.size(); i++) {
                s3Service.deleteImage(fileNameList.get(i));
            }
        }

        if (!CollectionUtils.isEmpty(fileList)) {
            List<String> fileNameList = s3Service.uploadImage(fileList);
            String fileNameString  = fileNameListToString(fileNameList);
            requestDto.setImage(fileNameString);
        }
        else {
            System.out.println("입력된 사진 없음");
            requestDto.setImage(",");
        }

        review.update(requestDto.getContent(), requestDto.getImage(), requestDto.getStar(),
                requestDto.getTaste1(), requestDto.getTaste2(), requestDto.getTaste3(), requestDto.getTaste4(), requestDto.getTaste5());

        return id;
    }

    @Transactional(readOnly = true)
    public ReviewTotalResponseDto findByAlcohol(Long alcohol_id) {

        Alcohol alcohol = alcoholRepository.findById(alcohol_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 술이 없습니다. alcohol_id=" + alcohol_id));

        List<ReviewResponseDto> reviewResponseDtoList = reviewRepository.findByAlcoholOrderByModifiedDateDesc(alcohol).stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());

        return getTotal(reviewResponseDtoList);

    }



    @Transactional(readOnly = true)
    public List<ReviewResponseDto> findByUser (Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        return reviewRepository.findByUserOrderByModifiedDateDesc(user).stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public void delete(Long id) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. id" +id));

        deleteFileList(review);
        reviewRepository.delete(review);
    }


    @Transactional(readOnly = true)
    public void checkReviewDuplication(ReviewSaveRequestDto requestDto) {

        Optional<Review> review = reviewRepository.findByUserAndAlcohol(requestDto.getUser(), requestDto.getAlcohol());

        if (review.isPresent()) {
            throw new IllegalStateException("중복된 리뷰입니다.");
        }
    }

    public String fileNameListToString(List<String> fileNameList) {

        return StringUtils.join(fileNameList, ",");
    }

    public List<String> StringTofileNameList(String fileNameString) {

        return new ArrayList<String>(Arrays.asList(fileNameString.split(",")));
    }


    @Transactional(readOnly = true)
    public ReviewTotalResponseDto getTotal(List<ReviewResponseDto> reviewResponseDtoList) {

        Double total_star = getStarAverage(reviewResponseDtoList);
        ReviewTopTaste reviewTopTaste = getTopTaste(reviewResponseDtoList);


        if (reviewResponseDtoList.isEmpty()) {

            return ReviewTotalResponseDto.builder()
                    .total_star((double) 0)
                    .top_taste1(없음)
                    .top_taste2(없음)
                    .top_taste3(없음)
                    .top_taste4(없음)
                    .top_taste5(없음)
                    .top_taste1_percent(0)
                    .top_taste2_percent(0)
                    .top_taste3_percent(0)
                    .top_taste4_percent(0)
                    .top_taste5_percent(0)
                    .build();

        }
        else {
            return ReviewTotalResponseDto.builder()
                    .total_star(total_star)
                    .top_taste1(reviewTopTaste.getTop_taste1())
                    .top_taste2(reviewTopTaste.getTop_taste2())
                    .top_taste3(reviewTopTaste.getTop_taste3())
                    .top_taste4(reviewTopTaste.getTop_taste4())
                    .top_taste5(reviewTopTaste.getTop_taste5())
                    .top_taste1_percent(reviewTopTaste.getTop_taste1_percent())
                    .top_taste2_percent(reviewTopTaste.getTop_taste2_percent())
                    .top_taste3_percent(reviewTopTaste.getTop_taste3_percent())
                    .top_taste4_percent(reviewTopTaste.getTop_taste4_percent())
                    .top_taste5_percent(reviewTopTaste.getTop_taste5_percent())
                    .reviewResponseDtoList(reviewResponseDtoList)
                    .build();
        }

    }

    public double getStarAverage(List<ReviewResponseDto> reviewResponseDtoList) {

        double stars = 0;
        for(int i=0; i<reviewResponseDtoList.size(); i++) {
            stars += reviewResponseDtoList.get(i).getStar();
        }

        return Math.round(stars/reviewResponseDtoList.size()*10)/10.0;

    }


    public ReviewTopTaste getTopTaste(List<ReviewResponseDto> reviewResponseDtoList) {


        List<Integer>[] counts = new ArrayList[5];

        for (int i=0; i<5; i++) {
            counts[i] = new ArrayList<>();
            for (int j=0; j<4; j++) {
                counts[i].add(0);
            }
        }


        for (int i = 0; i < reviewResponseDtoList.size(); i++) {

            switch (reviewResponseDtoList.get(i).getTaste1()) {
                case 없음:
                    counts[0].set(0, counts[0].get(0) + 1);
                    break;
                case 약함:
                    counts[0].set(1, counts[0].get(1) + 1);
                    break;
                case 보통:
                    counts[0].set(2, counts[0].get(2) + 1);
                    break;
                case 강함:
                    counts[0].set(3, counts[0].get(3) + 1);
                    break;
            }
            switch (reviewResponseDtoList.get(i).getTaste2()) {
                case 없음:
                    counts[1].set(0, counts[1].get(0) + 1);
                    break;
                case 약함:
                    counts[1].set(1, counts[1].get(1) + 1);
                    break;
                case 보통:
                    counts[1].set(2, counts[1].get(2) + 1);
                    break;
                case 강함:
                    counts[1].set(3, counts[1].get(3) + 1);
                    break;
            }
            switch (reviewResponseDtoList.get(i).getTaste3()) {
                case 없음:
                    counts[2].set(0, counts[2].get(0) + 1);
                    break;
                case 약함:
                    counts[2].set(1, counts[2].get(1) + 1);
                    break;
                case 보통:
                    counts[2].set(2, counts[2].get(2) + 1);
                    break;
                case 강함:
                    counts[2].set(3, counts[2].get(3) + 1);
                    break;
            }
            switch (reviewResponseDtoList.get(i).getTaste4()) {
                case 없음:
                    counts[3].set(0, counts[3].get(0) + 1);
                    break;
                case 약함:
                    counts[3].set(1, counts[3].get(1) + 1);
                    break;
                case 보통:
                    counts[3].set(2, counts[3].get(2) + 1);
                    break;
                case 강함:
                    counts[3].set(3, counts[3].get(3) + 1);
                    break;
            }
            switch (reviewResponseDtoList.get(i).getTaste5()) {
                case 없음:
                    counts[4].set(0, counts[4].get(0) + 1);
                    break;
                case 약함:
                    counts[4].set(1, counts[4].get(1) + 1);
                    break;
                case 보통:
                    counts[4].set(2, counts[4].get(2) + 1);
                    break;
                case 강함:
                    counts[4].set(3, counts[4].get(3) + 1);
                    break;
            }
        }


        Taste[] tastes = new Taste[5];
        int[] percent = new int[5];

        for (int i=0; i<5; i++) {

            int index = counts[i].indexOf(Collections.max(counts[i]));

            switch (index) {

                case 0:
                    tastes[i] = 없음;
                    percent[i] = (int)Math.round((double)counts[i].get(0)/reviewResponseDtoList.size()*100);
                    break;
                case 1:
                    tastes[i] = 약함;
                    percent[i] = (int)Math.round((double)counts[i].get(1)/reviewResponseDtoList.size()*100);

                    break;
                case 2:
                    tastes[i] = 보통;
                    percent[i] = (int)Math.round((double)counts[i].get(2)/reviewResponseDtoList.size()*100);
                    break;
                case 3:
                    tastes[i] = 강함;
                    percent[i] = (int)Math.round((double)counts[i].get(3)/reviewResponseDtoList.size()*100);
                    break;
            }
        }

        return new ReviewTopTaste.ReviewTopTasteBuilder()
                .top_taste1(tastes[0])
                .top_taste2(tastes[1])
                .top_taste3(tastes[2])
                .top_taste4(tastes[3])
                .top_taste5(tastes[4])
                .top_taste1_percent(percent[0])
                .top_taste2_percent(percent[1])
                .top_taste3_percent(percent[2])
                .top_taste4_percent(percent[3])
                .top_taste5_percent(percent[4])
                .build();

    }

    public String saveFileList(List<MultipartFile> fileList) {

        if (!CollectionUtils.isEmpty(fileList)) {
            List<String> fileNameList = s3Service.uploadImage(fileList);
            String fileNameString  = fileNameListToString(fileNameList);
            return fileNameString;
        }
        else {
            System.out.println("입력된 사진 없음");
            return ",";
        }
    }

    public void deleteFileList(Review review) {

        if (review.getImage() != ",") {
            List<String> fileNameList = StringTofileNameList(review.getImage());
            for (int i=0; i<fileNameList.size(); i++) {
                s3Service.deleteImage(fileNameList.get(i));
            }
        }
    }

}
