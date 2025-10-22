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
7. [🔐 Backend System](#-backend-system)
   1. [Session Management](#session-management)
   2. [Redis Usecase](#redis-usecase)
   3. [Scalable Notification System](#scalable-notification-system)
   4. [Message Queue Integration](#message-queue-integration)
   5. [Why Server-Sent Events (SSE)](#why-server-sent-events-sse)
8. [🚀 Code Execution Service (Worker)](#-code-execution-service-worker)
9. [🐳 Quick Setup (Docker Compose)](#-quick-setup-docker-compose)
10. [🧠 Challenges Faced & Solutions](#-challenges-faced--solutions)
11. [❓ Next steps](#-next-steps)

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

   - **Here, in the Go worker**, new `docker container` is spin up based on the language and the code is copied in the container, `compile (if needed)`. Then code is **executed with testcases in case of Submit** or **testcases (expected output) are created based on solution code in case of Run**.

7. Then the `result` is constructed based on the `stdOut` and `stdErr` and push into the `Submission Response Queue.`

8. The Submission Response is `Consume` by the `Spring boot server` and pick up the stored result.

9. The `status(Accepted or Wrong Answer or TLE or MLE or RunTime Eror)` is updated into the `database`.

10. Then the result is send back to the `frontend` using the `SSE`.

## 🧱 Tech Stack

| Layer             | Technology      | Purpose                                                    |
| ----------------- | --------------- | ---------------------------------------------------------- |
| Frontend          | Next.js (React) | Code editor & UI                                           |
| Backend           | Spring Boot     | API, Auth, SSE, queue producers/consumers                  |
| Worker            | Go              | Code execution in Docker sandbox                           |
| Message Queue     | RabbitMQ        | Async messaging                                            |
| Database          | PostgreSQL      | Store User, Problems, testcases, submissions               |
| Cache/Token Store | Redis           | Temporary storage for verification & password reset tokens |
| Containerization  | Docker          | Safe sandboxing                                            |

## 💾 Database Design

## ![Hashcodex Architecture](/public/db.png)

### User

- Stores `user information` like **name**, **email**, **hashed Password**, etc.

- `Role` field is enum whose values are `USER`, `ADMIN`.

- **Email and password are the primary source of authentication.** `email_verified` field store whether user have verified there email or not.

- `public_id` is the UUID which can be `expose to public (frontend)` instead of the database id.

---

### Session

- Session Table stores the logged in `user session data` like **session id**, **user id**, **creation timestamp** and **expiry timestamp**.

- The `expiry` of the session is usually long like `1 week`.

- It also helps to `refersh the Access Token` which is `short-lived JWT` usually of `15 minutes`.

- There `One-To-Many` relationship from `User to Session`.

---

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

---

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

---

### Problem Testcases

- Store the testcases related to a problem.

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

---

### Topics

- It stores the `problem topics`. like `Array`, `Linked List`, `Stack`, etc.

- There is `Many-to-Many` relationship between `problems and topics`.

---

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

## 🔐 Backend System

> - **Language** : Java (JDK 21)
> - **Framework**: Spring Boot v3.5
> - **Build System**: Gradle
> - **Architecture**: Controller - Service - Repository
> - **Database Migrations** : Flyway
> - **Security** : Spring Security
> - **Persistence API**: Spring data JPA

### Session Management

> After creating new account in the platform, then a confirmation link is sent to the registered email address to verify the account.

**After verification,**

- User login with the registered email and password.

- Server verifies the credentails.

- Then a new entry is created in the `Session Table` with the **expiration value** fetch from the `application.properties` file.

- Also, and **Access Token** which is a `short-lived JWT` is created where the `user public id as subject`. **user's role**, and the **unique session id** is also stored in the JWT paylod and the expiration is also set using value store d in `application.properties`.

- The generated access token is send to frontend as **HTTP-Only Cookie**

- **On every Request**, the cookie is automatically send along with the request.

- **In secure endpoints**, the token cookie is extracted and verify before reaching to `controller`.

- **If the token is expired**, and then the current session is fetch using session id stored in the JWT payload, if the session is not expired then `new access token is generated` and the expiration of session is updated.

- Then the request is **forwarded** to the controller.

---

### Redis Usecase

> **Redis** is used to store the **authentication token** during `email verification` and `password reset`.

- After creating new account, the email need to verify.

- Then a secure random token is generated and stored in redis. The `token is stored as value` and the `user public id as key`.

  ```text
  verify_<User-Public-Id> : <Secure-Random-Token>
  ```

- Similary, for password reset,

  ```
  pswd_<User-Public-Id> : <Secure-Random-Token>
  ```

---

### Scalable Notification System

> Purpose: Send verification and password reset emails asynchronously using an event-driven architecture.

- To keep the main request fast and resilient, email notifications are handled asynchronously using Spring Boot’s `@EventListener` and `@Async` annotations.

- This design decouples notification logic from core business logic — making the system scalable

- **In DEV mode**, notifications are simply printed to the console for easy debugging.

- **In PROD mode**, the listener delegates to the appropriate Notifier implementation (e.g., EmailNotifier).

- Here is exmple code of Notification Event Listener

  ```java
  @Component
  @RequiredArgsConstructor
  public class NotificationEventListener {
    private final List<Notifier<? extends NotificationPayload>> notifiers;
    private final ProfileProperties profileProperties;

    @Async
    @EventListener
    public void handleNotification(NotificationPayload payload) {
        switch (profileProperties.active()) {
            case DEV -> {
                System.out.println("To: " + payload.recipient());
                System.out.println(payload.content());
            }

            case PROD -> notifiers.stream()
                .filter(notifier -> notifier.getPayloadType().isInstance(payload))
                .findFirst()
                .ifPresent(notifier -> NotificationEventListener.sendNotification(notifier, payload));
        }
    }

    private static <T extends NotificationPayload> void sendNotification(
        Notifier<T> notifier,
        NotificationPayload payload
    ) {
        T typedPayload = notifier.getPayloadType().cast(payload);
        notifier.send(typedPayload);
    }
  }

  ```

---

### Message Queue Integration

> Hashcodex uses **RabbitMQ** to decouple the API/backend from the execution workers and to build a robust, scalable, event-driven pipeline for running and returning submission results.

- There are two Queue **Submission Request Queue** and **Submission Response Queue**.

- In rabbit MQ, we need exchange and routing keys. Also, need to bind the queue, exchange, routing key.

- The values of the Queue, Exchange and Routing Keys are

  ```text
  // Request
  Request Exchange = hashcodex.req.exchange
  Request Queue = hashcodex.req.queue
  Request Routingkey = hashcodex.req

  // Response
  Response Exchange = hashcodex.res.exchange
  Response Queue = hashcodex.res.queue
  Response Routingkey = hashcodex.res

  // Binding Request
  Request Exchange + Request Routingkey --> Request Queue

  // Binidng Response
  Response Exchange + Response Routingkey --> Response Queue
  ```

- In **Submission Request Queue**, the `producer is the spring boot server` which actally push submission payload to the queue. And, `Go Worker is the consumer` of this queue who listen to the queue and fetch the payload .

  Message Payload structure:

  ```json
  {
    "submissionId" : 1, // DB submission id (null in case of RUN)
    "language" : "JAVA", // or CPP or PYTHON
    "solutionCode" : null, // null in case of SUBMIT
    "code" : "// driver + user code...",
    "startLine" : 4,
    "testcases" : [
      {"input", "1 2 3 4\n5", "output" : "0 3"}
    ],
    "submissionType" : "SUBMIT" // or "RUN"
  }
  ```

  **Note:** Along with the message payload, a `correlation id` is sent. this is the unique submission id. It uniquely identify each submission payload.

  The `submissionId` in the payload is the Database submission id which helps to update the submission response status in the database. It can be `null` in case of `RUN`, running with custom testcase doesnot create submission entry in database.

  `startLine` is the interger value which store from which line the user code start in the main code after merging driver code and user code. Helps in `aligning Error line` with the user code.

- In **Submission Response Queue**, The `Go Worker is the producer` who push the submission result/response to the queue and `spring boot server is the consumer` who listen to the queue and fetch the submission result.

  ```json
  {
    "id": 1, // submission id
    "total": 10, // total testcases
    "passed": 5, // total passed
    "status": "WA", // overall status
    "compileError": null, // compile error if any
    "timeMs": "200", // total time taken in milli second
    "cases": [
      {
        "input": "// input testcase",
        "output": "// output testcase",
        "expected": "// expected value",
        "error": "// error is any else null",
        "status": "// testcase status"
      }
    ],
    "errorMessage": null,
    "submissionType": "SUBMIT" // or "RUN"
  }
  ```

  **Note:** Along with the response payload, the same `correlation id` is send back.

---

### Why Server-Sent Events (SSE)

> I choose Server-Sent Events (SSE) because my platform only requires one-way, real-time updates from the backend to the frontend, not a full duplex connection like WebSockets.

- **Unidirectional communication fits perfectly**

  In my use case, after a user submits code, the backend just needs to stream status updates.

  The client doesn’t need to continuously send messages back so a simple, server-to-client stream is ideal.

  SSE is lightweight,and built on standard HTTP — no special protocol handling.

- **Better than Polling**

  Traditional polling (e.g., hitting `/status` every few seconds) is wasteful causes high server load.

  SSE provides instant push updates over a single long-lived HTTP connection.

- **Simpler than WebSockets for this use case**

  WebSockets provide full-duplex communication which is not required as the platform doesn’t require bidirectional communication, only required server → client updates (for code execution results).

## 🚀 Code Execution Service (Worker)

> The Code Execution Service (written in Go) is a distributed worker responsible for compiling and executing user-submitted code securely inside isolated Docker containers.
>
> It consumes jobs from RabbitMQ queues and publishes results back once execution is completed.

### 🐳 Docker Sandboxed Execution

The worker executes user code using ephemeral containers with strict isolation.

- Docker images for the respective languages are :

  - Java : `openjdk:21-jdk`
  - C++ : `gcc:13`
  - Python : `python:3.11-alpine`

**Docker Containers Configuration,**

- **NetworkMode : "none"** - completely isolated (no internet access)

- **No Linux capabilities**

- **No privileged containers**

- **Limited memory and CPU**

  | For         | Memory (MB) | CPUNanos      | Pids Limit |
  | ----------- | ----------- | ------------- | ---------- |
  | **Compile** | 512         | 2,000,000,000 | 256        |
  | **Run**     | 256         | 1,000,000,000 | 128        |

  **To ensure fairness,** Hashcodex `dynamically scales time per language` using predefined multipliers.
  C++ is fastest among these 3 languages, so the base time decided by the `execution time of C++` and it is set by the problem admin.

  | Language | Time Factor |
  | -------- | ----------- |
  | C++      | 1.0x        |
  | Java     | 2.0x        |
  | Python   | 5.0x        |

**Code Compilation & Execution**

**After docker container is created**,

- source code is copied into the container.

- Then code is compiled (if applicable).

- Then the code is run per testcases. Testcase are feeded by standard input.

- The job of the driver code is to accept the input and call the Solution with input.

**Result Collection and Formatting**

- Output is collected with the **StdOut** and **StdErr**.

- **In case of Compilation Error & Run Time Error**, the error message is passed through a layer that format the error message.

- When something error happend like a `compile time error` or a `Run Time Error`, the error message contains the line number where the error happens. This

- Error line number is based on th merged code (driver code + user code). So the Error message is updated based on the user code by the help of the `startLine` value send in the submission paylod.

## 🐳 Quick Setup (Docker Compose)

Here is the step by step guid to setup the project locally and run it using `docker compose`.

**Before setup,** make have installed `git` and `docker` in your system.

Also, Docker should be running in your system.

1. Clone the repository

```bash
git clone https://github.com/shahrohit/hashcodex.git
```

2. Go into the `hashcodex` directory.

```bash
cd hashcodex
```

3. create `.env` file in the frontend root directory

4. Copy the below code into the `.env` file of the frontend (created just before)

```
NEXT_PUBLIC_BACKEND_URL=http://localhost:8000
```

5. Now build the project

```bash
docker compose build
```

6. Pull the docker image of compiler for each language. Run the below command one by one.

```
docker pull gcc:13
docker pull openjdk:21-jdk
docker pull python:3.11-alpine
```

7. Now, Run the project.

```bash
docker compose up -d
```

![Docker Compse](/public/demo_docker_compose.png)

**Note:** Make sure the each service is running just like in the above image

8. Now click on this url, [http://localhost:3000](http://localhost:3000). You will see the home page of `hashcodex`. You will see the some problem topics like Array, Linked List, etc and a problem `Two Sum`. If not then your services are not working properly. In that case restart the docker containers.

9. Click on the `Get started` button in the top right corner.

10. **You will ask for login**, the default login id and password is given below. These credentails have the `admin access`. You will maybe redirect to the admin page after login where you can manage problems.

- Demo Email Address: `hikameb683@lorkex.com`
- Demo Password : `admin@123`

**Note:** The give email is temporary mail. Is is just used for demo purpose.

11. Again go to the home page [http://localhost:3000](http://localhost:3000) then select the`Two Sum` problem. Then start solving your problem

12. To Stop the docker containers, run the below command,

```bash
docker compose down
```

## 🧠 Challenges Faced & Solutions

1. **Executing external user code securely**

   **Challenge**: Running arbitrary user code in the backend introduces security risks.

   **Solution**: All user code is executed inside isolated Docker containers with strict resource limits, no network access, and dropped Linux capabilities.

---

2. **Ensuring complete isolation and safety**

   **Challenge**: Preventing malicious code from accessing the host system.

   **Solution**: Containers run with:

   - NetworkMode: none (no internet access)
   - CapDrop: ["ALL"] (no Linux capabilities)
   - SecurityOpt: ["no-new-privileges"]
   - Limited CPU, memory, and PID resources.

---

3. **Designing LeetCode-style solution structure**

   **Challenge**: Allow users to only implement the function body, not boilerplate or I/O code.

   **Solution**: Each problem stores a driver code with a `{{code}}` placeholder. User-submitted logic replaces this placeholder at runtime before execution.

---

4. **Structuring testcases with multiple parameters**

   **Challenge**: Problems often contain multiple input parameters and outputs.

   **Solution**: Each problem stores params, input, and output fields as newline-separated strings (e.g., `"nums\ntarget"`), allowing dynamic UI generation and flexible parsing.

---

5. **Handling submissions with predefined testcases**

   **Challenge**: Evaluate user code against official testcases during “Submit”.

   **Solution**: Worker runs the code using problem’s stored testcases and compares outputs with the expected results for each case.

---

6. **Handling custom runs with user-defined testcases**

   **Challenge**: Allow users to test code with custom input before submission.

   **Solution**: During `“Run”`, worker first executes solution code to generate expected output, then runs user code with the same input for comparison.

---

7. **Evaluating testcase results**

   **Challenge**: Accurately compare outputs and determine verdicts.

   **Solution**: Normalize both outputs (trim spaces, newlines) and compare line-by-line to classify results as Accepted, Wrong Answer, TLE, RTE, etc.

---

8. **Disabling user output streams**

   **Challenge**: Preventing print/debug statements from interfering with expected results.

   **Solution**: In driver code, output streams are temporarily disabled while calling the user’s function, then re-enabled for displaying final results.

---

9. **Aligning error line numbers with frontend editor**

   **Challenge**: Compiler/runtime errors include extra lines from driver code, confusing users.

   **Solution**: Worker reformats error messages, removing absolute paths and adjusting line offsets to match user code lines.

---

10. **Asynchronous execution & real-time feedback**

    **Challenge**: Ensuring non-blocking execution and real-time result updates.

    **Solution**:

    - Submissions are processed asynchronously via RabbitMQ.
    - Worker sends results to Response Queue.
    - Backend streams live updates to the frontend using Server-Sent Events (SSE).

## ❓ Next steps

- Implement rate limiting for the problem submission.
- Setup a CI/CD pipeline.
- Implement container orchestration with kubernetes.
- Implement horizontal scaling of worker service.

## 👨‍💻 Author

Built with ❤️ by Rohit Shah  
🔗 [GitHub](https://github.com/shahrohit) · [LinkedIn](https://linkedin.com/in/shahrohit01)
