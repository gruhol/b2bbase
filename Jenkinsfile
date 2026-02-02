pipeline {
    agent any

    tools {
        maven "maven"
    }

    environment {
        JWT_SECRET = credentials('B2BPOINT_JWT_SECRET')
        DATABASE_IP = credentials('B2BPOINT_DATABASE_IP')
        DATABASE_NAME = credentials('B2BPOINT_DATABASE_NAME')
        DATABASE_PASSWORD = credentials('B2BPOINT_DATABASE_PASSWORD')
        DATABASE_USERNAME = credentials('B2BPOINT_DATABASE_USERNAME')
        EMAIL = credentials('B2BPOINT_EMAIL')
        EMAIL_PASSWORD = credentials('B2BPOINT_EMAIL_PASSWORD')
        TINYAPI = credentials('B2BPOINT_TINYAPI')
    }

    stages {
        stage('Checkout') {
            steps {
                git credentialsId: 'GITHUB_GRUHOL',
                    url: 'https://github.com/gruhol/b2bbase.git',
                    branch: 'main'
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
                    def dockerCmd = '''docker run -d \
                        --name b2bpoint \
                        --network traefik_proxy \
                        -v /srv/b2bpoint:/data \
                        -e "DATABASE_IP=${DATABASE_IP}" \
                        -e "DATABASE_NAME=${DATABASE_NAME}" \
                        -e "DATABASE_PASSWORD=${DATABASE_PASSWORD}" \
                        -e "DATABASE_USERNAME=${DATABASE_USERNAME}" \
                        -e "TINYAPI=${TINYAPI}" \
                        -e "JWT_SECRET=${JWT_SECRET}" \
                        -e "EMAIL=${EMAIL}" \
                        -e "MAIL_PASSWORD=${MAIL_PASSWORD}" \
                        -e "PROFILE=prod" \
                        --label traefik.enable=true \
                        --label traefik.http.routers.b2bapi.rule='Host(`b2bpoint.pl`) && PathPrefix(`/api`)' \
                        --label traefik.http.routers.b2bapi.entrypoints=websecure \
                        --label traefik.http.routers.b2bapi.tls.certresolver=letsencrypt \
                        --label traefik.http.routers.b2bapi.priority=100 \
                        --label traefik.http.services.b2bapi.loadbalancer.server.port=8082 \
                        --label traefik.http.middlewares.strip-api.stripprefix.prefixes=/api \
                        --label traefik.http.routers.b2bapi.middlewares=strip-api \
                        b2bpoint:b2bpoint'''

                    sh dockerCmd
                }
            }
        }

    }
}