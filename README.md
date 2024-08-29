## 💬 Chat Application

This chat application is designed to deliver seamless real-time communication with a robust set of features that cater to modern messaging needs. the application ensures efficiency, security, and scalability, making it ideal for both personal and professional use.

## 📑 Table of Contents
- [🚀 Features](#-features)
  - [💬 Real-time Messaging](#-real-time-messaging)
  - [👥 Conversations Management](#-conversations-management)
  - [📁 File Sharing](#-file-sharing)
  - [🔐 Authentication and Authorization](#-authentication-and-authorization)
- [🔔 Advanced Notification System](#-advanced-notification-system)
  - [📡 Server-Sent Events (SSE) Integration](#-server-sent-events-sse-integration)
  - [❤️ Heartbeat Mechanism](#-heartbeat-mechanism)
  - [⚙️ Dynamic Event Handling](#️-dynamic-event-handling)
  - [🔄 Efficient User Management](#-efficient-user-management)
- [🛠️ Technology Stack](#-technology-stack)
  - [☕ Backend](#-backend)
  - [🌐 Frontend](#-frontend)

## 🚀 Features

### 💬 Real-time Messaging
- Engage in instant conversations with friends, family, or colleagues. Messages are delivered in real-time, ensuring smooth and uninterrupted communication.

### 👥 Conversations Management
- Organize and manage conversations with ease. The application allows users to create, update, and archive chats, keeping communication streamlined and organized.

### 📁 File Sharing
- Share images, videos, and documents effortlessly within conversations. The file-sharing feature supports various media types, enabling rich and versatile communication.

### 🔐 Authentication and Authorization
- Security is paramount, with user authentication and role-based authorization managed through Keycloak (OAuth2). This ensures that user data is protected and access is appropriately controlled.

## 🔔 Advanced Notification System

The application features a cutting-edge notification system powered by the `NotificationService`, which is integral to maintaining real-time communication and user engagement.

### 📡 Server-Sent Events (SSE) Integration
- The `NotificationService` employs SSE to push real-time notifications to users, ensuring they receive timely updates without needing to refresh or poll the server continuously.

### ❤️ Heartbeat Mechanism
- A built-in heartbeat mechanism runs at regular intervals to maintain active connections and monitor user presence. This ensures users are accurately tracked as online or offline, enhancing the overall user experience.

### ⚙️ Dynamic Event Handling
- Notifications are tailored and sent based on specific events, such as new messages or system alerts. The service dynamically manages these notifications, ensuring users receive relevant information in real-time.

### 🔄 Efficient User Management
- The service efficiently manages connections by adding or removing emitters based on user activity. This ensures that only active users are maintained in the system, optimizing resource usage.

## 🛠️ Technology Stack

### ☕ Backend
- **Java 17+**: Core programming language.
- **Spring Boot 3.x**: Framework for building the backend.
- **Keycloak 20+**: Used for authentication and authorization (OAuth2).
- **PostgreSQL**: Database management system.
- **Liquibase**: Database version control.
- **Jilt**: Dependency injection utility.
- **Docker Compose**: For orchestrating multi-container Docker applications.
- **Lombok**: For reducing boilerplate code in Java.

### 🌐 Frontend
- **Angular 18**: Framework for building the frontend.
- **Bootstrap 5.3**: Frontend CSS framework for responsive design.
- **FontAwesome**: Icons for a modern user interface.
- **Keycloak JS**: JavaScript library for Keycloak integration.