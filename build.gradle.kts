// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
  val composeVersion by extra("1.0.0-beta06")
  val kotlinVersion by extra("1.4.32")
  repositories {
    google()
    mavenCentral()
  }
  dependencies {
    classpath("com.android.tools.build:gradle:7.0.0-alpha15")
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
  }
}

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
}