package graduation.alcoholic.review;


import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.Review;
import graduation.alcoholic.domain.User;
import graduation.alcoholic.domain.enums.Taste;
import graduation.alcoholic.login.domain.member.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public Long save(String email, ReviewSaveRequestDto requestDto, List<MultipartFile> fileList) {

        User user = userRepository.findByEmail(email);
        requestDto.setUser(user);

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
    public ReviewTotalResponseDto findByAlcohol(Long alcohol_id) {

        Alcohol alcohol = alcoholRepository.findById(alcohol_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 술이 없습니다. alcohol_id=" + alcohol_id));

        List<ReviewResponseDto> reviewResponseDtoList = reviewRepository.findByAlcoholOrderByModifiedDateDesc(alcohol).stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());

        return getTotal(reviewResponseDtoList);

    }



    @Transactional(readOnly = true)
    public List<ReviewResponseDto> findByUser (String email) {

        User user = userRepository.findByEmail(email);
        return reviewRepository.findByUserOrderByModifiedDateDesc(user).stream()
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


    @Transactional(readOnly = true)
    public ReviewTotalResponseDto getTotal(List<ReviewResponseDto> reviewResponseDtoList) {

        Double total_star = getStarAverage(reviewResponseDtoList);
        ReviewTopTaste reviewTopTaste = getTopTaste(reviewResponseDtoList);


        if (reviewResponseDtoList == null) {
            return new ReviewTotalResponseDto.ReviewTotalResponseDtoBuilder()
                    .total_star((double) 0)
                    .top_taste_1(없음)
                    .top_taste_2(없음)
                    .top_taste_3(없음)
                    .top_taste_4(없음)
                    .top_taste_5(없음)
                    .top_taste_1_percent(0)
                    .top_taste_2_percent(0)
                    .top_taste_3_percent(0)
                    .top_taste_4_percent(0)
                    .top_taste_5_percent(0)
                    .build();
        }
        else {
            return new ReviewTotalResponseDto.ReviewTotalResponseDtoBuilder()
                    .total_star(total_star)
                    .top_taste_1(reviewTopTaste.getTop_taste_1())
                    .top_taste_2(reviewTopTaste.getTop_taste_2())
                    .top_taste_3(reviewTopTaste.getTop_taste_3())
                    .top_taste_4(reviewTopTaste.getTop_taste_4())
                    .top_taste_5(reviewTopTaste.getTop_taste_5())
                    .top_taste_1_percent(reviewTopTaste.getTop_taste_1_percent())
                    .top_taste_2_percent(reviewTopTaste.getTop_taste_2_percent())
                    .top_taste_3_percent(reviewTopTaste.getTop_taste_3_percent())
                    .top_taste_4_percent(reviewTopTaste.getTop_taste_4_percent())
                    .top_taste_5_percent(reviewTopTaste.getTop_taste_5_percent())

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

            switch (reviewResponseDtoList.get(i).getTaste_1()) {
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
            switch (reviewResponseDtoList.get(i).getTaste_2()) {
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
            switch (reviewResponseDtoList.get(i).getTaste_3()) {
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
            switch (reviewResponseDtoList.get(i).getTaste_4()) {
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
            switch (reviewResponseDtoList.get(i).getTaste_5()) {
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
                .top_taste_1(tastes[0])
                .top_taste_2(tastes[1])
                .top_taste_3(tastes[2])
                .top_taste_4(tastes[3])
                .top_taste_5(tastes[4])
                .top_taste_1_percent(percent[0])
                .top_taste_2_percent(percent[1])
                .top_taste_3_percent(percent[2])
                .top_taste_4_percent(percent[3])
                .top_taste_5_percent(percent[4])
                .build();

    }

}
