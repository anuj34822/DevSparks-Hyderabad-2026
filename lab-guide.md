# IBM Bob: Java Modernization Lab
## DevSparks Hyderabad 2026

> **Duration:** 60 minutes · **Hands-on · IBM Bob IDE**

---

## Contents
1. [About this Lab](#1-about-this-lab)
2. [Pre-requisites](#2-pre-requisites)
3. [Overview](#3-overview)
4. [Hands-on Lab Steps](#4-hands-on-lab-steps)
   - 4.1 [Import Project into Bob Workspace](#41-import-project-into-bob-workspace)
   - 4.2 [Reverse Engineering](#42-reverse-engineering)
   - 4.3 [Java Version Upgrade](#43-java-version-upgrade)
   - 4.4 [Full Application Modernization](#44-full-application-modernization)
   - 4.5 [OpenShift Deployment Artifacts (Optional)](#45-openshift-deployment-artifacts-optional)
5. [Key Modernization Achievements](#5-key-modernization-achievements)
6. [Troubleshooting](#6-troubleshooting)

---

## 1. About this Lab

This lab shows you how to use IBM Bob to modernize a legacy Java application — not just by running commands, but by building a **reusable system** that you can apply to any codebase.

You will learn how to create three things in Bob and use them together:

- **A custom Mode** — gives Bob a specific persona and expertise. Bob behaves like a Modernization Architect every time you use it
- **A Skill** — a step-by-step playbook that Bob follows when you call `/java-upgrade`. No manual steps, no guesswork
- **Rules** — XML files that encode your architecture standards and constraints into Bob's reasoning

These three together form a system. Once built, any developer on your team can clone this repo, open it in Bob, and get the same consistent results.

We apply this system to a **Legacy Struts 1.3** NetBanking application and:

- Reverse engineer the codebase to produce documentation, architecture diagrams, and ER diagrams
- Upgrade Java from 1.8 to 17 using the `/java-upgrade` skill
- Migrate the full application to Spring Boot 3.x + React 18 + PostgreSQL 15 using the Modernization Architect mode
- Generate OpenShift deployment artifacts — Dockerfile, Kubernetes manifests, and deploy scripts

---

## 2. Pre-requisites

### 2.1 IBM Bob IDE
Install IBM Bob from: `https://bob.ibm.com/docs/ide/getting-started/install`

### 2.2 Optional — Install these if you want to run local builds

**OpenJDK 17**
```sh
# macOS
brew install openjdk@17
# Ubuntu/Debian
sudo apt update && sudo apt install openjdk-17-jdk
# RHEL
sudo dnf install java-17-openjdk-devel
# Windows (winget)
winget install --id Microsoft.OpenJDK.17
```

**Maven**
```sh
# macOS
brew install maven
# Ubuntu/Debian
sudo apt update && sudo apt install maven
# RHEL
sudo dnf install maven
# Windows (winget)
winget install --id Apache.Maven
```

**SDKMAN** *(macOS / Linux only)*
```sh
# macOS
curl -s "https://get.sdkman.io" | bash
# Ubuntu/Debian/RHEL
curl -sL https://get.sdkman.io | sudo bash
```

### 2.3 Install PlantUML Plugin

You need this to preview the diagrams Bob generates.

1. Click the **Extensions** icon in the Bob IDE sidebar
2. Search for **PlantUML**
3. Install **PlantUML Markdown Preview**

<!-- IMAGE: PlantUML Markdown Preview extension visible in the Extensions panel -->

### 2.4 The Application We Are Modernizing

This is a legacy NetBanking application built on:

| Layer | Technology |
|---|---|
| Language | Java 8 |
| Framework | Apache Struts 1.x |
| Views | JSP with scriptlets |
| Persistence | Plain JDBC — no ORM |
| Database | SQLite |
| Config | web.xml, struts-config.xml |
| Packaging | WAR |

---

## 3. Overview

We take this legacy stack all the way to a modern, cloud-native application:

| Layer | Before | After |
|---|---|---|
| Runtime | Java 1.8 | Java 17 |
| Back-end | Struts 1.3 | Spring Boot 3.x |
| Front-end | JSP + Scriptlets | React 18 SPA |
| Database | SQLite | PostgreSQL 15 + Flyway |
| Auth | HTTP Session | JWT + BCrypt |
| Deployment | WAR on Tomcat | Docker + OpenShift |

### How this lab is structured — the Mode + Skill + Rules system

Before you start, take a minute to understand what is already in this repo and why:

| What | What it does | Where it lives |
|---|---|---|
| **Mode** | Gives Bob the persona of a Modernization Architect — with the right tools, permissions, and expertise | `.bob/custom_modes.yaml` |
| **Skill** | Tells Bob exactly how to upgrade Java — step by step, no ambiguity | `.bob/skills/java-upgrade/SKILL.md` |
| **Rules** | 6 XML files that encode migration standards and architecture constraints Bob must follow | `.bob/rules-modernization-architect/` |

You will use each of these during the lab. By the end, you will understand how to build this kind of system yourself for any use case.

---

## 4. Hands-on Lab Steps

### 4.1 Import Project into Bob Workspace

1. Clone the repo:
   ```sh
   # macOS / Linux
   git clone https://github.com/anuj34822/java-modernization-lab

   # Windows (Command Prompt or PowerShell)
   git clone https://github.com/anuj34822/java-modernization-lab
   ```
   > If git is not installed on Windows: `winget install --id Git.Git`

2. Open IBM Bob IDE. Go to **File → Open Folder** and select the `java-modernization-lab` folder

<!-- IMAGE: Bob IDE showing File → Open Folder menu -->

<!-- IMAGE: Folder picker with java-modernization-lab selected -->

3. Check the Explorer panel on the left. You should see: `.bob/`, `images/`, `legacy-netbanking/`, `lab-guide.md`

<!-- IMAGE: Bob IDE Explorer panel showing the correct folder structure -->

---

### 4.2 Reverse Engineering

**Goal:** Let Bob read the legacy codebase and produce full documentation and diagrams — without you writing a single line.

1. Click the mode selector at the bottom-right of Bob IDE and select **Ask**

<!-- IMAGE: Mode selector at bottom-right with Ask highlighted -->

2. Type this prompt in the chat and click the **Enhance Prompt** icon (✨) before hitting Enter:

   > *"Help me understand this legacy-netbanking application. Save the generated documentation and diagrams in legacy-netbanking-documentation directory. Make sure to generate required PlantUML diagrams like sequence diagram along with mermaid architecture and ER diagram etc of current implementation."*

<!-- IMAGE: Chat input bar showing the Enhance Prompt icon -->

3. Bob rewrites your prompt into a more detailed version. Review it and press **Enter**

<!-- IMAGE: Bob's enhanced prompt shown in the chat -->

4. Bob creates a task list and starts working. Click **Approve** for each task as it appears

<!-- IMAGE: Bob's task list with Approve buttons -->

5. When Bob finishes, right-click the generated `.md` file in Explorer and select **Open Preview**

<!-- IMAGE: Right-click menu on the .md file showing Open Preview -->

<!-- IMAGE: Documentation rendered in the preview panel -->

6. Right-click any `.puml` file in Explorer and select **Preview PlantUML File** to see the diagrams

<!-- IMAGE: Right-click menu on a .puml file showing Preview PlantUML File -->

<!-- IMAGE: Sequence diagram rendered in the PlantUML preview -->

---

### 4.3 Java Version Upgrade

**Goal:** Upgrade the project from Java 8 to Java 17.

Before you run anything — open `.bob/skills/java-upgrade/SKILL.md` and read it. This is the Skill that Bob will follow. You will see every step it takes: update `pom.xml`, add the OpenRewrite plugin, run `mvn rewrite:run`, fix any broken dependencies, validate the build, and write an audit report. Reading it now will help you understand what Bob is doing and why.

1. In the Bob chat, type `/java-upgrade` and press **Enter**

<!-- IMAGE: /java-upgrade typed in the Bob chat with Bob activating the skill -->

2. Bob will ask you to confirm two things:
   - Project path → type `legacy-netbanking`
   - Target Java version → type `17`

3. Bob works through the upgrade — updating `pom.xml`, running `mvn rewrite:run`, fixing any dependency issues. Approve each task

<!-- IMAGE: Bob's task list for the java-upgrade skill — showing pom.xml edit and mvn rewrite:run steps -->

4. When done, Bob creates `legacy-netbanking/java-upgrade-report.md`. Open it in Preview to see the Mermaid flowchart of every change that was applied

<!-- IMAGE: java-upgrade-report.md open in Preview showing the Mermaid audit flowchart -->

> If `mvn` is not installed, see section 2.2.

---

### 4.4 Full Application Modernization

**Goal:** Migrate the full application to Spring Boot 3.x + React 18 + PostgreSQL.

This step uses the **Modernization Architect** custom mode backed by 6 rule files. Spend a few minutes exploring the setup before you run it — understanding how the Mode and Rules are built is one of the key things you are here to learn.

**Step A — Look at the Mode**

1. Click the gear icon (Settings) at the bottom-left of Bob IDE and select **Modes**

<!-- IMAGE: Settings gear icon at bottom-left of Bob IDE -->

2. Find **Modernization Architect** in the list and open it

<!-- IMAGE: Modes list with Modernization Architect visible -->

3. Read the Role Definition. This text is what tells Bob how to think and behave during the migration

<!-- IMAGE: Role Definition text of the Modernization Architect mode -->

**Step B — Look at the Rules**

4. In the Explorer panel, expand `.bob/rules-modernization-architect/`. You will see 6 XML files

<!-- IMAGE: Explorer showing .bob/rules-modernization-architect/ expanded with 6 files listed -->

5. Open one of them. You will see how architecture decisions, migration constraints, and code patterns are written in plain XML. This is how you bake your firm's standards into Bob for any project

<!-- IMAGE: A rule file open in the editor showing the XML structure -->

**Step C — Run the Migration**

6. Click the mode selector at the bottom-right and select **Modernization Architect**

<!-- IMAGE: Mode selector showing Modernization Architect selected -->

7. Type this prompt and press **Enter**:

   > *"Modernize the legacy-netbanking application. Backend: Spring Boot 3.x, Java 17, PostgreSQL, JWT authentication. Frontend: React 18 SPA."*

8. Bob produces a Todo list covering the full migration. Approve each task as it runs

<!-- IMAGE: Bob's Todo list for the modernization showing all the phases -->

9. When complete, check the Explorer panel — you will see a new `modern-netbanking/` folder with the fully migrated project

<!-- IMAGE: Explorer showing the new modern-netbanking/ folder structure -->

> If Bob stops mid-way, type: *"Complete remaining tasks from the todo list"*

---

### 4.5 OpenShift Deployment Artifacts *(Optional)*

Still in **Modernization Architect** mode, type:

> *"I need to deploy this on OpenShift. Create the required artifacts and scripts."*

Bob generates a `Dockerfile`, Kubernetes YAML manifests, and a `deploy.sh` script.

<!-- IMAGE: Explorer showing the generated deployment files -->

<!-- IMAGE: Dockerfile or Kubernetes manifest open in the editor -->

---

## 5. Key Modernization Achievements

| Area | Result |
|---|---|
| Framework | Struts 1.x → Spring Boot 3.x · JSP → React 18 SPA |
| Database | SQLite → PostgreSQL 15 with Flyway migrations |
| Security | HTTP Session → JWT + BCrypt |
| Runtime | Java 8 → Java 17 via OpenRewrite AST recipes |
| Deployment | WAR → Docker + OpenShift / Kubernetes |
| Functional Parity | All features preserved — transfers, account history, admin |
| Reusable System | The Mode + Skill + Rules in this repo work on any Java codebase |

---

## 6. Troubleshooting

### 6.1 Maven or SDKMAN not installed
Switch to **Agent** mode and type: *"Install Maven"* or *"Install SDKMAN"*

<!-- IMAGE: Agent mode chat with Install SDKMAN typed -->

### 6.2 Build fails during migration
Click **Fix it** next to the error. Bob reads the failure and applies a fix automatically.

<!-- IMAGE: Fix it button shown next to a build error in Bob IDE -->

### 6.3 Bob stops before finishing
Type: *"Complete remaining tasks from the todo list"*
