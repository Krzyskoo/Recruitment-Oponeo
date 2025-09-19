# 🛒 E-Commerce Demo (Spring Boot)

This project was prepared as a **recruitment task**.  
It is a simplified e-commerce system that handles:

- customers (individual persons and companies),
- products,
- orders (with net/gross amount and VAT calculation).

---

## 🚀 Technology stack

- **Java 21+**
- **Spring Boot 3.5**
  - Spring Web (REST API)
  - Spring Data JPA
- **H2 Database** (in-memory database for testing/demo)
- **Lombok** (to reduce boilerplate)
- **ULID** (unique identifiers for orders)
- **JUnit 5 + WebTestClient + JSONAssert** (integration tests)
- **Maven** (build and dependency management)

---

## 🏗 Architecture

- **Domain-Driven Design (DDD-inspired)**: layered structure `controller → service → repository → model`
- **Entities**:
  - `Customer` – base class with inheritance (`PersonCustomer`, `CompanyCustomer`)
  - `Product` – with name, net price and VAT rate
  - `Order` – order with order items and amount calculation
- **DTO + Mappers** – convert entities into API responses
- **Exception handling** – e.g. duplicates, entity not found → returning `404` / `400`

---

## 🔗 API – Customer creation

➕ Create a customer (individual person)

-**POST http://localhost:8080/api/v1/customer/create**
```json
{
  "kind": "PERSON",
  "firstName": "Marek",
  "lastName": "Mostowiak",
  "email": "office@acme.pl",
  "phone": "+48221234567",
  "billingAddress": {
    "line1": "ul. Firmowa 10",
    "city": "Kraków",
    "zip": "30-001",
    "country": "PL"
  },
  "shippingAddress": {
    "line1": "ul. Magazynowa 22",
    "city": "Kraków",
    "zip": "30-002",
    "country": "PL"
  }
}
```
**Response**
```json
{
    "id": 1,
    "kind": "PERSON",
    "displayName": "Marek Mostowiak",
    "email": "office@acme.pl",
    "phone": "+48221234567"
}
```
**➕ Create a customer (Company)**
```json
{
  "kind": "COMPANY",
  "companyName": "ACME Sp. z o.o.",
  "taxId": "PL1234567890",
  "email": "office12345@acme.pl",
  "phone": "+48221234567",
  "billingAddress": {
    "line1": "ul. Firmowa 10",
    "city": "Kraków",
    "zip": "30-001",
    "country": "PL"
  },
  "shippingAddress": {
    "line1": "ul. Magazynowa 22",
    "city": "Kraków",
    "zip": "30-002",
    "country": "PL"
  }
}
```
**Response**
```json
{
    "id": 2,
    "kind": "COMPANY",
    "displayName": "ACME Sp. z o.o.",
    "email": "office12345@acme.pl",
    "phone": "+48221234567"
}
```
## 🔗 API – Product creation
**POST http://localhost:8080/api/v1/product/create**
```json
{
  "name": "Laptop Dell",
  "netPrice": 4000.00,
  "vatRate": 0.23
}
```
## 🔗 API – Product creation
**POST http://localhost:8080/api/v1/order/create**

**Request**
```json
{
  "customerId": 1,
  "items": [
    {
      "productId": 1,
      "quantity": 2
    }
  ]
}
```
**Response**
```json
{
    "id": "01K5HPMXYPMY2RDPAE49B6HDPZ",
    "customer": {
        "id": 1,
        "kind": "PERSON",
        "displayName": "Marek Mostowiak",
        "email": "office@acme.pl"
    },
    "items": [
        {
            "productName": "Laptop Dell",
            "quantity": 2,
            "unitNet": 4000.00,
            "lineNet": 8000.00,
            "lineGross": 9840.00
        }
    ],
    "totalNet": 8000.00,
    "totalGross": 9840.00
}
```
**GET http://localhost:8080/api/v1/order/{Order_Id}**

**Response**
```json
{
    "id": "01K5HPMXYPMY2RDPAE49B6HDPZ",
    "customer": {
        "id": 1,
        "kind": "PERSON",
        "displayName": "Marek Mostowiak",
        "email": "office@acme.pl"
    },
    "items": [
        {
            "productName": "Laptop Dell",
            "quantity": 2,
            "unitNet": 4000.0000,
            "lineNet": 8000.0000,
            "lineGross": 9840.0000
        }
    ],
    "totalNet": 8000.0000,
    "totalGross": 9840.0000
}
```
