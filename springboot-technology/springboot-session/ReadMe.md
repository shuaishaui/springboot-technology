## 1. 简介

多系统，单一位置登录，实现多系统同时登录的一种技术。 常出现在互联网应用和企业级平台中。 如：京东。 单点登录一般是用于互相授信的系统，实现单一位置登录，全系统有效的。
三方登录：某系统，使用其他系统的用户，实现本系统登录的方式。如，在京东中使用 微信登录。解决信息孤岛和用户不对等的实现方案。

## 2. Session 跨域

所谓 Session 跨域就是摒弃了系统（Tomcat）提供的 Session，而使用自定义的类似 Session 的机制来保存客户端数据的一种解决方案。 如：通过设置 cookie 的 domain 来实现 cookie 的跨域传递。在 cookie 中传递一个自定义 的 session_id。这个 session_id 是客户端的唯一标记。将这个标记作为 key，将客户端需要保 存的数据作为 value，在服务端进行保存（数据库保存或 NoSQL 保存）。这种机制就是 Session 的跨域解决。 
        什么是跨域： 客户端请求的时候，请求的服务器，不是同一个 IP，端口，域名，主机名 的时候，都称为跨域。 
         什么是域：在应用模型，一个完整的，有独立访问路径的功能集合称为一个域。如：百 度称为一个应用或系统。百度下有若干的域，如：搜索引擎（www.baidu.com），百度贴吧 （tie.baidu.com），百度知道（zhidao.baidu.com），百度地图（map.baidu.com）等。域信息， 有时也称为多级域名。域的划分： 以 IP，端口，域名，主机名为标准，实现划分。 localhost/127.0.0.1
        使用 cookie 跨域共享，是 session 跨域的一种解决方案。 jsessionid 是和 servlet 绑定的 httpsession 的唯一标记。
cookie 应用 -newCookie("",""). request.getCookies()->cookie[]-> 迭代找到需要使用的 cookie response.addCookie(). cookie.setDomain()- 为 cookie 设定有效域范围。 cookie.setPath()- 为 cookie 设定有效 URI 范围

## 3. SpringSession 共享 了解

​        spring-session 技术是 spring 提供的用于处理集群会话共享的解决方案。spring-session 技术是将用户 session 数据保存到三方存储容器中，如：mysql，redis 等。 Spring-session 技术是解决同域名下的多服务器集群 session 共享问题的。不能解决跨域 session 共享问题。