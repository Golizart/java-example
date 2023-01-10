version = rootProject.version
group = "com.akurus.exaple.gradle"

plugins {
    id("com.github.johnrengelman.shadow")
}

dependencies {
    implementation("com.google.guava:guava")
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveBaseName.set("gradleHelloWorld")
        archiveVersion.set("0.1")
        archiveClassifier.set("")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "com.example.printreversarray.PrintReversArrayApplication"))
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}


