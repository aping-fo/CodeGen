package com.road.oplog.test;

import com.road.oplog.bean.OpLogInfo;
import com.road.oplog.bean.OpHeader;
import com.road.oplog.bean.tx.TxOpPlayerRegister;
import com.road.oplog.mgr.OpLogMgr;

public class OpTest
{
    static OpLogInfo opLogInfo;

    static
    {
        opLogInfo = new OpLogInfo()
        {
            @Override
            public String getOpGameSvrId()
            {
                return "GAME_SVR_ID";
            }

            @Override
            public int getOpGameId()
            {
                return 1;
            }

            @Override
            public int getOpGameZoneId()
            {
                return 10001;
            }

            @Override
            public String getOpOpenId()
            {
                return "OPEN_ID";
            }

            @Override
            public long getOpRoleId()
            {
                return 10000000L;
            }

            @Override
            public String getOpNickName()
            {
                return "peter";
            }

            @Override
            public int getOpPlatId()
            {
                return 0;
            }
            
        };
    }

    public static void main(String[] args)
    {
        OpLogMgr.init();

        Runnable taskRun = new Runnable()
        {
            @Override
            public void run()
            {
                long t1 = System.currentTimeMillis();
                OpHeader register = new TxOpPlayerRegister(opLogInfo);
                for (int i = 0; i < 10000; i++)
                {
                    OpLogMgr.sendMsg(register);
                }
                long t2 = System.currentTimeMillis();
                System.err.println(t2 - t1);
            }
        };
        Thread testThread = new Thread(taskRun, "test sender thread");
        testThread.start();
    }
}
