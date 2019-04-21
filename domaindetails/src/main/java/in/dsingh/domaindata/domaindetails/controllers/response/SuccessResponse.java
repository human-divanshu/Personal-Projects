package in.dsingh.domaindata.domaindetails.controllers.response;

import lombok.Data;

@Data
public class SuccessResponse extends BaseResponse {

  public SuccessResponse() {
    super(ApiStatus.SUCCESS);
  }
}
