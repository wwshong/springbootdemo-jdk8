#!/usr/bin/groovy
@Library('shared-lib') _

 
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
                 
                //global variables function   

                //takes parameters def call(String name, String dayOfWeek)
                helloWorldSimple("john", "Monday") 

                //pass a Map of name/val to helloWorld
                helloWorld("name": "Doe", "dayOfWeek": "Tues")

                //1. pass map of name/val to helloWorldExternal
                //2. helloWorldExternal.groovy calls loadLinuxScript(name: 'hello-world.sh') to read hello-world.sh and save it
                //3. then run hello-world.sh on the Agent
                helloWorldExternal("name": "Tom", "dayOfWeek": "Wed")
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
