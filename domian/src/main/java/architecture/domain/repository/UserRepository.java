package architecture.domain.repository;

import architecture.domain.interactor.UserUseCase;
import architecture.domain.entity.UserDetailEntity;
import architecture.domain.entity.UserIdsEntity;
import io.reactivex.Flowable;
import java.util.List;

/**
 * 创建时间: 2017/06/19 19:13 <br>
 * 作者: SmartDengg <br>
 * 描述:
 */
public interface UserRepository {

  Flowable<UserIdsEntity> getUserResponse(UserUseCase.Request request);

  Flowable<List<UserDetailEntity>> getDetailResponse(UserUseCase.Request request);
}
