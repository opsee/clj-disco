# clj-disco

clj-disco is a client library for interacting with registrator.

TODO:
Clean up all of the terrible assumptions we made to get this out the door quckly.
- Support for multiple back-ends
- Don't have magic constants like /opsee.co/registrator

## Installation

```clj
user=> (require '[clj-disco.core :as disco])
nil
#'user/disco
```

## Usage

clj-disco gets its configuration information from the environment:

`ETCD\_HOST` (default: 127.0.0.1) is the hostname or IP address of an etcd client listener.

`ETCD\_PORT` (default: 2379) is the port etcd is listening on for client connections.

```clj
user=> (disco/get-service-endpoint "test-service")
{:host "127.0.0.1", :port "1234"}
```

## How to run the tests

You will need a copy of Etcd running and to set your environment variables appropriately.

`lein midje` will run all tests.

`lein midje namespace.*` will run only tests beginning with "namespace.".

`lein midje :autotest` will run all the tests indefinitely. It sets up a
watcher on the code files. If they change, only the relevant tests will be
run again.

# LICENSE

See LICENSE.md
