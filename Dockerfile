FROM openjdk:17-jdk-slim

EXPOSE 8080

ENV VAPID_PUBLIC_KEY=BINchN6o0ZBG65k_0s37VCSuFu_OangXrKwVKGJ045WISwyN0bRJ5qNC-7-0-XM1SnwtVcPQnWbCshZz8-eY2Gw
ENV VAPID_PRIVATE_KEY=KLalNZH5EK020yYj9FYTwUikrqx55sh-NCtgio2V1PQ

COPY target/web-push-0.0.1-SNAPSHOT.jar /web-push-1.0.jar

ENTRYPOINT ["java", "-jar", "/web-push-1.0.jar"]