package architecture.domain;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 创建时间:  2017/06/19 18:49 <br>
 * 作者:  SmartDengg <br>
 * 描述:
 */
public abstract class UseCase<Request extends KeyRequest, Response> {

  private CompositeSubscription subscriptions = new CompositeSubscription();

  public void subscribe(final Request request, Observer<Response> useCaseSubscriber) {

    final Subscription subscription =
        this.interactor(request).subscribeOn(Schedulers.io()).subscribe(useCaseSubscriber);
    subscriptions.add(subscription);
  }

  protected abstract Observable<Response> interactor(Request request);

  public void unsubscribe() {
    if (!subscriptions.hasSubscriptions()) subscriptions.clear();
  }
}
