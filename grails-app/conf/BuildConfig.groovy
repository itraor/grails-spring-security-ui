//if(System.getenv('TRAVIS_BRANCH')) {
//    grails.project.repos.grailsCentral.username = System.getenv("GRAILS_CENTRAL_USERNAME")
//    grails.project.repos.grailsCentral.password = System.getenv("GRAILS_CENTRAL_PASSWORD")
//}

grails.project.work.dir = 'target'
grails.project.docs.output.dir = 'docs/manual' // for backwards-compatibility, the docs are checked into gh-pages branch

grails.project.dependency.resolver="maven"
grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		grailsCentral()
		mavenLocal()
		mavenCentral()
		mavenRepo 'http://download.java.net/maven/2/'
		mavenRepo 'http://repo.spring.io/milestone'
	}

	plugins {
		compile ':spring-security-core:2.0-RC4'
		compile ':mail:1.0.5'
		compile ':jquery:1.11.1'
		compile ':jquery-ui:1.10.3'
		compile ':famfamfam:1.0.1'
		compile ':spring-security-acl:2.0-RC2', {
			export = false
		}

		compile ":hibernate4:4.3.6.1", {
			export = false
		}

		build ':release:3.0.1', ':rest-client-builder:2.0.1', {
			export = false
		}
	}
}
