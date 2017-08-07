package architecture.model.service;

import architecture.domain.interactor.UserUseCase;
import architecture.domain.repository.UserRepository;
import architecture.domain.response.UserDetailResponse;
import architecture.domain.response.UserIdsResponse;
import architecture.domain.response.base.ResponseListPojo;
import architecture.domain.response.base.ResponsePojo;
import architecture.model.generator.ServiceGenerator;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import java.util.List;
import org.reactivestreams.Publisher;
import retrofit2.http.Field;
import retrofit2.http.GET;

/**
 * 创建时间:  2017/06/19 19:11 <br>
 * 作者:  SmartDengg <br>
 * 描述:
 */
public class UserService implements UserRepository {

  private final InternalService service;

  interface InternalService {

    @GET("1.json") Flowable<ResponsePojo<UserIdsResponse>> getUser(@Field("accesskey") String key);

    @GET("2.json") Flowable<ResponseListPojo<UserDetailResponse>> getUserList(
        @Field("id") int userIds);
  }

  private UserService() {
    this.service = ServiceGenerator.createService(InternalService.class);
  }

  public static UserService create() {
    return new UserService();
  }

  @Override public Flowable<UserIdsResponse> getUserResponse(UserUseCase.Request request) {
    return service.getUser(request.key)
        .concatMap(
            new Function<ResponsePojo<UserIdsResponse>, Publisher<? extends UserIdsResponse>>() {
              @Override public Publisher<? extends UserIdsResponse> apply(
                  @NonNull ResponsePojo<UserIdsResponse> responsePojo) throws Exception {
                return responsePojo.filterWebServiceErrors();
              }
            });
  }

  @Override
  public Flowable<List<UserDetailResponse>> getDetailResponse(UserUseCase.Request request) {
    return service.getUserList(request.id)
        .concatMap(
            new Function<ResponseListPojo<UserDetailResponse>, Publisher<? extends List<UserDetailResponse>>>() {
              @Override public Publisher<? extends List<UserDetailResponse>> apply(
                  @NonNull ResponseListPojo<UserDetailResponse> responseListPojo) throws Exception {
                return responseListPojo.filterWebServiceErrors();
              }
            });
  }
}
