# Metro Cash and Carry POS System

A comprehensive **Point-of-Sale (POS)** system designed for Metro Cash and Carry stores to streamline operations like branch management, employee management, inventory tracking, and customer transactions. The system ensures an efficient workflow for managing day-to-day business activities.

## Features

- **Branch Management**: Easily manage branches and their specific data.
- **Employee Management**: Add, update, and track employees working across different branches.
- **Data Entry**: Supports vendor and product entry for inventory management.
- **Login System**:
    - Super Admin Login
    - Branch Manager Login
    - Data Entry Operator Login
- **Inventory Management**: Simplified process for maintaining product stocks.
- **Splash Screen**: A loading screen for better user experience.
- **Database Integration**: Uses a robust database backend for persistence.

## Project Structure

### Source Code

#### Base Package: `com.mycompany.pos`
- **BranchManager**: Manages branch-specific operations.
- **DatabaseConnection**: Ensures seamless database connectivity.
- **DataEntry**: Facilitates vendor and product data entry.
- **Pos**: The main entry point for the application.
- **Splashscreen**: Displays a user-friendly splash screen during startup.
- **Login Forms**: Separate login interfaces for:
    - `LoginAdmin`
    - `LoginBranch`
    - `cashierLoginFrame`
    - `dataEntryOperatorLoginFrame`

### Resources
- **Images**: Stored in `src/main/resources/images/` for UI elements and icons.
- **Stylesheets**: Includes `style.css` for custom application styling.

### Build Configuration
- **Maven Project**: Managed via a `pom.xml` file for dependency management.
