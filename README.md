# Talabat Food Delivery System

## 📦 Overview

**Talabat** is a desktop-based food delivery management system developed in **Java** using **Object-Oriented Programming (OOP)** principles. It features a graphical user interface built with **JavaFX** and uses **file-based storage** with `.txt` and `.csv` files.

The system supports three primary user roles — **Admin**, **Seller**, and **Customer** — each with distinct capabilities related to user management, product handling, order processing, and report generation.

---

## 👤 User Roles & Features

### 🛡️ Admin
- **User Management**:
  - Add, Edit, Remove, List, and Search Users by any field.
- **Reports & Analytics**:
  - Orders per Seller and Customer.
  - Seller/Customer with the most orders and highest revenue.
  - View all order details.
  - Compute average and total revenue over a specific time period.

### 🧑‍🍳 Seller
- **Product Management**:
  - Add, Edit, Remove, List, and Search Products.
- **Sales Analytics**:
  - Pieces sold, best-selling, and top-revenue products over specific periods.
  - Order details and status management (Pending → Shipped).
  - Revenue analysis over time.

### 🧑 Customer
- **Cart & Orders**:
  - Create cart, search products, add/remove items, and confirm/cancel cart.
  - Add delivery address.
  - View payment details.
- **Order History**:
  - View all previous order details.

---

## 🧱 Project Structure

| Class Name           | Description |
|----------------------|-------------|
| `TalabatApp`         | Main launcher class for the JavaFX application. |
| `Admin`              | Contains admin-related logic and data. |
| `AdminScene`         | GUI for admin operations. |
| `AdminFileGenerator` | Generates admin reports (TXT/CSV). |
| `Customer`           | Customer-specific attributes and methods. |
| `CustomerScene`      | GUI for customer operations. |
| `Seller`             | Seller-specific attributes and methods. |
| `SellerScene`        | GUI for seller operations. |
| `Order`              | Represents order data (products, status, total). |
| `Product`            | Represents a product listed by a seller. |
| `SignUpScene`        | GUI for user registration. |
| `User`               | Base class for all user types. |
| `UserRole`           | Enum for user types (ADMIN, SELLER, CUSTOMER). |
| `Managable`          | Interface for standard CRUD and search functionality. |

---

## 💾 File-Based Storage

All data is persisted in `.txt` and `.csv` files, ensuring easy readability and portability.

- `users.txt`: Stores user data (Admin, Seller, Customer).
- `products.csv`: Stores seller product listings.
- `orders.csv`: Logs all orders placed by customers.
