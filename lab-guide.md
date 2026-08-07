# IBM Bob: Java Modernization Lab
## DevSparks Hyderabad 2026

> **Duration:** 60 minutes · **Hands-on · IBM Bob IDE**

## Revision Chart
| Version | Primary Author | Description | Date |
| :--- | :--- | :--- | :--- |
| 1.0 | Anuj Shrivastava | Initial Version — no premium package, Bob-native skill approach | Aug 2026 |

---

## Contents
- [About this Lab](#about-this-lab)
- [Pre-requisites](#pre-requisites)
- [Overview](#overview)
- [Hands-on Lab Steps](#hands-on-lab-steps)
  - [Step 1 — Import Project into Bob Workspace](#step-1--import-project-into-bob-workspace)
  - [Step 2 — Reverse Engineering](#step-2--reverse-engineering)
  - [Step 3 — Java Version Upgrade](#step-3--java-version-upgrade)
  - [Step 4 — Full Application Modernization](#step-4--full-application-modernization)
  - [Step 5 — OpenShift Deployment Artifacts (Optional)](#step-5--openshift-deployment-artifacts-optional)
- [Key Modernization Achievements](#key-modernization-achievements)
- [Troubleshooting](#troubleshooting)

---

## About this Lab

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

## Pre-requisites

### IBM Bob IDE
Install IBM Bob from: `https://bob.ibm.com/docs/ide/getting-started/install`

> **Make sure you are logged in to Bob IDE before starting the lab.**

### Optional — Install these if you want to run local builds

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

### Install PlantUML Plugin

You need this to preview the diagrams Bob generates.

1. Click the **Extensions** icon in the Bob IDE sidebar
2. Search for **PlantUML**
3. Install **PlantUML Markdown Preview**

![PlantUML Markdown Preview extension in the Extensions panel](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image1.png)

### The Application We Are Modernizing

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

## Overview

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

## Hands-on Lab Steps

> **Before you begin:** Make sure you are logged in to IBM Bob IDE. Open Bob and confirm your account is active — you should see your name or profile icon at the bottom-left of the IDE.

---

### Step 1 — Import Project into Bob Workspace

**Goal:** Clone the lab repo directly inside Bob — no terminal needed.

1. Open **IBM Bob IDE**. You will see the Welcome screen.

![Bob IDE Welcome screen showing the Start menu with Clone Git Repository option](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image2.png)

2. Click **"Clone Git Repository..."** from the Start menu.

   A URL input bar appears at the top of Bob. Paste this URL and press **Enter**:
   ```
   https://github.com/anuj34822/java-modernization-lab
   ```

![URL input bar at the top of Bob with the repo URL entered](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image3.png)

3. Bob asks where to save the repo. Select your **Documents** folder (or any preferred folder) and click **"Select as Repository Destination"**.

![Folder picker showing the destination folder selected](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image4.png)

4. When the clone completes, Bob asks **"Would you like to open the cloned repository?"** — click **Open**.

5. The Explorer panel on the left shows the project. Confirm you can see:

   ```
   java-modernization-lab/
   ├── .bob/
   ├── images/
   ├── legacy-netbanking/
   └── lab-guide.md
   ```

![Bob Explorer panel showing the correct folder structure with .bob/ highlighted](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image5.png)

   > **Notice the `.bob/` folder** — this contains the custom Mode, Skill, and all 6 Rule files that power this lab. You get everything pre-configured just by cloning. Nothing to set up manually.

---

### Step 2 — Reverse Engineering

**Goal:** Let Bob read the legacy codebase and produce full documentation and diagrams — without you writing a single line.

1. Click the mode selector at the bottom-right of Bob IDE and select **Ask**

![Mode selector at bottom-right with Ask highlighted](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image6.png)

2. Type this prompt in the chat and click the **Enhance Prompt** icon (✨) before hitting Enter:

   > *"Help me understand this legacy-netbanking application. Save the generated documentation and diagrams in legacy-netbanking-documentation directory. Make sure to generate required PlantUML diagrams like sequence diagram along with mermaid architecture and ER diagram etc of current implementation."*

![Chat input showing the prompt typed with the Enhance Prompt icon highlighted](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image7.png)

3. Bob rewrites your prompt into a more detailed version. Review it and press **Enter**

![Bob's enhanced version of the prompt shown in the chat](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image8.png)

4. Bob creates a task list and starts working. Click **Approve** for each task as it appears

![Bob's task list running the reverse engineering with Approve buttons](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image9.png)

5. When Bob finishes, right-click the generated `.md` file in Explorer and select **Open Preview**

![Right-click context menu on the generated .md file showing Open Preview](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image10.png)

![Generated documentation rendered in the Preview panel](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image11.png)

6. Right-click any `.puml` file and select **Preview PlantUML File** to see the diagrams

![Right-click context menu on a .puml file showing Preview PlantUML File](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image12.png)

---

### Step 3 — Java Version Upgrade

**Goal:** Upgrade the project from Java 8 to Java 17 using the `/java-upgrade` Bob skill.

> **Pre-check — verify Maven is available before starting:**
> ```sh
> mvn -version
> ```
> If this command is not found, install Maven first — see the Pre-requisites section.

Before you run anything, open `.bob/skills/java-upgrade/SKILL.md` and read it. This is the Skill Bob will follow. You will see every step: update `pom.xml`, add the OpenRewrite plugin, run `mvn rewrite:run`, fix dependency conflicts, validate the build, write an audit report. Reading it now helps you understand what Bob is doing and why.

1. In the Bob chat, type `/java-upgrade` and press **Enter**

![Bob chat showing /java-upgrade typed and Bob activating the skill](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image13.png)

2. Bob asks you to confirm two things:
   - Project path → type `legacy-netbanking`
   - Target Java version → type `17`

![Bob asking to confirm the project path and target Java version](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image14.png)

3. Bob works through the upgrade automatically. Approve each task as it appears:
   - Updates `pom.xml` compiler settings to Java 17
   - Adds the OpenRewrite Maven plugin
   - Runs `mvn rewrite:run` to apply the UpgradeToJava17 recipe
   - Fixes any dependency conflicts
   - Runs `mvn clean package` to validate the build

![Bob's task list for the java-upgrade skill showing pom.xml edit, mvn rewrite:run, and build validation steps](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image15.png)

4. When complete, Bob creates `legacy-netbanking/java-upgrade-report.md`. Open it in Preview to see the Mermaid flowchart of every change applied

![java-upgrade-report.md open in Preview showing the Mermaid audit flowchart](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image16.png)

---

### Step 4 — Full Application Modernization

**Goal:** Migrate the full application to Spring Boot 3.x + React 18 + PostgreSQL.

This step uses the **Modernization Architect** custom mode backed by 6 rule files. Spend a few minutes exploring the setup before running it.

**Step A — Explore the Mode**

1. Click the gear icon (⚙) at the bottom-left of Bob IDE → select **Modes**

![Bob IDE Settings menu showing the Modes option](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image17.png)

2. Find **Modernization Architect** in the list and open it

![Modes list with Modernization Architect visible](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image18.png)

3. Read the Role Definition — this is the text that tells Bob how to think and behave during migration

![Role Definition text of the Modernization Architect custom mode](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image19.png)

**Step B — Explore the Rules**

4. In the Explorer panel, expand `.bob/rules-modernization-architect/` — you will see 6 XML files

![Explorer panel showing .bob/rules-modernization-architect/ expanded with all 6 XML files listed](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image20.png)

5. Open one of them — you will see how architecture decisions and migration constraints are written in plain XML

![One of the rule XML files open in the editor showing its structure](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image21.png)

**Step C — Run the Migration**

6. Click the mode selector at the bottom-right and select **Modernization Architect**

![Mode selector at bottom-right showing Modernization Architect selected](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image22.png)

7. Type this prompt and press **Enter**:

   > *"Modernize the legacy-netbanking application. Backend: Spring Boot 3.x, Java 17, PostgreSQL, JWT authentication. Frontend: React 18 SPA."*

8. Bob produces a Todo list covering the full migration. Approve each task as it runs

![Bob's Todo list for the full modernization showing all migration phases](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image23.png)

9. When complete, the Explorer panel shows a new `modern-netbanking/` folder with the fully migrated project

![Explorer panel showing the new modern-netbanking/ folder structure after migration](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image24.png)

> If Bob stops mid-way, type: *"Complete remaining tasks from the todo list"*

---

### Step 5 — OpenShift Deployment Artifacts *(Optional)*

Still in **Modernization Architect** mode, type:

> *"I need to deploy this on OpenShift. Create the required artifacts and scripts."*

Bob generates a `Dockerfile`, Kubernetes YAML manifests, and a `deploy.sh` script.

![Explorer showing the generated Dockerfile, Kubernetes manifests, and deploy.sh](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image25.png)

![Generated Dockerfile or Kubernetes manifest open in the editor](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image26.png)

---

## Key Modernization Achievements

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

## Troubleshooting

### Maven not installed
Switch to **Agent** mode and type: *"Install Maven"*

![Agent mode chat with Install Maven typed](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image27.png)

### Build fails during migration
Click **Fix it** next to the error. Bob reads the failure and applies a targeted fix automatically.

### Bob stops before finishing
Type: *"Complete remaining tasks from the todo list"*
