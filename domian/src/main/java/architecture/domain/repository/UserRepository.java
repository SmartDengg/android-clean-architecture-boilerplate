package architecture.domain.repository;

import architecture.domain.interactor.UserUseCase;
import architecture.domain.response.UserDetailResponse;
import architecture.domain.response.UserIdsResponse;
import java.util.List;
import rx.Observable;

/**
 * 创建时间: 2017/06/19 19:13 <br>
 * 作者: dengwei <br>
 * 描述:
 */
public interface UserRepository {

  Observable<UserIdsResponse> getUserResponse(UserUseCase.Request request);

  Observable<List<UserDetailResponse>> getDetailResponse(UserUseCase.Request request);
}
