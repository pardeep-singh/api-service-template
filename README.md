# Clojure based REST API service template project

REST API based template project written in Clojure using Compojure and Components library.
This service exposes two endpoints, /ping and /hello. This template project can be extended by adding more routes and adding components for required dependencies.

Refer to [this](https://github.com/pardeep-singh/restro-search-engine) project as it uses Elasticsearch Component along with components used in this template project.
It exposes CRUD APIs using Elasticsearch.

## Prerequisites

You will need [Java](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html), [Leiningen](https://leiningen.org/)
to run this project.

## Running the API Service

### Using Lein
To start a API server, run below command:

```
lein run
```

This will start the server on Port 9099. Run below curl command to verify
```
curl http://localhost:9099/ping
```

### Using Java
Build the Jar using below command
```
lein uberjar
```
and then run the following command to start the API server
```
java -jar target/restro-search-engine-0.1.0-SNAPSHOT-standalone.jar
```
Use the curl command mentioned above to verify.

### Run tests
```
lein test :all
....
Ran 3 tests containing 5 assertions.
0 failures, 0 errors.```
