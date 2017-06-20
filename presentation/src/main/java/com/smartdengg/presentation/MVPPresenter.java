package com.smartdengg.presentation;

/**
 * 创建时间: 2017/06/19 22:53 <br>
 * 作者: dengwei <br>
 * 描述:
 */
public interface MVPPresenter<V> {

  void attachView(V view);

  void detachView();
}
