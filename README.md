# API Documentation


- IP Address (LAN): 10.232.100.171
- Port: 8080
- Swagger path: ```/swagger-ui/index.html```


## Entities

- Account:

 ```java
class Account {
  private String id;
  private String customerPhone;
  private Integer balance;
  private Boolean isDeleted;
}
  ```

- Payment:

```java
class Payment {
  private String id;
  private Long paymentDate;
  private Integer amountPaid;
  private Account account;
}
```


### Account

#### Create new Account

Request

- Endpoint : ```/banks```
- Method : POST
- Header :
  - Content-Type: application/json
  - Accept: application/json
- Response :

```json
{
  "customerPhone": "089696969696",
  "balance": 969696
}
```

- Response:

```json
{
  "message": "A new bank has been added.",
  "data": {
    "id": "string",
    "customerPhone": "089696969696",
    "balance": 96969696,
    "isDeleted": false
  }
}
```

#### Create new Account by Phone Number

Request

- Endpoint : ```/banks/phone```
- Method : POST
- Header :
  - Content-Type: application/json
  - Accept: application/json
- Params:
  - customerPhone: String
- Response :

```json
{
  "message": "A new bank has been added.",
  "data": {
    "customerPhone": "089696969696",
    "balance": 1000000
  }
}
```

#### Get Account By ID

Request

- Endpoint : ```/banks/{id}```
- Method : GET
    - Accept: application/json
- Response :

```json
{
  "message": "Data bank found with ID %s.",
  "data": {
    "id": "string",
    "customerPhone": "089696969696",
    "balance": 96969696,
    "isDeleted": false
  }
}
```

#### Get All Accounts

Request

- Endpoint : ```/banks```
- Method : GET
    - Accept: application/json
- Response :

```json
{
  "message": "Data bank found with ID 250b8bb1-7d55-458e-b30f-76c7307399bc.",
  "data": [{
    "id": "string",
    "customerPhone": "089696969696",
    "balance": 96969696,
    "isDeleted": false
  }]
}
```

#### Update Account

Request :

- Endpoint : ```/banks```
- Method : PUT
- Header :
    - Content-Type: application/json
    - Accept: application/json
- Body :

```json
{
  "id": "string",
  "customerPhone": "089696969696",
  "balance": 969696,
  "isDeleted": false
}
```

Response :

```json
{
  "message": "An existing bank has been updated.",
  "data": {
    "id": "string",
    "customerPhone": "089696969696",
    "balance": 969696,
    "isDeleted": false
  }
}
```

#### Delete Account By ID ```(Soft Delete)```

Request :

- Endpoint : ```/banks/{id}```
- Method : DELETE
- Header :
    - Accept: application/json

Response :

```json
{
  "message": "A bank with ID %s has been removed.",
  "data": {
    "id": "string",
    "customerPhone": "089696969696",
    "balance": 969696,
    "isDeleted": true
  }
}
```

### Payment

#### Get Payment By ID

Request :

- Endpoint : ```/payments/{id}```
- Method : GET
    - Accept: application/json
- Response :

```json
{
  "message": "Data payment found with ID %s.",
  "data": {
    "idPayment": "string",
    "paymentDate": 165235320423,
    "amountPaid": 9696,
    "phoneNumber": "089696969696"
  }
}
```

#### Get Payments Per Page

Request :

- Endpoint : ```/payments/page```
- Method : GET
    - Accept: application/json
- Params :
  - from: Long
  - until: Long
- Response :

```json
{
  "data": [{
      "id": "string",
      "paymentDate": 165235320423,
      "amountPaid": 9696,
      "account": {
        "id": "string",
        "customerPhone": "089696969696",
        "balance": 960000,
        "isDeleted": false
      }
  }],
  "totalElement": 1,
  "totalPage": 1,
  "pageIndex": 0,
  "pageSize": 5
}
```

### Transaction

#### Debit

Request :

- Endpoint : ```/payments/debit```
- Method : POST
- Header :
    - Content-Type: application/json
    - Accept: application/json
- Body :

```json
{
  "amountPaid": 9696,
  "phoneNumber": "089696969696"
}
```

Response :

```json
{
  "message": "Payment debit successful.",
  "data": {
    "idPayment": "string",
    "paymentDate": 165235320423,
    "amountPaid": 9696,
    "phoneNumber": "089696969696"
  }
}
```
