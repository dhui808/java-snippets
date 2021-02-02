package observableclient;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import rx.Observable;

@Component("restClient")
public class ObservableRestClient {

  @Autowired
  @Qualifier("restTemplate")
  private RestTemplate restTemplate;
  
  @HystrixCommand(commandProperties = {@HystrixProperty(name = "execute.isolation.strategy", value = "THREAD")})
  public Observable<JsonNode> getCustomerById(CustomerContext ctx, String customerId) {
    return Observable.create(subscriber -> {
      try {
        HttpHeaders header = new HttpHeaders();
        headers.add("Authorization", "Bearer " + ctx.getToken());
        headers.add("x-client-id", "abc123456");
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Accept", "application/json");
        HttpEntity<String> requetEntity = new HttpEntity<String>(headers);
        JsonNode response = restTemplate.exchange("/{customerId}/base", HttpMethod.GET, requestEntity, JsonNode.class, customerId).getBody();
        subscriber.onNext(response);
        subscriber.onCompleted();
      } catch (Throable e) {
        subscriber.onError(e);
      }
    });
  }

}
