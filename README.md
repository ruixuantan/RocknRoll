# Rock n Roll
A die rolling application.

## Setting up
Prerequisites: Install
JDK 13,
scala 2.3.15,
sbt 1.5.3,
npm 7.14.0

### Server
```sh
sbt clean compile
sbt rocknrollServer/run
```

To test rocknroll-core,
```sh
sbt rocknrollCore/test
```

### Web
```sh
cd rocknroll-web
npm run start
```
To line rocknroll-web,
```shell
cd rocknroll-web
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