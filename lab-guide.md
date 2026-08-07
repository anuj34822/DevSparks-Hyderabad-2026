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
  - [Step 2 — Reverse Engineering in Ask Mode](#step-2--reverse-engineering-in-ask-mode)
  - [Step 3 — Explore and Run the Skill](#step-3--explore-and-run-the-skill)
  - [Step 4 — Explore the Custom Mode and One Rule](#step-4--explore-the-custom-mode-and-one-rule)
  - [Step 5 — Run Full Modernization with the Custom Mode](#step-5--run-full-modernization-with-the-custom-mode)
  - [Step 6 — OpenShift Deployment Artifacts (Optional)](#step-6--openshift-deployment-artifacts-optional)
- [What Participants Should Learn](#what-participants-should-learn)
- [Screenshot Checklist for Dry Run](#screenshot-checklist-for-dry-run)
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
| **Custom Mode** | How to give Bob a reusable persona and operating model | `.bob/custom_modes.yaml` | Learn more about custom modes |
| **Skill** | How to encode a repeatable workflow that can be invoked with a slash command | `.bob/skills/java-upgrade/SKILL.md` | Learn more about skills |
| **Rule** | How to constrain Bob with explicit modernization standards | `.bob/rules-modernization-architect/` | Learn more about rules |

> For the workshop, keep your attention on **one custom mode, one skill, and one representative rule**. The rest of the files are supporting implementation details.

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

### Step 2 — Reverse Engineering in Ask Mode

**Goal:** Use Bob's built-in **Ask** mode to understand the legacy application before using the custom assets.

1. Click the mode selector at the bottom of the Bob chat panel and select **Ask**.

![Mode selector showing Ask selected](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image7.png)

2. Enter this prompt, click the **Enhance Prompt** icon, review the enhanced prompt, and then press **Enter**:

   > *"Analyze the legacy-netbanking Java EE application end-to-end and generate a comprehensive documentation package saved to legacy-netbanking-documentation/. The package must include: (1) a Mermaid system architecture diagram (layers + deployment topology), (2) a Mermaid ER diagram with all tables, columns, types, FKs, and indexes, (3) a Mermaid class diagram covering all 5 Java packages and their relationships, (4) a Mermaid request-flow diagram with the full Struts URL routing table and session state machine, (5) a Mermaid data-flow diagram (Level 0 + Level 1) calling out critical race conditions, (6) a Mermaid modernization roadmap showing legacy→modern technology mapping and a target architecture, (7) a PlantUML sequence diagram file (.puml) with at minimum 6 flows: Login, Account Summary, Fund Transfer (including failure paths), Transaction History, Admin Create User, and DB Initialization, (8) a PlantUML component diagram (.puml) annotating architectural anti-patterns, (9) a Security Analysis document cataloguing all vulnerabilities by CVSS severity (Critical/High/Medium/Low) with file references and remediation guidance, and (10) a full API/URL reference with HTTP method, handler, auth requirements, and ActionForm field mappings. Each document must reference the actual source files and concrete class/method names found by reading the codebase — no assumptions."*

![Chat input showing the extended reverse-engineering prompt typed and ready to enhance](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image8.png)

3. Bob automatically expands your prompt into a detailed task plan. Review it and press **Enter** to confirm.

![Bob's expanded prompt ready to run](https://raw.githubusercontent.com/anuj34822/DevSparks-Hyderabad-2026/main/images/image9.png)

4. Bob creates a task list and starts working. Click **Approve** for each task as it appears.

<!-- image10: Bob's task list with Approve buttons -->

5. When Bob finishes, right-click the generated `.md` file in Explorer and select **Open Preview**.

<!-- image11: right-click .md file showing Open Preview -->

6. Right-click any `.puml` file and select **Preview PlantUML File** to see the diagrams.

<!-- image12: documentation rendered in Preview panel -->

> **What to understand here:** Ask mode is the baseline Bob capability. It helps participants understand the application before they use a skill or a custom mode.

> **Validation checkpoint:** Confirm that `legacy-netbanking-documentation/` was created, that it contains generated documentation files, that at least one `.puml` file is present, and that the generated content references real source files and class names from the codebase.

---

### Step 3 — Explore and Run the Skill

**Goal:** Understand how a Bob skill works, then use the `/java-upgrade` skill to upgrade the application from Java 8 to Java 17.

1. In the Explorer, open `.bob/skills/java-upgrade/SKILL.md` and read it before running anything.

2. Look for the key steps encoded in the skill:
   - update `pom.xml`
   - add the OpenRewrite plugin
   - run `mvn rewrite:run`
   - fix dependency conflicts
   - validate with `mvn clean package`
   - write an audit report

3. Install Java 17 and Maven if they are not already available.

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

4. Verify both tools are available before continuing:
```sh
java -version
mvn -version
```

5. In the Bob chat, type `/java-upgrade` and press **Enter**.

<!-- image13: /java-upgrade typed in chat -->

6. Bob asks you to confirm two things:
   - Project path → type `legacy-netbanking`
   - Target Java version → type `17`

<!-- image14: Bob asking path + version -->

7. Bob works through the upgrade automatically. Approve each task as it appears.

<!-- image15: Bob's upgrade task list -->

8. When complete, Bob creates `legacy-netbanking/java-upgrade-report.md`. Open it in Preview to see the Mermaid flowchart of every change applied.

<!-- image16: java-upgrade-report.md in Preview -->

> **What to understand here:** the skill is a reusable playbook. Participants should see that the slash command is not magic — it is backed by an explicit file they can read and customize.

> **Validation checkpoint:** Before invoking `/java-upgrade`, make sure participants understand that the skill file defines the workflow. After the run, confirm that Bob produced the upgrade report and that the participant can connect the slash command to the underlying `SKILL.md` file.

---

### Step 4 — Explore the Custom Mode and One Rule

**Goal:** Understand how a custom mode and a rule shape Bob's behavior before running the larger modernization.

1. Click the gear icon at the bottom-left of Bob IDE and open **Modes**.

<!-- image17: Settings menu showing Modes option -->

2. Find **Modernization Architect** and open it.

<!-- image18: Modes list with Modernization Architect visible -->

3. Read the **Role Definition** and identify what kind of persona and behavior the mode gives Bob.

<!-- image19: Role Definition text -->

4. In the Explorer panel, expand `.bob/rules-modernization-architect/`.

<!-- image20: rules folder expanded with XML files -->

5. Open **one representative XML rule file** and read it carefully.

<!-- image21: one XML rule file open in editor -->

6. Ask participants these two questions before moving on:
   - How is the **custom mode** changing Bob's role?
   - How is the **rule** constraining Bob's decisions?

> **What to understand here:** the custom mode defines **who Bob is**, while the rule defines **what Bob must obey**. This is the core teaching point of the lab.

> **Validation checkpoint:** Before moving to Step 5, participants should be able to explain the difference between the custom mode and the rule in their own words.

---

### Step 5 — Run Full Modernization with the Custom Mode

**Goal:** Use the **Modernization Architect** mode and the rules to drive a governed modernization.

1. Click the mode selector at the bottom of the Bob chat panel and select **Modernization Architect**.

<!-- image22: mode selector showing Modernization Architect selected -->

2. Type this prompt and press **Enter**:

   > *"Modernize the legacy-netbanking application. Backend: Spring Boot 3.x, Java 17, PostgreSQL, JWT authentication. Frontend: React 18 SPA."*

3. Bob produces a Todo list covering the full migration. Approve each task as it runs.

<!-- image23: Bob's migration Todo list -->

4. When complete, the Explorer panel shows a new `modern-netbanking/` folder with the fully migrated project.

<!-- image24: Explorer with modern-netbanking/ folder -->

> If Bob stops mid-way, type: *"Complete remaining tasks from the todo list"*

> **What to understand here:** this is the point where participants see the custom mode and rule set affecting the output. This step should be presented as governed Bob behavior, not just a migration demo.

---

### Step 6 — OpenShift Deployment Artifacts *(Optional)*

Still in **Modernization Architect** mode, type:

> *"I need to deploy this on OpenShift. Create the required artifacts and scripts."*

Bob generates a `Dockerfile`, Kubernetes YAML manifests, and a `deploy.sh` script.

<!-- image25: Explorer with Dockerfile + K8s manifests -->

<!-- image26: Dockerfile open in editor -->

> **What to understand here:** the same custom mode continues to govern follow-on work beyond the core migration.

---

## What Participants Should Learn

By the end of this lab, participants should be able to explain:

1. how **Ask mode** helps reverse engineer an unfamiliar application
2. how a **Skill** turns a repeatable workflow into a reusable slash command
3. how a **custom Mode** changes Bob's persona and operating style
4. how a **Rule** constrains Bob's decisions using explicit standards
5. how those three building blocks can replace a premium workflow with a transparent, teachable Bob-based system

---

## Screenshot Checklist for Dry Run

Capture these screenshots while running the lab once as a participant:

| File | What to capture |
|---|---|
| `image7.png` | Ask mode selected in the Bob mode picker |
| `image8.png` | The extended reverse-engineering prompt typed in the chat before sending |
| `image9.png` | Bob's enhanced version of the prompt, ready to confirm |
| `image10.png` | Bob's reverse-engineering task list with Approve buttons |
| `image11.png` | Right-click menu on the generated documentation file showing **Open Preview** |
| `image12.png` | A generated documentation or PlantUML preview rendered successfully |
| `image13.png` | `/java-upgrade` typed in the Bob chat |
| `image14.png` | Bob asking for project path and target Java version |
| `image15.png` | Bob's Java upgrade task list |
| `image16.png` | `java-upgrade-report.md` open in Preview |
| `image17.png` | Bob IDE settings or menu path used to open **Modes** |
| `image18.png` | Modes list with **Modernization Architect** visible |
| `image19.png` | The **Role Definition** of the custom mode |
| `image20.png` | `.bob/rules-modernization-architect/` expanded in Explorer |
| `image21.png` | One representative XML rule file open in the editor |
| `image22.png` | **Modernization Architect** selected in the mode picker |
| `image23.png` | Bob's modernization todo list |
| `image24.png` | `modern-netbanking/` visible in Explorer after generation |
| `image25.png` | Generated OpenShift artifacts visible in Explorer |
| `image26.png` | Generated Dockerfile or Kubernetes manifest open in the editor |
| `image27.png` | Agent mode with the troubleshooting prompt `Install Maven` typed |

---

## Troubleshooting

### Maven not installed
Switch to **Agent** mode and type: *"Install Maven"*

<!-- image27: Agent mode with Install Maven typed -->

### Build fails during migration
Click **Fix it** next to the error. Bob reads the failure and applies a targeted fix automatically.

### Bob stops before finishing
Type: *"Complete remaining tasks from the todo list"*
