package graduation.alcoholic.mypage.zzim;

import graduation.alcoholic.domain.Zzim;
import graduation.alcoholic.domain.ZzimId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZzimRepository extends JpaRepository<Zzim, ZzimId> {
    List<Zzim> findByUserId(Long u_id);
    List<Zzim> findByUserIdAndAlcoholId(Long u_id, Long a_id);
    void deleteByUserIdAndAlcoholId (Long u_id,Long a_id);
}
