package graduation.alcoholic.Zzim;

import graduation.alcoholic.domain.Zzim;
import graduation.alcoholic.domain.ZzimId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZzimRepository extends JpaRepository<Zzim, ZzimId> {
    
}
