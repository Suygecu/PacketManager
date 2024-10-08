plugins {
    id("java")
}

group = "com.suygecu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation ("mysql:mysql-connector-java:8.0.33")

}
tasks.register<JavaExec>("runClient") {
    mainClass.set("com.suygecu.client.Client")
    classpath = sourceSets.main.get().runtimeClasspath
}

tasks.register<JavaExec>("runServer") {
    mainClass.set("com.suygecu.server.Server")
    classpath = sourceSets.main.get().runtimeClasspath
}

tasks.test {
    useJUnitPlatform()
}