package com.smartdengg.presentation.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.smartdengg.presentation.R;
import io.reactivex.Flowable;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements UserContract.View<List<UserDetailModel>> {

  private UserContract.Presenter<List<UserDetailModel>> presenter =
      UserPresenterImp.create();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    this.presenter.attach(MainActivity.this);
    this.presenter.loadData();
  }

  @Override public void setLoadingProgress(boolean active) {
    /*show progress*/
  }

  @Override public void showData(Flowable<List<UserDetailModel>> data) {
    /*show data and hide progress*/
  }

  @Override public void showError(String errorMessage) {
    /*show error message and hide progress*/
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    this.presenter.detach();
  }

  @Override public int getPageId() {
    return 1;
  }
}
