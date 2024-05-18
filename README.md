# pterocli
CLI interface for Pterodactyl (Pelican) Panel game server orchestraction software.
Written in Kotlin and built with GraalVM

## Building
### Optional: Update native agent metadata

Refer to tests for environment variables needed to run them

#### Run tests against Pterodactyl panel and collect metadata

The following command will run tests with native image agent and collect various runtime metadata. 
Crucial for reflections, dynamic proxies etc.

```shell
./gradlew -Pagent test
```

#### Copy metadata to the resources

```shell
./gradlew metadataCopy --task test --dir src/main/resources/META-INF/native-image
```
### Build with native image

```shell
./gradlew nativeCompile
```
