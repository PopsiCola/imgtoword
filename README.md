# imgtoword
图片提取文字<br>
#基本思路：<br>
1.用户访问系统主页<br>
2.点击上传按钮，用户选择识别图片(单张上传，多张上传)<br>
3.后端进行图片识别，并将图片保存到七牛云中<br>
  3.1 识别调用百度智能云接口<br>
  3.2 七牛云服务器，免费10g存储空间<br>
4.识别完成，将提取出的文字保存到一个文件中。并将此文件也上传到七牛云。<br>
5.返回成功/失败信息。<br>
  5.1 成功信息：跳转到下载页面，供用户下载识别的文字。<br>
  5.2 失败信息：跳转失败页面。
