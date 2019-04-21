package in.dsingh.domaindata.domaindetails.controllers.response;

public class BaseResponse {

  public ApiStatus apiStatus;

  public BaseResponse(ApiStatus apiStatus) {
    this.apiStatus = apiStatus;
  }
}
