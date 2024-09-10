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
ChannelProcessingSystem can be tested with a simple webapp created here: http://52.15.67.195:8080/ by uploading channels.txt and parameters.txt. This online version of the app has file size limits set to 1MB

OR to run the app locally:

1. Make sure you have Java 21 installed on your system.
2. Open a terminal or command prompt.
3. Navigate to the root directory of the project.
4. Build the project using Maven by running the following command:

    ```sh
    mvn clean package
    ```

5. Once the build is successful, you can run the ChannelProcessingSystem class by executing the following command:

If channels.txt and parameters.txt files are present in the same folder as execution

    ```sh
    java -jar target/channelProcessingSystem-1.0-SNAPSHOT.jar
    ```

If paths to channels.txt and parameters.txt files are sent as arguments

    ```sh
    java -jar target/channelProcessingSystem-1.0-SNAPSHOT.jar /pathToChannelsTxt/channels.txt /pathToParametersTxt/parameters.txt
    ```

6. The ChannelProcessingSystem should now start running and processing the channel data.

## Running Tests
Code coverage report can be found by navigating to target/site/jacoco/index.html

```sh
mvn clean test
```
