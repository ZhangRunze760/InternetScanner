# 一个网络扫描mod，可以监测映射主机所映射的25565端口的ip访问、断开、连接等信息
# 须配合脚本使用
# 3465端口须开放，frp的3465端口须开放映射，协议为tcp和udp
# 非常适合用来防范狗日的、可恨的、无耻的、下流的ServerSeeker_net，Bunger等假人类服务器搜寻器
# 如果遇到盗号情况还可以直接查IP，一步到位
脚本：
```python
# coding:UTF-8
# Python 3.10.6

import os

old_ip = []
new_ip = []
flag = False

import socket
import json

HOST = 'localhost'
PORT = 3465
def send(message, flag):
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.connect((HOST, PORT))

        data = {'message': message, 'flag': flag}
        print(data)
        json_data = json.dumps(data)

        s.sendall(json_data.encode())

def catch():
    global flag
    while True:
        try:
            ip = os.popen("netstat -tn | grep ':25565 ' | grep 'ESTABLISHED'").read().split()[4]
        except:
            continue
        if ip == "" or ip in new_ip:
            continue
        new_ip.append(ip)
        for item in new_ip:
            if item in old_ip:
                new_ip.remove(item)
                flag = True
            else:
                old_ip.append(item)
                send(item, True)

        if flag:
            for item2 in new_ip:
                send(item, False)

catch()
```
