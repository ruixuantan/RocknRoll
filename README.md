# Rock n Roll
![example workflow](https://github.com/ruixuantan/rocknroll/actions/workflows/ci.yml/badge.svg)

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

## Links
* [core](rocknroll-core/README.md)
* [server](rocknroll-server/README.md)
* [web](rocknroll-web/README.md)