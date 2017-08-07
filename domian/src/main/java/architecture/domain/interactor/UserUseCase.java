package architecture.domain.interactor;

import architecture.domain.KeyRequest;
import architecture.domain.UseCase;
import architecture.domain.entity.UserDetailEntity;
import architecture.domain.repository.UserRepository;
import architecture.domain.response.UserDetailResponse;
import architecture.domain.response.UserIdsResponse;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.List;
import org.reactivestreams.Publisher;

/**
 * 创建时间:  2017/06/19 18:51 <br>
 * 作者:  SmartDengg <br>
 * 描述:
 */
public class UserUseCase extends UseCase<UserUseCase.Request, List<UserDetailEntity>> {

  private UserRepository userRepository;

  public UserUseCase(UserRepository userRepository, Executor<List<UserDetailEntity>> executor) {
    super(executor);
    this.userRepository = userRepository;
  }

  @Override protected Flowable<List<UserDetailEntity>> interactor(UserUseCase.Request request) {
    return userRepository.getUserResponse(request)
        .concatMap(new Function<UserIdsResponse, Publisher<Integer>>() {
          @Override public Publisher<Integer> apply(@NonNull UserIdsResponse userIdsResponse)
              throws Exception {
            return Flowable.fromIterable(userIdsResponse.ids);
          }
        })
        .concatMap(new Function<Integer, Publisher<List<UserDetailResponse>>>() {
          @Override public Publisher<List<UserDetailResponse>> apply(@NonNull Integer id)
              throws Exception {
            return userRepository.getDetailResponse(Request.createWithId(id));
          }
        })
        .concatMap(new Function<List<UserDetailResponse>, Publisher<List<UserDetailEntity>>>() {
          @Override public Publisher<List<UserDetailEntity>> apply(
              @NonNull List<UserDetailResponse> userDetailResponses) throws Exception {

            List<UserDetailEntity> userEntities = new ArrayList<>(userDetailResponses.size());
            for (UserDetailResponse detailResponse : userDetailResponses) {
              UserDetailEntity userDetailEntity = new UserDetailEntity();
              userDetailEntity.name = detailResponse.name;
              userDetailEntity.address = detailResponse.address;
              userEntities.add(userDetailEntity);
            }
            return Flowable.just(userEntities);
          }
        });
  }

  public static class Request extends KeyRequest {

    public int id;
    public String key;

    Request(int id) {
      this.id = id;
    }

    Request(String key) {
      super(key);
    }

    static Request createWithId(int id) {
      return new Request(id);
    }

    public static Request createWithKey(String key) {
      return new Request(key);
    }
  }
}
