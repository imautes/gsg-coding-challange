# Coding Challenge case study at GSG

### Build & Test
The project is built with Maven:

`mvn clean package`

### Run
Using maven:

`mvn spring-boot:run`

Using packaged jar file:

`java -jar target/Coding-Challenge-0.0.1-SNAPSHOT.jar`

### Documentation
Swagger Rest API documentation can be found under [localhost:8080/swagger-ui/index.html](localhost:8080/swagger-ui/index.html).

### Solution
The endpoint calculates the net price from the given gross price using the tax rate of the specified country.

As the task description does not contain specific algorythm on how the calculation has to be made, an assumption has been made that the VAT percentage is calculated from the gross price, based on the example (`81 = calculateNetPrice(100, DE);`) provided. Therefore the calculation is executed as following:
```
var netPrice = grossPrice - grossPrice * vatRate;
```
As the task description does not contain specific requirements on rounding of the result, none is being made.

The underlying "database" is a map containing 2 countries' tax rates:

| DE | 20 |
|----|----|
| FR | 19 |

Any other country ISO code or invalid value set for country ISO code will result in a `404 Not Found` response.

For an invalid request a `400 Bad Request` response will be returned.

### Improvement possibilities
* Read tax rates from a database or a 3rd party API
* Provide detailed error description when the response has a 400 or a 404 status code
* Provide API parameters to configure rounding
* Validate the provided inputs (gross amount and country ISO code)
* Provide separate calculation method where the VAT percentage is calculated from the net price:
```
var netPrice = grossPrice / (1 + vatRate);
```
