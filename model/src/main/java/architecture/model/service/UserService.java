package architecture.model.service;

import architecture.domain.entity.UserDetailEntity;
import architecture.domain.entity.UserIdsEntity;
import architecture.domain.entity.base.ResponseListPojo;
import architecture.domain.entity.base.ResponsePojo;
import architecture.domain.interactor.UserUseCase;
import architecture.domain.repository.UserRepository;
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

    @GET("1.json") Flowable<ResponsePojo<UserIdsEntity>> getUser(@Field("accesskey") String key);

    @GET("2.json") Flowable<ResponseListPojo<UserDetailEntity>> getUserList(
        @Field("id") int userIds);
  }

  private UserService() {
    this.service = ServiceGenerator.createService(InternalService.class);
  }

  public static UserService create() {
    return new UserService();
  }

  @Override public Flowable<UserIdsEntity> getUserResponse(UserUseCase.Request request) {
    return service.getUser(request.key)
        .concatMap(new Function<ResponsePojo<UserIdsEntity>, Publisher<? extends UserIdsEntity>>() {
          @Override public Publisher<? extends UserIdsEntity> apply(
              @NonNull ResponsePojo<UserIdsEntity> responsePojo) throws Exception {
            return responsePojo.filterWebServiceErrors();
          }
        });
  }

  @Override public Flowable<List<UserDetailEntity>> getDetailResponse(UserUseCase.Request request) {
    return service.getUserList(request.id)
        .concatMap(
            new Function<ResponseListPojo<UserDetailEntity>, Publisher<? extends List<UserDetailEntity>>>() {
              @Override public Publisher<? extends List<UserDetailEntity>> apply(
                  @NonNull ResponseListPojo<UserDetailEntity> responseListPojo) throws Exception {
                return responseListPojo.filterWebServiceErrors();
              }
            });
  }
}
