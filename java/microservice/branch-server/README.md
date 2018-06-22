# README

This document describes the requirements to be able to compile and run the grpc-gateway, This is a reverse proxy that
allows the execution of grpc services using HTTP Restful from a browser using javascript.

## Installation Requirements for Mac OS

The following steps needs to be perform only once:

1. Install [homebrew](https://docs.brew.sh/Installation).
2. Install [go](https://golang.org/dl).
3. Install the following dependencies in go:
 - go get golang.org/x/net/context
 - go get google.golang.org/grpc
 - go get -u github.com/grpc-ecosystem/grpc-gateway/protoc-gen-grpc-gateway
 - go get -u github.com/grpc-ecosystem/grpc-gateway/protoc-gen-swagger
 - go get -u github.com/golang/protobuf/protoc-gen-go

## Usage

1. Define a <b>.proto</b> service. Once a proto has been defined the next step is to generate the stubs for <b>go</b> 
(this will be used by the grpc-gateway) as well as the stubs for the desired language in which the service will be 
implemented ([java](https://grpc.io/docs/tutorials/basic/java.html)).

2. To create the stubs for <b>go</b> run the following command:\
   ```
   protoc -I/usr/local/include -I. \
     -I$GOPATH/src \
     -I$GOPATH/src/github.com/grpc-ecosystem/grpc-gateway/third_party/googleapis \
     --go_out=plugins=grpc:. \
     path/to/your_service.proto
    ```
   If the command above fails as follow:\
   ```
   protoc-gen-go: program not found or is not executable
   --go_out: protoc-gen-go: Plugin failed with status code 1.
   ```
    Then we need to add the binary plugin path as follow:
    ```
    protoc -I/usr/local/include -I. \
         -I$GOPATH/src \
         -I$GOPATH/src/github.com/grpc-ecosystem/grpc-gateway/third_party/googleapis \
         --go_out=plugins=grpc:. \
         --plugin=protoc-gen-go=/Users/xxx/go/bin/protoc-gen-go \
         path/to/your_service.proto
    ```
3. The next step is to create the grpc gateway with the following command:\

