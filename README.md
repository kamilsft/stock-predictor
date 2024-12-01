# Stock Predictor Project

## Version 1

### Overview
The **Stock Predictor Project** is a Spring Boot application designed to predict stock prices based on historical data fetched from the [Alpha Vantage API](https://www.alphavantage.co/). The project uses machine learning (via Weka) to provide next-day stock price predictions. It includes user authentication, stock tracking, and transaction management features.

---

### Features
- **User Management**:
  - Secure user registration and login using Spring Security.
  - Password encryption with BCrypt.
- **Stock Management**:
  - Fetch real-time stock data from Alpha Vantage API.
  - Store stock information in a MySQL database.
- **Transaction Management**:
  - Track user transactions (buy/sell).
  - View transaction history per user.
- **Predictions**:
  - Use historical stock data to train a machine-learning model.
  - Predict next-day stock prices using Linear Regression.
- **RESTful APIs**:
  - APIs for managing users, stocks, predictions, and transactions.

---

### Technologies Used
- **Backend**: Java, Spring Boot
- **Database**: MySQL, Spring Data JPA
- **Security**: Spring Security with BCrypt
- **API Integration**: Alpha Vantage API
- **Machine Learning**: Weka
- **Other Libraries**: Jackson for JSON parsing
- **Build Tool**: Maven

---

### Prerequisites
1. Java 17+ installed on your machine.
2. MySQL database configured.
3. Alpha Vantage API key.
