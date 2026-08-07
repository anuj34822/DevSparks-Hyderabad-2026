# IBM Bob: Java Modernization Lab
## DevSparks Hyderabad 2026

---

## Contents
- [About this Lab](#about-this-lab)
- [Overview](#overview)
- [Pre-requisites](#pre-requisites)
- [Hands-on Lab Steps](#hands-on-lab-steps)
  - [Step 1 — Import Project into Bob Workspace](#step-1--import-project-into-bob-workspace)
  - [Step 2 — Reverse Engineering in Agent Mode](#step-2--reverse-engineering-in-agent-mode)
  - [Step 3 — Explore and Run the Skill](#step-3--explore-and-run-the-skill)
  - [Step 4 — Explore the Custom Mode and One Rule](#step-4--explore-the-custom-mode-and-one-rule)
  - [Step 5 — Run Full Modernization with the Custom Mode](#step-5--run-full-modernization-with-the-custom-mode)
- [What Participants Should Learn](#what-participants-should-learn)
- [Why Mode, Skill, and Rules — Not Just a Prompt?](#why-mode-skill-and-rules--not-just-a-prompt)
- [Troubleshooting](#troubleshooting)

---

## About this Lab

This lab shows you how to use IBM Bob to modernize a legacy Java application by building and using a **reusable Bob system** rather than relying on a premium Java modernization package.

You will learn how to create and use three Bob building blocks together:

- **A custom Mode** — gives Bob a specific persona and expertise. Bob behaves like a Modernization Architect every time you use it.
- **A Skill** — a step-by-step playbook that Bob follows when you call `/java-upgrade`. No manual steps, no guesswork.
- **Rules** — XML files that encode architecture standards and constraints into Bob's reasoning.

These three pieces work together as one system. Once built, any developer on your team can clone this repo, open it in Bob, and get the same consistent results.

We use that system on a **legacy Struts 1.3 NetBanking application** to:

- Reverse engineer the codebase and generate documentation and diagrams
- Upgrade Java from 1.8 to 17 using the `/java-upgrade` skill
- Run a governed modernization using a custom mode plus rules
- Generate OpenShift deployment artifacts

> **Important:** The purpose of this lab is not to teach a premium modernization workflow. The purpose is to teach how Bob can be extended with a custom mode, a skill, and rules, using Java modernization as the scenario.

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

### The Three Bob Assets in This Repo

Before you start, identify the three Bob assets used throughout the lab:

| Asset | What it teaches | Where it lives | Learn more |
|---|---|---|---|
| **Custom Mode** | How to give Bob a reusable persona and operating model | `.bob/custom_modes.yaml` | [Learn more about custom modes](https://bob.ibm.com/docs/ide/configuration/custom-modes) |
| **Skill** | How to encode a repeatable workflow that can be invoked with a slash command | `.bob/skills/java-upgrade/SKILL.md` | [Learn more about skills](https://bob.ibm.com/docs/ide/features/skills) |
| **Rule** | How to constrain Bob with explicit modernization standards | `.bob/rules-modernization-architect/` | [Learn more about rules](https://bob.ibm.com/docs/ide/configuration/rules) |

> For the workshop, keep your attention on **one custom mode, one skill, and one representative rule**. The rest of the files are supporting implementation details.

---

## Pre-requisites

> ⚠️ **Install Java 17 and Maven before starting Step 3.** Steps 1, 2, 4, and 5 work on any machine with Bob IDE. Step 3 (Java upgrade) requires Java 17 and Maven — install them using the instructions below before you reach that step. If you do not have them, Bob can install them for you but it will take extra time.

### 1. IBM Bob IDE
Install IBM Bob from: `https://bob.ibm.com/docs/ide/getting-started/install`

> **Make sure you are logged in to Bob IDE before starting the lab.**

### 2. Java 17

Step 3 upgrades the application source code from Java 1.8 to Java 17 using OpenRewrite recipes. Java 17 must be installed on your development machine (your laptop) so Maven can compile and verify the upgraded code. In a real project this would also be installed on the application server — for this lab your laptop is the development system.

**Windows**
```
winget install --id Microsoft.OpenJDK.17
```
**macOS**
```
brew install openjdk@17
```
After install, Homebrew will warn that openjdk@17 is "keg-only". Add it to your PATH so the terminal can find it:
```
echo 'export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```
**Linux (Ubuntu/Debian)**
```
sudo apt update && sudo apt install openjdk-17-jdk
```
**Linux (RHEL)**
```
sudo dnf install java-17-openjdk-devel
```

Verify:
```
java -version
```
Expected: `openjdk version "17.x.x"`

### 3. Maven

**Windows**
```
winget install --id Apache.Maven
```
**macOS**
```
brew install maven
```
**Linux (Ubuntu/Debian)**
```
sudo apt update && sudo apt install maven
```
**Linux (RHEL)**
```
sudo dnf install maven
```

Verify:
```
mvn -version
```
Expected: `Apache Maven 3.x.x`

> **No admin rights on your Windows laptop?** Use the Bob fallback — see [Maven not installed](#maven-not-installed) in Troubleshooting.

### 4. PlantUML Plugin

You need this to preview the diagrams Bob generates in Step 2.

1. Click the **Extensions** icon in the Bob IDE sidebar
2. Search for **PlantUML**
3. Install **PlantUML Markdown Preview**

![PlantUML Markdown Preview extension in the Extensions panel](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image1.png)

---

## Hands-on Lab Steps

> **Before you begin:** Make sure you are logged in to IBM Bob IDE. Open Bob and confirm your account is active — you should see your name or profile icon at the bottom-left of the IDE.

> **Lab lens:** At every step, ask yourself which Bob capability you are learning: **Ask mode**, **custom mode**, **skill**, or **rule**. The lab is successful only if participants understand those Bob features, not just the modernization output.

---

### Step 1 — Import Project into Bob Workspace

**Goal:** Download the lab project, open it in Bob, and locate the three Bob assets used in this workshop.

1. Go to the lab repo and download the ZIP:
   ```
   https://github.com/anuj34822/DevSparks-Hyderabad-2026
   ```
   Click **Code → Download ZIP**. Extract it — you will get a folder named `DevSparks-Hyderabad-2026-main`.

![GitHub repo showing Code → Download ZIP](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image2.png)

2. Open **IBM Bob IDE**. You will see the Welcome screen below. Click **Open...** from the Start menu.

![Bob IDE Welcome screen](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image3.png)

3. Navigate to the extracted `DevSparks-Hyderabad-2026-main` folder and click **Open**.

![Folder picker showing DevSparks-Hyderabad-2026-main selected](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image4.png)

4. Bob may show a **Restricted Mode** banner at the top. Click **Manage** → **Trust** to enable all features.

![Restricted Mode / Workspace Trust dialog — click Trust](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image5.png)

5. Confirm the Explorer panel on the left shows:

   ```
   DevSparks-Hyderabad-2026-main/
   ├── .bob/
   ├── images/
   ├── legacy-netbanking/
   └── lab-guide.md
   ```

![Explorer panel showing the project structure with .bob/ highlighted](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image6.png)

6. Expand `.bob/` and identify these three things before proceeding:
   - `.bob/custom_modes.yaml`
   - `.bob/skills/java-upgrade/SKILL.md`
   - one XML rule file under `.bob/rules-modernization-architect/`

> **What to understand here:** this lab is built around those three Bob assets. The legacy application is the target system; the real learning objective is how Bob is extended.

> **Validation checkpoint:** At this stage, do not inspect every file in `.bob/`. Just confirm that the custom mode, the skill, and one representative rule are present and visible in the Explorer.

---

### Step 2 — Reverse Engineering in Agent Mode

**Goal:** Use Bob in **Agent mode** to read the entire legacy codebase and generate a documentation package. Bob will read all source files, build a plan, and write every document automatically.

1. Make sure the mode selector at the bottom of the Bob chat panel shows **Agent**. Click it and select **Agent** if it is not already set.

   ![Agent mode selected in the Bob mode selector dropdown](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image7.png)

2. Enter this **planning prompt** and press **Enter**:

   > *"Read the legacy-netbanking application (struts-config.xml, web.xml, pom.xml, all Java files under src/main/java, and schema.sql) and create a numbered documentation plan. For each document, list the file name and exactly what it will contain. Save the plan as legacy-netbanking-documentation/PLAN.md."*

   ![Planning prompt typed in Agent mode chat ready to send](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image8.png)

3. Bob reads the entire codebase. You will see two exploration steps in the chat — first `"Explored 2 folders, 4 files"`, then `"Explored 24 files"`. When it finishes, Bob displays the plan as a numbered table in the chat **and** writes `legacy-netbanking-documentation/PLAN.md` to disk. The folder appears in the Explorer on the left.

   ![Bob's chat showing the two Explored steps and PLAN.md written, with legacy-netbanking-documentation visible in Explorer](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image10.png)

4. Click `PLAN.md` in the Explorer to open it. Confirm it references real class names like `LoginAction`, `TransferAction`, `UserDAO`, `DBUtil` and real table names `USERS`, `ACCOUNTS`, `TRANSACTIONS`. This is your proof that Bob read the actual code — not a generic template.

   ![PLAN.md open in the editor showing numbered plan with real class and table names](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image11.png)

5. Now send this **execution prompt**:

   > *"Execute the plan in legacy-netbanking-documentation/PLAN.md and generate all the documentation files. Save everything to legacy-netbanking-documentation/. Include: (1) architecture.md — Mermaid system architecture diagram showing all layers and the Struts request lifecycle; (2) er-diagram.md — Mermaid ER diagram with all 3 tables, columns, types, FKs, and the 3 indexes from schema.sql; (3) class-diagram.md — Mermaid class diagram for all 5 packages (actions, dao, forms, servlets, util) and their dependencies; (4) request-flow.md — Mermaid flow diagram with all action mappings from struts-config.xml and session state; (5) security-analysis.md — vulnerabilities catalogued by CVSS severity with file and method references; (6) api-reference.md — all routes with HTTP method, handler class, ActionForm, and auth requirement; (7) sequences.puml — PlantUML sequence diagram with 4 flows: Login, Fund Transfer including failure path, Admin Create User, DB Initialization via InitServlet. Reference actual class and method names from the codebase in every file."*

   ![Execution prompt typed in Agent mode chat ready to send](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image12.png)

6. Bob builds a todo list and writes each file one by one. If an **Approve** button appears for each task, click it to proceed. If no Approve button appears, Bob will execute the full plan automatically — this is normal and depends on your Bob settings. Either way, watch the Explorer — files appear in `legacy-netbanking-documentation/` as Bob completes each one.

   ![Bob's todo list showing 12 tasks with all documentation files appearing in Explorer](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image13.png)

7. When Bob finishes, right-click `er-diagram.md` in `legacy-netbanking-documentation/` and select **Open Preview** to see the ER diagram rendered with all three tables.

   ![er-diagram.md open in Preview showing USERS, ACCOUNTS, TRANSACTIONS ER diagram](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image14.png)

8. Right-click `sequences.puml` and select **Preview PlantUML File** to see the sequence diagrams rendered.

   ![sequences.puml rendered in PlantUML Preview showing sequence diagrams](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image9.png)

> **What to understand here:** Agent mode is Bob's full-capability mode — it reads, plans, and writes. The two-prompt pattern (plan first, then execute) is a reusable technique for any large generation task: plan first so you can review what Bob will do, then execute so you can watch and approve each file.

> **Validation checkpoint:** `er-diagram.md` in Preview should show `USERS`, `ACCOUNTS`, and `TRANSACTIONS` with their FK relationships — exactly as you see in the screenshot above. Count the files in `legacy-netbanking-documentation/` — there should be at least 7 files including `PLAN.md`.

---

### Step 3 — Explore and Run the Skill

**Goal:** This step teaches you what a **Bob Skill** is and how it works. A skill is a plain markdown file (`.bob/skills/java-upgrade/SKILL.md`) that encodes a step-by-step playbook. When you type `/java-upgrade` in the Bob chat, Bob reads that file and follows the instructions inside it — no magic, no hidden code. The `/` prefix is how you invoke any skill in Bob. This is how we are using the skill.

> **Before this step:** Make sure Java 17 and Maven are installed. See [Pre-requisites](#pre-requisites) for install commands for your OS. Verify with `java -version` and `mvn -version`.

1. In the Explorer, open `.bob/skills/java-upgrade/SKILL.md` and read it **before** running anything.

2. Identify what the skill instructs Bob to do:
   - Read the project and identify the current Java version in `pom.xml`
   - Update `pom.xml` compiler source/target to Java 17
   - Add the OpenRewrite plugin and run `mvn rewrite:run`
   - Fix any dependency conflicts
   - Validate the build with `mvn clean package`
   - Write an audit report as `java-upgrade-report.md`

   > **This is the key insight:** the skill is just a markdown file you can read, edit, and share. The slash command `/java-upgrade` is not a black box — it is backed by this file.

3. In the Bob chat, type `/java-upgrade` and press **Enter**. Bob shows the skill description as an autocomplete suggestion — you can see it says *"Use when the user wants to upgrade a legacy Java application's runtime version"*. That description comes directly from `SKILL.md`.

   ![/java-upgrade typed in Bob chat showing skill autocomplete](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image15.png)

4. Bob activates the skill and works through the upgrade. If an **Approve** button appears, click it. If not, Bob runs through all steps automatically — this is normal.

5. When complete, Bob creates `legacy-netbanking/java-upgrade-report.md`. Open it in Preview to see the Mermaid flowchart of every change applied.

   ![java-upgrade-report.md open in Preview showing the upgrade flowchart](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image17.png)

> **What to understand here:** `/java-upgrade` is not a built-in Bob command — it is the skill you saw in `.bob/skills/java-upgrade/SKILL.md`. Any developer on your team can clone this repo, open it in Bob, and get the same repeatable Java upgrade by running the same slash command. That is the power of a skill.

> **Validation checkpoint:** After the run, open `.bob/skills/java-upgrade/SKILL.md` and `java-upgrade-report.md` side by side. The report should reflect exactly the steps the skill instructed Bob to follow.

---

### Step 4 — Explore the Custom Mode and One Rule

**Goal:** Understand how a custom mode and a rule shape Bob's behavior before running the larger modernization.

1. Click the gear icon at the bottom-left of Bob IDE and select **Bob Settings** → click **Modes** in the left panel.

2. You will see the full list of modes. Find **Modernization Architect** — notice it shows **Workspace** scope, meaning it came from this repo's `.bob/custom_modes.yaml`, not from a global install.

   ![Bob Settings Modes panel showing Modernization Architect with Workspace scope](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image18.png)

3. Click **Modernization Architect** to open it. Read the **Role Definition** and identify what kind of persona and behavior the mode gives Bob.

   ![Modernization Architect Role Definition text](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image19.png)

   > **Notice the `+` button** at the top-right of the Modes panel — that is how you would create your own custom mode from scratch. The Modernization Architect mode in this repo was built exactly that way, then saved to `.bob/custom_modes.yaml` so the whole team can use it.

   ![Bob Settings Modes panel showing the + button to create a new custom mode](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image28.png)

4. In the Explorer panel, expand `.bob/rules-modernization-architect/` — you will see the XML rule files that govern the modernization.

   ![.bob/rules-modernization-architect/ expanded in Explorer showing XML rule files](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image20.png)

5. Open **one representative XML rule file** and read it carefully.

6. Ask yourself these two questions before moving on:
   - How is the **custom mode** changing Bob's role?
   - How is the **rule** constraining Bob's decisions?

> **What to understand here:** the custom mode defines **who Bob is**, while the rule defines **what Bob must obey**. You do not explicitly invoke the rules — when you switch to the Modernization Architect mode in Step 5, Bob automatically picks up and applies every rule in `.bob/rules-modernization-architect/`. That is the point: the rules are always on, silently governing every decision Bob makes.

> **Validation checkpoint:** Before moving to Step 5, participants should be able to explain the difference between the custom mode and the rule in their own words.

---

### Step 5 — Run Full Modernization with the Custom Mode

**Goal:** Use the **Modernization Architect** mode and the rules to drive a governed modernization.

1. Click the mode selector at the bottom of the Bob chat panel and select **Modernization Architect**.

   ![Modernization Architect selected in the Bob mode selector](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image22.png)

2. Type this prompt and press **Enter**:

   > *"Modernize the legacy-netbanking application. Backend: Spring Boot 3.x, Java 17, PostgreSQL, JWT authentication. Frontend: React 18 SPA."*

3. Bob produces a Todo list covering the full migration. If an **Approve** button appears for each task, click it. If not, Bob runs through the migration automatically — this can take 10–20 minutes. Do not close Bob or start a new task.

   ![Bob's modernization todo list in Modernization Architect mode](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image23.png)

4. Bob works through the full migration — you will see it writing files, running commands, and asking for approvals on tool executions like Helm lint. This governed behavior is the rules in action.

   ![Bob executing modernization tasks — approving tool execution](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image24.png)

5. When complete, the Explorer shows a new `abc-bank/` folder containing the fully modernized application:
   - `abc-bank/backend/` — Spring Boot 3.x, JPA, JWT, PostgreSQL, `@Transactional` transfer service
   - `abc-bank/frontend/` — React 18 SPA with JWT auth, Axios, React Router 6
   - `abc-bank/helm/abc-bank/` — Helm chart with 10 templates for OpenShift deployment

   ![Bob approving Helm lint during governed modernization](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image25.png)

> If Bob stops mid-way, type: *"Complete remaining tasks from the todo list"*

> **What to understand here:** you never typed a rule name or invoked a rule command. The rules were loaded automatically when you selected the Modernization Architect mode. Every output Bob produced — the Spring Boot structure, the JWT implementation, the Helm chart, the `@Transactional` transfer service — reflects those rules. That is how rules work in Bob: you set them once in the mode, and Bob obeys them without being asked.

---

## What Participants Should Learn

By the end of this lab, participants should be able to explain:

1. how **Agent mode** combines reading, planning, and writing in one flow — and how the two-prompt pattern (plan first, then execute) makes any large generation task visible and controllable
2. how a **Skill** turns a repeatable workflow into a reusable slash command
3. how a **custom Mode** changes Bob's persona and operating style
4. how a **Rule** constrains Bob's decisions using explicit standards
5. how those three building blocks can replace a premium workflow with a transparent, teachable Bob-based system

---

## Why Mode, Skill, and Rules — Not Just a Prompt?

![How a Reusable IBM Bob System Works — Mode defines who Bob is, Skill defines what Bob does, Rules define how Bob does it correctly](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image29.png)

You could modernize a Java application by typing a long prompt into Bob every time. But you would get a different answer every time — different technology choices, different structure, different quality. The next developer on your team would get something else entirely.

That is the problem Mode, Skill, and Rules solve:

- **Without them:** User → Prompt → Different answer every time
- **With them:** User → Reusable Bob System → Consistent, governed output every time

Each construct has a specific job:

| Construct | The question it answers | Why not just put it in the prompt? |
|---|---|---|
| **Mode** | Who should Bob act as? | A prompt is forgotten after one conversation. A mode is always on. |
| **Skill** | What process should Bob follow? | A prompt has to be retyped and remembered. A skill is a `/command` anyone can run. |
| **Rules** | What constraints must Bob obey? | A prompt can be ignored or overridden by a longer conversation. A rule file is always loaded and always enforced. |

The real value is not the individual pieces — it is that once you build this system, **any developer on your team can clone the repo, open it in Bob, and get the same result**. The knowledge is in the repo, not in someone's head or chat history.

---

## Troubleshooting

### Maven not installed
If you arrive without Maven, Bob can install it for you. Switch to **Agent** mode and type:

- **Windows:** *"Install Maven on Windows using winget"*
- **macOS:** *"Install Maven on macOS using Homebrew"*
- **Linux:** *"Install Maven on Ubuntu using apt"*

Bob will run the install command via the terminal. Once complete, restart the Bob chat and re-run the `/java-upgrade` skill.

### Java not installed
Switch to **Agent** mode and type: *"Install OpenJDK 17"* — Bob will detect your OS and run the correct install command.

### Build fails during migration
Click **Fix it** next to the error. Bob reads the failure and applies a targeted fix automatically.

### Bob stops before finishing
Type: *"Complete remaining tasks from the todo list"*
