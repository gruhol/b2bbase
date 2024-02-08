pipeline {
    agent any

    tools {
        maven "maven"
    }

    environment {
        JWT_SECRET = credentials('JWT-SECRET')
        DATABASE_IP = credentials('DATABASE-IP')
        DATABASE_NAME = credentials('DATABASE-NAME')
        DATABASE_PASSWORD = credentials('DATABASE-PASSWORD')
        DATABASE_USERNAME = credentials('DATABASE-USERNAME')
        EMAIL = credentials('EMAIL')
        EMAIL_PASSWORD = credentials('EMAIL-PASSWORD')
        TINYAPI = credentials('TINYAPI')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/gruhol/b2bbase.git'
            }
        }

        stage('Build') {
            steps {
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

        stage('Package') {
            steps {
                script {
                    sh 'mvn package'
                }
            }
        }

        stage('Stop and remove container') {
            steps {
                script {
                    try {
                        sh 'docker inspect b2bpoint > /dev/null 2>&1'
                        sh 'docker stop b2bpoint && docker rm b2bpoint'
                    } catch (Exception e) {
                        echo "Kontener nie istnieje. Pominięcie etapu."
                    }
                }
            }
        }

        stage('Remove image') {
            steps {
                script {
                    try {
                        sh 'docker rmi b2bpoint:b2bpoint'
                    } catch (Exception e) {
                        echo "Obraz nie istnieje. Pominięcie etapu."
                    }
                }
            }
        }

        stage('Build docker') {
            steps {
                script {
                    sh 'docker build -t b2bpoint:b2bpoint .'
                }
            }
        }

        stage('Run docker') {
            steps {
                script {
                    sh 'docker run -p 90:8080 ' +
                    '-e JWT-SECRET=${JWT_SECRET} ' +
                    '-e DATABASE-IP=${DATABASE_IP} ' +
                    '-e DATABASE-NAME=${DATABASE_NAME} ' +
                    '-e DATABASE-PASSWORD=${DATABASE_PASSWORD} ' +
                    '-e DATABASE-USERNAME=${DATABASE_USERNAME} ' +
                    '-e EMAIL=${EMAIL} ' +
                    '-e EMAIL-PASSWORD=${EMAIL_PASSWORD} ' +
                    '-e TINYAPI=${TINYAPI} ' +
                    '-e PROFILE=prod ' +
                    '-d --name b2bpoint b2bpoint:b2bpoint'
                }
            }
        }
    }
}
