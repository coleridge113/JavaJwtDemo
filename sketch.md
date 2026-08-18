// Add Product
curl -i -X POST http://localhost:8080/api/v1/products \
  -H "Authorization: Bearer $AUTH_TOKEN" \
  -H "Content-Type: application/json" \
  -d "{\"name\": \"Laptop\", \"stockQuantity\": 10}"

// Create Order
curl -i -X POST http://localhost:8080/api/v1/orders \
  -H "Authorization: Bearer $AUTH_TOKEN" \
  -H "Content-Type: application/json" \
  -d "{\"customerName\": \"joseluna\", \"items\": [{\"productId\": 1, \"quantity\": 2}]}"

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
