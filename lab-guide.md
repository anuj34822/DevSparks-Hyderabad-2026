# IBM Bob: Java Modernization Lab
## DevSparks Hyderabad 2026

> **Duration:** 60 minutes · **Level:** Technical / Hands-on · **Tool:** IBM Bob IDE

---

## Contents
1. [About this Lab](#1-about-this-lab)
2. [Pre-requisites](#2-pre-requisites)
3. [Overview](#3-overview)
4. [Lab Steps](#4-lab-steps)
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
- Use Bob's **`/java-upgrade` skill** with OpenRewrite AST recipes to upgrade Java from 1.8 → 17 — no premium package required.
- Use a custom **'Modernization Architect'** mode to modernize the legacy Struts application to a cloud-native Spring Boot application.
- **Generate** required OpenShift artifacts and scripts for deployment.

---

## 2. Pre-requisites

### 2.1 IBM Bob IDE
Install and configure IBM Bob. Follow instructions from `https://bob.ibm.com/docs/ide/getting-started/install`

### 2.2 Optional Dependencies

**① OpenJDK 17**
```sh
brew install openjdk@17                    # macOS
sudo apt update && sudo apt install openjdk-17-jdk   # Ubuntu/Debian
sudo dnf install java-17-openjdk-devel     # RHEL
```

**② Maven**
```sh
brew install maven                         # macOS
sudo apt update && sudo apt install maven  # Ubuntu/Debian
sudo dnf install maven                     # RHEL
```

**③ SDKMAN**
```sh
curl -s "https://get.sdkman.io" | bash     # macOS
curl -sL https://get.sdkman.io | sudo bash # Ubuntu/Debian/RHEL
```

### 2.3 Install PlantUML Plugin
Install the **"PlantUML Markdown Preview"** extension in IBM Bob IDE:
1. Click the **Extensions** icon in the Bob IDE sidebar.
2. Search for **"PlantUML"**.
3. Install **"PlantUML Markdown Preview"**.

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

## 4. Lab Steps

### 4.1 Import Project into Bob Workspace

1. Clone the repository: `https://github.com/anuj34822/java-modernization-lab`
2. Open IBM Bob IDE → **File → Open Folder** → select the `java-modernization-lab` folder.
3. Confirm the folder structure appears in Explorer: `.bob/`, `images/`, `legacy-netbanking/`, `lab-guide.md`

---

### 4.2 Reverse Engineering – Understanding the Legacy Application
**Objective:** Analyze the undocumented early-2000s Struts code and auto-generate comprehensive documentation + diagrams.

1. **Switch to Ask Mode:** Click **"Ask"** at the bottom-right corner of Bob IDE.
2. **Enter and Enhance Prompt:** Type the prompt below, then click the **"Enhance Prompt"** icon before sending:
   > *"Help me understand this legacy-netbanking application. Save the generated documentation and diagrams in legacy-netbanking-documentation directory. Make sure to generate required PlantUML diagrams like sequence diagram along with mermaid architecture and ER diagram etc of current implementation."*
3. Bob will generate an enhanced prompt. Review it and hit **Enter** to send.
4. **Approve Tasks:** Keep reviewing and approving tasks as Bob works through the codebase.
5. **Review documentation:** Right-click the generated `.md` file → select **"Open Preview"**.
6. **Review diagrams:** Right-click a `.puml` file → select **"Preview PlantUML File"**.

---

### 4.3 Java Version Upgrade
**Objective:** Upgrade to Java 17 using Bob natively — no premium package needed.

> **How this works:** The `.bob/skills/java-upgrade/SKILL.md` skill in this repo teaches Bob to run OpenRewrite AST recipes via `mvn`, fix dependency conflicts, validate the build, and generate a Mermaid audit flowchart — all using Bob's built-in `read_file`, `apply_diff`, and `execute_command` capabilities.

1. In Bob IDE, type `/java-upgrade` in the chat (or Bob will offer to activate it automatically).
2. When asked, confirm the project path is `legacy-netbanking` and target version is **17**.
3. Bob will update `pom.xml`, add the OpenRewrite plugin, and run: `mvn rewrite:run`
4. Approve each task as Bob fixes dependency conflicts and re-runs the build.
5. On completion, Bob writes `legacy-netbanking/java-upgrade-report.md` — a Mermaid flowchart of all changes.

> **Note:** If `mvn` is not installed, see Pre-requisites 2.2.

---

### 4.4 Full Application Modernization
**Objective:** Transform to Spring Boot + React using a Custom Mode.

1. **Explore Custom Mode:** Open **Settings → Modes → "Modernization Architect"** and review the role definition.
2. **Review Rules:** Expand `.bob/rules-modernization-architect` in Explorer to see the 6 rule files governing the migration.
3. **Switch Mode:** Select **"Modernization Architect"** mode (mode selector, bottom-right) and enter the modernization prompt:
   > *"Modernize the legacy-netbanking application. Backend: Spring Boot 3.x, Java 17, PostgreSQL, JWT authentication. Frontend: React 18 SPA."*
4. **Approve Todo List:** Bob will systematically work through the entire migration.
5. **Review Results:** Check the new `modern-netbanking` project structure in Explorer.

> **Note:** If Bob stops prematurely, type: *"Complete remaining tasks from the todo list"*

---

### 4.5 Create Kubernetes/OpenShift Deployment Artifacts *(Optional)*

1. Still in **"Modernization Architect"** mode, enter:
   > *"I need to deploy it on OpenShift. Create required artifacts and scripts."*
2. Review the generated `Dockerfile`, Kubernetes YAML manifests, and `deploy.sh` scripts.

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

### 6.2 Build failure or error during migration
When an error occurs, click **"Fix it"** to let Bob analyze and remediate automatically.

### 6.3 Bob stops mid-way (context limit)
Type: *"Complete remaining tasks from the todo list"* to resume exactly where it stopped.

---

## References

- **Lab Repository:** https://github.com/anuj34822/java-modernization-lab
- **Bob Innovation Hub:** https://ibm-self-serve-assets.github.io/bob-innovation-hub/#/use-cases/java-modernization
- **Bob IDE Install:** https://bob.ibm.com/docs/ide/getting-started/install

---

*IBM Service Engineering Lab · DevSparks Hyderabad 2026*
