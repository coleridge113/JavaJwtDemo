#!/bin/bash

function api_login() {
    if [ "$#" -ne 2 ]; then
        echo "Usage: api_login <username> <password>"
        return 1
    fi

    local username="$1"
    local password="$2"

    # Make the API request and capture the response
    local response
    response=$(curl -s -X POST http://localhost:8080/api/v1/auth/login \
        -H 'Content-Type: application/json' \
        -d "{\"username\":\"${username}\", \"password\":\"${password}\"}")

    # Extract the token using grep/sed and assign it to AUTH_TOKEN
    AUTH_TOKEN=$(echo "$response" | grep -o '"token":"[^"]*"' | cut -d'"' -f4)

    if [ -z "$AUTH_TOKEN" ]; then
        echo "Login failed: Could not retrieve token."
        return 1
    fi

    echo "Successfully logged in. AUTH_TOKEN has been set."
}

function api_signup() {
    if [ "$#" -ne 2 ]; then
        echo "Usage: api_signup <username> <password>"
        return 1
    fi

    local username="$1"
    local password="$2"

    curl -X POST http://localhost:8080/api/v1/auth/signup \
        -H 'Content-Type: application/json' \
        -d "{\"username\":\"${username}\", \"password\":\"${password}\"}"
}

function send_message() {
    local text="${1:-Hello from RabbitMQ!}"
    local user="${2:-joseluna}"
    local token="${3:-$AUTH_TOKEN}"

    if [ -z "$token" ]; then
        echo "Usage: send_msg <JWT_TOKEN> [user] [message]"
        return 1
    fi

    curl -i -X POST http://localhost:8080/api/v1/message \
        -H "Authorization: Bearer $token" \
        -H "Content-Type: application/json" \
        -d "{\"user\": \"$user\", \"text\": \"$text\"}"
}

function get_order() {
    local number="$1"
    local token="${2:-$AUTH_TOKEN}"

    curl http://localhost:8080/api/v1/orders/$number \
        -H "Authorization: Bearer $AUTH_TOKEN"
}

function get_product() {
    local id="$1"
    local token="${2:-$AUTH_TOKEN}"

    curl http://localhost:8080/api/v1/products/$id \
        -H "Authorization: Bearer $AUTH_TOKEN"
}

function add_cart_item() {
    local id="$1"
    local qty="$2"
    local token="${3:-$AUTH_TOKEN}"

    curl -i -X POST http://localhost:8080/api/v1/carts \
        -H "Content-Type: application/json" \
        -H "Authorization: Bearer $AUTH_TOKEN" \
        -d '{
            "productId": '$id',
            "quantity": '$qty'
        }'
}
