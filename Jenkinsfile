def gv
pipeline {
    agent any
    parameters {
        // string(name:'VERSION',defaultValue: '', description: 'version to deploy')
        choice(name: 'VERSION', choices: ['1.1.0','1.2.0','1.3.0'],description:'')
        booleanParam(name: 'excuteTests',defaultValue: true, description: '' )
    }
    environment {
        
        NEW_VERSION = '1.3.0'

    }
    tools {
        maven 'Maven'
    }
    stages {
        stage('init') {
            steps {
                script {
                    gv = load "script.groovy"
                }
                echo 'init..'
                echo "environment ${env.NEW_VERSION}"
               
            }
        }
        stage('Deploy') {
            steps {
                 script {
                    gv.deployApp()
                }
            }
        }
    }
}