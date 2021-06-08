FROM jenkins/jenkins:lts

USER root
RUN groupadd docker && \
    curl -fsSL https://get.docker.com -o get-docker.sh && \
    sh get-docker.sh && \
    usermod -aG root jenkins && \
    usermod -aG docker jenkins

USER jenkins

ENV CASC_JENKINS_CONFIG=/var/jenkins_home/casc \
    JAVA_OPTS="-Djenkins.install.runSetupWizard=false  -Dsun.net.inetaddr.ttl=0"

COPY casc/ /var/jenkins_home/casc
COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/plugins.txt
