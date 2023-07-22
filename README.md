License: Apache+MIT

Example of using the Gradle Plugin ShadowJar to hide the internal dependencies of
the GCS (Google Cloud Storage) client jar so that they don't conflict with user code.

* gcs-hello-world, sample application (and library) that creates a file in a bucket.
* shaded-consumer, sample application that uses the library created above

This *example* code only. 

# Create the Library

```
cd gcs-hello-world
gradle publish
```

This will install into your local `.m2` Maven repo.

# Consume the Shaded Jar

```
cd shaded-consumer
# get app default credentials
gcloud auth application-default login
gradle run
```
