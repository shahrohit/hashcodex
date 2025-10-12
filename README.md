# Hashcodex - Online Code Execution & Evaluation Platform

A **secure**, **real-time** code execution & evaluation platform inspired by **LeetCode** and **HackerRank**.

## 📘 Overview

**Hashcodex** allows users to `solve coding problems` online, write and `write code in a browser editor`, and receive instant feedback.

It supports `multiple languages`, real-time result streaming, and `safe code execution in sandboxed environment`.

## Tech Stack:

- Frontend: React / Next.js
- Backend: Spring Boot
- Code Judge: Go + Docker
- Database: PostgreSQL
- Message Queue: RabbitMQ

## 🧩 Features

### 🧠 Problem Management

- Add, update, and browse **coding problems**.
- Each problem includes a title, description, constraints, **starter code**, and **sample test cases**.

## 💻 Online Code Editor

- Language support: `C++`, `Java`, `Python`
- Code highlighting, auto-indent, and line numbering.

## ⚙️ Code Execution & Judging

- Supports `Run with custom testcases` and `Submit with the predefined testcases.`
- Uses `RabbitMQ` for Asynchronous communication.
- Executes code in `Docker containers` with strict CPU, memory, and time limits.
- Real-time execution feedback using `Server-Sent Events (SSE)`.

## 🔐 Security

- Each execution runs in an `isolated Docker container`.
- Containers run in `Network Mode as none` with `limited CPU/memory` and `no privileged operations`

## 📈 Result & Status Tracking

- Immediate feedback for `compile/run errors`, `timeouts`, and `wrong answers`.
- Stores submission results in database.

## Architecture

![Hashcodex Architecture](/public/architecture.png)
