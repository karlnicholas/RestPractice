Example application for REST based micro-services architecture. 

Microservices are 

* EmployeeAddress
* EmployeeDetail
* EmployeeProject

Registry is Spring/Netflix Eureka at EmployeeEureka

Client facing REST interface/Gateway is at EmployeeApi.

Shows by example 

* Async calls to microservices.
* Random service port assignment and load balancing.
* Hateoas as implemented by SpringFramework.
* Currently includes swagger. Looking at spring docs
* Spring projection to sparse lists of projects and employees.
* Support (partial) for Pageable lists.
* Maven parent project
* Mocked unit testing of microservices and gateway
* SpringBoot for services, gateway, and registry.

Technologies used:

* Spring Boot
* Spring Data JPA
* Spring Rest
* Spring Netflix Eureka
* Spring RestTemplate
* Spring Ribbon Load Balancer
* Spring Hateoas Support
* Spring Async Services
* Spring WebMvc 
* Spring MockMvc
* Spring MockRestTemlateServer
