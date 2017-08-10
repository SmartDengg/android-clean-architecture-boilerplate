package architecture.domain.interactor;

import architecture.domain.KeyRequest;
import architecture.domain.UseCase;
import architecture.domain.repository.UserRepository;
import architecture.domain.entity.UserDetailEntity;
import architecture.domain.entity.UserIdsEntity;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
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
        .concatMap(new Function<UserIdsEntity, Publisher<Integer>>() {
          @Override public Publisher<Integer> apply(@NonNull UserIdsEntity userIdsEntity)
              throws Exception {
            return Flowable.fromIterable(userIdsEntity.ids);
          }
        })
        .concatMap(new Function<Integer, Publisher<List<UserDetailEntity>>>() {
          @Override public Publisher<List<UserDetailEntity>> apply(@NonNull Integer id)
              throws Exception {
            return userRepository.getDetailResponse(Request.createWithId(id));
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
