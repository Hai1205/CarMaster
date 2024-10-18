
## Technologies Used

- **Java (JDK 17)**: Main programming language for building the application.
- **Java Swing**: For building the desktop application interface.
- **MySQL**: Database to store all application data such as customer details, inventory, and sales information.
- **XAMPP**: For local MySQL and Apache server setup.
- **Maven**: Dependency management tool.
- **Libraries**:
  - mysql-connector-java: To connect Java with MySQL.
  - jcalendar: For date input and management.
  - jfreechart: To generate statistical charts and reports.

## Installation and Setup

1. Clone the repository: `https://github.com/Hai1205/CarMaster.git`
2. Set up MySQL database:
   - Install XAMPP.
   - Start Apache and MySQL services in XAMPP.
   - Import the provided database schema in MySQL Workbench or phpMyAdmin.
3. Configure the database connection:
   - Update the database connection details in `Database.java` file under the Util package.
4. Run the application:
   - Open the project in an IDE (e.g., NetBeans, IntelliJ).
   - Build and run the project.

## Usage

- **Admin**: Manage all entities like cars, suppliers, employees, and generate reports.
- **Sales**: Manage customer information and process car sales.
- **Inventory**: Track car stock, imports, and sales details.

## Future Enhancements

- **Online Integration**: Integrate with a website for online car listings and sales.
- **Mobile App**: Build an Android version of the app.
- **Advanced Reporting**: Add more detailed reports and insights with export capabilities to Excel/PDF.

## Contributing

If you'd like to contribute to this project, feel free to open a pull request or submit an issue on GitHub.

## License

This project is licensed under the MIT License - see the LICENSE file for details.