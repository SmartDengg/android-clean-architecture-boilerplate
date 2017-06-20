package architecture.model.service;

import architecture.domain.interactor.UserUseCase;
import architecture.domain.repository.UserRepository;
import architecture.domain.response.UserDetailResponse;
import architecture.domain.response.UserIdsResponse;
import architecture.domain.response.base.ResponseListVo;
import architecture.domain.response.base.ResponseVo;
import architecture.model.generator.ServiceGenerator;
import java.util.List;
import retrofit2.http.Field;
import retrofit2.http.GET;
import rx.Observable;
import rx.functions.Func1;

/**
 * 创建时间:  2017/06/19 19:11 <br>
 * 作者:  SmartDengg <br>
 * 描述:
 */
public class UserService implements UserRepository {

  private final InternalService service;

  interface InternalService {

    @GET("xxx.json") Observable<ResponseVo<UserIdsResponse>> getUser(
        @Field("accesskey") String key);

    @GET("xxxxx.json") Observable<ResponseListVo<UserDetailResponse>> getUserList(
        @Field("id") int userIds);
  }

  private UserService() {
    this.service = ServiceGenerator.createService(InternalService.class);
  }

  public static UserService create() {
    return new UserService();
  }

  @Override public Observable<UserIdsResponse> getUserResponse(UserUseCase.Request request) {
    return service.getUser(request.key)
        .concatMap(new Func1<ResponseVo<UserIdsResponse>, Observable<? extends UserIdsResponse>>() {
          @Override public Observable<? extends UserIdsResponse> call(
              ResponseVo<UserIdsResponse> userResponseVo) {
            return userResponseVo.filterWebServiceErrors();
          }
        });
  }

  @Override
  public Observable<List<UserDetailResponse>> getDetailResponse(UserUseCase.Request request) {
    return service.getUserList(request.id)
        .concatMap(
            new Func1<ResponseListVo<UserDetailResponse>, Observable<? extends List<UserDetailResponse>>>() {
              @Override public Observable<? extends List<UserDetailResponse>> call(
                  ResponseListVo<UserDetailResponse> userResponseListVo) {
                return userResponseListVo.filterWebServiceErrors();
              }
            });
  }
}
