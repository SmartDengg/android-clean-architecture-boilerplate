package architecture.model;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Publisher;

/**
 * 创建时间: 2017/07/31 14:58 <br>
 * 作者: dengwei <br>
 * 描述:
 */
@SuppressWarnings("All") public class SchedulersCompat {

  private static final FlowableTransformer computationTransformer = new FlowableTransformer() {
    @Override public Publisher apply(Flowable upstream) {
      return upstream.subscribeOn(Schedulers.computation())
          .observeOn(AndroidSchedulers.mainThread());
    }
  };

  private static final FlowableTransformer ioTransformer = new FlowableTransformer() {
    @Override public Publisher apply(Flowable upstream) {
      return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
  };

  private static final FlowableTransformer newTransformer = new FlowableTransformer() {
    @Override public Publisher apply(Flowable upstream) {
      return upstream.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }
  };

  private static final FlowableTransformer trampolineTransformer = new FlowableTransformer() {
    @Override public Publisher apply(Flowable upstream) {
      return upstream.subscribeOn(Schedulers.trampoline())
          .observeOn(AndroidSchedulers.mainThread());
    }
  };

  /**
   * Don't break the chain: use RxJava's compose() operator
   */
  public static <T> FlowableTransformer<T, T> applyComputationSchedulers() {

    return (FlowableTransformer<T, T>) computationTransformer;
  }

  public static <T> FlowableTransformer<T, T> applyIoSchedulers() {

    return (FlowableTransformer<T, T>) ioTransformer;
  }

  public static <T> FlowableTransformer<T, T> applyNewSchedulers() {

    return (FlowableTransformer<T, T>) newTransformer;
  }

  public static <T> FlowableTransformer<T, T> applyTrampolineSchedulers() {

    return (FlowableTransformer<T, T>) trampolineTransformer;
  }
}
