[private]
default:
    @just --list

repl:
    @rlwrap ./gradlew --console plain jshell

test:
    @./gradlew build