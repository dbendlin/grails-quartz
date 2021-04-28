if (System.getenv('TRAVIS_BRANCH')) {
    grails.project.repos.grailsCentral.username = System.getenv("GRAILS_CENTRAL_USERNAME")
    grails.project.repos.grailsCentral.password = System.getenv("GRAILS_CENTRAL_PASSWORD")
}

grails.project.work.dir = "target"

grails.project.dependency.resolution = {
    inherits "global"
    log "warn"

    repositories {

        mavenRepo "https://repo.grails.org/grails/core"

        mavenRepo "https://repo.grails.org/grails/plugins"
        //grailsHome()
        //grailsCentral()

        mavenRepo "https://repo1.maven.org/maven2"
        //mavenCentral()
    }

    dependencies {

        compile "commons-io:commons-io:2.8.0"

        compile("org.quartz-scheduler:quartz:2.2.1") {
            excludes 'slf4j-api', 'c3p0'
        }
        compile "org.grails:grails-docs:4.0.7"
    }

    plugins {
        build ':release:3.0.1', ':rest-client-builder:2.0.1', {
            export = false
        }
        
    }
}
