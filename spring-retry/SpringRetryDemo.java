//Spring-Retry, Hystrix, Async call
//in Spring Boot Application class, add @EnableRetry and add @EnableCircuitBreaker to a project config class.

public class SpringRetryDemo {

  @Autowired
  @QUalifier("customerRestTemplate")
  private RestTemplate restTemplate;
  
  @Autowired
  SpringRetryDemoDelegate springRetryDemoDelegate;
  
  @HystrixCommand
  public Future<Records> customerPost(JsonNode customerInfo, CustomerContext ctx) {
    return new AsyncResult<Records>() {
      @Override
      public Records invoke() {
        HttpHeaders header = new HttpHeaders();
        headers.add("Authorization", "Bearer " + ctx.getToken());
        headers.add("x-client-id", "abc123456");
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Accept", "application/json");
        HttpEntity<JsonNode> requetEntity = new HttpEntity<>(customerInfo, headers);
        ResponseEntity<List<Records>> response = springRetryDemoDelegate.doCustomerPost(requestEntity);
        Records records = null;
        if (null != response && CollectionUtils.isNotEmpty(response.getBody())) {
          records = response.getBody().get(0);
        }
        return records;
      }
    };    
  }
  
  @Bean
  SpringRetryDemoDelegate springRetryDemoDelegate() {
    return new SpringRetryDemoDelegate();
  }
  
  private class SpringRetryDemoDelegate {
  
    //@Retrable(maxAttempts = 3, backoff = @Backoff(delay = 1000})
    @Retrable(maxAttemptsExpression = "${retry.maxAttempts}", backoff = @Backoff(delayExpression = "${retry.delay}"})
    public ResponseEntity<List<Records>> doCustomerPost(HttpEntity<JsonNode> request) {
      response = restTemplate.exchange("/customers", HttpMethod.POST, request, new ParameterizedTypeReference<List<Records>>(){});
      return response;
    }
}
