check-deps:
	./gradlew dependencyUpdates -Drevision=release

dev:
	./gradlew run
