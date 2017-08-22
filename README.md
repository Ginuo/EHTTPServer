# EHTTPServer

以前实现的一个简单的Web服务器，重新整理了代码结构，修改了一些变量的命名，添加了简单的功能以及一个用于测试的Client

#基本结构

Enums：ContentType、StatusCode、RequestMethod
Classes：MyWebServer、RequestHandler、HttpRequest、HttpResponse、Client

#可以通过第一个命令行参数为服务器指定端口，默认端口为8080
#在浏览器地址栏输入“localhost:8080”将显示文件列表
