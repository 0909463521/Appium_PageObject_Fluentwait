def deployApp()
{
    build job: 'App';
}
def CopyAppToAutomation()
{
    sh "cp -r ${JENKINS_HOME}/workspace/App/app/build/outputs/apk/debug/app-debug.apk ${WORKSPACE}/src/test/resources/app/appBach.apk"

    
}

def deployAutomationAppium()
{
    sh "mvn test -P BachVu"
}


return this