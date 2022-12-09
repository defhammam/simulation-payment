- IP Address: 10.232.100.171
- Port: 8080


## Entities

- Account:

 ```java
class Account {
    private String id;
  
    @Column(nullable=false)
    private String customerPhone;
  
    @Column(nullable=false)
    private Integer balance;
  
    @Column(nullable=false)
    private Boolean isDeleted;
}
  ```

- Payment:

```java
public class Payment {
    private String id;
    private Long paymentDate;
    private Integer amountPaid;
    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;
}
```

## API Documentation

### Account

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

### Account

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

### Transaction

#### Debit

Request :

- Endpoint : ```/payments/debit```
- Method : POST
- Header :
    - Content-Type: application/json
    - Accept: application/json
- Params :
  - phone: String
  - amount: Integer

Response :

```json
{
  "message": "Payment debit successful.",
  "data": {
    "idPayment": "string",
    "paymentDate": "165235320423",
    "amountPaid": 969696,
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
  - to: Long
- Response :

```json
{
  "data": [{
      "id": "string",
      "customerPhone": "089696969696",
      "balance": 969696,
      "isDeleted": false
  }],
  "totalElement": 1,
  "totalPage": 1,
  "pageIndex": 0,
  "pageSize": 5
}
```
