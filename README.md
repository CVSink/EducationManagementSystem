# EducationManagementSystem
基于 JavaFX 的教务管理系统。包括学生管理、教师管理、成绩管理和课程管理。
## 如何使用
下面以 IDEA 为例
- clone 项目到本地用 IDEA 打开
- 在 Project Structure -> Libraries 引入以下 jar 包（所有所需的 jar 包都已打包在 jars 文件夹下）：
```
druid-1.1.2
javafx-swt
javafx.base
javafx.controls
javafx.fxml
javafx.graphics
javafx.media
javafx.swing
javafx.web
mysql-connector-java
```
- 导入数据库表（stums.sql）
- 修改 resources 下的数据库配置文件 db.properties （如果该配置文件没有自动生成，需要在 resources 下先自己手动创建）
```
driverClassName=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/stums?characterEncoding=utf8
username=你的连接用户名
password=你的数据库连接密码
maxActive=8
```
- 运行 stage 下 Launcher 类中的 main 方法（不是 Main 类里的 start 方法！）
- 进行上一步时可能会出现空指针异常，检查 target 文件夹下是否有 main.fxml ,如果没有，那么复制源文件中的 main.fxml 到 target/classes/stage 下（原因见下文 root 空指针异常）
- 这时再次运行 stage 下 Launcher 类中的 main 方法，大功告成。

## 可能会遇到的问题
### root 空指针异常
```
 Caused by: java.lang.NullPointerException: Location is required.
	at javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:3230)
	at javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:3194)
	at javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:3163)
	at javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:3136)
	at javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:3113)
	at javafx.fxml.FXMLLoader.load(FXMLLoader.java:3106)
	at stage.Main.start(Main.java:13)
 
 ```
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由于无法找到 fxml 文件导致的空指针异常，并且经过检查确定文件路径填写无误。引发该异常的原因是 IDEA 在编译时并不会把源文件的 fxml 文件复制到 target 文件夹，点开 target，会发现 fxml 文件缺失，这时只要把 fxml 文件复制到 target 文件夹就能正常运行。
 
 ## 其它工具
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果想对界面进行修改，需要使用 Scene Builder 编辑 main.fxml 文件。
 <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 Scene Builder下载 : https://www.oracle.com/java/technologies/javase/javafxscenebuilder-info.html
