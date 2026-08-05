# IBM Bob: Java Modernization Lab
## DevSparks Hyderabad 2026

> **Duration:** 60 minutes · **Level:** Technical / Hands-on · **Tool:** IBM Bob IDE

---

## Lab Repository
```
https://github.com/ibm-self-serve-assets/java-modernization-lab
```
Download or clone this repo. The `legacy-netbanking` folder is your starting point.

---

## What You Will Learn
- IBM Bob built-in modes, custom modes, and MCP extensibility
- AI-powered reverse engineering of undocumented legacy code
- Automated Java version upgrade (8 → 17) using **Java Modernization** mode
- Full framework migration using a custom **Modernization Architect** mode
- Auto-generated OpenShift / Kubernetes deployment artifacts

---

## Stack Transformation

| Layer | Legacy (Before) | Modern (After) |
|---|---|---|
| Runtime | Java 1.8 | Java 17 (OpenJDK) |
| Back-end Framework | Apache Struts 1.3 | Spring Boot 3.x + REST API |
| Front-end | JSP + Scriptlets | React 18 SPA |
| Database | SQLite (file-based) | PostgreSQL 15 + Flyway |
| Authentication | HTTP Session / Basic | JWT + BCrypt |
| Deployment | WAR on Tomcat | Docker + OpenShift / Kubernetes |

---

## Pre-Requisites

### Required
- **IBM Bob IDE** — installed and configured
  - Install guide: `https://bob.ibm.com/docs/ide/getting-started/install`
- **PlantUML Markdown Preview** extension installed in Bob IDE
  - Click Extensions icon → Search "PlantUML" → Install

### Optional (for local build verification)
```sh
# OpenJDK 17
brew install openjdk@17           # macOS
sudo apt install openjdk-17-jdk   # Ubuntu/Debian
sudo dnf install java-17-openjdk-devel  # RHEL

# Maven
brew install maven                # macOS
sudo apt install maven            # Ubuntu/Debian
sudo dnf install maven            # RHEL
```

---

## Lab Steps

### Step 4.1 — Import Project into Bob Workspace

1. Download/clone: `https://github.com/ibm-self-serve-assets/java-modernization-lab`
2. Open Bob IDE → **File → Open Folder** → select `java-modernization-lab`
3. Confirm Explorer shows: `.bob/`, `images/`, `legacy-netbanking/`, `lab-guide.md`

---

### Step 4.2 — Reverse Engineering (Ask Mode)
**Objective:** Analyze undocumented Struts code, auto-generate documentation + diagrams.

1. Click **"Ask"** (bottom-right of Bob IDE) to switch to Ask Mode
2. Enter this prompt and click the **"Enhance Prompt"** icon before sending:
   > *"Help me understand this legacy-netbanking application. Save the generated documentation and diagrams in legacy-netbanking-documentation directory. Make sure to generate required PlantUML diagrams like sequence diagram along with mermaid architecture and ER diagram etc of current implementation."*
3. Review the enhanced prompt Bob generates → hit **Enter**
4. Keep approving tasks as Bob works through the codebase
5. When done: right-click generated `.md` → **"Open Preview"**
6. Right-click a `.puml` file → **"Preview PlantUML File"**

---

### Step 4.3 — Java Version Upgrade (Java Modernization Mode)
**Objective:** Upgrade runtime from Java 8 → Java 17 using Bob's built-in workflow.

1. Click **"Start New Task"** → select **"Java Modernization"**
2. Set the project path to point to `legacy-netbanking` directory
3. Configure:
   - Select **"Java Upgrade"**
   - Disable **"Git Flow"**
   - Set **Target Java version = 17**
   - Disable **"Jakarta EE migration"**
4. Click **"Run Recipes"** → approve the generated Todo list
5. On completion, a summary flowchart shows all changes applied

> **Note:** If "Java Modernization" workflow is not visible, close open tasks or restart Bob IDE.

---

### Step 4.4 — Full Application Modernization (Modernization Architect Mode)
**Objective:** Full Struts → Spring Boot 3.x + React 18 + PostgreSQL migration.

1. Explore: **Settings → Modes → "Modernization Architect"** — review role definition
2. In Explorer, expand `.bob/rules-modernization-architect` — see the governing rule files
3. Switch to **"Modernization Architect"** mode (mode selector, bottom-right)
4. Enter the prompt:
   > *"Modernize the legacy-netbanking application. Backend: Spring Boot 3.x, Java 17, PostgreSQL, JWT authentication. Frontend: React 18 SPA."*
5. Approve the Todo list and let Bob work through the full migration
6. Review the new `modern-netbanking` project structure in Explorer

> **Note:** If Bob stops mid-way, type: *"Complete remaining tasks from the todo list"*

---

### Step 4.5 — OpenShift Deployment Artifacts *(Optional)*
**Objective:** Auto-generate Dockerfiles, K8s manifests, and deploy scripts.

1. Still in **"Modernization Architect"** mode, enter:
   > *"I need to deploy it on OpenShift. Create required artifacts and scripts."*
2. Review the generated `Dockerfile`, Kubernetes YAML manifests, and `deploy.sh`

---

## Key Modernization Achievements

| Area | Achievement |
|---|---|
| Framework | Struts 1.x → Spring Boot 3.x · JSP → React 18 SPA |
| Database | SQLite → PostgreSQL with Flyway migrations |
| Security | HTTP Session → JWT-based auth with BCrypt |
| Cloud-Native | Dockerized, K8s/OpenShift ready, externalized config |
| Runtime | Java 8 → Java 17 via automated recipes |
| Functional Parity | All legacy features (transfers, history) preserved |

---

## Troubleshooting

| Problem | Fix |
|---|---|
| SDKMAN not installed | Switch to **Advanced** mode → type `"Install SDKMAN"` |
| "Java Modernization" not visible | Close all open tasks and restart Bob IDE |
| Build failure / error during migration | Click the **"Fix it"** button next to the error |
| Bob stops mid-way (context limit) | Type: `"Complete remaining tasks from the todo list"` |

---

## References

- **Lab Repository:** https://github.com/ibm-self-serve-assets/java-modernization-lab
- **Full Lab Guide:** `lab-guide.md` in the repository root
- **Bob Innovation Hub:** https://ibm-self-serve-assets.github.io/bob-innovation-hub/#/use-cases/java-modernization
- **Bob IDE Install:** https://bob.ibm.com/docs/ide/getting-started/install
- **Lab Author:** Anand Awasthi — anand.awasthi@in.ibm.com

---

*IBM Service Engineering Lab · DevSparks Hyderabad 2026*
