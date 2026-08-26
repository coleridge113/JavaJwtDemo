// Add Product
curl -i -X POST http://localhost:8080/api/v1/products \
  -H "Authorization: Bearer $AUTH_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "1080p Web Camera with Dual Microphones",
    "stockQuantity": 50,
    "amountInCents": 3999
  }'


// Get paged products
curl -i -X GET "http://localhost:8080/api/v1/products?page=1&size=15&sort=name,desc" \
  -H "Authorization: Bearer $AUTH_TOKEN"

// Create Order
curl -i -X POST http://localhost:8080/api/v1/orders/test \
  -H "Authorization: Bearer $AUTH_TOKEN" 

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
curl -i -X POST http://localhost:8080/api/v1/carts/items \
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

// Add multiple products
curl -i -X POST http://localhost:8080/api/v1/products/batch \
  -H "Authorization: Bearer $AUTH_TOKEN" \
  -H "Content-Type: application/json" \
  -d '[
    {"name": "Laptop", "stockQuantity": 10, "amountInCents": 99999},
    {"name": "Wireless Mechanical Keyboard", "stockQuantity": 45, "amountInCents": 8999},
    {"name": "27-inch Gaming Monitor", "stockQuantity": 15, "amountInCents": 29999},
    {"name": "Ergonomic Vertical Mouse", "stockQuantity": 80, "amountInCents": 3999},
    {"name": "USB-C Multi-Port Hub", "stockQuantity": 120, "amountInCents": 2999},
    {"name": "Noise Canceling Headphones", "stockQuantity": 30, "amountInCents": 14999},
    {"name": "Wireless Mechanical Keyboard - Tactile Switches", "stockQuantity": 45, "amountInCents": 9999},
    {"name": "Ergonomic Vertical Mouse", "stockQuantity": 85, "amountInCents": 4499},
    {"name": "34-inch UltraWide Curved Display", "stockQuantity": 12, "amountInCents": 49999},
    {"name": "USB-C 11-in-1 Docking Station", "stockQuantity": 30, "amountInCents": 7999},
    {"name": "Active Noise-Canceling Wireless Headphones", "stockQuantity": 60, "amountInCents": 19999},
    {"name": "1080p Full HD Webcam with Privacy Cover", "stockQuantity": 90, "amountInCents": 4999},
    {"name": "Large Anti-Slip Desk Pad Mat (900x400mm)", "stockQuantity": 150, "amountInCents": 1999},
    {"name": "Dual Monitor Mount Adjustable Arm", "stockQuantity": 25, "amountInCents": 3999},
    {"name": "RGB Gaming Headset with Stand", "stockQuantity": 40, "amountInCents": 5999},
    {"name": "4K Ultra HD Streaming Camera", "stockQuantity": 18, "amountInCents": 12999},
    {"name": "USB Microphone with Pop Filter", "stockQuantity": 55, "amountInCents": 4999},
    {"name": "Ergonomic Mesh Office Chair with Lumbar Support", "stockQuantity": 15, "amountInCents": 18999},
    {"name": "Electric Height Adjustable Standing Desk", "stockQuantity": 8, "amountInCents": 29999},
    {"name": "Compact Aluminum Laptop Stand", "stockQuantity": 110, "amountInCents": 2499},
    {"name": "Magnetic Wireless Charger Stand (15W)", "stockQuantity": 75, "amountInCents": 2999},
    {"name": "Bluetooth Portable Speaker Waterproof", "stockQuantity": 65, "amountInCents": 3999},
    {"name": "65W GaN Fast Charger Dual USB-C", "stockQuantity": 140, "amountInCents": 3499},
    {"name": "Braided USB-C to USB-C Cable 2m", "stockQuantity": 300, "amountInCents": 1299},
    {"name": "High-Speed HDMI 2.1 Cable 4K@120Hz", "stockQuantity": 220, "amountInCents": 1499},
    {"name": "Uninterruptible Power Supply (UPS) 1500VA", "stockQuantity": 10, "amountInCents": 15999},
    {"name": "Cat 8 Ethernet Cable 10m High Speed", "stockQuantity": 180, "amountInCents": 1899},
    {"name": "Mechanical Keyboard Keycap Set - PBT", "stockQuantity": 50, "amountInCents": 2999},
    {"name": "Desk LED Lamp with Wireless Charging Base", "stockQuantity": 35, "amountInCents": 3999},
    {"name": "Smart Plug Wi-Fi Outlet Monitor", "stockQuantity": 95, "amountInCents": 1499},
    {"name": "External SSD Enclosure NVMe M.2", "stockQuantity": 70, "amountInCents": 2499},
    {"name": "1TB Portable NVMe External SSD", "stockQuantity": 40, "amountInCents": 8999},
    {"name": "2TB High-Speed PCIe Gen4 NVMe Internal SSD", "stockQuantity": 30, "amountInCents": 14999},
    {"name": "Wireless Presenter Clicker with Laser Pointer", "stockQuantity": 80, "amountInCents": 1999},
    {"name": "Screenbar Monitor Light Bar Desk Lamp", "stockQuantity": 22, "amountInCents": 4999},
    {"name": "Cable Management Box Set of 3", "stockQuantity": 130, "amountInCents": 1999},
    {"name": "Foldable Bluetooth Pocket Keyboard", "stockQuantity": 28, "amountInCents": 3499},
    {"name": "Stylus Pen for Touchscreen Tablets", "stockQuantity": 105, "amountInCents": 2999},
    {"name": "USB Graphics Drawing Tablet", "stockQuantity": 33, "amountInCents": 5999},
    {"name": "Ultra-Thin Silent Wireless Mouse", "stockQuantity": 95, "amountInCents": 1999},
    {"name": "Noise Isolating In-Ear Earbuds", "stockQuantity": 115, "amountInCents": 1499},
    {"name": "Computer Tower PC Dust Filter Cover", "stockQuantity": 210, "amountInCents": 999},
    {"name": "Thermal Compound Paste for CPU/GPU", "stockQuantity": 175, "amountInCents": 799},
    {"name": "120mm RGB PC Case Fan 3-Pack", "stockQuantity": 50, "amountInCents": 2999},
    {"name": "60% Mini RGB Mechanical Keyboard", "stockQuantity": 40, "amountInCents": 5499},
    {"name": "Wireless Gaming Controller for PC", "stockQuantity": 65, "amountInCents": 3999},
    {"name": "Portable Monitor 15.6 Inch Full HD", "stockQuantity": 20, "amountInCents": 11999},
    {"name": "Smart RGB LED Light Strip 5m", "stockQuantity": 85, "amountInCents": 1999},
    {"name": "USB Flash Drive 128GB USB 3.2", "stockQuantity": 160, "amountInCents": 1499},
    {"name": "MicroSDXC Memory Card 256GB with Adapter", "stockQuantity": 140, "amountInCents": 2499},
    {"name": "Privacy Screen Filter for 15.6 Laptop", "stockQuantity": 45, "amountInCents": 2999},
    {"name": "Vertical Laptop Stand Holder Double Slot", "stockQuantity": 60, "amountInCents": 2499},
    {"name": "Adjustable Footrest for Under Desk", "stockQuantity": 25, "amountInCents": 2999},
    {"name": "Blue Light Blocking Glasses for Screen Use", "stockQuantity": 120, "amountInCents": 1599},
    {"name": "Padded Wrist Rest Pad for Keyboard & Mouse", "stockQuantity": 85, "amountInCents": 1299},
    {"name": "Surge Protector Power Strip 8 Outlets", "stockQuantity": 90, "amountInCents": 2499}
  ]'
