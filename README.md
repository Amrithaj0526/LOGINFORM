# LOGINFORM (JavaFX)

Simple JavaFX login form created with Maven.

## Requirements
- JDK 17 or newer installed and `JAVA_HOME` set
- Maven installed

## How to run
1. Open a terminal in the project folder:

   ```powershell
   cd d:\loginform\LOGINFORM
   ```

2. Build the project (Maven or wrapper):

   ```powershell
   mvn clean package
   # or if you prefer the project wrapper (Windows):
   mvnw.cmd clean package
   ```

3. Run using the JavaFX Maven plugin (recommended):

   ```powershell
   mvn javafx:run
   # or with the wrapper (Windows):
   mvnw.cmd javafx:run
   ```

4. Run tests:

   ```powershell
   mvn test
   # or with the wrapper (Windows):
   mvnw.cmd test
   ```

Alternative: run the generated JAR with a local JavaFX SDK (if you have one):

```powershell
java --module-path "C:\path\to\javafx-sdk-20\lib" --add-modules javafx.controls,javafx.fxml -jar target\LOGINFORM-1.0-SNAPSHOT.jar
```

Default credentials in the demo app: **username**: `admin`, **password**: `password`.
