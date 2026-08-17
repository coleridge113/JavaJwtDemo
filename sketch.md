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
