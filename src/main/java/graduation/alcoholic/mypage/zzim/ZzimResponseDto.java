package graduation.alcoholic.mypage.zzim;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZzimResponseDto {
    private  Long id;
    private String image;
    private String name;

    @Builder
    public ZzimResponseDto (Long id, String image, String name) {
        this.id=id;
        this.name=name;
        this.image=image;
    }
}
