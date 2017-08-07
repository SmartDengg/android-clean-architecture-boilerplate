package com.smartdengg.presentation.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import architecture.domain.entity.UserDetailEntity;
import architecture.domain.interactor.UserUseCase;
import com.smartdengg.presentation.R;
import java.util.List;
import rx.Observable;

public class MainActivity extends AppCompatActivity
    implements UserContract.View<List<UserDetailEntity>> {

  private UserContract.Presenter<UserUseCase.Request, List<UserDetailEntity>> presenter =
      UserPresenterImp.create();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    this.presenter.fetchData(UserUseCase.Request.createWithKey("this is the access key"));
  }

  @Override public void showData(Observable<List<UserDetailEntity>> data) {
    /*show data and hide progress*/
  }

  @Override public void showError(String errorMessage) {
    /*show error message and hide progress*/
  }

  @Override public void showProgress() {
    /*show progress*/
  }
}
