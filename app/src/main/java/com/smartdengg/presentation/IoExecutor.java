package com.smartdengg.presentation;

import architecture.domain.UseCase;
import architecture.model.SchedulersCompat;
import io.reactivex.FlowableTransformer;

/**
 * 创建时间:  2017/08/07 11:36 <br>
 * 作者:  SmartDengg <br>
 * 描述:
 */
public class IoExecutor<Response> implements UseCase.Executor<Response> {

  public static <Response> UseCase.Executor<Response> create() {
    return new IoExecutor<>();
  }

  private IoExecutor() {
  }

  @Override public FlowableTransformer<Response, Response> transformer() {
    return SchedulersCompat.applyIoSchedulers();
  }
}
