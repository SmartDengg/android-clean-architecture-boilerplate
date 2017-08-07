package com.smartdengg.presentation.user;

import architecture.domain.UseCase;
import architecture.domain.WebServiceException;
import architecture.domain.entity.UserDetailEntity;
import architecture.domain.interactor.UserUseCase;
import architecture.model.service.UserService;
import com.smartdengg.presentation.IoExecutor;
import io.reactivex.Flowable;
import io.reactivex.subscribers.DisposableSubscriber;
import java.util.List;

/**
 * 创建时间:  2017/06/19 22:52 <br>
 * 作者:  SmartDengg <br>
 * 描述:
 */
public class UserPresenterImp
    implements UserContract.Presenter<UserUseCase.Request, List<UserDetailEntity>> {

  private UserContract.View<List<UserDetailEntity>> view;

  private UseCase<UserUseCase.Request, List<UserDetailEntity>> userUseCase;

  static UserContract.Presenter<UserUseCase.Request, List<UserDetailEntity>> create() {
    return new UserPresenterImp();
  }

  private UserPresenterImp() {

    final UserService userRepository = UserService.create();
    final UserUseCase.Executor<List<UserDetailEntity>> executor = IoExecutor.create();
    this.userUseCase = new UserUseCase(userRepository, executor);
  }

  @Override public void attachView(UserContract.View<List<UserDetailEntity>> view) {
    this.view = view;
  }

  @Override public void detachView() {
    userUseCase.unsubscribe();
  }

  @Override public void fetchData(UserUseCase.Request request) {
    userUseCase.subscribe(request, new InnerSubscriber());
  }

  private final class InnerSubscriber extends DisposableSubscriber<List<UserDetailEntity>> {

    @Override public void onStart() {
      view.showProgress();
    }

    @Override public void onError(Throwable e) {
      if (e instanceof WebServiceException) {
        view.showError(e.getMessage());
      } else {
        view.showError("error......");
      }
    }

    @Override public void onComplete() {
    }

    @Override public void onNext(List<UserDetailEntity> userEntities) {
      view.showData(Flowable.just(userEntities));
    }
  }
}
