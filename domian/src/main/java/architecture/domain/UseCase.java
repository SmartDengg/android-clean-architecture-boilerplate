package architecture.domain;

import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * 创建时间:  2017/06/19 18:49 <br>
 * 作者:  SmartDengg <br>
 * 描述:
 */
public abstract class UseCase<Request extends KeyRequest, Response> {

  private CompositeDisposable compositeDisposable = new CompositeDisposable();

  private final Executor<Response> executor;

  protected UseCase(Executor<Response> executor) {
    this.executor = executor;
  }

  public void subscribe(final Request request, DisposableSubscriber<Response> useCaseSubscriber) {

    final DisposableSubscriber<Response> disposableSubscriber =
        this.interactor(request).compose(executor.transformer()).subscribeWith(useCaseSubscriber);

    compositeDisposable.add(disposableSubscriber);
  }

  @CheckResult protected abstract Flowable<Response> interactor(@Nullable Request request);

  public void unsubscribe() {
    if (!compositeDisposable.isDisposed()) compositeDisposable.dispose();
  }

  public interface Executor<Response> {
    FlowableTransformer<Response, Response> transformer();
  }
}
