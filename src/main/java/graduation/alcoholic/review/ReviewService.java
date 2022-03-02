package graduation.alcoholic.review;


import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public Long save(ReviewSaveRequestDto requestDto) {
        return reviewRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, ReviewUpdateRequestDto requestDto) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. id" + id));

        review.update(requestDto.getContent(), requestDto.getImage(), requestDto.getStar(),
                requestDto.getTaste_1(), requestDto.getTaste_2(), requestDto.getTaste_3(), requestDto.getTaste_4(), requestDto.getTaste_5());

        return id;
    }

    @Transactional
    public ReviewResponseDto findByAlcohol (Long alcohol_id) {

        Review entity = reviewRepository.findByAlcohol(alcohol_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. id = "+id));

        return new ReviewResponseDto(entity);
    }

    @Transactional
    public ReviewResponseDto findByUser (Long user_id) {

        Review entity = reviewRepository.findByUser(user_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. id" +id));

        return new ReviewResponseDto(entity);
    }

    @Transactional
    public void delete(Long id) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. id" +id));

        reviewRepository.delete(review);
    }
}
