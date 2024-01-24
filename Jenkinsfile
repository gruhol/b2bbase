pipeline {

    agent {
        any
        docker { image 'maven:3.9.6-eclipse-temurin-17-alpine' }
    }

    stages {
        stage('Checkout') {
            steps {
                // Pobierz źródło kodu z repozytorium
                git branch: 'main', credentialsId: 'dabrowskiw@gmail.com', url: 'https://github.com/gruhol/b2bbase.git'
            }
        }

        stage('Build') {
            steps {
                // Uruchom proces budowania Maven
                script {
                    sh 'mvn clean install'
                }
            }
        }

        stage('Test') {
            steps {
                // Uruchom testy jednostkowe
                script {
                    sh 'mvn test'
                }
            }
        }
    }
}