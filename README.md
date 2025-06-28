
# Book API (Spring Boot + MySQL)

## 📦 Setup Database

```sql
1. CREATE DATABASE bookdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
2. OPEN FILE schema.sql than run script
```

## 🚀 Run Server

```
mvn spring-boot:run
```

## 🧪 Run Integration Tests

```
mvn test
```

## 📌 Sample Request

### POST /books
### REQUEST

```json
{
  "title": "Spring Boot in Action",
  "author": "kamol",
  "publishedDateBuddhist": "2566-12-01"
}
```

### GET /books?author=kamol
### RESPONSE
```json
[
  {
    "id": 1,
    "title": "Spring Boot in Action",
    "author": "kamol",
    "publishedDate": "2023-12-01"
  }
]
```
