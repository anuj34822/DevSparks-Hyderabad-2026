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

### Objective of this lab

In this lab, you will experience how IBM Bob transforms legacy Java modernization into a repeatable, AI-driven system. Specifically, you will learn how to:

- **Create a custom Mode** — a specialized persona with domain expertise built in, so Bob behaves like a modernization architect every time
- **Create a Skill** — a reusable procedural playbook (`/java-upgrade`) that Bob activates to run OpenRewrite AST recipes, fix dependencies, and generate an audit report
- **Create Rules** — XML rule files that encode architecture standards, migration constraints, and best practices directly into Bob's reasoning
- **Use these three together as a system** — Mode + Skill + Rules working in combination to deliver consistent, high-quality modernization outcomes across any engagement

In this lab, we apply this system to a **Legacy Struts 1.3** NetBanking application and:

- **Reverse Engineer** the legacy code to generate comprehensive documentation, architecture diagrams, ER diagrams, and UML diagrams
- Run the **`/java-upgrade` skill** to upgrade Java from 1.8 → 17 using OpenRewrite AST recipes
- Use the custom **Modernization Architect mode** (backed by 6 rule files) to migrate the full application to Spring Boot 3.x + React 18 + PostgreSQL 15
- **Generate** OpenShift deployment artifacts — Dockerfile, Kubernetes manifests, and deploy scripts

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

1. Click the **Extensions** icon in the Bob IDE sidebar
2. Search for **"PlantUML"**
3. Install **"PlantUML Markdown Preview"**

<!-- IMAGE: PlantUML Markdown Preview extension in Bob IDE Extensions panel -->

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

### The System: Mode + Skill + Rules

This lab is built on a three-part system inside Bob. Understanding how it works is as important as running it:

| Component | What it is | Where it lives in this repo |
|---|---|---|
| **Mode** | A custom persona — "Modernization Architect" — with domain expertise, tool permissions, and behavior rules baked in | `.bob/custom_modes.yaml` |
| **Skill** | A procedural playbook — `/java-upgrade` — that Bob follows step-by-step to run OpenRewrite recipes | `.bob/skills/java-upgrade/SKILL.md` |
| **Rules** | 6 XML files encoding architecture standards, migration constraints, and Spring Boot best practices | `.bob/rules-modernization-architect/` |

Together, these three give Bob the context, the knowledge, and the procedure to deliver a consistent modernization outcome every time — on any codebase.

---

## 4. Hands-on Lab Steps

### 4.1 Import Project into Bob Workspace

1. Clone the repository:
   ```sh
   git clone https://github.com/anuj34822/java-modernization-lab
   ```

2. Open IBM Bob IDE → **File → Open Folder** → select the `java-modernization-lab` folder

<!-- IMAGE: File → Open Folder in Bob IDE menu -->

<!-- IMAGE: Selecting the java-modernization-lab folder in the folder picker -->

3. Confirm the folder structure appears in the Explorer panel: `.bob/`, `images/`, `legacy-netbanking/`, `lab-guide.md`

<!-- IMAGE: Folder structure in Bob IDE Explorer showing .bob/, legacy-netbanking/, lab-guide.md -->

---

### 4.2 Reverse Engineering – Understanding the Legacy Application
**Objective:** Analyze the undocumented Struts codebase and auto-generate comprehensive documentation and diagrams.

1. **Switch to Ask Mode:** Click the mode selector at the bottom-right of Bob IDE and select **"Ask"**

<!-- IMAGE: Mode selector at bottom-right showing Ask selected -->

2. **Enter and Enhance Prompt:** Type the prompt below in the chat, then click the **"Enhance Prompt"** icon (✨) before sending:

   > *"Help me understand this legacy-netbanking application. Save the generated documentation and diagrams in legacy-netbanking-documentation directory. Make sure to generate required PlantUML diagrams like sequence diagram along with mermaid architecture and ER diagram etc of current implementation."*

<!-- IMAGE: Chat bar showing the Enhance Prompt icon highlighted -->

3. Bob generates an enhanced version of your prompt. Review it and hit **Enter** to send

<!-- IMAGE: Bob's enhanced version of the prompt in the chat -->

4. **Approve Tasks:** Bob creates a structured task list and starts working through the codebase. Keep approving each task as it appears

<!-- IMAGE: Bob's task list with approve buttons visible -->

5. **Review documentation:** Right-click the generated `.md` file in Explorer → select **"Open Preview"**

<!-- IMAGE: Right-click context menu on .md file showing Open Preview option -->

<!-- IMAGE: Generated documentation rendered in preview panel -->

6. **Review diagrams:** Right-click a `.puml` file in Explorer → select **"Preview PlantUML File"**

<!-- IMAGE: Right-click context menu on .puml file showing Preview PlantUML File -->

<!-- IMAGE: Sequence diagram rendered in the PlantUML preview panel -->

---

### 4.3 Java Version Upgrade
**Objective:** Upgrade to Java 17 using Bob's `/java-upgrade` skill.

