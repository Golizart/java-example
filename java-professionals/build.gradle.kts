plugins {
    java
    id("io.spring.dependency-management") version "1.1.0"
    id("com.github.johnrengelman.shadow") version "7.1.2" apply false
    jacoco
}

group = "com.akurus.exaple.gradle"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

allprojects {
    repositories {
        mavenCentral()
    }
    apply(plugin = "io.spring.dependency-management")

    val versionsGuava: String by project
    val jUnit: String by project
    dependencyManagement {
        dependencies {
            dependency("com.google.guava:guava:$versionsGuava")
            dependency("org.junit.jupiter:junit-jupiter:$jUnit")
        }
    }
}

subprojects {
    apply(plugin = "jacoco")
    apply(plugin = "java")
    configure<org.gradle.api.plugins.internal.DefaultJavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

tasks.register<JacocoReport>("codeCoverageReport") {
    executionData(fileTree(project.rootDir.absolutePath).include("**/build/jacoco/*.exec"))
    subprojects {
        sourceSets(this.sourceSets.main.get())
        rootProject.tasks["codeCoverageReport"].dependsOn(this.tasks.test)
    }
    classDirectories.setFrom(
            files(classDirectories.files.map {
                fileTree(it) {
                    exclude("**/generated/*")
                }
            })
    )
}

tasks.test {
    finalizedBy(tasks["codeCoverageReport"]) // report is always generated after tests run
}

tasks.withType<Test> {
    useJUnitPlatform()
}

//./gradlew -q managedVersions
tasks.register("managedVersions") {
    doLast {
        dependencyManagement.managedVersions.forEach { (t, u) ->
            println("$t, $u")
        }
    }
}
abstract class GreetingTask : DefaultTask() {
    @TaskAction
    fun greet() {
        println("Hello world!")
    }
}

//./gradlew -q hello
tasks.register<GreetingTask>("hello")