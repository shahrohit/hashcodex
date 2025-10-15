# -------------------------------
# Stage 1: Build Go Binary
# -------------------------------
FROM golang:1.25-alpine AS builder
WORKDIR /app

# Install git for module downloads
RUN apk add --no-cache git


# Copy go modules and download deps
COPY go.mod go.sum ./
RUN go mod download


# Copy source and build binary
COPY . .
RUN go build -o /code-judge


# -------------------------------
# Stage 2: Minimal Runtime
# -------------------------------
FROM alpine:3.20
WORKDIR /app

# Install Docker CLI for container control
RUN apk add --no-cache docker-cli


# Copy compiled binary
COPY --from=builder /code-judge .

# Run the code-judge
ENTRYPOINT ["./code-judge"]