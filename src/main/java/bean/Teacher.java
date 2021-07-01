package bean;

import javafx.beans.property.SimpleStringProperty;

public class Teacher {
    /** ID（主键） **/
    private final SimpleStringProperty teaID;
    /** 姓名 **/
    private final SimpleStringProperty teaName;
    /** 性别**/
    private final SimpleStringProperty teaSex;
    /** 出生日期**/
    private final SimpleStringProperty teaBirth;
    /** 所在专业**/
    private final SimpleStringProperty teaMajor;

    public Teacher(String teaID,
                   String teaName,
                   String teaSex,
                   String teaBirth,
                   String teaMajor) {
        this.teaID = new SimpleStringProperty(teaID);
        this.teaName = new SimpleStringProperty(teaName);
        this.teaSex = new SimpleStringProperty(teaSex);
        this.teaBirth = new SimpleStringProperty(teaBirth);
        this.teaMajor = new SimpleStringProperty(teaMajor);
    }


    public String getTeaID() {
        return teaID.get();
    }

    public SimpleStringProperty teaIDProperty() {
        return teaID;
    }

    public void setTeaID(String teaID) {
        this.teaID.set(teaID);
    }

    public String getTeaName() {
        return teaName.get();
    }

    public SimpleStringProperty teaNameProperty() {
        return teaName;
    }

    public void setTeaName(String teaName) {
        this.teaName.set(teaName);
    }

    public String getTeaSex() {
        return teaSex.get();
    }

    public SimpleStringProperty teaSexProperty() {
        return teaSex;
    }

    public void setTeaSex(String teaSex) {
        this.teaSex.set(teaSex);
    }

    public String getTeaBirth() {
        return teaBirth.get();
    }

    public SimpleStringProperty teaBirthProperty() {
        return teaBirth;
    }

    public void setTeaBirth(String teaBirth) {
        this.teaBirth.set(teaBirth);
    }

    public String getTeaMajor() {
        return teaMajor.get();
    }

    public SimpleStringProperty teaMajorProperty() {
        return teaMajor;
    }

    public void setTeaMajor(String teaMajor) {
        this.teaMajor.set(teaMajor);
    }

    @Override
    public String toString() {
        return "teacher{" +
                "teaID=" + teaID +
                ", teaName=" + teaName +
                ", teaSex=" + teaSex +
                ", teaBirth=" + teaBirth +
                ", teaMajor=" + teaMajor +
                '}';
    }
}
