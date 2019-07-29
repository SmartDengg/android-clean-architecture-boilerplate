package com.smartdengg.presentation.user;

import architecture.domain.UseCase;
import architecture.domain.WebServiceException;
import architecture.domain.entity.UserDetailEntity;
import architecture.domain.interactor.UserUseCase;
import architecture.model.service.UserService;
import com.smartdengg.presentation.IoExecutor;
import io.reactivex.subscribers.DisposableSubscriber;
import java.util.List;

/**
 * 创建时间:  2017/06/19 22:52 <br>
 * 作者:  SmartDengg <br>
 * 描述:
 */
public class UserPresenterImp
    implements UserContract.Presenter<UserUseCase.Request, List<UserDetailModel>> {

  private UserContract.View<List<UserDetailModel>> view;

  private UseCase<UserUseCase.Request, List<UserDetailEntity>> userUseCase;

  static UserContract.Presenter<UserUseCase.Request, List<UserDetailModel>> create() {
    return new UserPresenterImp();
  }

  private UserPresenterImp() {
    this.userUseCase =
        new UserUseCase(UserService.create(), IoExecutor.<List<UserDetailEntity>>create());
  }

  @Override public void attach(UserContract.View<List<UserDetailModel>> view) {
    this.view = view;
  }

  @Override public void detach() {
    userUseCase.release();
  }

  @Override public void load(UserUseCase.Request request) {
    userUseCase.fetch(request, new InnerSubscriber(view));
  }

  private final static class InnerSubscriber extends DisposableSubscriber<List<UserDetailEntity>> {

    private UserContract.View<List<UserDetailModel>> view;

    InnerSubscriber(UserContract.View<List<UserDetailModel>> view) {
      this.view = view;
    }

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

    @Override public void onNext(List<UserDetailEntity> detailResponses) {
      view.showData(UserConverter.convertDetail(detailResponses));
    }
  }
}
