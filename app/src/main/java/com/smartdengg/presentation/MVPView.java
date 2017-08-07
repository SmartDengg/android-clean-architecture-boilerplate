package com.smartdengg.presentation;

import io.reactivex.Flowable;

/**
 * 创建时间: 2017/06/19 22:53 <br>
 * 作者: dengwei <br>
 * 描述:
 */
public interface MVPView<T> {

  void showData(Flowable<T> data);

  void showError(String errorMessage);
}
