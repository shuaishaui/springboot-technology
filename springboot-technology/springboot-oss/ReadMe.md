## 1. 配置文件

在配置文件中填写好需要的配置参数，如下四个，分别对应自己在阿里云上面创建的OSS里面的参数

```java
aliyun.endpoint = zz
aliyun.accessKeyId = zzz
aliyun.accessKeySecret = zzz
aliyun.urlPrefix=zzz
aliyun.bucketName=zzzzz
```

## 2. 配置类

`config` --> `AliyunConfig`

在这里面，将OSS封装为bean，方便在各个地方引用

## 3. 服务

`service` --> `PicUploadService`

在这里面封装好服务和定义各个方法

## 4. 实战

`controller`  --> `PicUploadController`

这里面定义一个简单的controller类，进行OSS的调用，检验
