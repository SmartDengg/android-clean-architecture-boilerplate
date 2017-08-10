package architecture.domain.entity.base;

import architecture.domain.WebServiceException;
import io.reactivex.Flowable;

/**
 * 创建时间: 2017/06/19 19:19 <br>
 * 作者: dengwei <br>
 * 描述:
 */
public class ResponsePojo<Model> extends BaseResponse<Model> {

  /*{
      "reason": "成功的返回",
      "result": {},
      "error_code": 0
    }*/

  @Override public Flowable<Model> filterWebServiceErrors() {
    if (RESULT_OK.equals(error_code) && null != data) {
      return Flowable.just(data);
    } else {
      return Flowable.error(new WebServiceException(reason));
    }
  }
}
