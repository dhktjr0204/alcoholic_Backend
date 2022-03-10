package graduation.alcoholic.review;


import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.Review;
import graduation.alcoholic.domain.User;
import graduation.alcoholic.domain.enums.Taste;
import graduation.alcoholic.login.domain.member.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static graduation.alcoholic.domain.enums.Taste.*;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final AlcoholRepository alcoholRepository;
    private final S3Service s3Service;

    private List<ReviewResponseDto> reviewResponseDtoList;

    @Transactional
    public Long save(ReviewSaveRequestDto requestDto, List<MultipartFile> fileList) {

        checkReviewDuplication(requestDto);

        if (fileList != null) {
            List<String> fileNameList = s3Service.uploadImage(fileList);
            String fileNameString  = fileNameListToString(fileNameList);
            requestDto.setImage(fileNameString);
        }

        return reviewRepository.save(requestDto.toEntity()).getId();
    }


    @Transactional(readOnly = true)
    public void checkReviewDuplication(ReviewSaveRequestDto requestDto) {

        Alcohol alcohol = alcoholRepository.findById(requestDto.getAlcohol().getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 술이 없습니다. alcohol_id" + requestDto.getAlcohol().getId()));
        User user = userRepository.findById(requestDto.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. user_id = " + requestDto.getUser().getId()));
        Optional<Review> review = reviewRepository.checkReviewDuplication(user, alcohol);

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

    @Transactional
    public Long update(Long id, ReviewUpdateRequestDto requestDto, List<MultipartFile> fileList) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. id" + id));

        if (review.getImage() != null) {
            List<String> fileNameList = StringTofileNameList(review.getImage());
            for (int i=0; i<fileNameList.size(); i++) {
                s3Service.deleteImage(fileNameList.get(i));
            }
        }

        if (fileList != null) {

            List<String> fileNameList = s3Service.uploadImage(fileList);
            String fileNameString  = fileNameListToString(fileNameList);
            requestDto.setImage(fileNameString);
        }

        review.update(requestDto.getContent(), requestDto.getImage(), requestDto.getStar(),
                requestDto.getTaste_1(), requestDto.getTaste_2(), requestDto.getTaste_3(), requestDto.getTaste_4(), requestDto.getTaste_5());

        return id;
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> findByAlcohol(Long alcohol_id, Pageable pageable) {

        Alcohol alcohol = alcoholRepository.findById(alcohol_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 술이 없습니다. alcohol_id=" + alcohol_id));

        reviewResponseDtoList =  reviewRepository.findByAlcohol(alcohol, pageable).stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());

        Double total_star = getStarAverage(reviewResponseDtoList);
        Taste[] tastes = getTopTaste(reviewResponseDtoList);
        Taste top_taste_1 = tastes[0];
        Taste top_taste_2 = tastes[1];
        Taste top_taste_3 = tastes[2];
        Taste top_taste_4 = tastes[3];
        Taste top_taste_5 = tastes[4];

        reviewResponseDtoList.add(new ReviewResponseDto.ReviewResponseDtoBuilder()
                .total_star(total_star)
                .taste_1(top_taste_1)
                .taste_2(top_taste_2)
                .taste_3(top_taste_3)
                .taste_4(top_taste_4)
                .taste_5(top_taste_5)
                .build());

        return reviewResponseDtoList;

    }


    @Transactional(readOnly = true)
    public ReviewTotalResponseDto getTotal(Long alcohol_id) {

        if (!reviewResponseDtoList.isEmpty()) {
            if (reviewResponseDtoList.get(0).getId() != alcohol_id) {
                Alcohol alcohol = alcoholRepository.findById(alcohol_id)
                        .orElseThrow(() -> new IllegalArgumentException("해당 술이 없습니다. alcohol_id=" + alcohol_id));

                reviewResponseDtoList =  reviewRepository.findByAlcohol(alcohol).stream()
                        .map(ReviewResponseDto::new)
                        .collect(Collectors.toList());
            }
        }

        Double total_star = getStarAverage(reviewResponseDtoList);
        Taste[] tastes = getTopTaste(reviewResponseDtoList);
        Taste top_taste_1 = tastes[0];
        Taste top_taste_2 = tastes[1];
        Taste top_taste_3 = tastes[2];
        Taste top_taste_4 = tastes[3];
        Taste top_taste_5 = tastes[4];

        return new ReviewTotalResponseDto.ReviewTotalResponseDtoBuilder()
                .total_star(total_star)
                .top_taste_1(top_taste_1)
                .top_taste_2(top_taste_2)
                .top_taste_3(top_taste_3)
                .top_taste_4(top_taste_4)
                .top_taste_5(top_taste_5)
                .build();
    }

    public double getStarAverage(List<ReviewResponseDto> reviewResponseDtoList) {

        int stars = 0;
        for(int i=0; i<reviewResponseDtoList.size(); i++) {
            stars += reviewResponseDtoList.get(i).getStar();
        }

        return Math.round(stars/reviewResponseDtoList.size()*10)/10;

    }

    public Taste[] getTopTaste(List<ReviewResponseDto> reviewResponseDtoList) {

        int[][] counts = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};

        for (int i=0; i<reviewResponseDtoList.size(); i++) {


            switch (reviewResponseDtoList.get(i).getTaste_1()) {
                case 없음:
                    counts[0][0]++;
                    break;
                case 약함:
                    counts[0][1]++;
                    break;
                case 보통:
                    counts[0][2]++;
                    break;
                case 강함:
                    counts[0][3]++;
                    break;
            }

            switch (reviewResponseDtoList.get(i).getTaste_2()) {
                case 없음:
                    counts[1][0]++;
                    break;
                case 약함:
                    counts[1][1]++;
                    break;
                case 보통:
                    counts[1][2]++;
                    break;
                case 강함:
                    counts[1][3]++;
                    break;
            }

            switch (reviewResponseDtoList.get(i).getTaste_3()) {
                case 없음:
                    counts[2][0]++;
                    break;
                case 약함:
                    counts[2][1]++;
                    break;
                case 보통:
                    counts[2][2]++;
                    break;
                case 강함:
                    counts[2][3]++;
                    break;
            }

            switch (reviewResponseDtoList.get(i).getTaste_4()) {
                case 없음:
                    counts[3][0]++;
                    break;
                case 약함:
                    counts[3][1]++;
                    break;
                case 보통:
                    counts[3][2]++;
                    break;
                case 강함:
                    counts[3][3]++;
                    break;
            }

            switch (reviewResponseDtoList.get(i).getTaste_5()) {
                case 없음:
                    counts[4][0]++;
                    break;
                case 약함:
                    counts[4][1]++;
                    break;
                case 보통:
                    counts[4][2]++;
                    break;
                case 강함:
                    counts[4][3]++;
                    break;
            }
        }


        Taste[] tastes = {};

        for (int i=0; i<5; i++) {

            switch (Arrays.asList(counts[i]).indexOf(Arrays.stream(counts[i]).max())) {
                case 0:
                    tastes[i] = 없음;
                    break;
                case 1:
                    tastes[i] = 약함;
                    break;
                case 2:
                    tastes[i] = 보통;
                    break;
                case 3:
                    tastes[i] = 강함;
                    break;
            }
        }
        return tastes;
    }


    @Transactional(readOnly = true)
    public List<ReviewResponseDto> findByAlcohol(Long alcohol_id) {

        Alcohol alcohol = alcoholRepository.findById(alcohol_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 술이 없습니다. alcohol_id=" + alcohol_id));

        return reviewRepository.findByAlcohol(alcohol).stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> findByUser (Long user_id, Pageable pageable) {

        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. user_id=" + user_id));

        return reviewRepository.findByUser(user, pageable).stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. id" +id));

        if (review.getImage() != null) {
            List<String> fileNameList = StringTofileNameList(review.getImage());
            for (int i=0; i<fileNameList.size(); i++) {
                s3Service.deleteImage(fileNameList.get(i));
            }
        }

        reviewRepository.delete(review);
    }
}
