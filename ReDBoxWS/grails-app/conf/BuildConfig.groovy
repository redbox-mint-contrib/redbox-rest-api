grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.fork = [
    // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
    //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

    // configure settings for the test-app JVM, uses the daemon by default
    test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
    // configure settings for the run-app JVM
    run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the run-war JVM
    war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the Console UI JVM
    console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "info" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()
        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.
        // runtime 'mysql:mysql-connector-java:5.1.24'
        //		org.grails:grails-datastore-core:2.0.6.RELEASE
        //		- org.grails:grails-datastore-gorm:2.0.6.RELEASE
        //		- org.springframework:spring-jdbc:3.2.5.RELEASE
        //		- org.springframework:spring-context:3.2.5.RELEASE
        //		- org.springframework:spring-beans:3.2.5.RELEASE
        //		- org.springframework:spring-aspects:3.2.5.RELEASE
        //		- org.codehaus.groovy:groovy-all:2.1.9
        //		- org.springframework:spring-tx:3.2.5.RELEASE
        //		- org.springframework:spring-expression:3.2.5.RELEASE
        //		- org.slf4j:jcl-over-slf4j:1.7.5
        //		- org.slf4j:slf4j-api:1.7.5
        //		- org.springframework:spring-web:3.2.5.RELEASE
        //		- org.springframework:spring-core:3.2.5.RELEASE
        //		- org.springframework:spring-context-support:3.2.5.RELEASE
        //		- org.springframework:spring-aop:3.2.5.RELEASE
        //		- org.aspectj:aspectjweaver:1.7.2
        //		- asm:asm:3.3.1
        //		- org.aspectj:aspectjrt:1.7.2
        //		- cglib:cglib:2.2.2
        //		- org.grails:grails-datastore-simple:2.0.6.RELEASE
        //		- org.springframework:spring-webmvc:3.2.5.RELEASE
        //		
	    String datastoreVersion = '2.0.6.RELEASE'		
		compile "org.grails:grails-datastore-core:$datastoreVersion",
				"org.grails:grails-datastore-gorm:$datastoreVersion",
				"org.grails:grails-datastore-gorm-hibernate:$datastoreVersion",
				"org.grails:grails-datastore-simple:$datastoreVersion"
		compile "org.springframework:spring-jdbc:3.2.5.RELEASE",
		        "org.springframework:spring-context:3.2.5.RELEASE",
				"org.springframework:spring-beans:3.2.5.RELEASE",
				"org.springframework:spring-aspects:3.2.5.RELEASE",
				"org.codehaus.groovy:groovy-all:2.1.9",
				"org.springframework:spring-tx:3.2.5.RELEASE",
				"org.springframework:spring-expression:3.2.5.RELEASE",
				"org.slf4j:slf4j-api:1.7.5",
				"org.springframework:spring-web:3.2.5.RELEASE",
				"org.springframework:spring-core:3.2.5.RELEASE",
				"org.springframework:spring-context-support:3.2.5.RELEASE",
				"org.springframework:spring-aop:3.2.5.RELEASE",
				"org.springframework:spring-webmvc:3.2.5.RELEASE"  		
    }

    plugins {
        // plugins for the build system only
        build ":tomcat:7.0.47"

        // plugins for the compile step
        compile ":scaffolding:2.0.1"
        compile ':cache:1.1.1'

        // plugins needed at runtime but not for compilation
        runtime ":hibernate:3.6.10.6" // or ":hibernate4:4.1.11.6"
        runtime ":database-migration:1.3.8"
        runtime ":jquery:1.10.2.2"
        runtime ":resources:1.2.1"
//		String datastoreVersion = '2.0.6.RELEASE'
//		
//				compile "org.grails:grails-datastore-core:$datastoreVersion",
//						"org.grails:grails-datastore-gorm:$datastoreVersion",
//						"org.grails:grails-datastore-gorm-hibernate:$datastoreVersion",
//						"org.grails:grails-datastore-simple:$datastoreVersion"				
		                
        // Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0.1"
        //runtime ":cached-resources:1.1"
        //runtime ":yui-minify-resources:0.1.5"
    }
}