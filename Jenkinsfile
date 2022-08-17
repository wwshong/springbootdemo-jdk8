#!/usr/bin/groovy
@Library('JenkinsSharedLibUtil')
import jenkinslib.JenkinsSharedLibUtil
def jenkinsSharedLibUtil = new JenkinsSharedLibUtil()
pipeline {
    agent any

    options {
        disableConcurrentBuilds()
    }

	environment {
		
		FOO = "bar"
		
	}
    stages {
        stage("shared lib demo") {
            steps {
                script {
                    def s1 = jenkinsSharedLibUtil.sayHi()
                    def s2 = jenkinsSharedLibUtil.sayHi2()
                    println "s1=${s1}"
                    println "s2=${s2}"
                }
                //global variables function   
                helloWorldSimple("john", "Monday") 
            }
        }

        stage("Build") {
            steps {
		    script {
			    println("pure java code 2")
			    List<String> list = new ArrayList<>();
			    list.add("one")
			    list.add("two")
			    println(list)
			    
			     def map = [
    "Monday": ["exercise", "work"],
    "Sat": ["relax"]
]
map.each { k, v ->
    println "$k = $v"
	
	
}
		    }
		    sh "printenv"
		    echo "FOO=${env.FOO}"
		println "This print ${env.FOO}"
		    
		   

		    
		    buildApp() }
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
