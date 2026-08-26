# E-Commerce Backend Service

DISCLAIMER: The project is named JavaJwtDemo since that was the initial plan: to create a basic auth service. It evolved to what it is now which is an E-Commerce Backend Service.

The project incorporates the MVC architectural pattern and layered as Controller-Service-Repository.
It achieves the following:
- Signup / Signin
- Fuzzy searching products
- Adding items to a cart
- Viewing the cart
- Creating an order from the cart
- Viewing an order
- Emailing a customer once an order is made


This project has the following technologies/libraries:
- **JWT** for Authentication / Authorization
- **Elasticsearch** for fuzzy searching products
- **RabbitMQ** for messages
- **Mailpit** to simulate email notifications
- **Docker** to host 3rd party services
- **SQLITE** for database
