<div align="center">

# 🚀 Arethium : Road To Role

### AI-Powered Career Readiness Operating System

<p align="center">
Transform Career Aspirations Into Measurable Readiness
</p>

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge\&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge\&logo=springboot\&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue?style=for-the-badge\&logo=postgresql)
![Gemini AI](https://img.shields.io/badge/Google-Gemini_AI-red?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-Build-red?style=for-the-badge\&logo=apachemaven)

</div>

---

# 📖 Overview

Arethium is an AI-powered career development platform designed to help students and aspiring professionals build a structured path toward their dream careers.

Instead of following random tutorials and disconnected learning resources, users receive personalized AI-generated roadmaps, assessments, interview preparation, and readiness tracking based on their career goals.

The platform aims to bridge the gap between learning and employability.

---

# ❗ Problem Statement

Millions of learners face challenges such as:

* Not knowing what to learn next
* Following unstructured learning paths
* Lack of personalized career guidance
* Difficulty measuring job readiness
* Insufficient interview preparation
* Poor visibility into learning progress

As a result, many students spend months or years learning inefficiently without a clear roadmap.

---

# 💡 Solution

Arethium provides an AI-driven system that:

* Identifies a user's career goal
* Generates a personalized roadmap
* Organizes learning into modules
* Evaluates progress through assessments
* Provides interview preparation
* Calculates readiness toward a target role

This transforms career preparation into a measurable and structured journey.

---

# ✨ Features

## 🎯 Goal-Based Career Planning

Users define:

* Target Role
* Target Company

Examples:

* Backend Engineer at Google
* Data Scientist at Microsoft
* ML Engineer at OpenAI
* Cybersecurity Analyst at Amazon

---

## 🧠 AI Roadmap Generation

Powered by Google Gemini.

Generates:

* Learning Modules
* Learning Sequence
* Module Descriptions
* Estimated Learning Path

---

## 📚 Module-Based Learning

Structured learning progression with:

* Java
* Spring Boot
* SQL
* DSA
* System Design
* Docker Fundamentals
* Cloud Concepts

and more depending on the selected role.

---

## 📝 AI Generated Assessments

Each module can generate:

* Multiple Choice Questions
* Skill Verification Tests
* Progress Validation

---

## 🎤 AI Interview Preparation

Role-specific interview generation:

* Backend Engineering
* Software Development
* Data Science
* Data Analytics
* Machine Learning

Users answer generated questions and receive evaluation scores.

---

## 📊 Career Readiness Tracking

Tracks:

* Module Completion
* Assessment Performance
* Interview Readiness
* Overall Progress

---

# 🏛️ System Architecture

```text
User
 │
 ▼
Goal Selection
 │
 ▼
AI Roadmap Generation
 │
 ▼
Learning Modules
 │
 ▼
Assessments
 │
 ▼
Readiness Tracking
 │
 ▼
Interview Preparation
 │
 ▼
Career Readiness Score
```

---

# 🛠️ Technology Stack

| Technology        | Purpose               |
| ----------------- | --------------------- |
| Java 21           | Core Language         |
| Spring Boot       | Backend Framework     |
| Spring Data JPA   | Data Access           |
| Hibernate         | ORM                   |
| PostgreSQL        | Database              |
| Thymeleaf         | Server-Side Rendering |
| Google Gemini API | AI Generation         |
| Maven             | Dependency Management |

---

# 📂 Project Structure

```bash
controller/
├── AuthController
├── OnboardingController
└── DashboardController

service/
├── AuthService
├── OnboardingService
├── DashboardService
├── RoadmapGeneratorService
├── AssessmentGenerator
└── InterviewGeneratorService

repository/
├── UsersRepository
├── GoalsRepository
├── ModulesRepository
├── AssessmentRepository
└── QuestionsRepository

entity/
├── Users
├── Goals
├── Modules
├── Assessment
└── Questions

dto/
├── RegisterDTO
├── LoginDTO
├── GoalsDTO
├── MessageDTO
├── ModuleResponseDTO
├── RoadmapResponseDTO
├── QuestionsDTO
├── AssessmentsResponseDTO
└── ScoreDTO

session/
└── UserSession
```

---


# 🚀 Installation

## Clone Repository

```bash
git clone https://github.com/YOUR_USERNAME/arethium.git
cd arethium
```

## Configure Database

Update application.properties:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/arethium
spring.datasource.username=root
spring.datasource.password=your_password
```

## Configure Gemini API

```properties
gemini.api.key=YOUR_API_KEY
```

## Run Application

```bash
mvn clean install
mvn spring-boot:run
```

---

# 🚀 Future Scope

* AI Resume Analyzer
* Smart Study Scheduler
* Personalized Learning Analytics
* Mobile Application
* Cloud Deployment
* Docker Containerization
* Kubernetes Orchestration
* AI Career Mentor
* Community Learning Platform
* Job Matching Engine

---

# 🏆 Bharat Academix CodeQuest 2026

Arethium was developed as a hackathon project focused on solving career uncertainty through Artificial Intelligence.

The platform combines roadmap generation, assessments, interview preparation, and readiness tracking into a unified career development system.

---

# 👨‍💻 Developer

**Al-Amin Reza**

Project:
**Arethium — Road To Role**

---

# ⭐ Support

If you find this project useful:

* Star the repository
* Fork the project
* Share feedback
* Contribute improvements

---

<div align="center">

### Built with ☕ Java, Spring Boot & Google Gemini AI

### Arethium — Road To Role 🚀

</div>
