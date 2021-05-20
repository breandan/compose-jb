import org.gradle.api.publish.PublishingExtension

plugins {
    id("org.jetbrains.kotlin.multiplatform") version("1.4.32") apply(false)
}

val COMPOSE_WEB_VERSION: String by project

subprojects { 
    apply(plugin = "maven-publish")

    pluginManager.withPlugin("maven-publish") {
        configure<PublishingExtension> { 
            publications {
                create<MavenPublication>("maven") {
                    groupId = "org.jetbrains.compose.web"
                    version = COMPOSE_WEB_VERSION
                }
            }

            repositories {
                val COMPOSE_REPO_USERNAME: String? by project
                val COMPOSE_REPO_KEY: String? by project

                maven {
                    name = "internal"
                    url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev")
                    credentials {
                        username = COMPOSE_REPO_USERNAME ?: ""
                        password = COMPOSE_REPO_KEY ?: ""
                    }
                }
            }
        }
    }

    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven { 
            url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") 
        }
        maven {
            url = uri("https://packages.jetbrains.team/maven/p/ui/dev")
        }
    }
}
