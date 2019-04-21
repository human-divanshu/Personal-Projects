package in.dsingh.domaindata.domaindetails.common;

import in.dsingh.domaindata.domaindetails.controllers.DomainController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {DomainController.class})
@Slf4j
public class RestExceptionControllerAdvice {

  @ExceptionHandler(Exception.class)
  public void handleAllExceptions(Exception e) {
    log.error("Exception in advice {}", e);
  }
}
