package engine.repository.info;

import engine.entity.info.UserHelpQuizInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHelpQuizInfoRepository extends JpaRepository<UserHelpQuizInfo, Long> {

}
