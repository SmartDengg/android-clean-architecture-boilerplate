package architecture.domain.interactor;

import architecture.domain.UseCase;
import architecture.domain.entity.UserEntity;
import architecture.domain.repository.UserRepository;
import architecture.domain.response.UserDetailResponse;
import architecture.domain.response.UserIdsResponse;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.functions.Func1;

/**
 * 创建时间:  2017/06/19 18:51 <br>
 * 作者:  SmartDengg <br>
 * 描述:
 */
public class UserUseCase extends UseCase<UserUseCase.Request, List<UserEntity>> {

  private UserRepository userRepository;

  public UserUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override protected Observable<List<UserEntity>> interactor(UserUseCase.Request request) {
    return userRepository.getUserResponse(request)
        .concatMap(new Func1<UserIdsResponse, Observable<Integer>>() {
          @Override public Observable<Integer> call(UserIdsResponse userIdsResponse) {
            return Observable.from(userIdsResponse.ids);
          }
        })
        .concatMap(new Func1<Integer, Observable<List<UserDetailResponse>>>() {
          @Override public Observable<List<UserDetailResponse>> call(Integer id) {
            return userRepository.getDetailResponse(Request.createWithId(id));
          }
        })
        .concatMap(new Func1<List<UserDetailResponse>, Observable<? extends List<UserEntity>>>() {
          @Override public Observable<? extends List<UserEntity>> call(
              List<UserDetailResponse> userDetailResponses) {
            List<UserEntity> userEntities = new ArrayList<>(userDetailResponses.size());
            for (UserDetailResponse detailResponse : userDetailResponses) {
              UserEntity userEntity = new UserEntity();
              userEntity.name = detailResponse.name;
              userEntity.address = detailResponse.address;
              userEntities.add(userEntity);
            }
            return Observable.just(userEntities);
          }
        });
  }

  public static class Request {

    public int id;
    public String key;

    public Request(int id) {
      this.id = id;
    }

    public Request(String key) {
      this.key = key;
    }

    public static Request createWithId(int id) {
      return new Request(id);
    }

    public static Request createWithKey(String key) {
      return new Request(key);
    }
  }
}
