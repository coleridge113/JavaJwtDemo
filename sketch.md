// Add Product
curl -i -X POST http://localhost:8080/api/v1/products \
  -H "Authorization: Bearer $AUTH_TOKEN" \
  -H "Content-Type: application/json" \
  -d "{\"name\": \"Laptop\", \"stockQuantity\": 10}"


// Get paged products
curl -i -X GET "http://localhost:8080/api/v1/products?page=1&size=15&sort=name,desc" \
  -H "Authorization: Bearer $AUTH_TOKEN"

// Create Order
curl -i -X POST http://localhost:8080/api/v1/orders \
  -H "Authorization: Bearer $AUTH_TOKEN" \

// Create order w/ multiple products
curl -i -X POST http://localhost:8080/api/v1/orders \
  -H "Authorization: Bearer $AUTH_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "joseluna",
    "items": [
      {"productId": 1, "quantity": 2},
      {"productId": 2, "quantity": 1},
      {"productId": 3, "quantity": 5},
      {"productId": 4, "quantity": 3},
      {"productId": 5, "quantity": 1}
    ]
  }'

// Add cart item
curl -i -X POST http://localhost:8080/api/v1/carts \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $AUTH_TOKEN" \
  -d '{
    "productId": 5,
    "quantity": 2
  }'


// Get Product
curl http://localhost:8080/api/v1/products/1 \
     -H "Authorization: Bearer $AUTH_TOKEN"

// Get Cart Items
curl http://localhost:8080/api/v1/carts \
     -H "Authorization: Bearer $AUTH_TOKEN"
