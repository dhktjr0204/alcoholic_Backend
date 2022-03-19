package graduation.alcoholic.board;

import graduation.alcoholic.domain.Alcohol;
import lombok.Builder;
import lombok.Data;

@Data
public class BoardResponseDto {
    private Long id;
    private String name;
    private String image;

    @Builder
    public BoardResponseDto(Alcohol a) {
        this.id=a.getId();
        this.name=a.getName();
        this.image=a.getImage();
    }
}
