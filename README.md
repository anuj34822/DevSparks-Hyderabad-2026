# IBM Bob: DevSparks Hyderabad 2026
### Two Hands-On Labs with IBM Bob IDE

---

## Labs

### 🔬 Lab 1 — Java Modernization
Transform a **Legacy Struts 1.3 + Java 8 + SQLite** application into a modern **Spring Boot 3.x + React 18 + PostgreSQL 15** cloud-native app — using IBM Bob.

📖 **[Start Lab 1 → lab-guide.md](./lab-guide.md)**

| Layer | Before | After |
|---|---|---|
| Runtime | Java 1.8 | Java 17 |
| Back-end | Apache Struts 1.3 | Spring Boot 3.x |
| Front-end | JSP + Scriptlets | React 18 SPA |
| Database | SQLite | PostgreSQL 15 + Flyway |
| Auth | HTTP Session | JWT + BCrypt |
| Deploy | WAR / Tomcat | Docker + OpenShift |

---

### 🔄 Lab 2 — AI-Powered SDLC
Use IBM Bob as an **end-to-end AI pair programmer across the entire SDLC** — from reading GitHub issues through to a documentation-ready architecture diagram. Bob is wired to GitHub CLI, Tavily web search, and Draw.io via MCP.

📖 **[Start Lab 2 → lab-guide-sdlc.md](./lab-guide-sdlc.md)**

| SDLC Phase | What Bob Does | Tool Used |
|---|---|---|
| Discovery | Fetches open GitHub issues | `gh` CLI |
| Planning | Researches topic with live web search | Tavily MCP |
| Design | Generates a visual design diagram | Draw.io MCP |
| Development | Implements the feature using subagents | Agent Mode |
| Documentation | Produces a final architecture diagram | Draw.io MCP |

---

## Duration
- **Lab 1:** 60 minutes · Hands-on · IBM Bob IDE required
- **Lab 2:** 60 minutes · Hands-on · IBM Bob IDE required

## Prerequisites
- IBM Bob IDE installed → `https://bob.ibm.com/docs/ide/getting-started/install`
- **Lab 1:** PlantUML Markdown Preview extension in Bob IDE
- **Lab 2:** GitHub CLI (`gh`) · Tavily API key · Node.js (for Draw.io MCP)

## References
- Bob Innovation Hub: https://ibm-self-serve-assets.github.io/bob-innovation-hub/#/use-cases/java-modernization
- Bob IDE Install: https://bob.ibm.com/docs/ide/getting-started/install
- GitHub CLI: https://cli.github.com/
- Tavily: https://www.tavily.com/
