pipeline {
  agent any

  // Jenkins añadirá estos tools al PATH automáticamente.
  tools { 
    jdk 'jdk-21'          // <-- sin modificar PATH a mano
    maven 'maven-3.9'     // <-- sin modificar PATH a mano
  }

  options { timestamps() }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    // === DIAGNÓSTICO: información de Java/Maven y PATH ===
    stage('Java & Maven info') {
      steps {
        // NOTA: Sin withEnv ni overrides de PATH. Evitamos el bug JENKINS-41339.
        sh '''
          set -e
          echo "JAVA_HOME=${JAVA_HOME}"
          echo "MAVEN_HOME=${MAVEN_HOME}"
          echo "PATH=${PATH}"
          which sh || true
          which java || true
          which mvn || true
          java -version
          mvn -v
        '''
      }
    }

    stage('Unit Tests + HTML Report') {
      steps {
        // Ejecuta tests y genera el reporte HTML de Surefire
        sh 'mvn -B -Dmaven.test.failure.ignore=true test surefire-report:report'

        // ⬇️ CAMBIO 1: generar sitio y PDF (requiere maven-pdf-plugin en el pom)
        sh '''
          set -e
          mvn -B site pdf:pdf
          # El pdf típico queda en target/site/pdf/site.pdf (según la config del pom)
          # Lo copiamos a un nombre estable para archivarlo fácil:
          if [ -f target/site/pdf/site.pdf ]; then
            cp target/site/pdf/site.pdf target/surefire-report.pdf
          fi
        '''
      }
    }
  }

  post {
    always {
      // Publica resultados JUnit
      junit 'target/surefire-reports/*.xml'

      // Publica el reporte HTML de Surefire
      publishHTML(target: [
        reportDir: 'target/site',
        reportFiles: 'surefire-report.html',
        reportName: 'Unit Test Report',
        keepAll: true,
        alwaysLinkToLastBuild: true,
        allowMissing: false
      ])

      // Archiva el HTML
      archiveArtifacts artifacts: 'target/site/surefire-report.html', fingerprint: true

      // ⬇️ CAMBIO 2: archivar el PDF si existe
      archiveArtifacts artifacts: 'target/surefire-report.pdf', fingerprint: true, allowEmptyArchive: true
    }
  }
}
