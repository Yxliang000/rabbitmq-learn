# rabbitmq-learn
RabbitMQ安装配置
### 下载 RabbitMQ
[官网](https://www.rabbitmq.com/install-rpm.html)下载 RabbitMQ 的 rpm 包，根据自己的系统选择对应的版本：
![download.jpg](https://upload-images.jianshu.io/upload_images/21875931-4bbb365cd595bb12.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
### 下载 Erlang
因为 RabbitMQ 是用 Erlang 语言开发的，所以还需要下载对应的 Erlang 依赖包：
###### RabbitMQ 与 Erlang 对应关系如下图：![RabbitMQ 与 Erlang对应关系.jpg](https://upload-images.jianshu.io/upload_images/21875931-7bfb508982ef119d.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

###### 选择对应版本点击下载：![erlang.jpg](https://upload-images.jianshu.io/upload_images/21875931-0dbb35c5dd5a31ff.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
### 安装
###### 将下载好的 rpm 包导入 Linux 系统目录中
安装 Erlang：```rpm -ivh erlang-23.0.4-1.el7.x86_64.rpm ```
安装 socat 依赖：```yum install socat```
安装RabbitMQ：```rpm -ivh rabbitmq-server-3.8.8-1.el7.noarch.rpm ```

- 启动服务：```systemctl start rabbitmq-server``` 或 ```service rabbitmq-server start```
- 关闭服务：```systemctl stop rabbitmq-server``` 或 ```service rabbitmq-server stop```
- 重启服务：```systemctl restart rabbitmq-server```
- 查看服务状态：```systemctl status rabbitmq-server``` 或 ```service rabbitmq-server status```


### 配置管理
RabbitMQ提供了一个 web 页面来对 RabbitMQ 进行管理、配置、查看信息，使用此页面需要先配置外部访问权限和安装插件。
###### 创建配置文件
RabbitMQ 默认会读取 /etc/rabbitmq 目录下的 rabbitmq.config 配置文件，但安装完成后，该目录下是空的。我们可以自己在该目录下创建这个文件：
```vim rabbitmq.config```
添加以下内容：
```[{rabbit, [{loopback_users, []}]}].```
rabbitmq默认创建的来宾用户guest，密码也是guest，这个用户默认只能是本机访问，从外部访问需要添加上面的配置。
###### 安装插件
使用 RabbitMQ 自带的插件管理命令 rabbitmq-plugins 启动插件
```rabbitmq-plugins enable rabbitmq_management```
###### 使用 guest 用户登录管理界面
RabbitMQ 管理界面的Http默认端口是15672，将该端口加入到防火墙开放端口中：
- 开放端口：```firewall-cmd --zone=public --add-port=15672/tcp --permanent```
- 重启防火墙使之生效firewall-cmd --reload

访问 ip:15672即出现如下登录界面，用户名密码都是 guest![登录界面.jpg](https://upload-images.jianshu.io/upload_images/21875931-8009b07cbd610431.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
登陆成功后就可以在此页面对RabbitMQ进行一些管理配置了：![RabbitMQ首页.jpg](https://upload-images.jianshu.io/upload_images/21875931-88f982b60495f715.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
在这个界面里面我们可以做些什么？
可以手动创建虚拟host，创建用户，分配权限，创建交换机，创建队列等等，还有查看队列消息，消费效率，推送效率等等。
除了使用配置页面进行配置外，也可以使用 RabbitMQ 的命令行 rabbitmqctl 进行管理设置，后面加具体的命令信息。
可使用 ```rabbitmqctl help``` 查看可操作的命令
如：
查看用户：```rabbitmqctl list_users```
