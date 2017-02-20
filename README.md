create postgres docker image with initial data, and use with testcontainers
-------------------------------------------------------------------------------

## Abstraction

+ Create postgresql docker image with initial tables and data.
+ Use this image in JUnit with testcontainers.

## Useage

+ install docker.
+ `cd db_prepare`
+ `docker build . -t testdb:1.0.0`

`testdb:1.0.0` create table and recode from *.sql in db_prepare directory.
In this sample, this has 2 tables("PRODUCT" and "DIV"),
and username is postgres, password is "", database is postgres.

If change these, please chenge Dockerfile.

+ run docker quickstart (if windows) or docker-machine 
+ `gradle test`

+ If you run Junit in IDE, set following Docker env.
    + `DOCKER_HOST`
    + `DOCKER_MACHINE_NAME`
    + `DOCKER_TLS_VERIFY`
    + `DOCKER_CERT_PATH`

## Othres




# (Japansese translation)
 
## 概要

+ 初期データ投入済みのpostgresql Dockeイメージを構築する
+ 上記イメージを用いて、Junitと testcontainersを使用してDBテストを行う

## 手順

+ Docker のインストール
+ `cd db_prepare`
+ `docker build . -t testdb:1.0.0`

`testdb:1.0.0` は db_prepare フォルダにある *.sql ファイルからテーブルとデータを作成します。
このサンプルでは、DIVとPRODUCT という2つのテーブルを持ちます。

また、ユーザー名とデータベース名は postgres, パスワードは空です。
もしこれを変更する場合は、 Dockerfile を修正してください。

+ Docker quickstart を実行する(Windowsの場合)か、 docker-machineを起動する。
+ `gradle test`

+ もし、Junitを IDE から実行する場合、 以下のDocker関連の環境変数の設定が必要です。
    + `DOCKER_HOST`
    + `DOCKER_MACHINE_NAME`
    + `DOCKER_TLS_VERIFY`
    + `DOCKER_CERT_PATH`
