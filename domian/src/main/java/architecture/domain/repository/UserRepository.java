package architecture.domain.repository;

import architecture.domain.interactor.UserUseCase;
import architecture.domain.response.UserDetailResponse;
import architecture.domain.response.UserIdsResponse;
import io.reactivex.Flowable;
import java.util.List;

/**
 * 创建时间: 2017/06/19 19:13 <br>
 * 作者: dengwei <br>
 * 描述:
 */
public interface UserRepository {

  Flowable<UserIdsResponse> getUserResponse(UserUseCase.Request request);

  Flowable<List<UserDetailResponse>> getDetailResponse(UserUseCase.Request request);
}
