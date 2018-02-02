pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '20'))
        timestamps()
        timeout(time: 10, unit:'MINUTES')
    }

    triggers {
        pollSCM('* * * * *')
    }
    
    stages{
        stage('Build and Unit Test') {
            steps {
                parallel (
                    "JS and CSS" : {
                        sh './gradlew jshintjs jsdocjs csslint'
                    },
                    "Java Test Compile" : {
                        withEnv(["PATH+JAVA=${tool 'jdk8'}/bin"]) {
                            sh './gradlew testClasses'
                        }
                    },
                    "JS Unit Test" : {
                        sh 'npm install'
                        sh 'grunt'
                    }
                )
            }

            post {
                success {
                    publishHTML([
                        allowMissing: true,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'build/js/doc',
                        reportFiles: 'index.html',
                        reportName: 'JavaScript documentation',
                        reportTitles: 'JavaScript documentation'
                    ])

                    warnings canComputeNew: false, canResolveRelativePaths: false, categoriesPattern: '', defaultEncoding: '', excludePattern: '', healthy: '', includePattern: '', messagesPattern: '', parserConfigurations: [[parserName: 'CssLint', pattern: 'build/css/lint/csslint.xml'], [parserName: 'JSLint', pattern: 'build/js/hint/jshint.xml']], unHealthy: ''

                    junit 'build/js/TEST*.xml'
                }
            }
        }

        stage('Deploy to Acceptance Test Environment') {
            steps {
                sh 'echo "Copying web app to Acceptance Environment (/var/www-test)"'
                sh 'rsync -av --exclude=.svn web/*.html web/images web/js web/styles /var/www-test/'
                slackSend color:'good', message: "${env.HUDSON_URL} : ${env.BUILD_NUMBER} deployed to acceptance test (<${env.BUILD_URL}|Open>)"
            }
        }

        stage('Acceptance Test') {
            tools {
                jdk "jdk8"
            }
            steps {
                withEnv(['PATH+CHROMEHOME=/usr/lib/chromium-browser/']) {
                    wrap([$class: 'Xvfb']) {
                        sh './gradlew test -Dcricket.url=http://localhost:8081/calc.html'
                    }
                }
            }

            post {
                always {
                    publishHTML([
                        allowMissing: true,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'build/reports/spec',
                        reportFiles: 'CricketSpec.html',
                        reportName: 'Cricket Specification',
                        reportTitles: 'Cricket Specification'
                    ])
                    junit 'build/test-results/test/TEST-*.xml'
                }

                success {
                    slackSend color:'good', message: "${env.HUDSON_URL} : ${env.BUILD_NUMBER} acceptance test passed (<${env.BUILD_URL}|Open>)"
                }

                failure {
                    slackSend color:'danger', message: "${env.HUDSON_URL} : ${env.BUILD_NUMBER} acceptance test failed (<${env.BUILD_URL}|Open>)"
                }
            }
        }

        stage('Deploy to System Test Environment') {
            steps {
                timeout(time:5, unit:'DAYS') {
                    input 'Do you want to deploy to System Test?'
                }
                sh 'echo "Copying web app to Test Environment (/var/www)"'
                sh 'rsync -av --exclude=.svn web/*.html web/images web/js web/styles /var/www/'
                slackSend color:'good', message: "${env.HUDSON_URL} : ${env.BUILD_NUMBER} deployed to system test (<${env.BUILD_URL}|Open>)"
            }
        }
    }
}