> **What you are doing here:** You are invoking a **Skill** — a procedural playbook stored in `.bob/skills/java-upgrade/SKILL.md`. Open that file now and read it. You will see exactly the steps Bob will follow: update `pom.xml`, add the OpenRewrite plugin, run `mvn rewrite:run`, fix dependency conflicts, validate the build, and write an audit report. This is how you build a Skill for any repeatable task.

1. In Bob IDE chat, type `/java-upgrade` and hit **Enter**. Bob activates the skill automatically

<!-- IMAGE: Chat showing /java-upgrade typed and Bob activating the skill -->

2. When Bob asks, confirm:
   - Project path: `legacy-netbanking`
   - Target version: **17**

3. Bob updates `pom.xml`, adds the OpenRewrite plugin, and runs `mvn rewrite:run`. Approve each task

<!-- IMAGE: Bob's task list for the java-upgrade skill showing pom.xml update and mvn rewrite:run -->

4. On completion, Bob writes `legacy-netbanking/java-upgrade-report.md` — open it to see the Mermaid flowchart of every change applied

<!-- IMAGE: java-upgrade-report.md open in preview showing the Mermaid flowchart -->

> **Note:** If `mvn` is not installed, see Pre-requisites 2.2.

---

### 4.4 Full Application Modernization
**Objective:** Migrate the full application to Spring Boot 3.x + React 18 + PostgreSQL using the Modernization Architect custom mode.

> **What you are doing here:** You are using a **Mode** powered by **Rules**. Before running the migration, take 2 minutes to explore how it is built — this is the most important part of the lab.

**Step A — Explore the Mode**

1. Open **Settings** (gear icon, bottom-left of Bob IDE) → select **Modes**

<!-- IMAGE: Settings gear icon at the bottom-left of Bob IDE -->

2. Find **"Modernization Architect"** in the list and click it to view its configuration

<!-- IMAGE: Modes list showing Modernization Architect -->

3. Read the **Role Definition** — this is what gives Bob its modernization persona and domain expertise

<!-- IMAGE: Role definition text of the Modernization Architect mode -->

**Step B — Explore the Rules**

4. In the Explorer panel, expand `.bob/rules-modernization-architect/` — you will see 6 XML rule files

<!-- IMAGE: Explorer panel showing .bob/rules-modernization-architect/ expanded with 6 files -->

5. Open any one rule file — notice how it encodes specific architecture decisions, constraints, and code patterns Bob must follow. This is how you encode your firm's standards into Bob

<!-- IMAGE: One rule file open in the editor showing XML structure -->

**Step C — Run the Modernization**

6. Click the mode selector at the bottom-right → select **"Modernization Architect"**

<!-- IMAGE: Mode selector showing Modernization Architect selected -->

7. Enter the modernization prompt:

   > *"Modernize the legacy-netbanking application. Backend: Spring Boot 3.x, Java 17, PostgreSQL, JWT authentication. Frontend: React 18 SPA."*

8. Bob generates a structured Todo list and begins the migration. Approve each task

<!-- IMAGE: Bob's Todo list for the full modernization showing all phases -->

9. Once complete, review the new `modern-netbanking/` project in Explorer

<!-- IMAGE: Explorer showing the new modern-netbanking/ project structure -->

> **Note:** If Bob stops mid-way, type: *"Complete remaining tasks from the todo list"*

---

### 4.5 Create Kubernetes/OpenShift Deployment Artifacts *(Optional)*

1. Still in **"Modernization Architect"** mode, enter:

   > *"I need to deploy it on OpenShift. Create required artifacts and scripts."*

2. Bob generates a `Dockerfile`, Kubernetes YAML manifests, and a `deploy.sh` script

<!-- IMAGE: Generated OpenShift artifacts visible in Explorer -->

<!-- IMAGE: Dockerfile or Kubernetes manifest open in the editor -->

---

## 5. Key Modernization Achievements

- **Framework:** Struts 1.x → Spring Boot 3.x; JSP → React 18 SPA
- **Database:** SQLite → PostgreSQL 15 with Flyway migrations
- **Security:** Basic HTTP Session → JWT-based authentication with BCrypt
- **Cloud-Native:** Dockerized, Kubernetes/OpenShift ready, externalized config
- **Runtime:** Java 8 → Java 17 via the `/java-upgrade` skill with OpenRewrite AST recipes
- **Functional Parity:** All legacy features (transfers, account history) preserved
- **Reusable System:** Mode + Skill + Rules built in this lab can be adapted to modernize any Java application

---

## 6. Troubleshooting

### 6.1 `mvn` or SDKMAN not installed
Switch to **Agent** mode and enter: *"Install Maven"* or *"Install SDKMAN"*

<!-- IMAGE: Agent mode chat showing Install SDKMAN command -->

### 6.2 Build failure or error during migration
When an error occurs, click **"Fix it"** — Bob analyzes the failure and applies a fix automatically

<!-- IMAGE: Fix it button visible next to a build error in Bob IDE -->

### 6.3 Bob stops mid-way
Type: *"Complete remaining tasks from the todo list"* to resume exactly where it stopped
