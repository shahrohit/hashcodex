# Hashcodex - Online Code Execution & Evaluation Platform

A **secure**, **real-time**, and **scalable** code execution & evaluation platform inspired by **LeetCode** and **HackerRank**.

![Hashcodex Architecture](/public/demo_ui.png)

## 📑 Table of Content

1. [📘 Overview](#-overview)
2. [🧩 Features](#-features)
3. [🔄 System Architecture](#-system-architecture)
4. [🏃 Execution Workflow](#-execution-workflow)
5. [🧱 Tech Stack](#-tech-stack)
6. [💾 Database Design](#-database-design)
7. [🔐 Authentication Workflow](#-authentication-workflow)
8. [🏗️ Go Worker Service Workflow](#-go-worker-service-workflow)
9. [🐳 Quick Setup (Docker Compose)](#-quick-setup-docker-compose)
10. [🧠 Challenges Faced & Solutions](#-challenges-faced--solutions)

## 📘 Overview

**Hashcodex** allows users to `solve coding problems` online, write code in a `browser-based editor`, and receive instant feedback in real time.

It supports `multiple languages`, `asynchronous execution`, and `safe code execution` in sandboxed environment.

## 🧩 Features

- 🖥️ **Responsive User Interface** built with `Next.js` and `Monaco Editor`

- 🔐 **JWT Authentication** with `refresh token mechanism`.

- 🧑‍💻 **Online code editor** that support `code highlighting`, `auto-indent`, and `line numbering`.

- 💻 **Multi-language** support (`Java`, `c++`, `Python`)

- ▶️ **Run code** with `custom testcases`

- ✅ **submit code** with `predefined testcases.`

- ⚙️ **Asynchronous execution** using `RabbitMQ queues`.

- 📦 **Secure sandboxed code execution** `(Docker isolation)`. Containers run in `NetworkMode=none` with `limited CPU/memory` and `no privileged operations`

- 🔄 **Real-time result** streaming via `Server-Sent Events (SSE)`.

- 🗃️ **Database persistence** for user sessions, submissions, and problems.

## 🔄 System Architecture

![Hashcodex Architecture](/public/architecture.png)

> The system follows a distributed event-driven architecture where user submissions are processed asynchronously by Go-based workers executing code securely inside Docker sandboxes.

## 🏃 Execution Workflow

After login into the platform,

1. **User selects problem** and writes code then request `Run/submit` to backend by sending **code**, **language**, **problemId** and `custom testacases (in case of Run)` to backend.
2. **Backend verify the request** and then `fetch problem details` like **driver code**, **solution code(in case of Run)**, **testcases(in case of Submit)**. Also, create new submission entry in `DB with status PENDING`.
3. **After collecting all the problem detail**, create a new submission payload and a `unique submission id`.
4. `Push` the submission payload to `Submission Request Queue`.
5. Here,

   - The `unique submission Id` is then `return back to the user`.
   - The `Submission palyload` is consume by the `Go Worker Service`.

6. Here,

   - The `unique submission Id` is immediately send to backend to open a `SSE connection`.

   - **Here, in the Go worker**, new `docker container` is spin up based on the language and the code is copied in the container, `compile (if needed)`. Then code is **executed with testcases in case of Submit** or **testcases are created based on solution code in case of Run**.

7. Then the `result` is constructed based on the `stdOut` and `stdErr` and push into the `Submission Response Queue.`

8. The Submission Response is `Consume` by the `Spring boot server` and pick up the stored result.

9. The `status(Accepted or Wrong Answer or TLE or MLE or RunTime Eror)` is updated into the `databased`.

10. Then the result is send back to the `frontend` using the `SSE`.

## 🧱 Tech Stack

| Layer            | Technology      | Purpose                                      |
| ---------------- | --------------- | -------------------------------------------- |
| Frontend         | Next.js (React) | Code editor & UI                             |
| Backend          | Spring Boot     | API, Auth, SSE, queue producers/consumers    |
| Worker           | Go              | Code execution in Docker sandbox             |
| Message Queue    | RabbitMQ        | Async messaging                              |
| Database         | PostgreSQL      | Store User, Problems, testcases, submissions |
| Containerization | Docker          | Safe sandboxing                              |

## 💾 Database Design

![Hashcodex Architecture](/public/db.png)

### User

- Stores `user information` like **name**, **email**, **hashed Password**, etc.

- `Role` field is enum whose values are `USER`, `ADMIN`.

- **Email and password are the primary source of authentication.** `email_verified` field store whether user have verified there email or not.

- `public_id` is the UUID which can be `expose to public (frontend)` instead of the database id.

### Session

- Session Table stores the logged in `user session data` like **session id**, **user id**, **creation timestamp** and **expiry timestamp**.

- The `expiry` of the session is usually long like `1 week`.

- It also helps to `refersh the Access Token` which is `short-lived JWT` usually of `15 minutes`.

- There `One-To-Many` relationship from `User to Session`.

### Problems

- Stores `problem details` like **title**, **difficulty**, **description**, etc
- `number` filed is th unique number assigned to each problems representing the problem number.
- `slug` is the unique indentifcation of problem which is `URL friendly` name of the problem title.

- `params` is a string that represent `paramters in the function/method` signature in code. `Multiple paramters` are separated by `\n`.

  Example:

  ```java
  class Solution{
      public int[] twoSum(int[] nums, int target){
          // user code
      }
  }
  ```

  **Here,** the params will be `"nums\ntarget"`

- `active` field is used by **ADMIN** to `hide or unhide` a problem from **USER**.

- `time_limit` controls the how much time a code have `maximum time to execute in seconds`, when it exceed it will give `Time Limit Exceed (TLE)`.

### Problem Code

- Store language and the corresponding code.

- `driver_code` is the full main code that contains main function/method and there is a **placeholder** `{{code}}` in the user solution code.

  ```cpp
  #include <bits/stdc++.h>

  {{code}}

  int main(){
      // input testcase paramters

      // Call Solution with the input

      // Display Result (for comparison with expected output)
      return 0;
  }
  ```

- `starter_code` is for the users to which is shown in the code editor in frotend. User needs to complete this code.

  ```cpp
  class Solution {
  public:
      vector<int> twoSum(vector<int>& nums, int target) {
        // write your code here
      }
  };

  ```

- `solution_code` it is like starter code but there is optimized solution of the problem.

  ```cpp
  class Solution {
  public:
      vector<int> twoSum(vector<int>& nums, int target) {
          unordered_map<int, int> seen;
          for (int i = 0; i < nums.size(); ++i) {
              int need = target - nums[i];
              auto it = seen.find(need);
              if (it != seen.end()) {
                  return { it->second, i };
              }
              seen[nums[i]] = i;
          }
          return {};
      }
  };
  ```

### Problem Testcases

- Store the testcases realted to a problem.

- `input` field represents the input testcase. In case of multiple parameters, inputs are seprated by `\n`.

  The testcase in the classic `Two Sum` problem is :

  ```text
  nums = [2, 7, 3, 9, 4]
  target = 9
  ```

  It is stored as `"2 7 3 9 4\n9"` similar to the `params` field `"nums\ntarget"` in Problems.

  So, frontend can easily `split both input and params` by `\n` and `render UI accordingly`.

- `output` field stores the expected output. It is compare as it is with the output return by the code.

- `sample` represent the testcase is `sample testcase or not`.

### Topics

- It stores the `problem topics`. like `Array`, `Linked List`, `Stack`, etc.

- There is `Many-to-Many` relationship between `problems and topics`.

### Problem Submission

- It stores the `User Problem Submssion` with there `language` and `code`.

- `status` represent the outcome of the submission. status is initially set to `PENDING` when create a new submission request.

- **After the code execution**, the status of result is updated in the Database.

- Here, status value are :
  - **PENDING** = code is being judge,
  - **CTE** = Compile Time Error
  - **RTE** = Run Time Error
  - **WA** = Wrong Answer
  - **TLE** = Time Limit Exceed
  - **MLE** = Memeory Limit Exceed
  - **SOLVED** = Submission Accepted
  - **SERVER_ERROR** = Something went wrong

## 👨‍💻 Author

Built with ❤️ by Rohit Shah  
🔗 [GitHub](https://github.com/shahrohit) · [LinkedIn](https://linkedin.com/in/shahrohit01)
