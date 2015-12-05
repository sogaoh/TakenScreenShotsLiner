# TakenScreenShotsLiner
スクリーンショットを撮って並べるツール

# 参考情報
- 環境設定
	- http://sogaoh.hatenablog.com/entry/2015/11/21/223131
- 機能概要
	- http://qiita.com/sogaoh/items/c5005f7c93a9d4b8e8c4

# 利用手順概要
- [未起動だったら] Selenium Server をバックグラウンドで起動
```
sudo su -
ps -ef | grep selenium
（java のプロセス :  seleniumのjar呼び出し  がなければ未起動）
nohup java -jar /opt/selenium-server-standalone-2.22.0.jar &
```
- スクリーンショット出力先・整列HTML出力先の定義 (ini)
	- sample: tssl.ini_sample
- ターゲットファイルの定義 (CSV)
	- sample: tssl.csv_sample
- スクリーンショット取得実行
```
cd {TakenScreenShostsLiner Dir}
python ./takeshots.py [./tssl.ini [./tssl.csv]]
```
- スクリーンショット整列HTML出力実行
```
groovy ./liner.groovy [-p ./tssl.ini] [-l ./tssl.csv] [-w 120] [-h 90] [-t JST]
```
- スクリーンショット整列HTMLを表示させてチェック


