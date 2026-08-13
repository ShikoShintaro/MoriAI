# MoriAI — AI Companion System (FINISHED)

## Overview

MoriAI is an ongoing full-stack AI companion project focused on building and integrating a custom Transformer-based language model into a real-world mobile application.

The project explores:

* Training and fine-tuning large-scale language models
* Building a real-time AI chat system
* Full-stack integration (Android, backend, and AI inference service)
* Deploying distributed AI systems using cloud services

This project is currently under active development, and model responses are being continuously improved for stability and coherence.

---

## Current Status

* Android chat interface fully implemented (Jetpack Compose)
* Node.js backend with authentication and MongoDB integration completed
* Dataset pipeline completed (~158M lines processed and cleaned)
* Transformer model training and refinement ongoing
* AI responses still being optimized for consistency and structure
* Cloud integration with external AI inference service in progress

---

## System Architecture

```
[ Android Application (Jetpack Compose) ]
                  │
                  ▼
        [ Node.js Backend API ]
                  │
        ┌─────────┴─────────┐
        ▼                   ▼
[ MongoDB Database ]   [ AI Inference Service ]
                          (FastAPI / Transformer Model)
```

---

## Tech Stack

Frontend:

* Kotlin (Jetpack Compose)

Backend:

* Node.js
* Express.js

Database:

* MongoDB

AI / ML:

* PyTorch
* Custom Transformer Model

---

## Model Development

* Custom Transformer architecture designed from scratch
* Large-scale dataset preprocessing completed
* Training pipeline implemented and actively refined

Current focus:

* Improving response coherence
* Reducing repetition and hallucinations
* Stabilizing generation output
* Enhancing instruction-following behavior

---

## Current Features

* Real-time chat interface (Android)
* User authentication system (OTP-based verification)
* Backend API integration
* MongoDB-based user and message storage
* AI-powered response generation (prototype stage)

---

## Setup Guide

### 1. Clone Repository

```bash
git clone https://github.com/ShikoShintaro/MoriAI.git
cd MoriAI
```

---

### 2. Backend Setup (Node.js)

```bash
cd server
npm install
node server.js
```

---

### 3. Android Application

* Open project in Android Studio
* Sync Gradle
* Run on emulator or physical device

---

## Planned Improvements

* Improve AI response stability and personality consistency
* Add long-term memory system
* Optimize inference speed
* Deploy backend and AI services to cloud
* Add contextual conversation understanding
* Improve UI/UX experience in mobile app

---

## Author

Shiko Shintaro

---

## Notes

MoriAI is a research-driven project exploring the intersection of deep learning, mobile systems, and distributed backend architecture.

The system is designed to evolve from a prototype into a scalable AI companion platform.
