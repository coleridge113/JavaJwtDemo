#!/bin/bash

function api_login() {
    if [ "$#" -ne 2 ]; then
        echo "Usage: api_login <username> <password>"
        return 1
    fi

    local username="$1"
    local password="$2"

    curl -X POST http://localhost:8080/api/v1/auth/login \
        -H 'Content-Type: application/json' \
        -d "{\"username\":\"${username}\", \"password\":\"${password}\"}"
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
