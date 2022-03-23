package graduation.alcoholic.web.recommendation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendScore{

    private Long id;

    private int score1;
    private int score2;
    private int score3;
    private int score4;
    private int score5;

    private int total_score;
    private double std;
    private int none_score;
    private int strong_score;

    @Builder
    public RecommendScore(Long id, int score1, int score2, int score3, int score4, int score5, int total_score) {
        this.id = id;
        this.score1 = score1;
        this.score2 = score2;
        this.score3 = score3;
        this.score4 = score4;
        this.score5 = score5;
        this.total_score = total_score;
        this.std = getStd();
        this.none_score = 0;
    }

    public double getStd() {

        double avg = (double)(score1 + score2 + score3 + score4 + score5) / 5;
        double std = (Math.pow((score1 - avg),2 ) + Math.pow((score2 - avg),2 ) + Math.pow((score3 - avg),2 ) + Math.pow((score4 - avg),2 ) + Math.pow((score5 - avg),2 ))/5;
        return std;
    }

}
