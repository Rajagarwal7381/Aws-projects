pipeline {
    agent any

    environment {
        IMAGE_NAME = "demo-app"
        AWS_REGION = "ap-south-1"
        ACCOUNT_ID = "560449669727"
        REPO_NAME = "demo"
        EKS_CLUSTER = "demo-cluster5"
        DEPLOYMENT_NAME = "demo-app"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/Rajagarwal7381/Aws-projects'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${IMAGE_NAME}:${BUILD_NUMBER} ."
            }
        }

        stage('Login to AWS ECR') {
            steps {
                sh """
                aws ecr get-login-password --region ${AWS_REGION} | \
                docker login --username AWS --password-stdin ${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com
                """
            }
        }

        stage('Tag & Push Image') {
            steps {
                sh """
                docker tag ${IMAGE_NAME}:${BUILD_NUMBER} \
                ${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${REPO_NAME}:${BUILD_NUMBER}

                docker push ${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${REPO_NAME}:${BUILD_NUMBER}
                """
            }
        }

        stage('Configure kubectl for EKS') {
            steps {
                sh """
                aws eks update-kubeconfig --region ${AWS_REGION} --name ${EKS_CLUSTER}
                """
            }
        }

        stage('Deploy to EKS') {
            steps {
                sh """
                kubectl set image deployment/${DEPLOYMENT_NAME} \
                ${DEPLOYMENT_NAME}=${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${REPO_NAME}:${BUILD_NUMBER} \
                --record
                kubectl rollout status deployment/${DEPLOYMENT_NAME}
                """
            }
        }
    }
}
