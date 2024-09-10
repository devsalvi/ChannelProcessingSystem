# ChannelProcessingSystem

ChannelProcessingSystem is a Java-based project designed to process and analyze channel data. This project includes various functions to read, write, and process channel data, as well as calculate metrics.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Running Tests](#running-tests)


## Installation

To get started with the ChannelProcessingSystem project, clone the repository and build the project using Maven.

```sh
git clone https://github.com/yourusername/ChannelProcessingSystem.git
cd ChannelProcessingSystem
mvn clean install
```

## Usage
- Online
> ChannelProcessingSystem can be tested with a simple webapp created here:
>> http://52.15.67.195:8080/ by uploading channels.txt and parameters.txt.
>> This temporary online version of the app has file size limits set to 1MB

- Locally:
> 1. Make sure you have Java 21 installed on your system.
> 2. Open a terminal or command prompt.
> 3. Navigate to the root directory of the project. A pre packaged .jar file is included in this locatiom.
> 4. Run java -jar command to execute application 
>    
```sh
    java -jar channelProcessingSystem-1.0-SNAPSHOT.jar
```
> 5. Please follow prompts to continue
>> <img width="691" alt="Screenshot 2024-09-10 at 3 09 27 PM" src="https://github.com/user-attachments/assets/b02cfbff-4880-4b22-adbe-0d0705f1006b">


> 5. You can also build the project using Maven by running the following command:

```sh
    mvn clean package

```
> 6. Once the build is successful, you can run the ChannelProcessingSystem class by executing the following command:

```sh
    java -jar target/channelProcessingSystem-1.0-SNAPSHOT.jar
```

> 7. The ChannelProcessingSystem should now startup. Please follow application prompts to continue.

## Running Tests
Code coverage report can be found by navigating to target/site/jacoco/index.html

```sh
mvn clean test
```
