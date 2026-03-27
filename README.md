# MoriAI — Custom Transformer-Based AI Companion (WIP)

## Overview

MoriAI is an ongoing project focused on building a custom AI companion system powered by a Transformer-based model.

The goal of this project is to explore:

* training large-scale language models from scratch
* integrating them into real-world applications
* developing a full-stack AI system (mobile, backend, and model)

This project is currently under active development. Model outputs and features are still being improved.

---

## Current Status

* Android chat interface implemented
* Node.js and MongoDB backend functional
* Dataset pipeline completed (~158M lines processed)
* Transformer model training in progress
* Model responses are still unstable and being refined

---

## System Architecture

```id="arch2"
[ Android App (Jetpack Compose) ]
              │
              ▼
      [ Node.js Backend API ]
              │
              ▼
      [ Transformer Model (Training / Inference in progress) ]
```

---

## Tech Stack

* Frontend: Kotlin (Jetpack Compose)
* Backend: Node.js, Express
* Database: MongoDB
* AI Training: PyTorch

---

## Model Development

* Custom Transformer architecture
* Large-scale dataset preprocessing completed
* Training currently ongoing

Current focus:

* improving response coherence
* stabilizing training
* reducing loss

---

## Features (Current)

* Chat interface (Android)
* User authentication with OTP verification
* API communication between frontend and backend

---

## Setup Guide

### 1. Clone Repository

```bash id="clone2"
git clone https://github.com/ShikoShintaro/MoriAI.git
cd MoriAI
```

### 2. Backend Setup

```bash id="backend2"
cd backend
npm install
node server.js
```

### 3. Android App

* Open the project in Android Studio
* Run on emulator or physical device

---

## Planned Improvements

* Improve model response quality
* Add memory and personality system
* Optimize inference performance
* Deploy backend and model to cloud

---

## Author

Shiko Shintaro
