# Food Delivery App

This application calculates the delivery fee for food couriers based on regional base fee, vehicle type, and weather conditions.

## Features

- Import weather data from the Estonian Environment Agency
- Store and manage weather data in an H2 database
- Calculate delivery fee based on regional base fee, vehicle type, and weather conditions
- Provide a REST API for requesting the delivery fee according to input parameters

## Technologies Used

- Java
- Spring framework
- H2 database
- JAXB for XML parsing
- Gradle for build management
- Spring REST Docs for API documentation

## Prerequisites

- JDK 11 or later
- Gradle 7.2 or later (if not using the Gradle Wrapper included in the project)

## Build and Run

1. Clone the repository:
    ```
    git clone https://github.com/okuuuu/fooddeliveryapp.git
    cd fooddeliveryapp
    ```
2. Build the project:
    ```
    ./gradlew build
    ```
    Note: If you're on Windows, use `gradlew.bat` instead of `./gradlew`.
3. Run the application:
    ```
    ./gradlew bootRun
    ```
    The application will start and listen on port 8080.
4. Access the REST API:<br>
    Send a request to the `/delivery-fee` endpoint with the required parameters, e.g.,
    ```
    http://localhost:8080/delivery-fee?city=Tallinn&vehicle=Car
    ```
## API Documentation

The API documentation is generated using Spring REST Docs and can be found in the `src/docs` directory after building the project. Open the `index.html` file in your browser to view the documentation.

## License

This project is licensed under the [Apache-2.0 license](LICENSE).
