def deployApp()
{
    build job: 'BUILD_ANDROID_APP_2';
}
def CopyAppToAutomation()
{
    sh "cp -r ${JENKINS_HOME}/workspace/BUILD_ANDROID_APP_2/app/build/outputs/apk/debug/app-debug.apk ${WORKSPACE}/src/test/resources/app/appBach.apk"

    
}

def deployAutomationAppium()
{
    sh "mvn test -P abc"
}


return this