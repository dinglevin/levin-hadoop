package org.levin.hadoop.hdfs.dirs;

import org.apache.hadoop.conf.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;

public class LevinFSDirectoryTest {
    @BeforeClass
    public static void setup() {
        System.setProperty("hadoop.home.dir", "/Users/dinglevin/local-lib/hadoop");
    }
    
    @Test
    public void testCreateOnly() throws Exception {
        LevinFSNamesystem namesystem = new LevinFSNamesystem("levin", "root");
        LevinFSDirectory dir = new LevinFSDirectory(namesystem, new Configuration());
        dir.getRootDir().dumpTreeRecursively(System.out);
    }
}
