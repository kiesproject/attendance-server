# Overview
AppTeam勤怠管理アプリのサーバサイド  
必要そうな情報をとりあえず並べました．読みにくいですがご了承下さい．

# Library version
I will upgrade Scala and Sbt versions later.
- Scala: 2.11.7
- sbt: 0.13.11
- Anorm: 2.5.2
- PostgreSQL: 9.4-1201-jdbc41

# Architecture
Play Framework + heroku + Scala + (Akka?)

# 実行方法
## Remote
gitのmasterブランチにプッシュするとherokuにデプロイさせます．  
デプロイが成功したらHTTPリクエスト投げて動作を確認してください．  
```
$ git push origin master
```

## Local
ローカル実行の際はPostgreSQLをインストールしておいてください．  
ここら辺のDBセットアップはシェルスクリプト書いた方が良いかも．  
### 手順
1. PostgreSQLのインストール
1. ローカルDBサーバ起動
1. DB作成 Table作成 Data挿入
1. playを実行
1. リクエスト投げる

#### 1. PostgreSQLのインストール
```
$ brew install postgresql
```

#### 2. DBサーバ起動
```
$ postgres -D /usr/local/var/postgres
```

#### 3. テーブル作成
忘れた

#### 4. 実行
```
$ sbt
> run
```

#### 5. HTTPリクエスト
localhostにHTTPリクエストする  
エンドポイントはルーティングを参照してください（リンク付けしてないです :sweat_smile:）

# Routing
レスポンスが正常に実装されていないので，エラーとサクセスのレスポンス載せてません 🙇‍♂️

## ルートエンドポイント
`https://kies-attendance.herokuapp.com/`

## ユーザー登録
`POST /register`
``` json
{
  "name" : "zumi",
  "password" : "hoge",
  "admin" : true
}
```

## ログイン
`POST /login`
``` json
{
  "name" : "zumi",
  "password" : "hoge"
}
```


