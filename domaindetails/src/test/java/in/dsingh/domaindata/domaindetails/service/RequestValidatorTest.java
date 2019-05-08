package in.dsingh.domaindata.domaindetails.service;

import static org.junit.Assert.*;

import org.junit.Test;

public class RequestValidatorTest {

  @Test
  public void getWordsAsString() {
    String text = "dermatologbetultas.com Premium Park Sayfas? 400.000+ Alan Ad? Natro’ya Güveniyor! dermatologbetultas.com Bu Alan Ad? Kay?t Edilmi?tir ve Natro Park Sayfas?n? Ku";

    System.out.println(RequestValidator.getWordsAsString(text));
  }
}