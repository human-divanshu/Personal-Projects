package in.dsingh.domaindata.domaindetails.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket postsApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .useDefaultResponseMessages(false)
        .apiInfo(apiInfo())
        .select().paths(regex("/*.*"))
        .apis(RequestHandlerSelectors.basePackage("in.dsingh.domaindata"))
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("Domain Details bot")
        .description("Bot for domain details")
        .termsOfServiceUrl("http://dsingh.in")
        .version("1.0").build();
  }
}

