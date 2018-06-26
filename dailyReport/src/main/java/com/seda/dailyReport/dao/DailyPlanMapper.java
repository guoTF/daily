package com.seda.dailyReport.dao;

import com.seda.dailyReport.model.DailyPlan;
import com.seda.dailyReport.model.DailyPlanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DailyPlanMapper {
    long countByExample(DailyPlanExample example);

    int deleteByExample(DailyPlanExample example);

    int deleteByPrimaryKey(String id);

    int insert(DailyPlan record);

    int insertSelective(DailyPlan record);

    List<DailyPlan> selectByExample(DailyPlanExample example);

    DailyPlan selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DailyPlan record, @Param("example") DailyPlanExample example);

    int updateByExample(@Param("record") DailyPlan record, @Param("example") DailyPlanExample example);

    int updateByPrimaryKeySelective(DailyPlan record);

    int updateByPrimaryKey(DailyPlan record);
}