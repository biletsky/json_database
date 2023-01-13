# JSON-Database
client-server application that allows clients to store data in JSON format.

#### Stack and topics covered:
* JSON (Gson)
* Sockets
* Multithreading (threads, executors, synchronization, shared data, locks)
* Collections framework (list, set and map interfaces)
* Design patterns (command, singleton)
* Reading and writing files
* Input and output streams
* JCommander
* Maven 

### Build
to build the project 

```shell
mvn clean package
```
### Run
first run the server

```shell
Server package/ Server.class
```

then run the client with the request args

```shell
-t set -k "some key" -v "some value"
```

or you can run the client with a file containing the request as json, for example `set.json` 
has the following content:

```json
{
  "type": "set",
  "key": "some key",
  "value": "some value"
}
```

the output should be something like

```
Sent: {
  "type": "set",
  "key": "some key",
  "value": "some value"
}
Received: {
  "response": "OK"
}
```

### Usage

| option            | description                            |
|:------------------|:---------------------------------------|
| -t, --type        | Type of the request (set, get, delete) |
| -k, --key         | Record key                             |
| -v, --value       | Value to add                           |
| -in, --input-file | File containing the request as json    |
