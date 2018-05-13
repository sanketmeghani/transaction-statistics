[![Build Status](https://travis-ci.org/sanketmeghani/transaction-statistics.svg?branch=master)](https://travis-ci.org/sanketmeghani/transaction-statistics)

# Transaction Statistics Service

REST API to track and get statistics of transactions

The​ ​main​ ​use​ ​case​ ​for​ ​our​ ​API​ ​is​ ​to calculate​ ​realtime​  statistic​s ​from​ ​the​ ​last​ ​60​ ​seconds.​ ​There​ ​will​ ​be​ ​two​ ​APIs,​ ​one​ ​of​ ​them​ ​is called​ ​every​ ​time​ ​a​ ​transaction​ ​is​ ​made.​ Another is called to get statistics about the transactions within last 60 seconds.

## Instructions to build 

```
mvn clean package
```

## Start the application

### Using spring boot maven plugin

```
mvn spring-boot:run
```

### Using generated executable jar

```
java -jar target/transaction-statistics-0.0.1.jar
```

The apis are available on `http://<server-name/ip>:8080`

## REST APIs

### POST /transactions

Payload: 
```
{
  amount: 11.78,
  timestamp: 1526147824985
}
```
Where:

* **amount**: A double value specifying transaction amount
* **timestamp**: A long​​ value specifying​​ unix​​time​​ format​​ in​​ milliseconds

This api is called every time a new transaction occurs. It should return an empty body response with

* **201**: In case of success
* **204**: In case of transaction is older than 60 seconds

Sample API call using `curl`

```
curl -i -X POST -H "Content-Type: application/json" -d '{ "amount": 11.78, "timestamp": 1526147824985 }' http://localhost:8080/transactions
```

### GET /statistics

It​ ​returns​ ​the​ ​statistic​ ​based​ ​on​ ​the​ ​transactions​ ​which​ ​happened​ ​in​ ​the​ ​last​ ​60 seconds.

Sample API call using `curl`

```
curl -i -H "Content-Type: application/json" http://localhost:8080/statistics
```

It returns the statistics in JSON format:

```
{
  sum: 25.0,
  avg: 12.5,
  min: 12.5,
  max: 12.5,
  count: 2
}
```

Where,

* **sum**: A double value representing sum of transaction amounts in last 60 seconds
* **avg**: A double value representing average of transaction amounts in last 60 seconds
* **min**: A double value representing min transaction amount in last 60 seconds
* **max**: A double value representing max transaction amount in last 60 seconds
* **count**: Number of transactions in last 60 seconds 

## Time & space complexities

Both the above APIs are implemented to have time and space complexities of `O(1)`