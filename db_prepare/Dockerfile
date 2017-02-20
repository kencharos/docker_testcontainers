# テスト用初期データ投入済み pg コンテナを作る。
# cd db_prepare 
# docker build . -t testdb:1.0.0
# とすると、 testdb:1.0 で参照可能になる。
# 稼動確認方法
## docker run  test:1.0.0 -d pg # バックグラウンド起動
## docker run -it --rm test:1.0.0 -h 192.168.99.100 -U postgres # 接続
## docker stop testdb
## docker rm testdb
FROM postgres:9.6

# set utf-8
RUN localedef -i ja_JP -c -f UTF-8 -A /usr/share/locale/locale.alias ja_JP.UTF-8
ENV LANG ja_JP.UTF-8

# データの保存先を コンテナの中にしてしまう。
ENV PGDATA /pgdata

# POSTGRESS_XX 環境変数を設定すると初期設定時のDB,ユーザー、パスワードを指定できる。
# 未設定の場合、ユーザー、DBは postgress, パスワードはなし。
# ENV POSTGRESS_DB test
# ENV POSTGRESS_UER hoge
# ENV POSTGRES_PASSWORD hoge

EXPOSE 5432

# set initial sql
COPY *.sql /docker-entrypoint-initdb.d/

# ↑は起動時に初期データを作る仕組みなので、テストで何度も使いまわすには向かない。
# 次のスクリプトでそれをビルド時に強制的に動かす。
# ref http://gongo.hatenablog.com/entry/2015/08/04/232650
WORKDIR /sh
COPY restore.sh /sh/

RUN /sh/restore.sh
