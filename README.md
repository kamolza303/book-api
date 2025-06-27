
# Book API (Spring Boot + MySQL)

## 📦 Setup Database

```sql
CREATE DATABASE bookdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
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

```json
{
  "title": "Spring Boot in Action",
  "author": "Craig Walls",
  "publishedDateBuddhist": "2566-12-01"
}
```

### GET /books?author=Craig Walls

```json
[
  {
    "id": 1,
    "title": "Spring Boot in Action",
    "author": "Craig Walls",
    "publishedDate": "2023-12-01"
  }
]
```
