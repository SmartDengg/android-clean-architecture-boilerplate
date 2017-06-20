package com.smartdengg.presentation.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import architecture.domain.entity.UserEntity;
import com.smartdengg.presentation.R;
import java.util.List;
import rx.Observable;

public class MainActivity extends AppCompatActivity implements UserContract.View<List<UserEntity>> {

  private UserContract.Presenter<List<UserEntity>> presenter = UserPresenterImp.create();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    this.presenter.loadData("this is the access key");
  }

  @Override public void showData(Observable<List<UserEntity>> data) {
    /*show data and hide progress*/
  }

  @Override public void showError(String errorMessage) {
    /*show error message and hide progress*/
  }

  @Override public void showProgress() {
    /*show progress*/
  }
}
