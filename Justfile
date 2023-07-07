[private]
default:
    @just --list

# launch a jshell repl
repl:
    @rlwrap ./gradlew --console plain jshell

# build and run tests
test:
    @./gradlew build

# publish locally
publish-local:
    #ORG_GRADLE_PROJECT_signingKey=...
    #ORG_GRADLE_PROJECT_signingPassword=..
    @./gradlew publishToMavenLocal

# publish to maven central
publish:
    #https://oss.sonatype.org/
    #ORG_GRADLE_PROJECT_signingKey=...
    #ORG_GRADLE_PROJECT_signingPassword=..
    #OSSRH_USER=
    #OSSRH_PASSWORD=
    @./gradlew publishToSonatype closeAndReleaseSonatypeStagingRepository