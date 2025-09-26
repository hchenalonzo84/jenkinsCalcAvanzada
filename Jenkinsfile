pipeline {
  agent any

  tools {
    // Deben existir en Manage Jenkins → Tools con estos nombres
    jdk 'jdk-21'
    maven 'maven-3.9'
  }

  options { timestamps() }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    // Info rápida y robusta del entorno (una sola línea, sin tocar PATH manualmente)
    stage('Java & Maven info') {
      steps {
        sh 'set -euxo pipefail; echo "JAVA_HOME=${JAVA_HOME}"; which sh; which java; java -version; which mvn; mvn -v'
      }
    }

    stage('Unit Tests + HTML Report') {
      steps {
        // Ejecuta tests y genera el HTML con maven-surefire-report-plugin
        sh 'set -euxo pipefail; mvn -B -Dmaven.test.failure.ignore=true test surefire-report:report'

        // Opcional: generar PDF si tienes wkhtmltopdf en el agente
        // sh 'if [ -f target/site/surefire-report.html ]; then wkhtmltopdf target/site/surefire-report.html target/surefire-report.pdf; fi'
      }
    }
  }

  post {
    always {
      // Publica resultados JUnit aunque no existan (no falla el build por eso)
      junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'

      // Publica el reporte HTML (no fallar si falta)
      publishHTML(target: [
        reportDir: 'target/site',
        reportFiles: 'surefire-report.html',
        reportName: 'Unit Test Report',
        keepAll: true,
        alwaysLinkToLastBuild: true,
        allowMissing: true
      ])

      // Archiva artefactos (no fallar si faltan)
      archiveArtifacts artifacts: 'target/site/surefire-report.html', fingerprint: true, allowEmptyArchive: true
      archiveArtifacts artifacts: 'target/surefire-report.pdf', fingerprint: true, allowEmptyArchive: true
    }
  }
}
