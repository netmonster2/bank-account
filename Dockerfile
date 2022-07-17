FROM amazoncorretto:17.0.3

LABEL maintainer="Mahmoud Bouhdida<mahbouha@gmail.com>"

ENV SOURCE_FOLDER=${home_dir}/source_folder

COPY domain/src ${SOURCE_FOLDER}/domain/src
COPY domain/build.gradle ${SOURCE_FOLDER}/domain/build.gradle
COPY api/src ${SOURCE_FOLDER}/api/src
COPY api/build.gradle ${SOURCE_FOLDER}/api/build.gradle
COPY persistence/src ${SOURCE_FOLDER}/persistence/src
COPY persistence/build.gradle ${SOURCE_FOLDER}/persistence/build.gradle
COPY gradle ${SOURCE_FOLDER}/gradle
COPY build.gradle ${SOURCE_FOLDER}/build.gradle
COPY gradlew ${SOURCE_FOLDER}/gradlew
COPY settings.gradle ${SOURCE_FOLDER}/settings.gradle

WORKDIR ${SOURCE_FOLDER}

RUN ./gradlew :domain:publishToMavenLocal :persistence:publishToMavenLocal
RUN ./gradlew clean build

ENTRYPOINT ["./gradlew",":api:bootRun","--args='--spring.profiles.active=dev'"]