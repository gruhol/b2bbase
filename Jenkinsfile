pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Pobierz źródło kodu z repozytorium
                git 'https://github.com/gruhol/b2bbase.git'
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