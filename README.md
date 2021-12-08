# Inter Bank Transaction Hub (IBTH)

Inter Bank Transaction Hub is a java spring boot project for inter bank transactions and testing purposes.

## Requirements
You need to have Java 11 or later and Maven 3.3.3 or later.

## Build
Open a terminal and navigate to the root directory of this project. Type the following command and execute.

##### Lanka Clear to LOFC
```bash
mvn clean install -P lofc,dev
```

##### Lanka Clear to CLC
```bash
mvn clean install -P clc,dev
```

##### Lanka Clear to BRAC
```bash
mvn clean install -P brac,dev
```

## Run Application

1. Go inside `apps/acquirer-app/target` directory.
2. Run jar file. ``` java -jar acquirer-app-lofc.jar ```


## Run Tester
1. Go inside `testers/acquirer-tester/target` directory.
2. Run jar file. ``` java -jar acquirer-tester-lofc.jar ```

## Tester API Documentation
Start the aacquirer-tester in localhost and open a web browser and visit swagger ui url.

##### For BRAC
http://localhost:9095/atm-tester/swagger-ui/
