pluginManagement {
	repositories {
		gradlePluginPortal()
		maven {
			name = "Fabric"
			url = uri("https://maven.fabricmc.net/")
		}
		maven {
			name = "Jitpack"
			url = uri("https://jitpack.io")
		}
		maven {
			name = "SignalumMavenInfrastructure"
			url = uri("https://maven.thesignalumproject.net/infrastructure")
		}
	}
}