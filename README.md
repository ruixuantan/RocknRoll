# Rock n Roll
A die rolling application.

## Setting up
Prerequisites: Install
JDK 13,
scala 2.3.15,
sbt 1.5.3,
npm 7.14.0

### Compiling and Installing dependencies
```sh
./start.sh compile
```

### Starting the Server
```sh
./start.sh server
```

### Starting the Front End
```sh
./start.sh web
```

### Testing and Linting
```sh
./start.sh test
```

## Ports
| Services| Port Number |
| ------- | ----------- |
| Server  | 8080        |
| Postgres| 5432        |
| Web     | 4200        |


## Links
* [core](rocknroll-core/README.md)
* [server](rocknroll-server/README.md)
* [web](rocknroll-web/README.md)