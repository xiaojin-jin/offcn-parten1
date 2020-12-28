package com.offcn.project.service.impl;

import com.offcn.project.mapper.*;
import com.offcn.project.pojo.*;
import com.offcn.project.service.ProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectInfoServiceImpl implements ProjectInfoService {
    @Autowired
    private TReturnMapper returnMapper;
    @Autowired
    private TProjectMapper projectMapper;
    @Autowired
    private TTagMapper tTagMapper;
    @Autowired
    private TTypeMapper tTypeMapper;
    @Autowired
    private TProjectImagesMapper projectImagesMapper;

    /**
     * 获取项目信息
     *
     * @param projectId
     * @return
     */
    @Override
    public TProject findProjectInfo(Integer projectId) {
        TProject project = projectMapper.selectByPrimaryKey(projectId);
        return project;
    }
//获取项目标签
    @Override
    public List<TTag> findAllTag() {
        return tTagMapper.selectByExample(null);
    }
//获取项目分类
    @Override
    public List<TType> findAllType() {
        return tTypeMapper.selectByExample(null);
    }

    @Override
    public TReturn findReturnInfo(Integer returnId) {
        return returnMapper.selectByPrimaryKey(returnId);
    }

    /**
     * 获取系统中所有项目
     *
     * @return
     */
    @Override
    public List<TProject> findAllProject() {
        return projectMapper.selectByExample(null);
    }

    /**
     * 获取项目图片
     *
     * @param id
     * @return
     */
    @Override
    public List<TProjectImages> getProjectImages(Integer id) {
        TProjectImagesExample example = new TProjectImagesExample();
        example.createCriteria().andProjectidEqualTo(id);
        return projectImagesMapper.selectByExample(example);
    }
    /**
     * 获取项目回报列表
     *
     * @param projectId
     * @return
     */
    @Override
    public List<TReturn> getReturnList(Integer projectId) {
        TReturnExample example = new TReturnExample();
        example.createCriteria().andProjectidEqualTo(projectId);
        return returnMapper.selectByExample(example);
    }


}