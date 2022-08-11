#!/usr/bin/groovy

pipeline {
    agent any

    options {
        disableConcurrentBuilds()
    }

    stages {

        stage("Build") {
            steps { buildApp() }
		}
	
		stage("Pack Docker Image") {
			steps { packImage() }
		}
        stage("Deploy - Dev") {
            steps { deploy('dev') }
		}

	}
}


// steps
def buildApp() {
	sh "chmod 777 mvnw"
	sh "./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=wwshong/whong-docker-demo:whong-docker-demo"
	 
}

def packImage() {
	 
	dir ('.' ) {
		def appImage = docker.build("springboot-on-jenkins/myapp:${BUILD_NUMBER}")
	}
}

def deploy(environment) {

	def containerName = ''
	def port = ''

	if ("${environment}" == 'dev') {
		containerName = "spring_app_dev"
		port = "8181"
	} 
	else {
		println "Environment not valid"
		System.exit(0)
	}

	sh "docker ps -f name=${containerName} -q | xargs --no-run-if-empty docker stop"
	sh "docker ps -a -f name=${containerName} -q | xargs -r docker rm"
	sh "docker run -d -p ${port}:8080 --name ${containerName} springboot-on-jenkins/myapp:${BUILD_NUMBER}"

}
