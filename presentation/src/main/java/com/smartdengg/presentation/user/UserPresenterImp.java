package com.smartdengg.presentation.user;

import architecture.domain.WebServiceException;
import architecture.domain.entity.UserEntity;
import architecture.domain.interactor.UserUseCase;
import architecture.model.service.UserService;
import java.util.List;
import rx.Observable;
import rx.Subscriber;

/**
 * 创建时间:  2017/06/19 22:52 <br>
 * 作者:  SmartDengg <br>
 * 描述:
 */
public class UserPresenterImp implements UserContract.Presenter<List<UserEntity>> {

  private UserContract.View<List<UserEntity>> view;

  private UserUseCase userUseCase;

  static UserContract.Presenter<List<UserEntity>> create() {
    return new UserPresenterImp();
  }

  private UserPresenterImp() {
    this.userUseCase = new UserUseCase(UserService.create());
  }

  @Override public void attachView(UserContract.View<List<UserEntity>> view) {
    this.view = view;
  }

  @Override public void detachView() {
    userUseCase.unsubscribe();
  }

  @Override public void loadData(String key) {
    final UserUseCase.Request request = UserUseCase.Request.createWithKey(key);
    userUseCase.subscribe(request, new InnerSubscriber());
  }

  private final class InnerSubscriber extends Subscriber<List<UserEntity>> {

    @Override public void onStart() {
      view.showProgress();
    }

    @Override public void onCompleted() {

    }

    @Override public void onError(Throwable e) {
      if (e instanceof WebServiceException) {
        view.showError(e.getMessage());
      } else {
        view.showError("bala bala...");
      }
    }

    @Override public void onNext(List<UserEntity> userEntities) {
      view.showData(Observable.just(userEntities));
    }
  }
}
