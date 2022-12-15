FROM openjdk
COPY target/GradeManagement-0.0.1-SNAPSHOT.jar GradeManagement-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","GradeManagement-0.0.1-SNAPSHOT.jar"]