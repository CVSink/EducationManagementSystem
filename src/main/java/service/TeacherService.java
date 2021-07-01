package service;

import bean.Teacher;

import java.util.List;

public interface TeacherService {
    /**
     * 保存一个老师
     */
    public void save(Teacher tea);
    /**
     * 删除老师
     */
    public void delete(int tId);
    /**
     * 更新一个老师信息
     */
    public void update(int id,Teacher tea);
    /**
     * 获取指定老师
     */
    public Teacher get(int tId);
    /**
     * 获取所有的老师
     */
    public List<Teacher> getAll();
}
