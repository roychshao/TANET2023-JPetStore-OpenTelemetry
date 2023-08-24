# TANET2023-JPetStore-OpenTelemetry(Automatic)

like AOP, it needs to run Jaeger and otelcol before start JPetStore.  
However, Automatic need to do more things

* before instrumentation, it need to build once to get tomcat
```
cd main
./mvnw clean package
./mvnw cargo:run -P tomcat90
```

and then use automatic instrumentation

```
cp opentelemetry-javaagent.jar setenv.sh ./target/cargo/installs/apache-tomcat-9.0.70/apache-tomcat-9.0.70/bin
cd ./target
cp jpetstore.war /cargo/installs/apache-tomcat-9.0.70/apache-tomcat-9.0.70/webapp
cd /cargo/installs/apache-tomcat-9.0.70/apache-tomcat-9.0.70/bin
chmod +x *
./startup.sh
```

these commands will add opentelemetry-javaagent.jar to tomcat and instrumentation the project  
to stop tomcat:
```
./shutdown.sh
```

* after doing these, it can be observed in both Jaeger UI and otelcol console
