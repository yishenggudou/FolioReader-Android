
test:
	./gradlew build

install:
	./gradlew build install -i
	cp -vrf folioreader/build/outputs/aar/folioreader-release.aar /Users/timger/AndroidStudioProjects/ebooktemplate/app/libs/folioreader-release.aar
	#cp -vf folioreader/build/libs/folioreader-0.5.4-sources.jar /Users/timger/AndroidStudioProjects/ebooktemplate/app/libs/folioreader-0.5.4-sources.jar

jar:
	./gradlew sourceJar -i
