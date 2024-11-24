# Inventory Management System

A simple inventory management system built with Java and Maven.

## Prerequisites
- Java Development Kit (JDK) 21 or higher
- Apache Maven 3.6 or higher
- Any text editor or IDE (VSCode recommended)

## Technology Stack
- Java 21
- Maven 3.x
- JUnit 5.10.1 for testing

## Project Setup

### Installing Prerequisites
1. Install Java:
```bash
# For Ubuntu/Debian
sudo apt update
sudo apt install openjdk-21-jdk

# Verify installation
java --version
```

2. Install Maven:
```bash
# For Ubuntu/Debian
sudo apt install maven

# Verify installation
mvn --version
```

### Building the Project
1. Clone the repository:
```bash
git clone <repository-url>
cd inventory-management-system
```

2. Build the project:
```bash
mvn clean install
```

### Running the Application
There are several ways to run the application:

1. Using Maven exec plugin:
```bash
mvn exec:java
```

2. Using the compiled JAR:
```bash
# Create executable JAR with dependencies
mvn clean package assembly:single

# Run the JAR
java -jar target/inventory-management-system-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### Development Commands

#### Basic Maven Commands
```bash
# Clean the project
mvn clean

# Compile the project
mvn compile

# Run tests
mvn test

# Package the application
mvn package

# Install to local repository
mvn install

# Clean and install (most common)
mvn clean install
```

#### Testing Commands
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=InventoryManagerTest

# Skip tests
mvn install -DskipTests
```

#### Other Useful Commands
```bash
# Show dependency tree
mvn dependency:tree

# Run the application
mvn exec:java

# Create executable JAR with dependencies
mvn clean package assembly:single
```

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── inventory/
│   │           ├── models/
│   │           │   ├── Order.java
│   │           │   ├── OrderItem.java
│   │           │   ├── Product.java
│   │           │   └── User.java
│   │           ├── services/
│   │           │   ├── InventoryManager.java
│   │           │   ├── OrderManager.java
│   │           │   ├── SearchService.java
│   │           │   └── UserManager.java
│   │           ├── utils/
│   │           │   └── FileHandler.java
│   │           └── App.java
│   └── resources/
│       └── inventory.txt
└── test/
    └── java/
        └── com/
            └── inventory/
                └── InventoryManagerTest.java
```

## Features Implemented
1. Inventory Management
   - Product tracking with real-time quantity updates
   - Stock level monitoring with threshold checking
   - Category organization
   - File-based persistence for inventory data

2. Order Processing
   - Order creation and management
   - Real-time inventory updates on order processing
   - Order status tracking (PENDING, COMPLETED)
   - Multi-level quantity validation

3. Search and Filter System
   - Name-based product search
   - Price range filtering
   - Stock level filtering

4. User Authentication
   - Role-based access control (Admin/User)
   - Basic login system
   - User registration
   - Operation authorization

## Default Access
Default admin account:
- Username: admin
- Password: admin123

## Configuration
The application uses a file-based storage system:
- Inventory data is stored in `src/main/resources/inventory.txt`
- File format: CSV with fields: id,name,price,quantity,category

## Testing
The project uses JUnit 5 for testing. Tests can be run using:
```bash
mvn test
```

## Building for Production
To create a production-ready JAR with all dependencies:
```bash
mvn clean package assembly:single
```
This will create an executable JAR in the `target` directory.

## Contributing
1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request