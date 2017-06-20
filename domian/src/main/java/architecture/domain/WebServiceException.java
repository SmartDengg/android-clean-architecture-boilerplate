package architecture.domain;

/**
 * 创建时间:  2017/06/19 22:41 <br>
 * 作者:  SmartDengg <br>
 * 描述:
 */
public class WebServiceException extends RuntimeException {
  public WebServiceException(String detailMessage) {
    super(detailMessage);
  }
}
