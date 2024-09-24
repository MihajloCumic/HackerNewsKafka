# HackerNewsKafka
# Kafka Producer-Consumer with CSV Writer

This project is a Java-based application that integrates Kafka producers and consumers to process data from the [Hacker News API](https://github.com/HackerNews/API). Depending on the type of data (`job`, `story`, or `comment`), the application directs the data to the appropriate consumer, which then writes the processed JSON data to a CSV file using a custom CSV writer module.

## Features

- **Kafka Producer**: Fetches data from the Hacker News API and produces messages to Kafka based on their type (`job`, `story`, or `comment`).
- **Kafka Consumers**: Separate consumers for each type (job, story, and comment) listen for respective Kafka messages.
- **CSV Writer Module**: Each consumer writes the received JSON data to a dedicated CSV file.
- **Data Flow**: 
  - If the data is of type `job`, it is sent to the **Job Consumer**.
  - If the data is of type `story`, it is sent to the **Story Consumer**.
  - If the data is of type `comment`, it is sent to the **Comment Consumer**.
