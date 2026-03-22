pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {url = uri("https://www.jitpack.io" ) }
    }
}

rootProject.name = "PetLink"
include(":app")
include(":core-ui")
include(":core-mvi")
include(":core-navigation")
include(":feature-profile-data")
include(":feature-profile-domain")
include(":feature-profile-ui-main")
include(":feature-profile-ui-edit")
include(":feature-profile-ui-settings")
include(":feature-profile-ui-create_account")
include(":feature-profile-ui-friends")
include(":feature-profile-ui-achievement")
include(":feature-map-ui-main")
include(":feature-community-ui-main")
include(":feature-calendar-data")
include(":feature-calendar-domain")
include(":feature-calendar-ui-add_event")
include(":feature-calendar-ui-calendar_view")
include(":feature-calendar-ui-edit_event")
include(":feature-calendar-ui-history")
include(":core-data")
include(":core-di")
include(":feature-profile-data-impl")
include(":feature-profile-domain-impl")
include(":feature-calendar-domain-impl")
include(":feature-calendar-data-impl")
include(":feature-calendar-ui-main")
include(":feature-community-data")
include(":feature-community-domain")
include(":feature-community-data-impl")
