package architecture.domain.entity.base;

import architecture.domain.WebServiceException;
import io.reactivex.Flowable;
import java.util.List;

/**
 * 创建时间: 2017/06/19 19:19 <br>
 * 作者: dengwei <br>
 * 描述:
 */
public class ResponseListPojo<Model> extends BaseResponse<List<Model>> {

  /*{
      "reason": "成功的返回",
      "result": {},
      "error_code": 0
    }*/

  /*{
      "reason": "当前可请求的次数不足",
      "result": null,
      "error_code": 10012
    }*/

  @Override public Flowable<List<Model>> filterWebServiceErrors() {
    if (RESULT_OK.equals(error_code) && null != data) {
      return Flowable.just(data);
    } else {
      return Flowable.error(new WebServiceException(reason));
    }
  }
}
