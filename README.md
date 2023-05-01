# 概要

JSONとファイルをmultipart/form-dataでフロントから受け取りBFFからBEに送信する例

## 実行

以下コマンドで実行

```
gradle bootRun
```

下記コマンドでpostの確認。

```
curl -i -X POST \
 -F "file=@./public/wordlist.txt" \
 -F "jsonValue={\"isUseDefaultWordList\": true};type=application/json" \
 http://localhost:9090/upload
```

## 備考

BEへのクライアント部の実装を行っています。
[BEへのクライアント](src/main/java/com/example/demo/bff/infrastructure/FileApi.java)
[BEへのクライアントのテスト](src/test/java/com/example/demo/bff/infrastructure/FileApiTest.java)
