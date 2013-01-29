package com.nhn.hippo.web.dao.hbase;

import com.profiler.common.dto.thrift.AgentInfo;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-web-applicationContext.xml")
public class HbaseAgentInfoDaoTest {

    @Autowired
    private HbaseAgentInfoDao selectDao;
    @Autowired
    private com.profiler.server.dao.hbase.HbaseAgentInfoDao insertDao;

    @Test
    public void testSelectAgentInfoStartTime() throws Exception {
        AgentInfo agentInfo1 = createAgentInfo(10000);
        insertDao.insert(agentInfo1);

        AgentInfo agentInfo2 = createAgentInfo(20000);
        insertDao.insert(agentInfo2);

        AgentInfo agentInfo3 = createAgentInfo(30000);
        insertDao.insert(agentInfo3);

        long testcaseAgent1 = selectDao.findAgentInfoBeforeStartTime("testcaseAgent", 20005);
        Assert.assertEquals(testcaseAgent1, 20000);

        long testcaseAgent2 = selectDao.findAgentInfoBeforeStartTime("testcaseAgent", 10004);
        Assert.assertEquals(testcaseAgent2, 10000);

        long testcaseAgent3 = selectDao.findAgentInfoBeforeStartTime("testcaseAgent", 50000);
        Assert.assertEquals(testcaseAgent3, 30000);


    }

    private AgentInfo createAgentInfo(long startTime) {
        AgentInfo agentInfo = new AgentInfo();
        agentInfo.setAgentId("testcaseAgent");
        agentInfo.setApplicationName("testcaseApplication");
        agentInfo.setHostname("testcaseHostName");
        agentInfo.setPorts("9995");
        agentInfo.setIsAlive(true);
        agentInfo.setTimestamp(startTime);
        return agentInfo;
    }
}
