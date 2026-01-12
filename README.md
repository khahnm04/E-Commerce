# 🛒 E-Commerce Platform

A full-featured E-Commerce platform built with modern architecture, completely separating Backend and Frontend. The system includes a powerful RESTful API handling complex business logic and two separate user interfaces for Customers (Storefront) and Administrators (Admin Dashboard).

## 🚀 Tech Stack

### Backend (Server)
- **Core:** Java 17, Spring Boot 3.x
- **Database:** MySQL 8.0
- **ORM:** Spring Data JPA (Hibernate)
- **Caching:** Redis (Token blacklist, data caching)
- **Security:** Spring Security, JWT (Access Token & Refresh Token), OAuth2 (Google/Facebook Login)
- **API Documentation:** Swagger UI / OpenAPI
- **Utilities:** MapStruct (DTO Mapping), Lombok, Cloudinary (Image Storage), Docker Compose

### Frontend (Client & Admin)
- **Framework:** Next.js 14/15 (App Router), ReactJS
- **Language:** TypeScript
- **Styling:** Tailwind CSS, PostCSS
- **State Management:** React Hooks / Context API (or Zustand/Redux)
- **Form Handling:** React Hook Form, Just-Validate
- **Build Tool:** Yarn

## ✨ Key Features

### 👤 Customer Features (Client)

**Authentication:**
- Registration, Login (Email/Phone/Google/Facebook)
- Password Recovery

**Products:**
- Search, Filter (by price, brand, category)
- View product details
- View variants (Color/Size)

**Interactions:**
- Product Q&A (Nested Comments)
- Reviews (Future feature)
- Wishlist

**Account Management:**
- Profile management
- Address book (multiple shipping addresses)
- Password change
- Order history

**Cart & Checkout:** *(In Development)*
- Add to cart
- Checkout process

**News:**
- View articles and promotional news

### 🛠 Admin Features (Admin Dashboard)

**Overview:**
- Revenue, orders, and new user statistics

**Product Management:**
- CRUD operations for products with multiple variants
- Manage Attributes & attribute values
- Upload images to Cloudinary (with garbage image deletion support)

**Category & Brand Management:**
- Hierarchical category structure
- Brand management

**Role-Based Access Control (RBAC):**
- Detailed Role & Permission management

**User Management:**
- View user list
- Lock/unlock accounts

**Q&A Management:**
- Answer customer questions
- Hide/delete inappropriate questions

**News Management:**
- Compose news articles (Rich Text Editor)

## 📂 Project Structure

```
e-commerce/
├── backend/                # Spring Boot source code
│   ├── src/main/java/...   # Controller, Service, Repository, Entity...
│   ├── docker-compose.yml  # Docker config for MySQL & Redis
│   └── ...
├── frontend/
│   ├── client/             # Customer interface (Next.js)
│   └── admin/              # Admin interface (Next.js)
└── database/               # SQL initialization files & Database design (Draw.io)
```

## ⚙️ Installation Guide

### 1. Prerequisites

- Java JDK 17+
- Node.js 18+ & Yarn
- Docker & Docker Compose (or manually install MySQL and Redis)
- Cloudinary account (to get API Key for image upload)

### 2. Database & Redis Setup

The fastest way is using Docker:

```bash
cd backend
docker-compose up -d
# This will run MySQL on port 3306 and Redis on port 6379
```

Or import the `backend/database/db_ecommerce.sql` file into your MySQL Workbench.

### 3. Backend Configuration & Run

Open `backend/src/main/resources/application.yml` and update the configuration:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/db_ecommerce
    username: root
    password: your_password # <--- Change to your DB password
  data:
    redis:
      host: localhost
      port: 6379

cloudinary:
  cloud-name: your_cloud_name # <--- Get from Cloudinary
  api-key: your_api_key
  api-secret: your_api_secret

jwt:
  secret-key: your_very_long_secret_key # <--- Generate a random string
```

Run the application:

```bash
./mvnw spring-boot:run
```

Server will run at: **http://localhost:8080**

### 4. Frontend (Client) Configuration & Run

Navigate to the client directory:

```bash
cd frontend/client
```

Create `.env.local` file and configure:

```env
NEXT_PUBLIC_API_URL=http://localhost:8080/api/v1
```

Install dependencies and run:

```bash
yarn install
yarn dev
```

Client runs at: **http://localhost:3000**

### 5. Frontend (Admin) Configuration & Run

Navigate to the admin directory:

```bash
cd frontend/admin
```

Create `.env.local` file:

```env
NEXT_PUBLIC_API_URL=http://localhost:8080/api/v1
```

Install dependencies and run:

```bash
yarn install
yarn dev
```

Admin runs at: **http://localhost:3001** (or another port if 3000 is busy)

## 🔐 Default Accounts (Seed Data)

If you run the SQL initialization file (`db_ecommerce.sql`), the system comes with these accounts:

| Role  | Username / Email     | Password |
|-------|---------------------|----------|
| Admin | admin@gmail.com     | 123456   |
| User  | user@gmail.com      | 123456   |

## 🤝 Contributing

All contributions are welcome! If you want to improve the project:

1. Fork the project
2. Create a new branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📞 Contact

- **Author:** [Your Name]
- **Email:** [Your Email]
- **Github:** [Your Github Link]

---

Happy Coding! 🚀
