package com.offcn.project.service;

import com.offcn.project.pojo.*;

import java.util.List;

public interface ProjectInfoService {

//    获取项目回报列表
     List<TReturn> getReturnList(Integer projectId);
     /**
      * 获取系统中所有项目
      * @return
      */
     List<TProject> findAllProject();

     /**
      * 获取项目图片
      * @param id
      * @return
      */
     List<TProjectImages> getProjectImages(Integer id);
     /**
      * 获取项目信息
      * @param projectId
      * @return
      */
     TProject findProjectInfo(Integer projectId);

     //获取项目标签
     List<TTag> findAllTag();

     //获取项目分类
     List<TType> findAllType();

     //项目回报
     TReturn findReturnInfo(Integer returnId);
}
