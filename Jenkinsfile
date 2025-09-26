pipeline {
  agent any

  tools {
    jdk   'jdk-21'
    maven 'maven-3.9'
  }

  options {
    timestamps()
  }

  stages {

    stage('Checkout') {
      steps {
        checkout scm
        sh 'git --version'
      }
    }

    stage('Unit Tests + HTML Report') {
      steps {
        // Ejecuta pruebas y genera el reporte HTML de Surefire
        sh 'mvn -B -Dmaven.test.failure.ignore=true test surefire-report:report'

        // Intento opcional de convertir el HTML a PDF si hay Chrome instalado en el agente
        // (en el contenedor oficial de Jenkins NO viene instalado, así que esto no falla si no está)
        sh '''
          set -eu
          HTML=target/site/surefire-report.html
          PDF=target/surefire-report.pdf

          if [ -f "$HTML" ]; then
            if command -v google-chrome >/dev/null 2>&1; then
              google-chrome --headless=new --disable-gpu --print-to-pdf="$PDF" "$HTML" || true
            elif command -v chrome >/dev/null 2>&1; then
              chrome --headless=new --disable-gpu --print-to-pdf="$PDF" "$HTML" || true
            else
              echo "Chrome no está instalado en este agente; omito la conversión a PDF."
            fi
          else
            echo "No se encontró $HTML"
          fi
        '''
      }
    }
  }

  post {
    always {
      // Publica resultados JUnit (soporta multimódulo)
      junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'

      // Publica el reporte HTML
      publishHTML(target: [
        reportDir: 'target/site',
        reportFiles: 'surefire-report.html',
        reportName: 'Unit Test Report',
        keepAll: true,
        alwaysLinkToLastBuild: true,
        allowMissing: false
      ])

      // Archiva artefactos (HTML siempre; PDF solo si existe)
      archiveArtifacts artifacts: 'target/site/surefire-report.html', fingerprint: true
      archiveArtifacts artifacts: 'target/surefire-report.pdf', fingerprint: true, allowEmptyArchive: true
    }
  }
}
