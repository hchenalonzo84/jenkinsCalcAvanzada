pipeline {
  agent any
  tools { jdk 'jdk-21'; maven 'maven-3.9' }
  options { timestamps() }

  stages {
    stage('Checkout') { steps { checkout scm } }

    stage('Unit Tests + HTML Report') {
      steps {
        bat 'mvn -B -Dmaven.test.failure.ignore=true test surefire-report:report'
        bat """
        if exist target\\site\\surefire-report.html (
          \"C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe\" ^
            --headless=new --disable-gpu ^
            --print-to-pdf=\"%CD%\\target\\surefire-report.pdf\" ^
            \"%CD%\\target\\site\\surefire-report.html\"
        ) else (
          echo No se encontro target\\site\\surefire-report.html
        )
        """
      }
    }
  }

  post {
    always {
      junit 'target/surefire-reports/*.xml'
      publishHTML(target: [
        reportDir: 'target/site',
        reportFiles: 'surefire-report.html',
        reportName: 'Unit Test Report',
        keepAll: true,
        alwaysLinkToLastBuild: true,
        allowMissing: false
      ])
      archiveArtifacts artifacts: 'target/site/surefire-report.html', fingerprint: true
      archiveArtifacts artifacts: 'target/surefire-report.pdf', fingerprint: true
    }
  }
}
