package com.zxy.demo.config;

import com.zxy.demo.service.LikedServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;



public class LikeTask extends QuartzJobBean {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    LikedServiceImpl lsi;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        log.info("LikeTask-------- {}");

        //将 Redis 里的点赞信息同步到数据库里
        lsi.transLikesFromRedis2DB();
        lsi.transLikeCountFromRedis2DB();
    }
}
