# -------------------------------
# Stage 1: Build with Node
# -------------------------------
FROM node:20-alpine AS build
WORKDIR /app

COPY package.json bun.lock ./
RUN npm install -g bun && bun install --frozen-lockfile

COPY . .
RUN npm run build

# -------------------------------
# Stage 2: Run with Bun
# -------------------------------
FROM oven/bun:1.1.20 AS runtime
WORKDIR /app

COPY --from=build /app ./

EXPOSE 3000
CMD ["bun", "start"]
