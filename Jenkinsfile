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
       maven 'Maven 3.1';
    }
    stages {
        stage('Prepare Environment') {
            steps {
                script {
                    gv = load "script.groovy"
                }
              sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''

                
            }
        }
        stage('Test') {
            steps {
                 script {
                    gv.deployApp()
                    gv.CopyAppToAutomation()
                    gv.deployAutomationAppium()
                    
                }
            }
            
        }
    }
}