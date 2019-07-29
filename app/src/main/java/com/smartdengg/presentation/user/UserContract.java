package com.smartdengg.presentation.user;

import com.smartdengg.presentation.MVPPresenter;
import com.smartdengg.presentation.MVPView;

/**
 * 创建时间: 2017/06/19 22:46 <br>
 * 作者: dengwei <br>
 * 描述:
 */
interface UserContract {

  interface Presenter<Request, Result> extends MVPPresenter<View<Result>> {
    void load(Request request);
  }

  interface View<Result> extends MVPView<Result> {
    // Android platform
    String getPackageName();
  }
}
