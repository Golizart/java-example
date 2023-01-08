version = rootProject.version
group = "com.akurus.exaple.gradle"

plugins {
    id("java")
    id("com.github.johnrengelman.shadow")

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


