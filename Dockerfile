# Говорим скачать такой то образ: maven/Java образ
FROM maven:3.8.4-openjdk-17

# Установливает рабочую директорию в /app
WORKDIR /app

# Копирует файл pom.xml в приложение.
COPY . /app

