package in.dsingh.domaindata.domaindetails.controllers.response;

import in.dsingh.domaindata.domaindetails.common.ErrorMapping;
import lombok.Data;

@Data
public class FailureResponse extends BaseResponse {

  String error;

  public FailureResponse(ErrorMapping error) {
    super(ApiStatus.FAILURE);
    this.error = error.toString();
  }
}
