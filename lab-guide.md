# IBM Bob: Java Modernization Lab
## DevSparks Hyderabad 2026

> **Duration:** 60 minutes · **Hands-on · IBM Bob IDE**

---

## Contents
- [About this Lab](#about-this-lab)
- [Overview](#overview)
- [Pre-requisites](#pre-requisites)
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

### How this lab is structured — the Mode + Skill + Rules system

Before you start, take a minute to understand what is already in this repo and why:

| What | What it does | Where it lives |
|---|---|---|
| **Mode** | Gives Bob the persona of a Modernization Architect — with the right tools, permissions, and expertise | `.bob/custom_modes.yaml` |
| **Skill** | Tells Bob exactly how to upgrade Java — step by step, no ambiguity | `.bob/skills/java-upgrade/SKILL.md` |
| **Rules** | 6 XML files that encode migration standards and architecture constraints Bob must follow | `.bob/rules-modernization-architect/` |

You will use each of these during the lab. By the end, you will understand how to build this kind of system yourself for any use case.

---

## Pre-requisites

### IBM Bob IDE
Install IBM Bob from: `https://bob.ibm.com/docs/ide/getting-started/install`

> **Make sure you are logged in to Bob IDE before starting the lab.**

### Install PlantUML Plugin

You need this to preview the diagrams Bob generates in Step 2.

1. Click the **Extensions** icon in the Bob IDE sidebar
2. Search for **PlantUML**
3. Install **PlantUML Markdown Preview**

![PlantUML Markdown Preview extension in the Extensions panel](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image1.png)

---

## Hands-on Lab Steps

> **Before you begin:** Make sure you are logged in to IBM Bob IDE. Open Bob and confirm your account is active — you should see your name or profile icon at the bottom-left of the IDE.

> **Note:** Almost everything in this lab is done through prompts. You type a sentence or a command in Bob — Bob does the rest. The only exception is Step 3, where Maven and Java 17 must be installed on your machine so Bob can run the build. Instructions for that are included at the start of Step 3.

---

### Step 1 — Import Project into Bob Workspace

**Goal:** Download the lab project and open it in Bob.

1. Go to the lab repo and download the ZIP:
   ```
   https://github.com/anuj34822/java-modernization-lab
   ```
   Click **Code → Download ZIP**. Extract it — you will get a folder named `java-modernization-lab-main`.

![GitHub repo showing Code → Download ZIP](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image2.png)

2. Open **IBM Bob IDE**. You will see the Welcome screen below. Click **Open...** from the Start menu.

![Bob IDE Welcome screen](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image3.png)

3. Navigate to the extracted `java-modernization-lab-main` folder and click **Open**.

![Folder picker showing java-modernization-lab-main selected](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image4.png)

4. Bob may show a **Restricted Mode** banner at the top. Click **Manage** → **Trust** to enable all features.

![Restricted Mode / Workspace Trust dialog — click Trust](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image5.png)

5. Confirm the Explorer panel on the left shows:

   ```
   java-modernization-lab-main/
   ├── .bob/
   ├── images/
   ├── legacy-netbanking/
   └── lab-guide.md
   ```

![Explorer panel showing the project structure with .bob/ highlighted](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image6.png)

   > **Notice the `.bob/` folder** — this contains the custom Mode, Skill, and all 6 Rule files that power this lab. You get everything pre-configured just by downloading. Nothing to set up manually.

---

### Step 2 — Reverse Engineering

**Goal:** Let Bob read the legacy codebase and produce full documentation and diagrams — without you writing a single line.

1. Click the mode selector at the bottom-right of Bob IDE and select **Ask**

![Mode selector at bottom-right with Ask highlighted](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image7.png)

2. Type this prompt in the chat and click the **Enhance Prompt** icon (✨) before hitting Enter:

   > *"Help me understand this legacy-netbanking application. Save the generated documentation and diagrams in legacy-netbanking-documentation directory. Make sure to generate required PlantUML diagrams like sequence diagram along with mermaid architecture and ER diagram etc of current implementation."*

![Chat input showing the prompt typed with the Enhance Prompt icon highlighted](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image8.png)

3. Bob rewrites your prompt into a more detailed version. Review it and press **Enter**

![Bob's enhanced version of the prompt shown in the chat](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image9.png)

4. Bob creates a task list and starts working. Click **Approve** for each task as it appears

![Bob's task list running the reverse engineering with Approve buttons](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image10.png)

5. When Bob finishes, right-click the generated `.md` file in Explorer and select **Open Preview**

![Right-click context menu on the generated .md file showing Open Preview](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image11.png)

![Generated documentation rendered in the Preview panel](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image12.png)

6. Right-click any `.puml` file and select **Preview PlantUML File** to see the diagrams

![Right-click context menu on a .puml file showing Preview PlantUML File](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image13.png)

---

### Step 3 — Java Version Upgrade

**Goal:** Upgrade the project from Java 8 to Java 17 using the `/java-upgrade` Bob skill.

> **This is where the Skill comes in.** The **Skill** (`/java-upgrade`) is defined in `.bob/skills/java-upgrade/SKILL.md` — the file you can see in the Explorer right now. When you type `/java-upgrade`, Bob loads that playbook and follows it exactly, step by step, without you having to remember or direct anything.

#### Before you start this step — install Java 17 and Maven

The `/java-upgrade` skill runs real Maven commands behind the scenes (`mvn rewrite:run`, `mvn clean package`). Bob handles everything else through prompts, but your machine needs these two tools installed.

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

**SDKMAN** *(macOS / Linux — alternative way to install both)*
```sh
curl -s "https://get.sdkman.io" | bash
# then:
sdk install java 17-tem
sdk install maven
```

Once installed, verify both are available:
```sh
java -version
mvn -version
```

Both commands should return version numbers before you proceed.

---

Before you run anything, open `.bob/skills/java-upgrade/SKILL.md` and read it. You will see every step Bob is about to follow: update `pom.xml`, add the OpenRewrite plugin, run `mvn rewrite:run`, fix dependency conflicts, validate the build, write an audit report. Reading it now helps you understand what Bob is doing and why — and shows you how a Skill is written so you can build your own.

1. In the Bob chat, type `/java-upgrade` and press **Enter**

![Bob chat showing /java-upgrade typed and Bob activating the skill](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image14.png)

2. Bob asks you to confirm two things:
   - Project path → type `legacy-netbanking`
   - Target Java version → type `17`

![Bob asking to confirm the project path and target Java version](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image15.png)

3. Bob works through the upgrade automatically. Approve each task as it appears:
   - Updates `pom.xml` compiler settings to Java 17
   - Adds the OpenRewrite Maven plugin
   - Runs `mvn rewrite:run` to apply the UpgradeToJava17 recipe
   - Fixes any dependency conflicts
   - Runs `mvn clean package` to validate the build

![Bob's task list for the java-upgrade skill showing pom.xml edit, mvn rewrite:run, and build validation steps](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image16.png)

4. When complete, Bob creates `legacy-netbanking/java-upgrade-report.md`. Open it in Preview to see the Mermaid flowchart of every change applied

![java-upgrade-report.md open in Preview showing the Mermaid audit flowchart](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image17.png)

---

### Step 4 — Full Application Modernization

**Goal:** Migrate the full application to Spring Boot 3.x + React 18 + PostgreSQL.

> **This is where the Mode and Rules come in together.** The **Mode** (Modernization Architect) gives Bob the right persona and expertise for migration. The **Rules** (6 XML files in `.bob/rules-modernization-architect/`) tell Bob what architecture decisions to make and what constraints to follow. Both are already in the repo — you just switch into the Mode and Bob picks up everything automatically.

This step uses the **Modernization Architect** custom mode backed by 6 rule files. Spend a few minutes exploring the setup before running it — this is the part where you see how the system is built.

**Step A — Explore the Mode**

1. Click the gear icon (⚙) at the bottom-left of Bob IDE → select **Modes**

![Bob IDE Settings menu showing the Modes option](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image18.png)

2. Find **Modernization Architect** in the list and open it

![Modes list with Modernization Architect visible](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image19.png)

3. Read the Role Definition — this is the text that tells Bob how to think and behave during migration

![Role Definition text of the Modernization Architect custom mode](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image20.png)

**Step B — Explore the Rules**

4. In the Explorer panel, expand `.bob/rules-modernization-architect/` — you will see 6 XML files

![Explorer panel showing .bob/rules-modernization-architect/ expanded with all 6 XML files listed](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image21.png)

5. Open one of them — you will see how architecture decisions and migration constraints are written in plain XML. This is what makes Bob's output consistent and governed — not just smart, but bounded by your standards

![One of the rule XML files open in the editor showing its structure](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image22.png)

**Step C — Run the Migration**

6. Click the mode selector at the bottom-right and select **Modernization Architect**

![Mode selector at bottom-right showing Modernization Architect selected](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image23.png)

7. Type this prompt and press **Enter**:

   > *"Modernize the legacy-netbanking application. Backend: Spring Boot 3.x, Java 17, PostgreSQL, JWT authentication. Frontend: React 18 SPA."*

8. Bob produces a Todo list covering the full migration. Approve each task as it runs

![Bob's Todo list for the full modernization showing all migration phases](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image24.png)

9. When complete, the Explorer panel shows a new `modern-netbanking/` folder with the fully migrated project

![Explorer panel showing the new modern-netbanking/ folder structure after migration](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image25.png)

> If Bob stops mid-way, type: *"Complete remaining tasks from the todo list"*

---

### Step 5 — OpenShift Deployment Artifacts *(Optional)*

Still in **Modernization Architect** mode, type:

> *"I need to deploy this on OpenShift. Create the required artifacts and scripts."*

Bob generates a `Dockerfile`, Kubernetes YAML manifests, and a `deploy.sh` script.

![Explorer showing the generated Dockerfile, Kubernetes manifests, and deploy.sh](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image26.png)

![Generated Dockerfile or Kubernetes manifest open in the editor](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image27.png)

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

![Agent mode chat with Install Maven typed](https://raw.githubusercontent.com/anuj34822/java-modernization-lab/main/images/image28.png)

### Build fails during migration
Click **Fix it** next to the error. Bob reads the failure and applies a targeted fix automatically.

### Bob stops before finishing
Type: *"Complete remaining tasks from the todo list"*
