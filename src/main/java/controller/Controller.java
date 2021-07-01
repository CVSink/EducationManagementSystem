package controller;

import service.impl.*;
import bean.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    Tab tab_stu;
    @FXML
    Tab tab_course;
    @FXML
    Tab tab_score;
    @FXML
    Tab tab_teacher;
    @FXML
    TableView studentTableView;
    @FXML
    TableView courseTableView;
    @FXML
    TableView scoreTableView;
    @FXML
    TableView teacherTableView;
    @FXML
    TableColumn stuIDCol;
    @FXML
    TableColumn stuClassCol;
    @FXML
    TableColumn stuNameCol;
    @FXML
    TableColumn stuSexCol;
    @FXML
    TableColumn stuBirCol;
    @FXML
    TableColumn stuMajorCol;

    @FXML
    TableColumn cIdCol;
    @FXML
    TableColumn cMajorCol;
    @FXML
    TableColumn cNameCol;
    @FXML
    TableColumn cTypeCol;
    @FXML
    TableColumn cStartTremCol;
    @FXML
    TableColumn cPeriodCol;
    @FXML
    TableColumn cCreditCol;

    @FXML
    TableColumn score_sIdCol;
    @FXML
    TableColumn score_cIdCol;
    @FXML
    TableColumn scoreCol;
    @FXML
    TableColumn score_creditCol;

    @FXML
    TableColumn teaIDCol;
    @FXML
    TableColumn teaNameCol;
    @FXML
    TableColumn teaSexCol;
    @FXML
    TableColumn teaBirCol;
    @FXML
    TableColumn teaMajorCol;

    String adminID="0";

    /**
     * 添加学生
     */
    public void addStudent() {
        if(adminID.equals("0")) {
            alert("系统提示","请先登录",null, Alert.AlertType.ERROR);
            return;
        }
        Dialog<StudentResults> dialog = new Dialog<>();
        dialog.setTitle("添加学生");
        dialog.setHeaderText("输入要添加的学生信息：");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,60,10,10));

        TextField sID = new TextField();
        TextField sClass = new TextField();
        TextField sName = new TextField();
        TextField sSex = new TextField();
        TextField sBirth = new TextField();
        TextField sMajor = new TextField();

        grid.add(new Label("学号:"), 0, 0);
        grid.add(sID, 1, 0);
        grid.add(new Label("班级:"), 0, 1);
        grid.add(sClass, 1, 1);
        grid.add(new Label("姓名:"), 0, 2);
        grid.add(sName, 1, 2);
        grid.add(new Label("性别:"), 0, 3);
        grid.add(sSex, 1, 3);
        grid.add(new Label("出生日期:"), 0, 4);
        grid.add(sBirth, 1, 4);
        grid.add(new Label("所在学院:"), 0, 5);
        grid.add(sMajor, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new StudentResults(sID.getText(),sClass.getText(),
                        sName.getText(),sSex.getText(),
                        sBirth.getText(),sMajor.getText());
            }
            return null;
        });

        Optional<StudentResults> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((StudentResults results) -> {
            Student student = new StudentServiceImpl().get(Integer.parseInt(results.sID));
            if(student != null){
                alert("失败提示","学号为【" + results.sID + "】的学生数据已经存在，无法添加！",null, Alert.AlertType.ERROR);
            }else{
                // 保存信息到数据库
                new StudentServiceImpl().save(new Student(results.sID, results.sClass, results.sName,
                        results.sSex, results.sBirth, results.sMajor));

                alert("成功提示","成功保存学号为【" + results.sID + "】的学生数据！",null, Alert.AlertType.INFORMATION);
                refreshStuTable(); // 刷新界面
            }
        });
    }
    /**
     * 修改学生
     */
    public void changeStu(){
        if(adminID.equals("0")) {
            alert("系统提示","请先登录",null, Alert.AlertType.ERROR);
            return;
        }
        TextInputDialog d = new TextInputDialog();
        d.setTitle("修改学生信息");
        d.setHeaderText("输入要修改信息的学生学号：");
        d.setContentText("学号:");
        Optional<String> result = d.showAndWait();

        if(result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }

            Student student = new StudentServiceImpl().get(Integer.parseInt(result.get()));
            if(null != student){
                Dialog<StudentResults> dialog = new Dialog<>();
                dialog.setTitle("学生数据");
                dialog.setHeaderText(null);

                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getButtonTypes().addAll(ButtonType.OK);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20,60,10,10));

                TextField sID = new TextField(student.getStuID());
                TextField sClass = new TextField(student.getStuClass());
                TextField sName = new TextField(student.getStuName());
                TextField sSex = new TextField(student.getStuSex());
                TextField sBirth = new TextField(student.getStuBirth());
                TextField sMajor = new TextField(student.getStuMajor());

                grid.add(new Label("学号:"), 0, 0);
                grid.add(sID, 1, 0);
                grid.add(new Label("班级:"), 0, 1);
                grid.add(sClass, 1, 1);
                grid.add(new Label("姓名:"), 0, 2);
                grid.add(sName, 1, 2);
                grid.add(new Label("性别:"), 0, 3);
                grid.add(sSex, 1, 3);
                grid.add(new Label("出生日期:"), 0, 4);
                grid.add(sBirth, 1, 4);
                grid.add(new Label("所在专业:"), 0, 5);
                grid.add(sMajor, 1, 5);

                dialog.getDialogPane().setContent(grid);
                Optional<StudentResults> results = dialog.showAndWait();

                if(results.isPresent()){
                    Student stu = new Student(sID.getText(),sClass.getText(),
                            sName.getText(),sSex.getText(),
                            sBirth.getText(),sMajor.getText());
                    new StudentServiceImpl().update(Integer.parseInt(result.get()), stu);
                    alert("成功提示","成功修改学号为【" + student.getStuID() + "】的学生数据！",null, Alert.AlertType.INFORMATION);
                    refreshStuTable(); // 刷新界面
                }
            }else{
                alert("错误提示","没有该学生的记录，无法修改！",null, Alert.AlertType.ERROR);
            }
        }}

    /**
     * 删除学生
     */
    public void deleteStu(){
        if(adminID.equals("0")) {
            alert("系统提示","请先登录",null, Alert.AlertType.ERROR);
            return;
        }
        TextInputDialog d = new TextInputDialog();
        d.setTitle("删除学生");
        d.setHeaderText("输入要删除的学生学号：");
        d.setContentText("学号:");
        Optional<String> result = d.showAndWait();

        if (result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }
            Student student = new StudentServiceImpl().get(Integer.parseInt(result.get()));
            if(null != student){
                System.out.println("删除学号为" + student.getStuID() +"的数据");
                new StudentServiceImpl().delete(Integer.parseInt(student.getStuID()));
                alert("成功提示","成功删除学号为【" + student.getStuID() + "】的学生数据！",null, Alert.AlertType.INFORMATION);
                refreshStuTable();//刷新界面
            }else {
                alert("错误提示","没有该学生的记录，无法删除！",null, Alert.AlertType.ERROR);
            }
        }
    }

    /**
     * 查询学生
     */
    public void queryStu(){
        if(adminID.equals("0")) {
            alert("系统提示","请先登录",null, Alert.AlertType.ERROR);
            return;
        }

        TextInputDialog d = new TextInputDialog();
        d.setTitle("查询学生");
        d.setHeaderText("输入要查询的学生学号：");
        d.setContentText("学号:");
        Optional<String> result = d.showAndWait();

        if (result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }
            Student student = new StudentServiceImpl().get(Integer.parseInt(result.get()));
            if(null != student){
                Dialog<StudentResults> dialog = new Dialog<>();
                dialog.setTitle("学生数据");
                dialog.setHeaderText(null);

                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getButtonTypes().addAll(ButtonType.OK);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20,60,10,10));

                TextField sID = new TextField(student.getStuID());
                sID.setEditable(false);
                TextField sClass = new TextField(student.getStuClass());
                sClass.setEditable(false);
                TextField sName = new TextField(student.getStuName());
                sName.setEditable(false);
                TextField sSex = new TextField(student.getStuSex());
                sSex.setEditable(false);
                TextField sBirth = new TextField(student.getStuBirth());
                sBirth.setEditable(false);
                TextField sMajor = new TextField(student.getStuMajor());
                sMajor.setEditable(false);

                grid.add(new Label("学号:"), 0, 0);
                grid.add(sID, 1, 0);
                grid.add(new Label("班级:"), 0, 1);
                grid.add(sClass, 1, 1);
                grid.add(new Label("姓名:"), 0, 2);
                grid.add(sName, 1, 2);
                grid.add(new Label("性别:"), 0, 3);
                grid.add(sSex, 1, 3);
                grid.add(new Label("出生日期:"), 0, 4);
                grid.add(sBirth, 1, 4);
                grid.add(new Label("所在专业:"), 0, 5);
                grid.add(sMajor, 1, 5);

                dialog.getDialogPane().setContent(grid);
                dialog.showAndWait();
            }else {
                alert("错误提示","没有该学生的记录，无法查询！",null, Alert.AlertType.ERROR);
            }
        }
    }

    /**
     * 添加课程
     */
    public void addCourse(){
        if(adminID.equals("0")) {
            alert("系统提示","请先登录",null, Alert.AlertType.ERROR);
            return;
        }
        Dialog<CourseResults> dialog = new Dialog<>();
        dialog.setTitle("添加课程");
        dialog.setHeaderText("输入要添加的课程信息：");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,60,10,10));

        TextField cID = new TextField();
        TextField cMajor = new TextField();
        TextField cName = new TextField();
        TextField cType = new TextField();
        TextField cStartTerm = new TextField();
        TextField cPeriod = new TextField();
        TextField cCredit = new TextField();

        grid.add(new Label("课程号:"), 0, 0);
        grid.add(cID, 1, 0);
        grid.add(new Label("所属专业:"), 0, 1);
        grid.add(cMajor, 1, 1);
        grid.add(new Label("课程名称:"), 0, 2);
        grid.add(cName, 1, 2);
        grid.add(new Label("课程类型:"), 0, 3);
        grid.add(cType, 1, 3);
        grid.add(new Label("开课学期:"), 0, 4);
        grid.add(cStartTerm, 1, 4);
        grid.add(new Label("学时数:"), 0, 5);
        grid.add(cPeriod, 1, 5);
        grid.add(new Label("学分:"), 0, 6);
        grid.add(cCredit, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new CourseResults(cID.getText(),cMajor.getText(),
                        cName.getText(),cType.getText(),
                        cStartTerm.getText(),cPeriod.getText(), cCredit.getText());
            }
            return null;
        });
        Optional<CourseResults> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((CourseResults results) -> {
            Course course = new CourseServiceImpl().get(Integer.parseInt(results.cID));
            if(course != null){
                alert("错误提示","课程号为【" + results.cID + "】的数据已经存在，无法添加！",null, Alert.AlertType.INFORMATION);
            }else{
                // 保存信息到数据库
                new CourseServiceImpl().save(new Course(results.cID, results.cMajor, results.cName,
                        results.cType, results.cStartTerm, results.cPeriod, results.cCredit));

                alert("成功提示","成功保存课程号为【" + results.cID + "】的课程数据！",null, Alert.AlertType.ERROR);
                refreshCourseTable(); // 刷新界面
            }
        });
    }

    /**
     * 修改课程
     */
    public void changeCourse(){
        if(adminID.equals("0")) {
            alert("系统提示","请先登录",null, Alert.AlertType.ERROR);
            return;
        }

        TextInputDialog d = new TextInputDialog();
        d.setTitle("修改课程信息");
        d.setHeaderText("输入要修改信息的课程号：");
        d.setContentText("课程号:");
        Optional<String> result = d.showAndWait();

        if(result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }
            Course course = new CourseServiceImpl().get(Integer.parseInt(result.get()));
            if(null != course){
                Dialog<CourseResults> dialog = new Dialog<>();
                dialog.setTitle("课程数据");
                dialog.setHeaderText(null);

                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getButtonTypes().addAll(ButtonType.OK);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20,60,10,10));

                TextField cID = new TextField(course.getcID());
                TextField cMajor = new TextField(course.getcMajor());
                TextField cName = new TextField(course.getcName());
                TextField cType = new TextField(course.getcType());
                TextField cStartTerm = new TextField(course.getcStartTerm());
                TextField cPeriod = new TextField(course.getcPeriod());
                TextField cCredit = new TextField(course.getcCredit());

                grid.add(new Label("课程号:"), 0, 0);
                grid.add(cID, 1, 0);
                grid.add(new Label("所属专业:"), 0, 1);
                grid.add(cMajor, 1, 1);
                grid.add(new Label("课程名称:"), 0, 2);
                grid.add(cName, 1, 2);
                grid.add(new Label("课程类型:"), 0, 3);
                grid.add(cType, 1, 3);
                grid.add(new Label("开课学期:"), 0, 4);
                grid.add(cStartTerm, 1, 4);
                grid.add(new Label("学时数:"), 0, 5);
                grid.add(cPeriod, 1, 5);
                grid.add(new Label("学分:"), 0, 6);
                grid.add(cCredit, 1, 6);

                dialog.getDialogPane().setContent(grid);
                Optional<CourseResults> results = dialog.showAndWait();

                if(results.isPresent()){
                    course = new Course(cID.getText(),cMajor.getText(), cName.getText(),cType.getText(),
                            cStartTerm.getText(),cPeriod.getText(), cCredit.getText());

                    new CourseServiceImpl().update(Integer.parseInt(result.get()), course);
                    alert("成功提示","成功修改课程号为【" + course.getcID() + "】的课程数据！",null, Alert.AlertType.INFORMATION);
                    refreshCourseTable(); // 刷新界面
                }
            }else{
                alert("错误提示","没有该课程的记录，无法修改！",null, Alert.AlertType.ERROR);
            }
        }
    }

    /**
     * 删除课程
     */
    public void deleteCourse(){
        if(adminID.equals("0")) {
            alert("系统提示","请先登录",null, Alert.AlertType.ERROR);
            return;
        }

        TextInputDialog d = new TextInputDialog();
        d.setTitle("删除课程");
        d.setHeaderText("输入要删除的课程号：");
        d.setContentText("课程号:");
        Optional<String> result = d.showAndWait();

        if (result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }
            Course course = new CourseServiceImpl().get(Integer.parseInt(result.get()));
            if(null != course){
                System.out.println("删除课程号为" + course.getcID() +"的数据");
                new CourseServiceImpl().delete(Integer.parseInt(course.getcID()));
                alert("成功提示","成功删除课程号为【" + course.getcID() + "】的课程数据！",null, Alert.AlertType.INFORMATION);
                refreshCourseTable();//刷新界面
            }else {
                alert("错误提示","没有该课程的记录，无法删除！",null, Alert.AlertType.ERROR);
            }
        }
    }

    /**
     * 添加分数
     */
    public void addScore(){
        if(adminID.equals("0")) {
            alert("系统提示","请先登录",null, Alert.AlertType.ERROR);
            return;
        }
        Dialog<ScoreResults> dialog = new Dialog<>();
        dialog.setTitle("添加成绩");
        dialog.setHeaderText("输入学号、课程号和成绩：");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,60,10,10));

        TextField sID = new TextField();
        TextField cID = new TextField();
        TextField _score = new TextField();

        grid.add(new Label("学号:"), 0, 0);
        grid.add(sID, 1, 0);
        grid.add(new Label("课程号:"), 0, 1);
        grid.add(cID, 1, 1);
        grid.add(new Label("成绩:"), 0, 2);
        grid.add(_score, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new ScoreResults(sID.getText(),cID.getText(), _score.getText());
            }
            return null;
        });

        Optional<ScoreResults> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((ScoreResults results) -> {
            Score score = new ScoreServiceImpl().get(Integer.parseInt(results.stuID), Integer.parseInt(results.cID));
            Course course = new CourseServiceImpl().get(Integer.parseInt(results.cID));
            if(course == null){ // 课程不存在
                alert("失败提示", "该门课不存在，无法添加！", null, Alert.AlertType.ERROR);
                return;
            }
            if(score != null){ // 分数已经存在
                alert("失败提示","该学生的这门课已有成绩，无法添加！",null, Alert.AlertType.ERROR);
            }else{
                // 保存信息到数据库
                String gpa;
                if(Float.parseFloat(results.score)>=90)
                    gpa="4.0";
                else if(Float.parseFloat(results.score)<90&&Float.parseFloat(results.score)>=87)
                    gpa="3.7";
                else if(Float.parseFloat(results.score)<87&&Float.parseFloat(results.score)>=84)
                    gpa="3.3";
                else if(Float.parseFloat(results.score)<84&&Float.parseFloat(results.score)>=80)
                    gpa="3.0";
                else if(Float.parseFloat(results.score)<80&&Float.parseFloat(results.score)>=77)
                    gpa="2.7";
                else if(Float.parseFloat(results.score)<77&&Float.parseFloat(results.score)>=74)
                    gpa="2.3";
                else if(Float.parseFloat(results.score)<74&&Float.parseFloat(results.score)>=70)
                    gpa="2.0";
                else if(Float.parseFloat(results.score)<70&&Float.parseFloat(results.score)>=67)
                    gpa="1.7";
                else if(Float.parseFloat(results.score)<67&&Float.parseFloat(results.score)>=64)
                    gpa="1.3";
                else if(Float.parseFloat(results.score)<64&&Float.parseFloat(results.score)>=60)
                    gpa="1.0";
                else
                    gpa="0";
                new ScoreServiceImpl().save(Integer.parseInt(results.stuID), Integer.parseInt(results.cID),
                        new Score(results.stuID, results.cID, results.score, gpa));

                alert("成功提示","成功保存此门成绩！",null, Alert.AlertType.INFORMATION);
                refreshScoreTable(); // 刷新分数页面
            }
        });
    }

    /**
     * 删除分数
     */
    public void deleteScore(){
        if(adminID.equals("0")){
            alert("系统提示","请先登录",null, Alert.AlertType.ERROR);
            return;
        }

        Dialog<ScoreResults> dialog = new Dialog<>();
        dialog.setTitle("删除成绩");
        dialog.setHeaderText("输入学号，课程号：");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,60,10,10));

        TextField sID = new TextField();
        TextField cID = new TextField();

        grid.add(new Label("学号:"), 0, 0);
        grid.add(sID, 1, 0);
        grid.add(new Label("课程号:"), 0, 1);
        grid.add(cID, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new ScoreResults(sID.getText(),cID.getText());
            }
            return null;
        });

        Optional<ScoreResults> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((ScoreResults results) -> {
            Score score = new ScoreServiceImpl().get(Integer.parseInt(results.stuID), Integer.parseInt(results.cID));
            if(score == null){ // 分数已经存在
                alert("失败提示","没有这门课，无法删除！",null, Alert.AlertType.ERROR);
            }else{
                // 数据库删除信息
                new ScoreServiceImpl().delete(Integer.parseInt(results.stuID), Integer.parseInt(results.cID));
                alert("成功提示","成功删除这门成绩！",null, Alert.AlertType.INFORMATION);
                refreshScoreTable(); // 刷新分数页面
            }
        });
    }

    /**
     * 添加老师
     */
    public void addTeacher() {
        if(adminID.equals("0")) {
            alert("系统提示","请先登录",null, Alert.AlertType.ERROR);
            return;
        }
        Dialog<TeacherResults> dialog = new Dialog<>();
        dialog.setTitle("添加老师");
        dialog.setHeaderText("在下面输入要添加的老师信息：");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,60,10,10));

        TextField tID = new TextField();
        TextField tName = new TextField();
        TextField tSex = new TextField();
        TextField tBirth = new TextField();
        TextField tMajor = new TextField();

        grid.add(new Label("工号:"), 0, 0);
        grid.add(tID, 1, 0);
        grid.add(new Label("姓名:"), 0, 1);
        grid.add(tName, 1, 1);
        grid.add(new Label("性别:"), 0, 2);
        grid.add(tSex, 1, 2);
        grid.add(new Label("出生日期:"), 0, 3);
        grid.add(tBirth, 1, 3);
        grid.add(new Label("所在学院:"), 0, 4);
        grid.add(tMajor, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new TeacherResults(tID.getText(),
                        tName.getText(),tSex.getText(),
                        tBirth.getText(),tMajor.getText());
            }
            return null;
        });

        Optional<TeacherResults> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((TeacherResults results) -> {
            Teacher teacher = new TeacherServiceImpl().get(Integer.parseInt(results.tID));
            if(teacher != null){
                alert("失败提示","工号为【" + results.tID + "】的老师数据已经存在，无法添加！",null, Alert.AlertType.ERROR);
            }else{
                // 保存信息到数据库
                new TeacherServiceImpl().save(new Teacher(results.tID, results.tName,
                        results.tSex, results.tBirth, results.tMajor));

                alert("成功提示","成功保存工号为【" + results.tID + "】的老师数据！",null, Alert.AlertType.INFORMATION);
                refreshTeaTable(); // 刷新界面
            }
        });
    }

    /**
     * 修改老师
     */
    public void changeTea(){
        if(adminID.equals("0")) {
            alert("系统提示","请先登录",null, Alert.AlertType.ERROR);
            return;
        }
        TextInputDialog d = new TextInputDialog();
        d.setTitle("修改老师信息");
        d.setHeaderText("输入要修改信息的老师工号：");
        d.setContentText("工号:");
        Optional<String> result = d.showAndWait();

        if(result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }

            Teacher teacher = new TeacherServiceImpl().get(Integer.parseInt(result.get()));
            if(null != teacher){
                Dialog<TeacherResults> dialog = new Dialog<>();
                dialog.setTitle("老师数据");
                dialog.setHeaderText(null);

                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getButtonTypes().addAll(ButtonType.OK);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20,60,10,10));

                TextField tID = new TextField(teacher.getTeaID());
                TextField tName = new TextField(teacher.getTeaName());
                TextField tSex = new TextField(teacher.getTeaSex());
                TextField tBirth = new TextField(teacher.getTeaBirth());
                TextField tMajor = new TextField(teacher.getTeaMajor());

                grid.add(new Label("工号:"), 0, 0);
                grid.add(tID, 1, 0);
                grid.add(new Label("姓名:"), 0, 1);
                grid.add(tName, 1, 1);
                grid.add(new Label("性别:"), 0, 2);
                grid.add(tSex, 1, 2);
                grid.add(new Label("出生日期:"), 0, 3);
                grid.add(tBirth, 1, 3);
                grid.add(new Label("所在学院:"), 0, 4);
                grid.add(tMajor, 1, 4);

                dialog.getDialogPane().setContent(grid);
                Optional<TeacherResults> results = dialog.showAndWait();

                if(results.isPresent()){
                    Teacher tea = new Teacher(tID.getText(),
                            tName.getText(),tSex.getText(),
                            tBirth.getText(),tMajor.getText());
                    new TeacherServiceImpl().update(Integer.parseInt(result.get()), tea);
                    alert("成功提示","成功修改工号为【" + teacher.getTeaID() + "】的老师数据！",null, Alert.AlertType.INFORMATION);
                    refreshTeaTable(); // 刷新界面
                }
            }else{
                alert("错误提示","没有该老师的记录，无法修改！",null, Alert.AlertType.ERROR);
            }
        }}

    /**
     * 删除老师
     */
    public void deleteTea(){
        if(adminID.equals("0")) {
            alert("系统提示","请先登录",null, Alert.AlertType.ERROR);
            return;
        }
        TextInputDialog d = new TextInputDialog();
        d.setTitle("删除老师");
        d.setHeaderText("输入要删除的老师工号：");
        d.setContentText("工号:");
        Optional<String> result = d.showAndWait();

        if (result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }
            Teacher teacher = new TeacherServiceImpl().get(Integer.parseInt(result.get()));
            if(null != teacher){
                System.out.println("删除工号为" + teacher.getTeaID() +"的数据");
                new TeacherServiceImpl().delete(Integer.parseInt(teacher.getTeaID()));
                alert("成功提示","成功删除工号为【" + teacher.getTeaID() + "】的老师数据！",null, Alert.AlertType.INFORMATION);
                refreshTeaTable();//刷新界面
            }else {
                alert("错误提示","没有该老师的记录，无法删除！",null, Alert.AlertType.ERROR);
            }
        }
    }

    /**
     * 查询老师
     */
    public void queryTea(){
        if(adminID.equals("0")) {
            alert("系统提示","请先登录",null, Alert.AlertType.ERROR);
            return;
        }

        TextInputDialog d = new TextInputDialog();
        d.setTitle("查询老师");
        d.setHeaderText("输入要查询的老师工号：");
        d.setContentText("工号:");
        Optional<String> result = d.showAndWait();

        if (result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }
            Teacher teacher = new TeacherServiceImpl().get(Integer.parseInt(result.get()));
            if(null != teacher){
                Dialog<TeacherResults> dialog = new Dialog<>();
                dialog.setTitle("老师数据");
                dialog.setHeaderText(null);

                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getButtonTypes().addAll(ButtonType.OK);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20,60,10,10));

                TextField tID = new TextField(teacher.getTeaID());
                tID.setEditable(false);
                TextField tName = new TextField(teacher.getTeaName());
                tName.setEditable(false);
                TextField tSex = new TextField(teacher.getTeaSex());
                tSex.setEditable(false);
                TextField tBirth = new TextField(teacher.getTeaBirth());
                tBirth.setEditable(false);
                TextField tMajor = new TextField(teacher.getTeaMajor());
                tMajor.setEditable(false);

                grid.add(new Label("学号:"), 0, 0);
                grid.add(tID, 1, 0);
                grid.add(new Label("姓名:"), 0, 1);
                grid.add(tName, 1, 1);
                grid.add(new Label("性别:"), 0, 2);
                grid.add(tSex, 1, 2);
                grid.add(new Label("出生日期:"), 0, 3);
                grid.add(tBirth, 1, 3);
                grid.add(new Label("所在学院:"), 0, 4);
                grid.add(tMajor, 1, 4);

                dialog.getDialogPane().setContent(grid);
                dialog.showAndWait();
            }else {
                alert("错误提示","没有该老师的记录，无法查询！",null, Alert.AlertType.ERROR);
            }
        }
    }

    /**
     * 登录功能
     */
    public void login(){
        Dialog<LoginResults> dialog = new Dialog<>();
        dialog.setTitle("用户登录");
        dialog.setHeaderText("输入账号和密码：");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,60,10,10));

        TextField ID = new TextField();
        TextField password = new TextField();

        grid.add(new Label("账号:"), 0, 0);
        grid.add(ID, 1, 0);
        grid.add(new Label("密码:"), 0, 1);
        grid.add(password, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                adminID=ID.getText();
                System.out.println("尝试登录："+adminID);
                return new LoginResults(ID.getText(),password.getText());
            }
            return null;
        });
        Optional<LoginResults> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((LoginResults results) -> {
            Login login = new LoginServiceImpl().getUser(Integer.parseInt(results.stuID),Integer.parseInt(results.password));
            if(login == null){
                alert("错误提示","账号或密码错误！",null, Alert.AlertType.ERROR);
            }else{
                alert("成功提示","成功登录",null, Alert.AlertType.INFORMATION);
                refreshStuTable();  //刷新学生表
                refreshCourseTable(); // 刷新课程表
                refreshScoreTable();    //刷新成绩表
                refreshTeaTable();  //刷新老师表
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(adminID.equals("0")) {
            setTabVisible(tab_stu, studentTableView);
            setTabVisible(tab_course, courseTableView);
            setTabVisible(tab_score, scoreTableView);
            setTabVisible(tab_teacher,teacherTableView);
            return;
        }

        refreshStuTable(); // 默认显示学生表
        refreshCourseTable(); // 显示课程表
        refreshScoreTable();  // 显示学分表
        refreshTeaTable();  //显示老师表

        // 设置标签与表对应
        setTabVisible(tab_stu, studentTableView);
        setTabVisible(tab_course, courseTableView);
        setTabVisible(tab_score, scoreTableView);
        setTabVisible(tab_teacher,teacherTableView);
    }

    /**
     * 刷新学生表
     */
    private void refreshStuTable(){ // 刷新显示学生表
        stuIDCol.setCellValueFactory(new PropertyValueFactory<>("stuID"));
        stuClassCol.setCellValueFactory(new PropertyValueFactory<>("stuClass"));
        stuNameCol.setCellValueFactory(new PropertyValueFactory<>("stuName"));
        stuSexCol.setCellValueFactory(new PropertyValueFactory<>("stuSex"));
        stuBirCol.setCellValueFactory(new PropertyValueFactory<>("stuBirth"));
        stuMajorCol.setCellValueFactory(new PropertyValueFactory<>("stuMajor"));

        List<Student> students = new StudentServiceImpl().getAll();
        ObservableList<Student> data = FXCollections.observableArrayList();
        for (Student student : students) {
            data.add(student);
        }
        studentTableView.setItems(data);
    }
    /**
     * 刷新课程表
     */
    private void refreshCourseTable(){ // 刷新显示课程表
        cIdCol.setCellValueFactory(new PropertyValueFactory<>("cID"));
        cMajorCol.setCellValueFactory(new PropertyValueFactory<>("cMajor"));
        cNameCol.setCellValueFactory(new PropertyValueFactory<>("cName"));
        cTypeCol.setCellValueFactory(new PropertyValueFactory<>("cType"));
        cStartTremCol.setCellValueFactory(new PropertyValueFactory<>("cStartTerm"));
        cPeriodCol.setCellValueFactory(new PropertyValueFactory<>("cPeriod"));
        cCreditCol.setCellValueFactory(new PropertyValueFactory<>("cCredit"));

        List<Course> courses = new CourseServiceImpl().getAll();
        ObservableList<Course> data = FXCollections.observableArrayList();
        for (Course course : courses) {
            data.add(course);
        }
        courseTableView.setItems(data);
    }
    /**
     * 刷新成绩表
     */
    private void refreshScoreTable(){
        score_sIdCol.setCellValueFactory(new PropertyValueFactory<>("stuID"));
        score_cIdCol.setCellValueFactory(new PropertyValueFactory<>("cID"));
        score_creditCol.setCellValueFactory(new PropertyValueFactory<>("credit"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));

        List<Score> scores = new ScoreServiceImpl().getAll();
        ObservableList<Score> data = FXCollections.observableArrayList();
        for (Score score : scores) {
            data.add(score);
            System.out.println(score);
        }
        scoreTableView.setItems(data);
    }

    /**
     * 刷新老师表
     */
    private void refreshTeaTable(){ // 刷新显示学生表
        teaIDCol.setCellValueFactory(new PropertyValueFactory<>("teaID"));
        teaNameCol.setCellValueFactory(new PropertyValueFactory<>("teaName"));
        teaSexCol.setCellValueFactory(new PropertyValueFactory<>("teaSex"));
        teaBirCol.setCellValueFactory(new PropertyValueFactory<>("teaBirth"));
        teaMajorCol.setCellValueFactory(new PropertyValueFactory<>("teaMajor"));

        List<Teacher> teachers = new TeacherServiceImpl().getAll();
        ObservableList<Teacher> data = FXCollections.observableArrayList();
        for (Teacher teacher : teachers) {
            data.add(teacher);
        }
        teacherTableView.setItems(data);
    }

    /**
     * 检测ID合法
     */
    private boolean checkIdIllegal(String sID){
        if(sID.length() >= 10){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("输入的数据不合法！");
            alert.showAndWait();
            return true;
        }
        return false;
    }

    /**
     * 弹框
     */
    private void alert(String title, String content, String header, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private static class StudentResults {
        String sID;
        String sClass;
        String sName;
        String sSex;
        String sBirth;
        String sMajor;
        public StudentResults(String sID, String sClass, String sName, String sSex, String sBirth, String sMajor) {
            this.sID = sID;
            this.sClass = sClass;
            this.sName = sName;
            this.sSex = sSex;
            this.sBirth = sBirth;
            this.sMajor = sMajor;
        }
    }
    private static class CourseResults{
        private String cID;
        private String cMajor;
        private String cName;
        private String cType;
        private String cStartTerm;
        private String cPeriod;
        private String cCredit;

        public CourseResults(String cID, String cMajor, String cName, String cType, String cStartTerm, String cPeriod, String cCredit) {
            this.cID = cID;
            this.cMajor = cMajor;
            this.cName = cName;
            this.cType = cType;
            this.cStartTerm = cStartTerm;
            this.cPeriod = cPeriod;
            this.cCredit = cCredit;
        }
    }
    private static class ScoreResults{
        private String stuID;
        private String cID;
        private String score;

        public ScoreResults(String stuID, String cID) {
            this.stuID = stuID;
            this.cID = cID;
        }

        public ScoreResults(String stuID, String cID, String score) {
            this.stuID = stuID;
            this.cID = cID;
            this.score = score;
        }
    }
    private static class LoginResults{
        private String stuID;
        private String password;

        public LoginResults(String stuID,String password){
            this.stuID=stuID;
            this.password=password;
        }
    }
    private static class TeacherResults {
        String tID;
        String tName;
        String tSex;
        String tBirth;
        String tMajor;
        public TeacherResults(String tID, String tName, String tSex, String tBirth, String tMajor) {
            this.tID = tID;
            this.tName = tName;
            this.tSex = tSex;
            this.tBirth = tBirth;
            this.tMajor = tMajor;
        }
    }

    private interface Task {
        void execute();
    }
    private void setTabVisible(Tab tab, TableView tableView){
        setTabAction(tab, new Task() {
            @Override
            public void execute() {
                if(tableView.equals(studentTableView)){
                    courseTableView.setVisible(false);
                    studentTableView.setVisible(true);
                    scoreTableView.setVisible(false);
                    teacherTableView.setVisible(false);
                }
                else if(tableView.equals(courseTableView)){
                    studentTableView.setVisible(false);
                    courseTableView.setVisible(true);
                    scoreTableView.setVisible(false);
                    teacherTableView.setVisible(false);
                }
                else if(tableView.equals(scoreTableView)){
                    studentTableView.setVisible(false);
                    courseTableView.setVisible(false);
                    scoreTableView.setVisible(true);
                    teacherTableView.setVisible(false);
                }
                else if(tableView.equals(teacherTableView)){
                    studentTableView.setVisible(false);
                    courseTableView.setVisible(false);
                    scoreTableView.setVisible(false);
                    teacherTableView.setVisible(true);
                }
            }
        });
    }
    private void setTabAction(Tab tab, Task task) {
        tab.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                task.execute();
            }
        });
    }
}