# IBM Bob: Java Modernization Lab
## DevSparks Hyderabad 2026

> **Duration:** 60 minutes · **Level:** Technical / Hands-on · **Tool:** IBM Bob IDE

---

## Contents
1. [About this Lab](#1-about-this-lab)
2. [Pre-requisites](#2-pre-requisites)
3. [Overview](#3-overview)
4. [Hands-on Lab Steps](#4-hands-on-lab-steps)
   - 4.1 [Import Project into Bob Workspace](#41-import-project-into-bob-workspace)
   - 4.2 [Reverse Engineering – Understanding the Legacy Application](#42-reverse-engineering--understanding-the-legacy-application)
   - 4.3 [Java Version Upgrade](#43-java-version-upgrade)
   - 4.4 [Full Application Modernization](#44-full-application-modernization)
   - 4.5 [Create Kubernetes/OpenShift Deployment Artifacts (Optional)](#45-create-kubernetesopenshift-deployment-artifacts-optional)
5. [Key Modernization Achievements](#5-key-modernization-achievements)
6. [Troubleshooting](#6-troubleshooting)

---

## 1. About this Lab

### Objective of this lab:
- Experience IBM Bob features and capabilities.
- Understand Bob built-in modes, custom modes, and MCP extensibility.
- Explore Bob's comprehensive SDLC capabilities with a legacy Java application modernization use-case.

In this lab, we take a **Legacy Struts 1.3** application (no documentation available) and perform:

- **Reverse Engineer** the legacy code to generate comprehensive documentation, architecture diagrams, ER diagrams, and UML diagrams.
- Use Bob's **`/java-upgrade` skill** with OpenRewrite AST recipes to upgrade Java from 1.8 → 17.
- Use a custom **'Modernization Architect'** mode to modernize the legacy Struts application to a cloud-native Spring Boot application.
- **Generate** required OpenShift artifacts and scripts for deployment.

---

## 2. Pre-requisites

### 2.1 IBM Bob IDE
Install and configure IBM Bob. Follow instructions from `https://bob.ibm.com/docs/ide/getting-started/install`

### 2.2 Optional Dependencies

**① OpenJDK 17**
```sh
brew install openjdk@17                              # macOS
sudo apt update && sudo apt install openjdk-17-jdk   # Ubuntu/Debian
sudo dnf install java-17-openjdk-devel               # RHEL
```

**② Maven**
```sh
brew install maven                                   # macOS
sudo apt update && sudo apt install maven            # Ubuntu/Debian
sudo dnf install maven                               # RHEL
```

**③ SDKMAN**
```sh
curl -s "https://get.sdkman.io" | bash               # macOS
curl -sL https://get.sdkman.io | sudo bash           # Ubuntu/Debian/RHEL
```

### 2.3 Install PlantUML Plugin
Install the **"PlantUML Markdown Preview"** extension in IBM Bob IDE:

1. Click the **Extensions** icon in the Bob IDE sidebar.
2. Search for **"PlantUML"**.
3. Install **"PlantUML Markdown Preview"**.

![PlantUML Markdown Preview extension in Bob IDE Extensions panel](images/image1.png)

### 2.4 Demo Application
We are using a legacy-style NetBanking application built with:
- **Java 8** — no newer language features
- **Apache Struts 1.x** — Action, ActionForm, struts-config.xml
- **JSPs** with scriptlets (`<% %>`)
- **Plain Servlets** — InitServlet, LogoutServlet
- **JDBC** — no ORM/JPA
- **SQLite** — file-based database
- **XML Configuration** — web.xml, struts-config.xml
- **WAR packaging**

---

## 3. Overview

Complete journey from legacy **Struts 1.x + SQLite** to modern **Spring Boot 3.x + React 18 + PostgreSQL 15** including JWT Authentication and deployment to OpenShift/Kubernetes.

| Layer | Legacy (Before) | Modern (After) |
|---|---|---|
| Runtime | Java 1.8 | Java 17 (OpenJDK) |
| Back-end Framework | Apache Struts 1.3 | Spring Boot 3.x + REST API |
| Front-end | JSP + Scriptlets | React 18 SPA |
| Database | SQLite (file-based) | PostgreSQL 15 + Flyway |
| Authentication | HTTP Session / Basic | JWT + BCrypt |
| Deployment | WAR on Tomcat | Docker + OpenShift / Kubernetes |

---

## 4. Hands-on Lab Steps

### 4.1 Import Project into Bob Workspace

1. Clone the repository: `https://github.com/anuj34822/java-modernization-lab`

2. Open IBM Bob IDE → **File → Open Folder** → select the `java-modernization-lab` folder.

![File → Open Folder dialog in Bob IDE](images/image2.png)

![Selecting the java-modernization-lab folder](images/image3.png)

3. Confirm the folder structure appears in Explorer: `.bob/`, `images/`, `legacy-netbanking/`, `lab-guide.md`

![Expected folder structure in Bob IDE Explorer](images/image4.png)

---

### 4.2 Reverse Engineering – Understanding the Legacy Application
**Objective:** Analyze the undocumented early-2000s Struts code and auto-generate comprehensive documentation + diagrams.

1. **Switch to Ask Mode:** Click the mode selector at the bottom-right of Bob IDE and select **"Ask"**.

![Mode selector — select Ask mode](images/image5.png)

2. **Enter and Enhance Prompt:** Type the prompt below in the chat, then click the **"Enhance Prompt"** icon (✨) before sending:

   > *"Help me understand this legacy-netbanking application. Save the generated documentation and diagrams in legacy-netbanking-documentation directory. Make sure to generate required PlantUML diagrams like sequence diagram along with mermaid architecture and ER diagram etc of current implementation."*

![Enhance Prompt icon in the Bob IDE chat bar](images/image6.png)

3. Bob will generate an enhanced version of your prompt. Review it and hit **Enter** to send.

![Enhanced prompt generated by Bob](images/image7.png)

4. **Approve Tasks:** Bob will create a structured task list and start working through the codebase. Keep reviewing and approving each task.

![Bob processing and approving tasks](images/image8.png)

5. **Review documentation:** Once done, right-click the generated `.md` file in Explorer → select **"Open Preview"**.

![Right-click menu — Open Preview on generated .md file](images/image9.png)

![Generated documentation preview](images/image10.png)

6. **Review diagrams:** Right-click a `.puml` file in Explorer → select **"Preview PlantUML File"**.

![Right-click menu — Preview PlantUML File](images/image11.png)

![Sequence diagram rendered from .puml file](images/image12.png)

---

### 4.3 Java Version Upgrade
**Objective:** Upgrade to Java 17 using Bob's `/java-upgrade` skill.

> **How this works:** The `.bob/skills/java-upgrade/SKILL.md` skill in this repo teaches Bob to run OpenRewrite AST recipes via `mvn`, fix dependency conflicts, validate the build, and generate a Mermaid audit flowchart — all using Bob's built-in `read_file`, `apply_diff`, and `execute_command` capabilities.

1. In Bob IDE, type `/java-upgrade` in the chat. Bob will automatically activate the skill and guide you through each step.

2. When asked, confirm:
   - Project path: `legacy-netbanking`
   - Target version: **17**

3. Bob will update `pom.xml`, add the OpenRewrite plugin, and run `mvn rewrite:run`. Approve each task as Bob works through the upgrade.

4. On completion, Bob writes `legacy-netbanking/java-upgrade-report.md` — a Mermaid flowchart showing every change applied.

> **Note:** If `mvn` is not installed, see Pre-requisites 2.2.

---

### 4.4 Full Application Modernization
**Objective:** Transform to Spring Boot + React using a Custom Mode.

1. **Explore the Custom Mode:** Open **Settings** (gear icon, bottom-left) → select **Modes**.

![Opening Settings in Bob IDE](images/image21.png)

2. Find and select **"Modernization Architect"** to review its configuration.

![Modernization Architect mode in the Modes list](images/image22.png)

![Role definition of the Modernization Architect mode](images/image23.png)

3. **Review Rules:** In the Explorer panel, expand `.bob/rules-modernization-architect` to see the 6 rule files that govern every decision Bob makes during migration.

![Rule files inside .bob/rules-modernization-architect](images/image24.png)

4. **Switch Mode:** Click the mode selector at the bottom-right → select **"Modernization Architect"**.

![Switching to Modernization Architect mode](images/image25.png)

5. **Enter the modernization prompt:**

   > *"Modernize the legacy-netbanking application. Backend: Spring Boot 3.x, Java 17, PostgreSQL, JWT authentication. Frontend: React 18 SPA."*

6. **Approve Todo List:** Bob will generate a structured task plan and work through the full migration systematically. Approve each task.

![Bob's modernization Todo list](images/image26.png)

7. **Review Results:** Once complete, check the new `modern-netbanking` project structure in Explorer.

![Modernized project structure in Explorer](images/image27.png)

> **Note:** If Bob stops mid-way, type: *"Complete remaining tasks from the todo list"*

---

### 4.5 Create Kubernetes/OpenShift Deployment Artifacts *(Optional)*

1. Still in **"Modernization Architect"** mode, enter:

   > *"I need to deploy it on OpenShift. Create required artifacts and scripts."*

2. Bob will generate a `Dockerfile`, Kubernetes YAML manifests, and a `deploy.sh` script.

![Generated OpenShift deployment artifacts](images/image28.png)

![Deployment files in the Explorer](images/image29.png)

---

## 5. Key Modernization Achievements

- **Framework:** Struts 1.x → Spring Boot 3.x; JSP → React 18 SPA.
- **Database:** SQLite → PostgreSQL 15 with Flyway migrations.
- **Security:** Basic HTTP Session → JWT-based authentication with BCrypt.
- **Cloud-Native:** Dockerized, Kubernetes/OpenShift ready, externalized config.
- **Runtime:** Java 8 → Java 17 via OpenRewrite AST recipes using Bob's `/java-upgrade` skill.
- **Functional Parity:** All legacy features (transfers, account history) preserved.

---

## 6. Troubleshooting

### 6.1 `mvn` or SDKMAN not installed
Switch to **Agent** mode and enter: *"Install Maven"* or *"Install SDKMAN"*.

![Install SDKMAN via Agent mode](images/image30.png)

### 6.2 Build failure or error during migration
When an error occurs, click **"Fix it"** to let Bob analyze and remediate automatically.

![Fix it button in Bob IDE](images/image32.png)

### 6.3 Bob stops mid-way (context limit)
Type: *"Complete remaining tasks from the todo list"* to resume exactly where it stopped.
