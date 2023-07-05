[private]
default:
    @just --list

repl:
    @rlwrap ./gradlew --console plain jshell

test:
    @./gradlew build

publish-local:
    #ORG_GRADLE_PROJECT_signingKey=...
    #ORG_GRADLE_PROJECT_signingPassword=..
    @./gradlew publishToMavenLocal

publish:
    #ORG_GRADLE_PROJECT_signingKey=...
    #ORG_GRADLE_PROJECT_signingPassword=..
    @./gradlew publishToSonatype closeAndReleaseSonatypeStagingRepository