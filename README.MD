# HTTP Logging agent

The objective of this agent is to monitor outbound HTTP Request in Liferay.

For the moment it deals with 

- https://bitbucket.org/connect2id/oauth-2.0-sdk-with-openid-connect-extensions/src/release-11.13/src/main/java/com/nimbusds/oauth2/sdk/http/HTTPRequest.java
- https://bitbucket.org/connect2id/oauth-2.0-sdk-with-openid-connect-extensions/src/release-11.13/src/main/java/com/nimbusds/oauth2/sdk/http/HTTPResponse.java


## Installation instructions

Build using gradle

Start the application server with -javaagent:/path/to/http-logging-agent.jar

Agent's package and log4j's package have to be added to `module.framework.properties.org.osgi.framework.bootdelegation`

Example:

```
module.framework.properties.org.osgi.framework.bootdelegation=\
    __redirected,\
    com.liferay.expando.kernel.model,\
    com.liferay.portal.servlet.delegate,\
    com.liferay.portal.servlet.delegate*,\
    com.sun.ccpp,\
    com.sun.ccpp.*,\
    com.sun.crypto.*,\
    com.sun.image.*,\
    com.sun.imageio.plugins.*,\
    com.sun.jmx.*,\
    com.sun.jna,\
    com.sun.jndi.*,\
    com.sun.mail.*,\
    com.sun.management.*,\
    com.sun.media.*,\
    com.sun.msv.*,\
    com.sun.org.*,\
    com.sun.tools.*,\
    com.sun.xml.*,\
    com.yourkit.*,\
    jdk.*,\
    sun.*,\
    weblogic.jndi,\
    weblogic.jndi.*,\
    com.liferay.samples.fbo.http.logging.agent,\
    org.apache.logging.log4j
```

## Enable logging

Add an INFO `com.liferay.samples.fbo.http.logging.agent` logger
to the Liferay Server Administration.